package com.bm.automik.kernels

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.Maths

abstract class Kernel(var width: Int, var height: Int, var interpolation: Interpolation = Interpolation.exp5) {
    var map: Array<FloatArray> = createMap()

    abstract fun createMap(): Array<FloatArray>
    abstract val stepSize: Int

    fun resize(array: Array<FloatArray>, width: Int, height: Int): Array<FloatArray> {
        this.width = width
        this.height = height
        return Maths.scale(array, width, height)
    }
}
