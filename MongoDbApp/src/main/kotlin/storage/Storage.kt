package storage

import model.Article
import model.Block

enum class SortType {
    BY_NAME, BY_DATE
}

interface Storage {
    /* Article */
    fun putArticle(key: String, article: Article)
    fun removeArticle(key: String)

    fun getArticle(key: String) : Article
    fun getArticlesNames(type: SortType = SortType.BY_NAME) : List<String>

    fun addBlock(key: String, block: Block) : String
    fun removeBlock(key: String, block: Block)

    /* Image */
    // fun putImage(key: String, article: Article)
    // fun getImage(key: String)
}