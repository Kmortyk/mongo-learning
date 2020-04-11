import controller.AppViewController
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import model.HeaderBlock
import model.ImageBlock
import model.TextBlock
import storage.MongoDbStorage
import tornadofx.*
import view.ArticleView
import view.ArticlesListView


class MongoDbView : View() {

    // main mongo db storage instance
    private val storage = MongoDbStorage()

    // current article view
    private val articleView = ArticleView()

    // selectable articles list
    private val articleList = ArticlesListView(articleView, storage)

    // controller for actions
    private val controller = AppViewController(articleView, storage)

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
                menu(null, ImageView(imh)).action {
                    controller.addBlock(HeaderBlock(text = "Awesome header"))
                }
                menu(null, ImageView(imt)).action {
                    controller.addBlock(TextBlock(text = "Awesome text"))
                }
                menu(null, ImageView(imi)).action {
                    controller.addBlock(ImageBlock(src = ""))
                }
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
            style = "-fx-background-color: #ffffff;"
            add(articleList)
        }
    }
}

// options: --module-path /usr/share/openjfx/lib --add-modules=javafx.controls
class MongoDbApp : App(MongoDbView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}