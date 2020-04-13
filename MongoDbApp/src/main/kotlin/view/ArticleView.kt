package view

import controller.ArticleController
import model.*
import storage.Storage
import tornadofx.*

class ArticleView(
    private val storage: Storage) : View() {
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
        for(b in article.contentBlocks)
            addLayoutBlock(b)
    }

    // add new block
    fun addBlock(block: Block) {
        article.contentBlocks.add(block)
        addLayoutBlock(block)
    }

    // add existing block to the layout
    private fun addLayoutBlock(block: Block) {
        when (block) {
            is HeaderBlock -> layout.add(controller.header(block.text, block.size))
            is TextBlock   -> layout.add(controller.text(block.text))
            // is ImageBlock  -> root.add(controller.image(b.image))
        }
    }

    public fun articleKey() : String {
        return article.articleHeader.text
    }
}