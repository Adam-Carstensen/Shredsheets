package com.bm.automik.kernels

class PointKernel(width: Int, height: Int) : Kernel(width, height) {
    override val stepSize: Int
        get() = width

    override fun createMap(): Array<FloatArray> {
        return resize(arrayOf(floatArrayOf(1f)), width, height)
    }

    companion object {
        val Default: Kernel = PointKernel(1, 1)
    }
}
