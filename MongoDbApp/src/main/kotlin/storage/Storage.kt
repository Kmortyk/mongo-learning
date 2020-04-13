package storage

import model.Article
import model.Block

enum class SortType {
    BY_NAME, BY_DATE
}

interface Storage {

    /* Article */
    fun getArticleByName(name: String) : Article
    fun putArticle(article: Article)
    fun removeArticle(id: String)

    /* Articles */
    fun getArticlesNames(type: SortType = SortType.BY_NAME) : List<String>

    /* Block */
    fun addBlock   (id: String, block: Block) : String // -> blockId
    fun updateBlock(id: String, block: Block)
    fun removeBlock(id: String, block: Block)

    /* Image */
    // fun putImage(key: String, article: Article)
    // fun getImage(key: String)
}