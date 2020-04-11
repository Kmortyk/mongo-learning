import javafx.scene.control.DatePicker
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import storage.MongoDbStorage
import tornadofx.*
import view.ArticleView
import view.ArticlesListView
import java.io.File


class MongoDbView : View() {

    // main mongo db storage instance
    private val storage = MongoDbStorage()

    // current article view
    private val articleView = ArticleView()

    // selectable articles list
    private val articleList = ArticlesListView(articleView, storage)

    override val root = hbox {

        val imh = Image("header.png", 16.0, 16.0, false, true)
        val imt = Image("text.png",16.0, 16.0, false, true)
        val imi = Image("image.png",16.0, 16.0, false, true)

        // left view
        vbox {
            menubar {
                style {
                    paddingTop = 1
                    paddingBottom = 3
                }
                menu("", ImageView(imh))
                menu("", ImageView(imt))
                menu("", ImageView(imi))
            }
            add(articleView)
        }

        // right view
        vbox {
            hbox {
                style{
                    paddingLeft = 14
                    paddingTop = 3
                    paddingBottom = 3
                }
                datepicker()
            }
            add(articleList)
        }
    }
}

// options: --module-path /usr/share/openjfx/lib --add-modules=javafx.controls
class MongoDbApp : App(MongoDbView::class)