package com.bm.automik

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.extensions.getIndex
import com.bm.maths.extensions.relativePosition
import com.bm.maths.Maths
import com.bm.xplatform.PngReader

object MapGenerator {

    fun getHeightMap(width: Int, interpolation: Interpolation): FloatArray {
        val heights = FloatArray(width)
        heights[0] = interpolation.apply(0f) //get the first item
        //get everything between the first and last item
        for (i in 1 until width - 1) {
            heights[i] = interpolation.apply((i / width).toFloat())
        }
        heights[width - 1] = interpolation.apply(1f) //get the last item
        return heights
    }

    fun getHeightDistributionMap(width: Int, samples: Int, interpolation: Interpolation): FloatArray {
        val heights = FloatArray(width)
        for (i in 0 until samples) {
            val randomIndex = Maths.randomFloat(0f, (heights.size - 1).toFloat(), interpolation).toInt()
            heights[randomIndex]++
        }

        var max = heights.max()!!

        for (i in heights.indices) {
            heights[i] = heights[i] / max
        }

        return heights
    }

    fun getHeightMap(imagePath: String, pngReader: PngReader, smoothingPower: Float): FloatArray {
        val map = pngReader.GetAlphaMap(imagePath, .5f)

        val heights = FloatArray(map[0].size)
        for (y in map.indices) {
            val abstractRow = map[y]

            for (x in abstractRow.indices) {
                if (abstractRow[x] != 0f)
                    heights[x] += abstractRow[x]
            }
        }

        for (i in heights.indices) {
            heights[i] = (Math.pow(heights[i].toDouble(), smoothingPower.toDouble()) / map.size).toFloat()
        }

        return heights
    }

    fun getAlphaMap(heights: FloatArray, width: Int, height: Int, floor: Float): Array<FloatArray> {
        val alphaMap = Array(height) { FloatArray(width) }

        for (y in alphaMap.indices) {
            val mapRow = alphaMap[y]
            val relativeY = y.relativePosition(alphaMap.size)
            for (x in mapRow.indices) {
                val relativeX = x.relativePosition(mapRow.size)
                val xIndex = relativeX.getIndex(heights)
                if (relativeY <= heights[xIndex] || relativeY < floor)
                    alphaMap[y][x] = 1f
            }
        }
        return alphaMap
    }
}
