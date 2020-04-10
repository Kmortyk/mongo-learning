import storage.MongoDbStorage
import tornadofx.App
import tornadofx.View
import tornadofx.hbox
import view.ArticleView
import view.ArticlesListView

class MongoDbView : View() {

    // main mongo db storage instance
    private val storage = MongoDbStorage()

    // current article view
    private val articleView = ArticleView()

    // selectable articles list
    private val articleList = ArticlesListView(articleView, storage)

    override val root = hbox {
        add(articleView)
        add(articleList)
    }
}

// options: --module-path /usr/share/openjfx/lib --add-modules=javafx.controls
class MongoDbApp :
    App(MongoDbView::class)