package com.bm.maths.curves

import com.bm.factories.Point2DFloatFactory
import java.awt.geom.Point2D
import java.util.*


class BezierCurve(start: Point2D.Float, end: Point2D.Float, points: Double, isUserCreated: Boolean) {

    var curvePoints: ArrayList<Point2D.Float>

    init {
        val controlX = (start.x + end.x) / 2
        val stepValue = 1 / points

        val control1 = Point2DFloatFactory[controlX, start.y]
        val control2 = Point2DFloatFactory[controlX, end.y]

        curvePoints = ArrayList(points.toInt())

        var i = 0.0
        while (i <= 1) {
            curvePoints.add(CreateBezurePoint(start, control1, control2, end, i))
            i += stepValue
        }

        Point2DFloatFactory.dispose(control1)
        Point2DFloatFactory.dispose(control2)
    }

    companion object {
        private var ab: Point2D.Float? = null
        private var bc: Point2D.Float? = null
        private var cd: Point2D.Float? = null
        private var abbc: Point2D.Float? = null
        private var bccd: Point2D.Float? = null

        private fun CreateBezurePoint(a: Point2D.Float, b: Point2D.Float, c: Point2D.Float, d: Point2D.Float, percentage: Double): Point2D.Float {
            ab = GenerateCurveCoordinate(a, b, percentage)
            bc = GenerateCurveCoordinate(b, c, percentage)
            cd = GenerateCurveCoordinate(c, d, percentage)
            abbc = GenerateCurveCoordinate(ab!!, bc!!, percentage)
            bccd = GenerateCurveCoordinate(bc!!, cd!!, percentage)
            return GenerateCurveCoordinate(abbc!!, bccd!!, percentage)
        }

        private fun GenerateCurveCoordinate(point1: Point2D.Float, point2: Point2D.Float, percentage: Double): Point2D.Float {
            val x = (point1.x + (point2.x - point1.x) * percentage).toFloat()
            val y = (point1.y + (point2.y - point1.y) * percentage).toFloat()

            return Point2DFloatFactory[x, y]
        }
    }
}
