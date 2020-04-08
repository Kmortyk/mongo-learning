package controller

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.StackPane
import javafx.scene.text.FontWeight
import tornadofx.*

class ArticleController: Controller() {

    companion object {
        val HEADER_STEP = 3.px
    }

    fun header(text: String, size: Int = 1) : StackPane {
        return StackPane().apply {
            label(text) {
                maxWidth = Double.MAX_VALUE
                alignment = Pos.BASELINE_LEFT
                style {
                    fontSize = 25.px - size * HEADER_STEP
                    fontWeight = FontWeight.BOLD
                    padding = box(15.px, 10.px)
                }
            }
        }
    }

    fun text(text: String): StackPane {
        return StackPane().apply {
            label(text) {
                maxWidth = Double.MAX_VALUE
                alignment = Pos.BASELINE_LEFT
                style{
                    padding = box(10.px, 10.px)
                }
            }
        }
    }

    fun image(image: Image) : StackPane {
        return StackPane().apply {
            imageview(image) { }
        }
    }
}