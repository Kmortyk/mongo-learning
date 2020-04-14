package controller

import javafx.beans.value.ChangeListener
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.scene.text.FontWeight
import tornadofx.*


class ArticleController(): Controller() {

    companion object {
        const val HEADER_STEP = 3.0
        // header
        const val HEADER_START_HEIGHT = 43.0
        const val HEADER_LINE_STEP = 29.0
        // text
        const val TEXT_START_HEIGHT = 26.0
        const val TEXT_LINE_STEP = 16.0
    }

    fun header(text: String, l: ChangeListener<String>, f: ChangeListener<Boolean>, size: Int = 1) : StackPane {
        return StackPane().apply {
            focus(handler(resizable(textarea (text) {
                minHeight = 24.0
                isWrapText = true
                alignment = Pos.BASELINE_LEFT
                style {
                    fontSize = 25.px - size * HEADER_STEP
                    fontWeight = FontWeight.BOLD
                }
            }, HEADER_START_HEIGHT - size * HEADER_STEP,
                  HEADER_LINE_STEP - size * HEADER_STEP), l), f)
        }
    }

    fun text(text: String, l: ChangeListener<String>, f: ChangeListener<Boolean>): StackPane {
        return StackPane().apply {
            focus(handler(resizable(textarea (text) {
                minHeight = 24.0
                isWrapText = true
                alignment = Pos.BASELINE_LEFT
            }, TEXT_START_HEIGHT, TEXT_LINE_STEP), l), f)
        }
    }

    fun image(image: Image, f: ChangeListener<Boolean>) : StackPane {
        return StackPane().apply {
            val img = imageview(image)
            img.setOnMouseClicked {
                f.changed(img.focusedProperty(), false, true)
            }
        }
    }

    private fun resizable(textArea: TextArea, startHeight: Double, perLine: Double) : TextArea {
        textArea.setPrefSize(200.0, 40.0)
        textArea.isWrapText = true
        var oldLines = textArea.text.split('\n').size
        textArea.prefHeight = startHeight + perLine * (oldLines-1)

        textArea.textProperty().addListener { _, _, _ ->
            val lines = textArea.text.split('\n').size
            if (oldLines != lines) {
                val delta = lines - oldLines
                oldLines = lines
                textArea.prefHeight = textArea.layoutBounds.height + perLine * delta
            }
        }
        return textArea
    }

    private fun handler(textArea: TextArea, l: ChangeListener<String>) : TextArea {
        textArea.textProperty().addListener(l)
        return textArea
    }

    private fun focus(node: Node, l: ChangeListener<Boolean>) : Node {
        node.focusedProperty().addListener(l)
        return node
    }

}