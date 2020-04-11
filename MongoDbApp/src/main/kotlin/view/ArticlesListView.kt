package view

import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextInputDialog
import javafx.scene.text.Text
import javafx.util.Duration
import model.EmptyArticle
import storage.Storage
import tornadofx.*
import view.ArticlesListCell.Companion.ITEM_ADD
import view.ArticlesListCell.Companion.ITEM_REMOVE

class ArticlesListView(private val articleView: ArticleView,
                       private val storage: Storage) : View() {

    override val root = listview<String> {
        setCellFactory { ArticlesListCell() }
        prefWidth = 224.0

        items.addAll(storage.getArticlesNames())
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
                storage.removeArticle(cur)
            } else {
                if(cur == ITEM_ADD) {
                    selectionModel.clearSelection()
                    addItem(items)
                    cur = items[items.size - 2]
                }

                articleView.setArticle(
                    storage.getArticle(cur.toString())
                )
            }
        }

        // select first article
        if(items.size > 0) {
            runLater(Duration.millis(250.0)) {
                selectionModel.select(0)
                focusModel.focus(0)
                articleView.setArticle(
                    storage.getArticle(items[0])
                )
            }
        }
    }

    private fun addItem(items: ObservableList<String>) {
        val dlg = TextInputDialog()
        dlg.title = "New article"
        dlg.headerText = "Article name:"
        dlg.graphic = null

        val result = dlg.showAndWait()
        result.ifPresent {
            if(items.contains(it)) { // if exists
                alert(Alert.AlertType.WARNING, "Article exists.")
                return@ifPresent
            }
            if(it.isNotEmpty()) { // add new
                storage.putArticle(it, EmptyArticle(it))
                items.add(items.size-1, it)
            }
        }
    }
}