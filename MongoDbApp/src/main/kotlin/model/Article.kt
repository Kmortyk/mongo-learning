package model

import java.sql.Timestamp

open class Article(name: String,
                   val contentBlocks: List<Block> = listOf(),
                   val timestamp: Long = System.currentTimeMillis()) {

    val articleHeader: HeaderBlock = HeaderBlock(name)

    override fun toString(): String {
        return "Article(contentBlocks=$contentBlocks, articleHeader=$articleHeader)"
    }
}

/* Default greetings article */
class HelloArticle : Article (
    "Hello, ideas!",
    listOf(
        HeaderBlock("Hello world article", size = 3),
        TextBlock("In such difficult times"),
        TextBlock("We want to leave in peace and"),
        TextBlock("See the sunshine at the morning")
    )
)

/* Default error article */
class ErrorArticle(errText: String) : Article (
    errText,
    listOf(
        TextBlock("=(")
    )
)

/* Default empty article */
class EmptyArticle(text: String) : Article (
    text,
    listOf()
)