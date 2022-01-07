package com.bm.shredsheets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.bm.resources.Textures
import com.bm.shredsheets.models.Paint
import space.earlygrey.shapedrawer.ShapeDrawer

object RectModule {
    private var fillPaint: Paint? = null
    private var borderPaint: Paint? = null

    private fun getFillPaint(): Paint? {
        if (fillPaint == null) fillPaint = Paint()
        return fillPaint
    }

    var fillPaintByColor = HashMap<Color, Paint>()
    private fun getFillPaint(color: Color): Paint {
        var paint = fillPaintByColor.get(color)
        if (paint != null) return paint
        paint = Paint()
        paint.color = color
        fillPaintByColor[color] = paint
        return paint
    }

    private fun getBorderPaint(): Paint {
        if (borderPaint == null) borderPaint = Paint()
        return borderPaint!!
    }

    var borderPaintByColor = HashMap<Color, Paint>()
    private fun getBorderPaint(color: Color): Paint {
        var paint = borderPaintByColor.get(color)
        if (paint != null) return paint
        paint = Paint()
        paint.color = color
        borderPaintByColor[color] = paint
        return paint
    }

    fun DrawBorderedRect(batch: Batch, rect: Rectangle, fillColor: Color, borderColor: Color, borderWidth: Float) {
        val borderPaint: Paint = getBorderPaint(borderColor)
        DrawBorderedRect(batch, rect, getFillPaint(fillColor), borderPaint, borderWidth)
    }

    var drawer: ShapeDrawer? = null
    fun DrawBorderedRect(batch: Batch, rect: Rectangle, fillPaint: Paint, borderPaint: Paint, borderWidth: Float) {

        if (drawer == null || drawer!!.batch != batch) {
            drawer = ShapeDrawer(batch, Textures.getWhiteTexture())
        }

        drawer!!.filledRectangle(rect, Color(fillPaint.color))
        drawer!!.rectangle(rect, Color(borderPaint.color), borderPaint.strokeWidth)

    }

}