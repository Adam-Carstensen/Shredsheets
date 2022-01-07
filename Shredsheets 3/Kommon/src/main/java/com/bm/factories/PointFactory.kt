package com.bm.factories

import java.awt.Point

object PointFactory : Factory<Point>() {
    override fun getNewItem(): Point {
        return Point()
    }

    operator fun get(x: Int, y: Int): Point {
        val point = get()
        point.x = x
        point.y = y
        return point
    }
}
