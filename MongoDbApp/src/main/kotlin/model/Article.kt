package model

const val EMPTY_ID = ""

open class Article(public val id: String,
                   name: String,
                   blocks: List<Block>,
                   val timestamp: Long = System.currentTimeMillis()) {

    val articleHeader: HeaderBlock = HeaderBlock(text=name)
    val contentBlocks: MutableList<Block> = mutableListOf()

    init {
        for(b in blocks) {
            contentBlocks.add(b)
        }
    }

    override fun toString(): String {
        return "Article(contentBlocks=$contentBlocks, articleHeader=$articleHeader)"
    }
}

/* Default greetings article */
class HelloArticle : Article (EMPTY_ID, "Hello, ideas!",
    listOf(
        HeaderBlock("Hello world article", size = 3),
        TextBlock("In such difficult times"),
        TextBlock("We want to leave in peace and"),
        TextBlock("See the sunshine at the morning")
    )
)

/* Default error article */
class ErrorArticle(errText: String) : Article (EMPTY_ID, errText,
    listOf(
        TextBlock("=(")
    )
)

/* Default empty article */
class EmptyArticle(text: String) : Article (EMPTY_ID, text,
    listOf()
)