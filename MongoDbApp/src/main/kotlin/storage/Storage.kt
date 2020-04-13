package storage

import model.Article
import model.Block

enum class SortType {
    BY_NAME, BY_DATE
}

interface Storage {

    /* Article */
    fun getArticleByName(name: String) : Article
    fun removeArticleByName(name: String)
    fun putArticle(article: Article)

    /* Articles */
    fun getArticleItems(type: SortType = SortType.BY_NAME) : List<ArticleListItem>

    /* Block */
    fun addBlock   (id: String, block: Block, position: Int = -1) : String // -> blockId
    fun updateBlock(id: String, block: Block)
    fun removeBlock(id: String, block: Block)

    /* Image */
    // fun putImage(key: String, article: Article)
    // fun getImage(key: String)
}