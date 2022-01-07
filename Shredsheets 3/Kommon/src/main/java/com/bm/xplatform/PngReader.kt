package com.bm.xplatform

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.bm.xplatform.models.ImageModel
import java.awt.image.BufferedImage

abstract class PngReader {
    ///expects an x-y map starting at the top left.
    ///The width and height of the source image must be scaled to 'scalingFactor' before being returned
    abstract fun GetImageModel(file: FileHandle, scalingFactor: Float): ImageModel

    //byte[] GetImageBytes(FileHandle file, float scalingFactor);
    //int[][] GetPixelMap(byte[] pixelBytes, int width, int height, boolean hasAlphaChannel);


    ///Returns a map of the Int encoded (argb8888) Color objects which represent pixels from the image found at the path
    fun GetColorMap(path: String, scalingFactor: Float): Array<IntArray> {
        val imageModel = GetImageModel(Gdx.files.internal(path), scalingFactor)
        //image will be rendered upside down because of the order of reads vs writes
        imageModel.pixelMap!!.reverse()
        return imageModel.pixelMap!!
    }

    ///Returns a map of only the alpha channel of the image found at the path
    fun GetAlphaMap(path: String, scalingFactor: Float): Array<FloatArray> {
        val imageModel = GetImageModel(Gdx.files.internal(path), scalingFactor)
        //get pixels from iamge
        var pixelMap = imageModel.pixelMap!!
        //image will be rendered upside down because of the order of reads vs writes
        pixelMap.reverse()

        val map = Array(pixelMap.size) { FloatArray(pixelMap[0].size) }
        val color = Color()
        for (row in pixelMap.indices) {
            map[row] = FloatArray(pixelMap[row].size)
            for (column in pixelMap[row].indices) {
                Color.argb8888ToColor(color, pixelMap[row][column])
                map[row][column] = color.a
            }
        }

        return map
    }

//    @JvmOverloads
//    fun GetObjectiveColorMap(path: String, scalingFactor: Float, tolerance: Float = 0.2f): Array<IntArray> {
//        val imageModel = GetImageModel(Gdx.files.internal(path), scalingFactor)
//        var pixelMap = imageModel.pixelMap!!
//        pixelMap.reverse()
//        val map = Array(pixelMap.size) { IntArray(pixelMap[0].size)}
//
//        val color = Color()
//        val objectiveTypes = Actors.getTypes()
//        for (row in pixelMap.indices) {
//            for (column in 0 until pixelMap[row].size) {
//                Color.argb8888ToColor(color, pixelMap[row][column])
//                if (color.a != 1f) continue //0 is the defaultBias score, and will be used
//                var objectiveColor: Color
//                for ((_, value) in objectiveTypes) {
//                    objectiveColor = value.color
//
//                    val difference = Math.sqrt(Math.pow((color.r - objectiveColor.r).toDouble(), 2.0) + Math.pow((color.g - objectiveColor.g).toDouble(), 2.0) + Math.pow((color.b - objectiveColor.b).toDouble(), 2.0))
//                    if (difference < tolerance) {
//                        map[row][column] = value.mapKey
//                        break //escape early because a color should only match one color within tolerance.  //TODO: look into tolerance constraints
//                    }
//                }
//            }
//
//        }
//
//        return map
//    }

    companion object {
        private fun convertTo2DUsingGetRGB(image: BufferedImage): Array<IntArray> {
            val width = image.width
            val height = image.height
            val result = Array(height) { IntArray(width) }

            for (row in 0 until height) {
                for (col in 0 until width) {
                    result[row][col] = image.getRGB(col, row)
                }
            }

            return result
        }
    }


}
