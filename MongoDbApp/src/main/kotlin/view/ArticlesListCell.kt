package view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.Tooltip
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.util.Duration
import storage.ArticleListItem
import tornadofx.ChangeListener
import tornadofx.add

internal class ArticlesListCell : ListCell<ArticleListItem?>() {
    companion object{
        const val ITEM_REMOVE = "d"
        val ITEM_ADD = ArticleListItem("+", 0)

        private const val FX_GREY_BACKGROUND = "-fx-background-color: #ffffff;"
        private const val FX_PADDING = "-fx-padding: 4;"
    }

    override fun updateItem(item: ArticleListItem?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty || item == null) {
            text = ""
            graphic = null
            padding = Insets.EMPTY
        } else {
            // layouts
            val left = HBox()
            val right = HBox()
            val label = Label(item.name)
            // empty region
            val reg = Region()
            HBox.setHgrow(reg, Priority.ALWAYS)
            // set mouse hover property
            val tooltip = Tooltip(item.date)
            tooltip.showDelay = Duration.ZERO
            label.tooltip = tooltip

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