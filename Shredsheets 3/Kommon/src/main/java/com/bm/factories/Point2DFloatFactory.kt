package com.bm.factories

import java.awt.geom.Point2D

object Point2DFloatFactory : Factory<Point2D.Float>() {

    override fun getNewItem(): Point2D.Float {
        return Point2D.Float()
    }

    operator fun get(x: Float, y: Float): Point2D.Float {
        val point = get()
        point.x = x
        point.y = y
        return point
    }
}
