package view

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.Background
import javafx.scene.layout.HBox
import tornadofx.CssRule.Companion.c

internal class ArticlesListCell : ListCell<String?>() {
    companion object{
        const val ADD_ITEM = "+"
    }

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            graphic = null
        } else {
            val hBox = HBox()
            val label = Label(item)

            if(item == ADD_ITEM) {
                style = "-fx-background-color: #e0e0d1;"
                hBox.alignment = Pos.CENTER
                label.alignment = Pos.CENTER
            }

            hBox.children.add(label)
            graphic = hBox
        }
    }
}