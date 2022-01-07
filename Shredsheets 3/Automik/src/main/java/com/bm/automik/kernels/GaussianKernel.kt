package com.bm.automik.kernels

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.extensions.distance
import com.bm.maths.extensions.relativePosition
import com.bm.maths.Maths
import kotlin.math.pow

class GaussianKernel(width: Int, height: Int, interpolation: Interpolation = Interpolation.exp5) : Kernel(width, height, interpolation) {
    override val stepSize: Int
        get() = width/2

    override fun createMap(): Array<FloatArray> {
        val map = Array(height) { FloatArray(width) }

        for ((y, row) in map.withIndex()) {
            val relativeY = y.relativePosition(map.size)
            var distanceFromCenterY = relativeY.distance(.5f) * 2f

            for ((x, column) in row.withIndex()) {
                val relativeX = x.relativePosition(row.size)
                var distanceFromCenterX = relativeX.distance(.5f) * 2f
                var distanceFromCenter = Math.sqrt((distanceFromCenterY.pow(2) + distanceFromCenterX.pow(2)).toDouble()).toFloat() / Maths.sqrt2
                distanceFromCenter = interpolation.apply(distanceFromCenter, 0f, 1f)

                map[y][x] = (1f - distanceFromCenter)
            }
        }

        return map
    }

    companion object {
        val Default: Kernel = GaussianKernel(3, 3)
    }
}
