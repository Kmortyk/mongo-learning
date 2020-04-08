package view

import controller.ArticleController
import model.*
import tornadofx.View
import tornadofx.clear
import tornadofx.vbox

class ArticleView: View() {
    private val controller: ArticleController by inject()
    private var article: Article = HelloArticle()

    override val root = vbox {}

    init {
        setArticle(article)
    }

    public fun setArticle(article: Article) {
        this.article = article
        // clear old blocks
        root.clear()
        // add header
        root.add(controller.header(article.articleName.text))
        // add content blocks
        for(b in article.contentBlocks) {
            when (b) {
                is HeaderBlock -> root.add(controller.header(b.text, b.size))
                is TextBlock   -> root.add(controller.text(b.text))
                is ImageBlock  -> root.add(controller.image(b.image))
            }
        }
    }
}