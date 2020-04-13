package controller

import javafx.beans.value.ChangeListener
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import javafx.scene.image.Image
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

    fun header(text: String, l: ChangeListener<String>, size: Int = 1) : StackPane {
        return StackPane().apply {
            handler(resizable(textarea (text) {
                minHeight = 24.0
                isWrapText = true
                alignment = Pos.BASELINE_LEFT
                style {
                    fontSize = 25.px - size * HEADER_STEP
                    fontWeight = FontWeight.BOLD
                }
            }, HEADER_START_HEIGHT - size * HEADER_STEP,
                  HEADER_LINE_STEP - size * HEADER_STEP), l)
        }
    }

    fun text(text: String, l: ChangeListener<String>): StackPane {
        return StackPane().apply {
            handler(resizable(textarea (text) {
                minHeight = 24.0
                isWrapText = true
                alignment = Pos.BASELINE_LEFT
            }, TEXT_START_HEIGHT, TEXT_LINE_STEP), l)
        }
    }

    fun image(image: Image, l: ChangeListener<String>) : StackPane {
        return StackPane().apply {
            imageview(image) { }
        }
    }

    private fun resizable(textArea: TextArea, startHeight: Double, perLine: Double) : TextArea {
        textArea.setPrefSize(200.0, 40.0)
        textArea.isWrapText = true
        textArea.prefHeight = startHeight

        var oldLines = 1

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
}