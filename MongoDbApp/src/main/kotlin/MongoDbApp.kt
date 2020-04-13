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
import storage.SortType
import tornadofx.*
import view.ArticleView
import view.ArticlesListView

/**
 * TODO
 * 2. Отображение даты в списке статей
 * 1. Статьи в опредлённую дату (функционал)
 * 3. in-place добавление блока
 *
 * 4. Таблица картинок в базе данных (как сделать?)
 * 5. Отображение картинки в блоке
 * 6. Загрузка картинки с файловой системы в базу данных
 * 7. Сменить картинку правой кнопкой мыши при редактировании статьи
 * */

const val WIN_HEIGHT = 600.0

class MongoDbView : View() {

    companion object {
        const val ICON_SIZE = 16.0
        const val ICON_SMOOTH = true
    }

    // main mongo db storage instance
    private val storage = MongoDbStorage()

    // current article view
    private val articleView = ArticleView(storage)

    // selectable articles list
    private val articleList = ArticlesListView(articleView, storage)

    // controller for actions
    private val controller = AppViewController(articleView, storage)

    override val root = hbox {

        val imh = ImageView(Image("header.png", ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))
        val imt = ImageView(Image("text.png",ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))
        val imi = ImageView(Image("image.png",ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))
        val imd = ImageView(Image("del.png",ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))

        // left view
        vbox {
            menubar {
                style {
                    paddingTop = 1
                    paddingBottom = 3
                }

                actionable(menu(null, imh), EventHandler {
                    controller.addBlock(HeaderBlock(text="Awesome header"))
                })
                actionable(menu(null, imt), EventHandler {
                    controller.addBlock(TextBlock(text="Awesome text"))
                })
                actionable(menu(null, imi), EventHandler {
                    controller.addBlock(ImageBlock(src=""))
                })
                actionable(menu(null, imd), EventHandler {
                    val block = articleView.selectedBlock()
                    articleView.removeSelectedBlock()
                    storage.removeBlock(articleView.articleKey(), block)
                })
            }
            add(articleView)
        }

        // right view
        vbox {
            hbox {
                style{
                    paddingLeft = 2
                    paddingTop = 3
                    paddingBottom = 3
                }
                datepicker() {
                    promptText = "Enter query..."
                }
                button {
                    val sorts = listOf(SortType.BY_NAME, SortType.BY_DATE)
                    var cur = 0

                    graphic = ImageView(Image("sort.png", ICON_SIZE, ICON_SIZE, false, ICON_SMOOTH))

                    action {
                        cur = (cur + 1) % sorts.size
                        articleList.updateNames(sorts[cur])
                    }
                }
            }
            style = "-fx-background-color: #ffffff;"
            add(articleList)
        }
    }

    /* --- Common --------------------------------------------------------------------------------------------------- */

    private fun actionable(menu: Menu, ev: EventHandler<ActionEvent>) : Menu {
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
        stage.height = WIN_HEIGHT
        super.start(stage)
    }
}