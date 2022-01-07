package com.bm.automik.vectors

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.Vector2
import com.bm.maths.extensions.difference
import com.bm.extensions.graphics.drawArrow
import com.bm.automik.Cell2D
import com.bm.automik.CellMatrix
import com.bm.maths.Maths
import kotlin.math.pow


class Vector2Cell(x: Float, y: Float, var vector: Vector2) : Cell2D<Vector2Cell, Vector2MatrixModel>(x, y) {
    override fun getEmptyArray(size: Int): Array<Vector2Cell?> {
        return Array(size) { null }
    }

    override fun clone(): Vector2Cell {
        return Vector2Cell(x, y, vector)
    }

    override fun push(cell: Vector2Cell, impact: Float) {
        vector.set(Maths.average (vector.x, cell.vector.x, 1f), Maths.average (vector.y, cell.vector.y, 1f))
    }

    override fun add(cell: Vector2Cell, impact: Float) {
        vector.set(vector.x + cell.vector.x * impact, vector.y + cell.vector.y * impact)
    }

    override fun subtract(cell: Vector2Cell, impact: Float) {
        vector.set(vector.x - cell.vector.x * impact, vector.y - cell.vector.y * impact)
    }

    override fun multiply(cell: Vector2Cell, impact: Float) {
        vector.set(vector.x * cell.vector.x * impact, vector.y * cell.vector.y * impact)
    }

    override fun divide(cell: Vector2Cell, impact: Float) {
        vector.set(vector.x / cell.vector.x * impact, vector.y / cell.vector.y * impact)
    }

    override fun average(cell: Vector2Cell, impact: Float) {
        vector.set(Maths.average (vector.x, cell.vector.x, 1f), Maths.average (vector.y, cell.vector.y, 1f))
    }

    override fun screen(cell: Vector2Cell, impact: Float) {
        vector.set(Maths.screen(vector.x, cell.vector.x, 1f), Maths.screen(vector.y, cell.vector.y, 1f))
    }

    override fun difference(cell: Vector2Cell, impact: Float): Float {
        return Math.sqrt((vector.x.difference(cell.vector.x).pow(2) + vector.y.difference(cell.vector.y).pow(2)).toDouble()).toFloat()
    }

    override fun draw(matrix: CellMatrix<Vector2Cell, Vector2MatrixModel>, pixmap: Pixmap) {
        pixmap.drawArrow(x, y, vector)
    }

    override fun mask(alpha: Float) {
        vector.set(vector.x * alpha, vector.y * alpha)
    }

}