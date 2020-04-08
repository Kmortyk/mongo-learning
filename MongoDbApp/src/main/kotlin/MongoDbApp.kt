import javafx.collections.FXCollections
import javafx.scene.text.FontWeight
import tornadofx.*

import tornadofx.View
import tornadofx.hbox
import tornadofx.label
import view.ArticleView
import view.ArticlesListView
import java.time.LocalDate

class MongoDbView : View() {
    private val articleView = ArticleView()
    private val articleList = ArticlesListView(articleView)

    override val root = hbox {
        add(articleView)
        add(articleList)
    }
}

class MongoDbApp : App(MongoDbView::class)