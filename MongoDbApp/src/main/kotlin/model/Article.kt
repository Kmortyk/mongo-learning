package model

class Article(name: String, contentBlocks: List<Block> = listOf()) {

    val articleName: HeaderBlock
    val contentBlocks: MutableList<Block> = ArrayList()
    val timestamp: Long = 0

    init {
        articleName = HeaderBlock(name)

        for(b in contentBlocks)
            addBlock(b)
    }

    public fun addBlock(block: Block) {
        contentBlocks.add(block)
    }

    public fun putContent() {
        // TODO
    }
}