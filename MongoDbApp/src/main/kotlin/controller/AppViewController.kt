package controller

import model.Block
import model.TextBlock
import storage.Storage
import tornadofx.Controller
import view.ArticleView

class AppViewController(private val articleView: ArticleView,
                        private val storage: Storage): Controller() {

    fun addBlock(b: Block) {
        // insert block to db and get it's key
        b.id = storage.addBlock(articleView.articleKey(), b)
        // insert block into the view
        articleView.addBlock(b)
    }
}