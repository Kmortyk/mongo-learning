package storage

import com.mongodb.BasicDBObject
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Indexes
import com.mongodb.client.model.Updates
import model.*
import org.bson.Document
import org.bson.types.ObjectId
import java.util.*


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

    /* --- Implementation ------------------------------------------------------------------------------------------- */

    override fun putArticle(article: Article) {
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

    override fun removeArticleByName(name: String) {
        db.getCollection(COL_ARTICLES)
            .deleteOne(eq("name", name))
    }

    override fun getArticleByName(name: String) : Article {
        val doc = db.getCollection(COL_ARTICLES)
                    .find(eq("name", name))
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

        return Article(doc["_id"].toString(), doc["name"].toString(), blocks, t)
    }

    override fun getArticleItems(type: SortType): List<ArticleListItem> {
        val field = when(type) {
            SortType.BY_DATE -> Indexes.descending("timestamp")
            SortType.BY_NAME -> Indexes.ascending("name")
        }

        val docs: List<Document> = db.getCollection(COL_ARTICLES)
            .find()
            .sort(field)
            .into(ArrayList())

        val names = mutableListOf<ArticleListItem>()
        for(doc in docs) {
            names.add(
                ArticleListItem(
                    doc["name"].toString(), // wat
                    doc["timestamp"].toString().toLong()
                )
            )
        }
        return names
    }

    override fun addBlock(id: String, block: Block) : String {
        val doc = blockDocument(block)
        // add one element to array
        db.getCollection(COL_ARTICLES)
            .updateOne(
                eq("_id", ObjectId(id)),
                Updates.addToSet("blocks", doc)
            )
        return doc["_id"].toString()
    }

    override fun removeBlock(id: String, block: Block) {
        val filter = Document("_id", ObjectId(id))
        val update = Document("\$pull", Document("blocks", Document("_id", ObjectId(block.id))))

        db.getCollection(COL_ARTICLES).updateOne(filter, update)
    }

    override fun updateBlock(id: String, block: Block) {
        if(id.isEmpty())
            return

        val query = BasicDBObject()
        query["blocks._id"] = ObjectId(block.id)
        query["_id"] = ObjectId(id)

        val data = BasicDBObject()
        when (block) {
            is HeaderBlock -> {
                data["blocks.\$.type"] = "header"
                data["blocks.\$.text"] = block.text
            }
            is TextBlock -> {
                data["blocks.\$.type"] = "text"
                data["blocks.\$.text"] = block.text
            }
            is ImageBlock -> {
                data["blocks.\$.type"] = "image"
                data["blocks.\$.src"] = block.src
            }
        }

        val command = BasicDBObject()
        command["\$set"] = data

        db.getCollection(COL_ARTICLES)
            .updateOne(query, command)
    }
}