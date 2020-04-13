package view

import controller.ArticleController
import javafx.beans.value.ChangeListener
import storage.Storage
import tornadofx.*
import model.*

class ArticleView(
    private val storage: Storage) : View() {
    private val controller: ArticleController by inject()
    private var article: Article = HelloArticle()

    private val layout = vbox { prefWidth = 600.0 }
    override val root = scrollpane { add(layout) }
    init { setArticle(article) }

    fun setArticle(article: Article) {
        this.article = article
        // clear old blocks
        layout.clear()
        // add header
        layout.add(controller.header(article.articleHeader.text, handler(article.articleHeader)))
        // add content blocks
        for(b in article.contentBlocks)
            addLayoutBlock(b)
    }

    // add new block
    fun addBlock(block: Block) {
        article.contentBlocks.add(block)
        addLayoutBlock(block)
    }

    // get article unique key
    fun articleKey() : String {
        return article.id
    }

    // add existing block to the layout
    private fun addLayoutBlock(block: Block) {
        when (block) {
            is HeaderBlock -> layout.add(controller.header(block.text, handler(block), size=block.size))
            is TextBlock   -> layout.add(controller.text(block.text, handler(block)))
            // is ImageBlock  -> root.add(controller.image(b.image))
        }
    }

    private fun handler(block: Block) : ChangeListener<String> {
        return ChangeListener<String> { _, _, newValue ->
            run {
                when (block) {
                    is HeaderBlock -> block.text = newValue
                    is TextBlock -> block.text = newValue
                }
                storage.updateBlock(articleKey(), block)
            }
        }
    }
}