package model

interface Block

data class HeaderBlock(val text: String, val size: Int = 1) : Block
data class TextBlock(val text: String) : Block
data class ImageBlock(val src: String) : Block
