package view

import javafx.scene.control.Alert
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextInputDialog
import model.EmptyArticle
import storage.Storage
import tornadofx.View
import tornadofx.alert
import tornadofx.listview
import tornadofx.shortpress
import view.ArticlesListCell.Companion.ADD_ITEM

class ArticlesListView(articleView: ArticleView, storage: Storage) : View() {
    override val root = listview<String> {

        setCellFactory { _ -> ArticlesListCell() }

        items.addAll(storage.getArticlesNames())
        items.add("+")
        selectionModel.selectionMode = SelectionMode.SINGLE

        shortpress{
            val its = selectionModel.selectedItems
            if(its.size == 0) return@shortpress
            val cur = its[0]

            if(cur == ADD_ITEM) {
                selectionModel.clearSelection()

                val dlg = TextInputDialog()
                dlg.title = "New article"
                dlg.headerText = "Article name:"
                dlg.graphic = null

                val result = dlg.showAndWait()
                result.ifPresent {
                    if(items.contains(it)) {
                        alert(Alert.AlertType.WARNING, "Article exists.")
                        return@ifPresent
                    }
                    if(it.isNotEmpty()) {
                        storage.putArticle(it, EmptyArticle(it))
                        items.add(items.size-1, it)
                    }
                }
            }

            articleView.setArticle(storage.getArticle(cur.toString()))
        }
    }
}