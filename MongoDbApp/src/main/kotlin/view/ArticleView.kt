package view

import controller.ArticleController
import tornadofx.View
import tornadofx.vbox

class ArticleView: View() {
    private val controller: ArticleController by inject()

    override val root = vbox {
        add(controller.header("Hello world article"))
        add(controller.text("In such hard time"))
        add(controller.text("We want to leave in peace and"))
        add(controller.text("See the sunshine at the morning"))
    }
}