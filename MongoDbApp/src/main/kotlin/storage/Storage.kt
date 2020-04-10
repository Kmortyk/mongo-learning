package storage

import model.Article

enum class SortType {
    BY_NAME, BY_DATE
}

interface Storage {
    /* Article */
    fun putArticle(key: String, article: Article)
    fun getArticle(key: String) : Article
    fun getArticlesNames() : List<String>
    // fun removeArticle(key: String)
    // fun sortArticle(key: String, type: SortType)

    /* Image */
    // fun putImage(key: String, article: Article)
    // fun getImage(key: String)

}