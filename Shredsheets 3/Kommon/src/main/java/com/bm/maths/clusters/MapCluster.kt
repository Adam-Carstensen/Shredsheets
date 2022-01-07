package com.bm.maths.clusters

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.bm.common.IntKeyPair
import com.bm.maths.extensions.getRectangle
import com.bm.maths.curves.Curve
import java.util.*

class MapCluster(items: LinkedList<MapItem>) {
    var id = ++maxId
    var boundary: LinkedList<MapItem> = LinkedList()
        set(boundary) {
            field = boundary
            setPolygon(boundary)
        }
    var locations = HashSet<IntKeyPair>()
    private var polygon: Polygon? = null
    private var rectangle: Rectangle? = null

    val boundaryPoints: LinkedList<Vector2>
        get() {
            var points = LinkedList<Vector2>()
            for (i in boundary.indices) {
                val item = boundary[i]
                points.add(Vector2(item.sceneX.toFloat(), item.sceneY.toFloat()))
            }
            points = Curve.simplifyPoints(points)
            return points
        }

    private val midPoint = Vector2(0f, 0f)

    init {
        boundary = items
    }

    fun size(): Int {
        return this.boundary.size
    }

    fun add(item: MapItem): Boolean {
        this.boundary.add(item)
        setPolygon(this.boundary)
        return true
    }

    private fun setPolygon(boundary: LinkedList<MapItem>) {
        val vertices = FloatArray(boundary.size * 2)
        var i = 0
        while (i < boundary.size) {
            val item = boundary[i]
            vertices[i] = item.xIndex.toFloat()
            vertices[i + 1] = item.yIndex.toFloat()
            locations.add(IntKeyPair(item.xIndex, item.yIndex))
            i += 2
        }
        polygon = Polygon(vertices)

        var left = boundaryPoints.minBy { v -> v.x }!!
        var right = boundaryPoints.maxBy { v -> v.x }!!
        var bottom = boundaryPoints.minBy { v -> v.y }!!
        var top = boundaryPoints.maxBy { v -> v.y }!!
        if (rectangle != null) rectangle!!.set(left.x, bottom.y, right.x - left.x, top.y - bottom.y)
        else rectangle = Rectangle(left.x, bottom.y, right.x - left.x, top.y - bottom.y)
    }

    fun getRectangle(): Rectangle? {
        if (rectangle == null) {
            rectangle = boundaryPoints.getRectangle()
        }
        return rectangle
    }


    operator fun contains(item: MapItem): Boolean {
        return locations.contains(IntKeyPair(item.xIndex, item.yIndex)) || polygon!!.contains(item.xIndex.toFloat(), item.yIndex.toFloat())
    }

    fun getMidPoint(): Vector2 {
        if (this.boundary.size == 0) return midPoint
        var x = 0f
        var y = 0f
        for (item in this.boundary) {
            x += item.sceneX.toFloat()
            y += item.sceneY.toFloat()
        }
        return midPoint.set(x / this.boundary.size, y / this.boundary.size)
    }

    companion object {

        private var maxId = 0
    }
}
