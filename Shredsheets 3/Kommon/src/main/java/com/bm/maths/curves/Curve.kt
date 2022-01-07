package com.bm.maths.curves

import com.badlogic.gdx.math.Vector2
import com.bm.maths.Maths
import com.bm.maths.extensions.getRectangle
import com.bm.factories.Vec2Factory

import java.util.ArrayList
import java.util.LinkedList

object Curve {
    const val MAX_INTERPOLATION_RANGE = 128

    private fun getSegments(prePoint: Vector2, startPoint: Vector2, endPoint: Vector2, postPoint: Vector2, numSegments: Int): List<Vector2> {
        val result = ArrayList<Vector2>()
        val segmentPercentage = (1.0f / numSegments).toDouble()

        for (i in 1 until numSegments) {
            val value = (segmentPercentage * i).toFloat()
            val next = getPointOnCurve(prePoint, startPoint, endPoint, postPoint, value)
            result.add(next)
        }

        //if (endPoint.y < 32) endPoint.y = 0;
        result.add(endPoint)
        return result
    }

    // Calculates interpolated point between two points using Catmull-Rom Spline
    // From: http://tehc0dez.blogspot.com/2010/04/nice-curves-catmullrom-spline-in-c.html
    private fun getPointOnCurve(smooth1: Vector2, anchor1: Vector2, anchor2: Vector2, smooth2: Vector2, t: Float): Vector2 {
        val t2 = t * t
        val t3 = t2 * t
        val x = 0.5f * (2.0f * anchor1.x
                + (-smooth1.x + anchor2.x) * t
                + (2.0f * smooth1.x - 5.0f * anchor1.x + 4 * anchor2.x - smooth2.x) * t2
                + (-smooth1.x + 3.0f * anchor1.x - 3.0f * anchor2.x + smooth2.x) * t3)

        val y = 0.5f * (2.0f * anchor1.y
                + (-smooth1.y + anchor2.y) * t
                + (2.0f * smooth1.y - 5.0f * anchor1.y + 4 * anchor2.y - smooth2.y) * t2
                + (-smooth1.y + 3.0f * anchor1.y - 3.0f * anchor2.y + smooth2.y) * t3)

        return Vec2Factory.get(x, y)
    }

    fun simplifyPoints(points: LinkedList<Vector2>): LinkedList<Vector2> {
        var v1: Vector2
        var v2: Vector2? = null
        var v3: Vector2? = null
        var a1: Float
        var a2 = 0f
        var a3 = 0f
        var i = 0
        while (i < points.size) {
            v1 = points[i]
            if (v3 == null) {
                v3 = v1
                i++
                continue
            }

            if (v2 == null) {
                v2 = v1
                a2 = v2.angle(v1)
                i++
                continue
            }

            a1 = v1.angle(v2)

            if (a1 == a2 && a1 == a3) {
                points.removeAt(i - 1)
                i--
            }

            v3 = v2
            v2 = v1

            a3 = a2
            a2 = a1
            i++
        }
        return points
    }


    fun reducePoints(points: LinkedList<Vector2>, removalProbability: Float): LinkedList<Vector2> {
        val pointRectangle = points.getRectangle()

        var i = 0
        while (i < points.size) {
            val point = points[i]
            if (point.x == pointRectangle.x || point.x == pointRectangle.x + pointRectangle.width) {
                i++
                continue
            }
            if (point.y == pointRectangle.y || point.y == pointRectangle.y + pointRectangle.height) {
                i++
                continue
            }

            if (Maths.randomBoolean(removalProbability)) {
                points.removeAt(i)
                i--
            }
            i++
        }

        return points
    }
}


fun LinkedList<Vector2>.interpolate(alpha: Float, loopCount: Int, interpolateEdges: Boolean): LinkedList<Vector2> {
    for (i in 0 until loopCount) {
        var p1: Vector2? = null
        var p2: Vector2? = null
        for (p3 in this) {
            if (p1 != null && p2 != null) {
                if (interpolateEdges || p2.y != 0f && p2.x != 0f)
                    p2.set((p1.x + p2.x + p3.x) / 3, (p1.y + p2.y + p3.y) / 3)
            }
            p1 = p2
            p2 = p3
        }
    }
    return this
}

fun LinkedList<Vector2>.constrainedInterpolate(loopCount: Int, interpolateEdges: Boolean): LinkedList<Vector2> {
    for (i in 0 until loopCount) {
        var p1: Vector2? = null
        var p2: Vector2? = null
        for (p3 in this) {
            if (p1 != null && p2 != null) {
                if (p1.dst(p2) < Curve.MAX_INTERPOLATION_RANGE && p2.dst(p3) < Curve.MAX_INTERPOLATION_RANGE)
                    if (interpolateEdges || p2.y != 0f && p2.x != 0f)
                        p2.set((p1.x + p2.x + p3.x) / 3, (p1.y + p2.y + p3.y) / 3)
            }
            p1 = p2
            p2 = p3
        }
    }
    return this
}

