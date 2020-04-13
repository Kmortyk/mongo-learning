package view

import WIN_HEIGHT
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextInputDialog
import javafx.scene.text.Text
import javafx.util.Duration
import model.EmptyArticle
import storage.ArticleListItem
import storage.SortType
import storage.Storage
import tornadofx.*
import view.ArticlesListCell.Companion.ITEM_ADD
import view.ArticlesListCell.Companion.ITEM_REMOVE

class ArticlesListView(private val articleView: ArticleView,
                       private val storage: Storage) : View() {

    private var articleItems = listOf<ArticleListItem>()

    init {
        articleItems = storage.getArticleItems()
    }

    override val root = listview<ArticleListItem> {
        setCellFactory { ArticlesListCell() }
        prefWidth = 224.0
        prefHeight = WIN_HEIGHT

        items.addAll(storage.getArticleItems())
        items.add(ITEM_ADD)
        selectionModel.selectionMode = SelectionMode.SINGLE

        onMouseClicked = EventHandler {
            val its = selectionModel.selectedItems
            if(its.size == 0) return@EventHandler
            var cur = its[0]

            val n = it.pickResult.intersectedNode
            if(n is Text && n.text == ITEM_REMOVE) {
                // remove item
                items.remove(cur)
                storage.removeArticleByName(cur.name)
            } else {
                if(cur == ITEM_ADD) {
                    selectionModel.clearSelection()
                    addItem(items)
                    if(items.size > 1)
                        cur = items[items.size - 2]
                }

                articleView.setArticle(
                    storage.getArticleByName(cur.toString())
                )
            }
        }

        // select first article
        if(items.size > 0) {
            runLater(Duration.millis(250.0)) {
                // selectionModel.select(0)
                // focusModel.focus(0)
                articleView.setArticle(storage.getArticleByName(items[0].name))
            }
        }
    }

    private fun addItem(items: ObservableList<ArticleListItem>) {
        val dlg = TextInputDialog()
        dlg.title = "New article"
        dlg.headerText = "Article name:"
        dlg.graphic = null

        val result = dlg.showAndWait()
        result.ifPresent {
            for(itm in items) // if article with name exists fixme not necessary?
                if(itm.name == it) {
                    alert(Alert.AlertType.WARNING, "Article exists.")
                    return@ifPresent
                }

            if(it.isNotEmpty()) { // add new
                storage.putArticle(EmptyArticle(it))
                items.add(items.size-1, ArticleListItem(it, System.currentTimeMillis()))
            }
        }
    }

    fun updateNames(type: SortType = SortType.BY_NAME) {
        root.items.clear()
        root.items.addAll(storage.getArticleItems(type))
        root.items.add(ITEM_ADD)
    }

    fun findArticles(value: String?) {
        if(value == null) return
        val found = mutableListOf<ArticleListItem>()

        for(article in articleItems) {
            if(article.name.contains(value) || article.date.contains(value))
                found.add(article)
        }

        root.items.clear()
        root.items.addAll(found)
        root.items.add(ITEM_ADD)
    }
}