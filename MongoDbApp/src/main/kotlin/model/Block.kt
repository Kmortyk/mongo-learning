package model

abstract class Block(var id: String = "")

data class HeaderBlock(var text: String = "",
                       val size: Int = 1,
                       val _id: String = "") : Block(_id)

data class TextBlock(var text: String = "",
                     val _id: String = "") : Block(_id)

data class ImageBlock(var src: String = "",
                      val _id: String = "") : Block(_id)

