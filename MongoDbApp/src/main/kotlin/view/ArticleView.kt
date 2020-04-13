package view

import WIN_HEIGHT
import controller.ArticleController
import javafx.beans.value.ChangeListener
import storage.Storage
import tornadofx.*
import model.*

class ArticleView(
    private val storage: Storage) : View() {
    private val controller: ArticleController by inject()
    private var article: Article = HelloArticle()
    private var selectedBlock = -1

    private val layout = vbox {
        prefWidth = 600.0
        prefHeight = WIN_HEIGHT
    }

    override val root = scrollpane { add(layout) }
    init { setArticle(article) }

    fun setArticle(article: Article) {
        this.article = article
        // clear old blocks
        layout.clear()
        // add header
        layout.add(controller.header(article.articleHeader.text,
            handler(article.articleHeader),
            // can't delete header
            ChangeListener<Boolean> { _, _, _ -> run {} }))
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
            is HeaderBlock -> layout.add(controller.header(block.text,
                handler(block), focusHandler(block), size=block.size))
            is TextBlock   -> layout.add(controller.text(block.text,
                handler(block), focusHandler(block)))
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

    private fun focusHandler(block: Block) : ChangeListener<Boolean> {
        return ChangeListener<Boolean> { _, _, _ ->
            run {
                for(i in 0 until article.contentBlocks.size) {
                    if(article.contentBlocks[i].id == block.id)
                        selectedBlock = i // one for header
                }
            }
        }
    }

    fun selectedBlock(): Block {
        return article.contentBlocks[selectedBlock]
    }

    fun removeSelectedBlock() {
        layout.children.removeAt(selectedBlock + 1) // one for header
    }
}