import controller.AppViewController
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
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

    companion object {
        const val ICON_SIZE = 16.0
        const val ICON_SMOOTH = true
    }

    // main mongo db storage instance
    private val storage = MongoDbStorage()

    // current article view
    private val articleView = ArticleView()

    // selectable articles list
    private val articleList = ArticlesListView(articleView, storage)

    // controller for actions
    private val controller = AppViewController(articleView, storage)

    override val root = hbox {

        val imh = ImageView(Image("header.png", ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))
        val imt = ImageView(Image("text.png",ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))
        val imi = ImageView(Image("image.png",ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))

        // left view
        vbox {
            menubar {
                style {
                    paddingTop = 1
                    paddingBottom = 3
                }

                actionable(menu(null, imh), EventHandler {
                    println("ACTION!!!")
                    controller.addBlock(HeaderBlock(text = "Awesome header"))
                })
                actionable(menu(null, imt), EventHandler {
                    println("ACTION!!!")
                    controller.addBlock(TextBlock(text = "Awesome text"))
                })
                actionable(menu(null, imi), EventHandler {
                    println("ACTION!!!")
                    controller.addBlock(ImageBlock(src = ""))
                })
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

    /* --- Common --------------------------------------------------------------------------------------------------- */
    open fun actionable(menu: Menu, ev: EventHandler<ActionEvent>) : Menu {
        val menuItem = MenuItem()
        menu.items.add(menuItem)
        menu.onAction = ev
        menu.addEventHandler(Menu.ON_SHOWN) { menu.hide() }
        menu.addEventHandler(Menu.ON_SHOWING) { menu.fire() }
        return menu
    }
}

// options: --module-path /usr/share/openjfx/lib --add-modules=javafx.controls
class MongoDbApp : App(MongoDbView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}