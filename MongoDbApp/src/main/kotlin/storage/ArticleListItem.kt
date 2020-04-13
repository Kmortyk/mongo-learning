package storage

import java.sql.Date
import java.text.SimpleDateFormat

data class ArticleListItem(val name: String, val timestamp: Long) {

    public val date: String

    init {
        val sf = SimpleDateFormat("yyyy-MM-dd")
        date = sf.format(Date(timestamp))
    }
}