package storage

import java.sql.Date
import java.text.SimpleDateFormat

data class ArticleListItem(var name: String, val timestamp: Long) {

    public val date: String

    init {
        val sf = SimpleDateFormat("MM/dd/yyyy")
        date = sf.format(Date(timestamp))
    }
}