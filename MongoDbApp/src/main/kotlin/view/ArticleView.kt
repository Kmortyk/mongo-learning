package view

import WIN_HEIGHT
import controller.ArticleController
import controller.ArticleController.Companion.HEADER_START_HEIGHT
import javafx.beans.value.ChangeListener
import storage.Storage
import tornadofx.*
import model.*

class ArticleView(
    private val storage: Storage) : View() {
    private val controller: ArticleController by inject()
    private var article: Article = HelloArticle()
    private var selectedBlockIndex = -1

    private val layout = vbox {
        prefWidth = 600.0
    }

    override val root = scrollpane {
        add(layout)
        prefHeight = WIN_HEIGHT
    }
    init { setArticle(article) }

    fun setArticle(article: Article) {
        this.article = article
        root.vvalue = 0.0
        // clear old blocks
        layout.clear()
        // add header
        layout.add(controller.header(article.articleHeader.text,
            headerHandler(article.articleHeader),
            focusHandler(article.articleHeader)))
        // add content blocks
        for(i in 0 until article.contentBlocks.size) // first - header
            addLayoutBlock(i + 1, article.contentBlocks[i])
    }

    // add new block
    fun addBlock(block: Block) {
        article.contentBlocks.add(selectedBlockIndex() + 1, block) // next to this
        addLayoutBlock(layoutSelectedIndex() + 1, block) // next to this at layout
    }

    // get article unique key
    fun articleKey() : String {
        return article.id
    }

    // add existing block to the layout
    private fun addLayoutBlock(index: Int, block: Block) {
        when (block) {
            is HeaderBlock -> layout.children.add(index,
                controller.header(block.text, handler(block), focusHandler(block), size=block.size))
            is TextBlock   -> layout.children.add(index,
                controller.text(block.text, handler(block), focusHandler(block)))
            is ImageBlock  -> layout.children.add(index, controller.image(
                block.image(storage), focusHandler(block)))
        }
    }

    /* --- Selected block ------------------------------------------------------------------------------------------- */

    fun selectedBlockIndex(): Int { return selectedBlockIndex }

    private fun layoutSelectedIndex(): Int { return selectedBlockIndex + 1 /* for header */ }

    fun selectedBlock(): Block {
        if(selectedBlockIndex() < 0)
            return article.articleHeader
        return article.contentBlocks[selectedBlockIndex()]
    }

    fun removeSelectedBlock() {
        if(layoutSelectedIndex() > 0) { // not header
            layout.children.removeAt(layoutSelectedIndex())
            article.contentBlocks.removeAt(selectedBlockIndex())
        }
    }

    /* --- Article list --------------------------------------------------------------------------------------------- */

    private var articlesListView: ArticlesListView? = null

    fun setArticlesListView(articlesListView: ArticlesListView) {
        this.articlesListView = articlesListView
    }

    /* --- Handlers ------------------------------------------------------------------------------------------------- */

    private fun headerHandler(block: HeaderBlock) : ChangeListener<String> {
        return ChangeListener<String> { _, oldValue, newValue ->
            run {
                if(articlesListView != null) {
                    val upd = articlesListView!!.updateHeader(oldValue ?: "", newValue ?: "")
                    if(upd) {
                        block.text = newValue
                        storage.updateHeader(articleKey(), block)
                    }
                }
            }
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
                        selectedBlockIndex = i
                }

                // println("\${article.articleHeader.id} == ${block.id}")
                if(article.articleHeader.id == block.id)
                    selectedBlockIndex = -1

//                if(selectedBlockIndex > 0)
//                    println("focus! " + article.contentBlocks[selectedBlockIndex])
            }
        }
    }
}