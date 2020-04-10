package view

import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextInputDialog
import javafx.scene.text.Text
import model.EmptyArticle
import storage.Storage
import tornadofx.View
import tornadofx.alert
import tornadofx.listview
import view.ArticlesListCell.Companion.ITEM_ADD
import view.ArticlesListCell.Companion.ITEM_REMOVE

class ArticlesListView(private val articleView: ArticleView,
                       private val storage: Storage) : View() {

    override val root = listview<String> {
        setCellFactory { ArticlesListCell() }

        items.addAll(storage.getArticlesNames())
        items.add("+")
        selectionModel.selectionMode = SelectionMode.SINGLE

        onMouseClicked = EventHandler {
            val n = it.pickResult.intersectedNode
            if(n is Text && n.text == ITEM_REMOVE) {
                // remove item
            } else {
                val its = selectionModel.selectedItems
                if(its.size == 0) return@EventHandler
                var cur = its[0]

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