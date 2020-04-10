package view

import controller.ArticleController
import model.*
import tornadofx.*

class ArticleView: View() {
    private val controller: ArticleController by inject()
    private var article: Article = HelloArticle()

    override val root = vbox {
        maxWidth = 1000.0
        useMaxWidth = true
    }

    init {
        setArticle(article)
    }

    public fun setArticle(article: Article) {
        this.article = article
        // clear old blocks
        root.clear()
        // add header
        root.add(controller.header(article.articleHeader.text))
        // add content blocks
        for(b in article.contentBlocks) {
            when (b) {
                is HeaderBlock -> root.add(controller.header(b.text, b.size))
                is TextBlock   -> root.add(controller.text(b.text))
                // is ImageBlock  -> root.add(controller.image(b.image))
            }
        }
    }
}