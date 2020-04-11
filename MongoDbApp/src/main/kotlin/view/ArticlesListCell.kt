package view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.*
import tornadofx.add
import tornadofx.box
import tornadofx.px

internal class ArticlesListCell : ListCell<String?>() {
    companion object{
        const val ITEM_ADD = "+"
        const val ITEM_REMOVE = "d"

        private const val FX_PADDING = "-fx-padding: 4;"
        private const val FX_GREY_BACKGROUND = "-fx-background-color: #d3d3d3;"
    }

    override fun updateItem(item: String?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty || item == null) {
            text = ""
            graphic = null
            padding = Insets.EMPTY
        } else {
            // layouts
            val left = HBox()
            val right = HBox()
            val label = Label(item)
            // empty region
            val reg = Region()
            HBox.setHgrow(reg, Priority.ALWAYS)

            left.alignment = Pos.BASELINE_LEFT
            right.alignment = Pos.BASELINE_RIGHT

            if(item == ITEM_ADD) {
                reg.style += FX_GREY_BACKGROUND + FX_PADDING
                right.style += FX_GREY_BACKGROUND + FX_PADDING
                right.add(label)
            } else {
                reg.style += FX_PADDING
                right.style += FX_PADDING
                left.style += FX_PADDING

                left.children.add(label)
                right.add(Label(ITEM_REMOVE))
            }

            val layout = HBox()
            layout.apply {
                add(left); add(reg); add(right)
            }

            graphic = layout
        }
    }
}