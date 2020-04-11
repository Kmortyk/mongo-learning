package storage

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import model.*
import org.bson.Document
import org.bson.types.ObjectId


class MongoDbStorage : Storage {

    companion object {
        const val DATABASE_NAME = "mongo_java_app"
        const val USERNAME = "kmortyk2"
        const val PASSWORD = "qivYG3uFrI"
        const val HOST = "127.0.0.1"
        const val COL_ARTICLES = "articles"
    }

    private val connString = ConnectionString(
        "mongodb://$USERNAME:$PASSWORD@$HOST:27017/$DATABASE_NAME"
    )

    private val client : MongoClient
    private val db : MongoDatabase

    init {
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .retryWrites(true)
            .build()

        client = MongoClients.create(settings)
        db = client.getDatabase(DATABASE_NAME)
    }

    private fun blockDocument(b: Block) : Document {
        val block = Document()
        when (b) {
            is HeaderBlock -> {
                block["type"] = "header"
                block["text"] = b.text
            }
            is TextBlock -> {
                block["type"] = "text"
                block["text"] = b.text
            }
            is ImageBlock -> {
                block["type"] = "image"
                block["src"] = b.src
            }
        }
        // generate unique id for block
        block["_id"] = ObjectId()
        return block
    }

    /* fixme --- debug ---------------------------------------------------------------------------------------------- */

    public fun showDatabases() {
        client.listDatabaseNames().forEach(::println)
    }

    /* --- Implementation ------------------------------------------------------------------------------------------- */

    override fun putArticle(key: String, article: Article) {
        val col = db.getCollection(COL_ARTICLES)
        val doc = Document()
        val blocks = mutableListOf<Document>()

        for(b in article.contentBlocks)
            blocks.add(blockDocument(b))

        doc["name"] = article.articleHeader.text
        doc["timestamp"] = article.timestamp
        doc["blocks"] = blocks
        col.insertOne(doc)
    }

    override fun removeArticle(key: String) {
        db.getCollection(COL_ARTICLES)
            .deleteOne(eq("name", key))
    }

    override fun getArticle(key: String) : Article {
        val doc = db.getCollection(COL_ARTICLES)
                    .find(eq("name", key))
                    .first() ?: return ErrorArticle("Article not found")

        val blocksDoc = doc["blocks"] as List<*>
        val blocks = mutableListOf<Block>()

        for(b in blocksDoc) {
            b as Document
            when(b["type"]) {
                "header" -> blocks.add(HeaderBlock(_id="${b["_id"]}", text="${b["text"]}"))
                "text" -> blocks.add(TextBlock(_id="${b["_id"]}", text="${b["text"]}"))
                "image" -> blocks.add(ImageBlock(_id="${b["_id"]}", src="${b["src"]}"))
            }
        }

        // timestamp string
        val tStr = doc["timestamp"].toString()
        // conversion from scientific notation
        val t = if(tStr == "null") { System.currentTimeMillis() }
                else { tStr.toDouble().toLong() }

        return Article(doc["name"].toString(), blocks, t)
    }

    override fun getArticlesNames(type: SortType): List<String> {
        return db.getCollection(COL_ARTICLES)
                       .distinct("name", String::class.java)
                       .toList()
    }

    override fun addBlock(key: String, block: Block) : String {
        val doc = blockDocument(block)
        // add one element to array
        val res = db.getCollection(COL_ARTICLES)
            .updateOne(
                eq("name", key),
                Updates.addToSet("blocks", doc)
            )
        return res.upsertedId?.toString() ?: ""
    }

    override fun removeBlock(key: String, block: Block) {
        db.getCollection(COL_ARTICLES)
            .updateOne(
                eq("name", key),
                Updates.pullByFilter(eq("_id", block.id))
            )
    }
}