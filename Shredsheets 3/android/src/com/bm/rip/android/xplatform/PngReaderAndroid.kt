package com.bm.rip.android.xplatform

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.bm.xplatform.PngReader
import com.bm.xplatform.models.ImageModel

import java.nio.ByteBuffer
import kotlin.experimental.and

class PngReaderAndroid : PngReader() {

    override fun GetImageModel(file: FileHandle, scalingFactor: Float): ImageModel {
        val model = ImageModel()

        val pixmap = Pixmap(file)
        val scaled: Pixmap
        val buffer: ByteBuffer
        if (scalingFactor == 1f) {
            scaled = pixmap
            buffer = scaled.pixels
        } else {
            scaled = Pixmap((pixmap.width * scalingFactor).toInt(), (pixmap.height * scalingFactor).toInt(), pixmap.format)
            scaled.drawPixmap(pixmap, 0, 0, pixmap.width, pixmap.height, 0, 0, Math.ceil((pixmap.width * scalingFactor).toDouble()).toInt(), Math.ceil((pixmap.height * scalingFactor).toDouble()).toInt())
            buffer = scaled.pixels
            pixmap.dispose()
        }

        model.bytes = ByteArray(buffer.remaining())
        buffer.get(model.bytes)

        model.pixelMap = GetPixelMap(model.bytes, (pixmap.width * scalingFactor).toInt(), (pixmap.height * scalingFactor).toInt(), true)
        scaled.dispose()
        return model
    }

    fun GetPixelMap(pixelBytes: ByteArray?, width: Int, height: Int, hasAlphaChannel: Boolean): Array<IntArray> {
        val result = Array(height) { IntArray(width) }
        if (hasAlphaChannel) {
            val pixelLength = 4
            var row = 0
            var col = 0
            var pos = 0
            var pixel = 0
            while (pixel < pixelBytes!!.size) {
                var argb = 0
                argb += pixelBytes[pos++].toInt() and 0xff shl 16 //red
                argb += pixelBytes[pos++].toInt() and 0xff shl 8 //green
                argb += pixelBytes[pos++].toInt() and 0xff //blue
                argb += pixelBytes[pos++].toInt() and 0xff shl 24 //value

                result[row][col] = argb
                col++
                if (col == width) {
                    col = 0
                    row++
                }
                pixel += pixelLength
            }
        } else {
            val pixelLength = 3
            var pixel = 0
            var row = 0
            var col = 0
            while (pixel < pixelBytes!!.size) {
                val argb = Color.argb8888(1f, (pixelBytes[pixel + 2] and 0xff.toByte()).toFloat(), (pixelBytes[pixel + 1] and 0xff.toByte()).toFloat(), (pixelBytes[pixel] and 0xff.toByte()).toFloat())
                result[row][col] = argb
                col++
                if (col == width) {
                    col = 0
                    row++
                }
                pixel += pixelLength
            }
        }
        return result
    }

}
