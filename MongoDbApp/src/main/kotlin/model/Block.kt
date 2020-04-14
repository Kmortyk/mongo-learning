package model

import javafx.scene.image.Image
import org.bson.types.ObjectId
import storage.Storage
import java.io.ByteArrayInputStream


abstract class Block(var id: String = "")

/* --- Text --------------------------------------------------------------------------------------------------------- */

data class HeaderBlock(var text: String = "",
                       val size: Int = 1,
                       val _id: String = "") : Block(_id)

data class TextBlock(var text: String = "",
                     val _id: String = "") : Block(_id)

/* --- Image -------------------------------------------------------------------------------------------------------- */

data class ImageBlock(var src: ObjectId = ObjectId(),
                      val _id: String = "") : Block(_id) {

    private var data: ByteArray? = null

    fun image(storage: Storage) : Image {
        if(data == null)
            data = storage.getImage(src)
        return Image(ByteArrayInputStream(data))
    }
}

