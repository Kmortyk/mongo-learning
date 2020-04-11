package view

import controller.ArticleController
import model.*
import tornadofx.*

class ArticleView() : View() {
    private val controller: ArticleController by inject()
    private var article: Article = HelloArticle()

    private val layout = vbox {
        prefWidth = 600.0
    }

    override val root = scrollpane {
        add(layout)
    }

    init {
        setArticle(article)
    }

    fun setArticle(article: Article) {
        this.article = article
        // clear old blocks
        layout.clear()
        // add header
        layout.add(controller.header(article.articleHeader.text))
        // add content blocks
        for(b in article.contentBlocks) {
            when (b) {
                is HeaderBlock -> layout.add(controller.header(b.text, b.size))
                is TextBlock   -> layout.add(controller.text(b.text))
                // is ImageBlock  -> root.add(controller.image(b.image))
            }
        }
    }
}