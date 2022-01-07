package com.bm.rip.desktop.xplatform


import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.bm.xplatform.PngReader
import com.bm.xplatform.models.ImageModel
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.experimental.and

class PngReaderDesktop : PngReader() {

    override fun GetImageModel(file: FileHandle, scalingFactor: Float): ImageModel {
        val model = ImageModel()

        var buffer: BufferedImage
        try {
            buffer = ImageIO.read(file.read())
            buffer = resize(buffer, Math.ceil((buffer.width * scalingFactor).toDouble()).toInt(), Math.ceil((buffer.height * scalingFactor).toDouble()).toInt())
            model.bytes = (buffer.raster.dataBuffer as DataBufferByte).data
            model.pixelMap = GetPixelMap(model.bytes, buffer.width, buffer.height, true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

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
                var argb: Int = pixelBytes[pos++].toInt() and 0xff shl 24 //value
                argb += pixelBytes[pos++].toInt() and 0xff //red
                argb += pixelBytes[pos++].toInt() and 0xff shl 8 //green
                argb += pixelBytes[pos++].toInt() and 0xff shl 16 //blue

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

    companion object {
        fun resize(input: BufferedImage, scaledWidth: Int, scaledHeight: Int): BufferedImage {
            val output = BufferedImage(scaledWidth, scaledHeight, input.type)
            val g2d = output.createGraphics()
            g2d.drawImage(input, 0, 0, scaledWidth, scaledHeight, null)
            g2d.dispose()
            return output
        }
    }
}
