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
    override val root = hbox {
        add(ArticleView::class)
        add(ArticlesListView::class)
    }
}

class MongoDbApp : App(MongoDbView::class)