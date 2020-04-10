package view

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.*
import tornadofx.add

internal class ArticlesListCell : ListCell<String?>() {
    companion object{
        const val ITEM_ADD = "+"
        const val ITEM_REMOVE = "d"
    }

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            graphic = null
        } else {
            val left = HBox()
            val right = HBox()
            val label = Label(item)

            left.alignment = Pos.BASELINE_LEFT
            right.alignment = Pos.BASELINE_RIGHT

            if(item == ITEM_ADD) {
                style = "-fx-background-color: #e0e0d1;"
                right.add(label)
            } else {
                left.children.add(label)
                right.add(Label(ITEM_REMOVE))
            }
            // empty region
            val reg = Region()
            HBox.setHgrow(reg, Priority.ALWAYS)

            val layout = HBox()
            layout.apply {
                add(left); add(reg); add(right)
            }

            graphic = layout
        }
    }
}