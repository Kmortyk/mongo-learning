package model

open class Article(name: String, val contentBlocks: List<Block> = listOf()) {
    val articleName: HeaderBlock = HeaderBlock(name)
    val timestamp: Long = System.currentTimeMillis()
}

/* Default greetings article */
class HelloArticle: Article (
    "Hello, ideas!",
    listOf(
        HeaderBlock("Hello world article", size = 3),
        TextBlock("In such hard time"),
        TextBlock("We want to leave in peace and"),
        TextBlock("See the sunshine at the morning")
    )
)