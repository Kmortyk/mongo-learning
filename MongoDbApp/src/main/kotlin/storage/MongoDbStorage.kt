package storage

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import model.*
import org.bson.Document


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

    private val mongoClient : MongoClient
    private val database : MongoDatabase

    init {
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .retryWrites(true)
            .build()

        mongoClient = MongoClients.create(settings)
        database = mongoClient.getDatabase(DATABASE_NAME)
    }

    /* --- Debug ---------------------------------------------------------------------------------------------------- */

    public fun showDatabases() {
        mongoClient.listDatabaseNames().forEach(::println)
    }

    /* --- Implementation ------------------------------------------------------------------------------------------- */

    override fun putArticle(key: String, article: Article) {
        val col = database.getCollection(COL_ARTICLES)
        val doc = Document()
        val blocks = mutableListOf<Document>()

        for(b in article.contentBlocks) {
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
            blocks.add(block)
        }

        doc["name"] = article.articleHeader.text
        doc["blocks"] = blocks
        col.insertOne(doc)
    }

    override fun getArticle(key: String) : Article {
        val col = database.getCollection(COL_ARTICLES)
        val doc = col.find(eq("name", key)).first() ?:
                        return ErrorArticle("Article not found")

        val blocksDoc = doc["blocks"] as List<*>
        val blocks = mutableListOf<Block>()

        for(b in blocksDoc) {
            b as Document
            when(b["type"]) {
                "header" -> blocks.add(HeaderBlock(b["text"].toString()))
                "text" -> blocks.add(TextBlock(b["text"].toString()))
                "image" -> blocks.add(ImageBlock(b["src"].toString()))
            }
        }

        return Article(doc["name"].toString(), blocks)
    }

    override fun getArticlesNames(): List<String> {
        return database.getCollection(COL_ARTICLES)
                       .distinct("name", String::class.java)
                       .toList()
    }
}