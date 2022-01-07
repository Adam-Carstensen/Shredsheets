package com.bm.automik.alpha

import com.badlogic.gdx.graphics.Pixmap
import com.bm.maths.extensions.clamp
import com.bm.maths.extensions.difference
import com.bm.maths.Maths
import com.bm.automik.Cell2D
import com.bm.automik.CellMatrix

class AlphaCell(x: Float, y: Float, var value: Float) : Cell2D<AlphaCell, AlphaMatrixModel>(x, y) {
    var alphaPermeability = 0.5f

    private val gravityMode = true

    override fun clone(): AlphaCell {
        return AlphaCell(x, y, value)
    }

    override fun push(cell: AlphaCell, impact: Float) {
        average(cell, impact)
    }

    override fun draw(matrix: CellMatrix<AlphaCell, AlphaMatrixModel>, pixmap: Pixmap) {
        val color = (matrix as AlphaMatrix).color
        pixmap.setColor(color.r, color.g, color.b, value)
        pixmap.drawPixel(x.toInt(), y.toInt())
        //pixmap.fillRectangle(x.toInt(), y.toInt(), 1,1)
    }

    override fun mask(alpha: Float) {
        value = alpha
    }

    override fun add(cell: AlphaCell, impact: Float) {
        this.value += cell.value
        this.value = this.value.clamp(0f, 1f)
    }

    override fun subtract(cell: AlphaCell, impact: Float) {
        this.value -= cell.value
        this.value = this.value.clamp(0f, 1f)
    }

    override fun multiply(cell: AlphaCell, impact: Float) {
        this.value = Maths.average(this.value, this.value * cell.value, impact)
        this.value = this.value.clamp(0f, 1f)
    }

    override fun divide(cell: AlphaCell, impact: Float) {
        this.value = Maths.average(this.value, this.value / cell.value, impact)
        this.value = this.value.clamp(0f, 1f)
    }

    override fun average(cell: AlphaCell, impact: Float) {
        this.value = Maths.average(this.value, cell.value, impact)
        this.value = this.value.clamp(0f, 1f)
    }

    override fun screen(cell: AlphaCell, impact: Float) {
        this.value = Maths.average(this.value, Maths.screen(this.value, cell.value), impact)
        this.value = this.value.clamp(0f, 1f)
    }

    override fun difference(cell: AlphaCell, impact: Float): Float {
        return value.difference(cell.value) * impact
    }

    override fun getEmptyArray(size: Int): Array<AlphaCell?> {
        return arrayOfNulls<AlphaCell?>(size)
    }
}
