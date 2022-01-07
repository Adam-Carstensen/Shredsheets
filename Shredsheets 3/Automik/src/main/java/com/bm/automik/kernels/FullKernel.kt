package com.bm.automik.kernels

import com.bm.maths.Maths

class FullKernel(width: Int, height: Int) : Kernel(width, height) {
    override val stepSize: Int
        get() = width

    override fun createMap(): Array<FloatArray> {
        return Maths.scale(arrayOf(floatArrayOf(1f, 1f, 1f), floatArrayOf(1f, 1f, 1f), floatArrayOf(1f, 1f, 1f)), width, height)
    }

    companion object {
        val Default: Kernel = FullKernel(3, 3)
    }
}
