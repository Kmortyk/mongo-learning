package controller

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.StackPane
import javafx.scene.text.FontWeight
import tornadofx.*

class ArticleController: Controller() {

    fun header(text: String) : StackPane {
        return StackPane().apply {
            label(text) {
                maxWidth = Double.MAX_VALUE
                alignment = Pos.BASELINE_LEFT
                style {
                    fontSize = 20.px
                    fontWeight = FontWeight.BOLD
                    padding = box(15.px, 10.px)
                    // backgroundColor += c("#cecece")
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