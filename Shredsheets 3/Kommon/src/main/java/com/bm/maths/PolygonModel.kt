package com.bm.maths

open class PolygonModel(
        var borderVertices: FloatArray,
        var triangleVertices: FloatArray,
        var triangleIndices: ShortArray
) {

    fun scale(value: Float): PolygonModel {
        val scaled = borderVertices.clone()
        for (i: Int in 0 until scaled.size) scaled[i] *= value
        return Polygons.GetPolygonModel(scaled)
    }

}
