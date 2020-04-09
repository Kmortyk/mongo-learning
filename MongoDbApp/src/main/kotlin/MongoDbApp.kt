import storage.MongoDbStorage
import tornadofx.*
import view.*

class MongoDbView : View() {

    // main mongo db storage instance
    private val storage = MongoDbStorage()

    // current article view
    private val articleView = ArticleView()

    // selectable articles list
    private val articleList = ArticlesListView(
        articleView,
        storage.getArticlesNames()
    )

    override val root = hbox {
        add(articleView)
        add(articleList)
    }
}

class MongoDbApp :
    App(MongoDbView::class)