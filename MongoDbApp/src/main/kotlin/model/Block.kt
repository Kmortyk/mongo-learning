package model

abstract class Block(var id: String = "")

data class HeaderBlock(val text: String = "",
                       val size: Int = 1,
                       val _id: String = "") : Block(_id)

data class TextBlock(val text: String = "",
                     val _id: String = "") : Block(_id)

data class ImageBlock(val src: String = "",
                      val _id: String = "") : Block(_id)

