package com.bm.automik.levels

import com.badlogic.gdx.graphics.Pixmap
import com.bm.automik.Cell2D
import com.bm.automik.CellMatrix

class LevelCell(x: Float, y: Float, var levelComponent: LevelComponents = LevelComponents.Empty) : Cell2D<LevelCell, LevelMatrixModel>(x, y) {

    override fun getEmptyArray(size: Int): Array<LevelCell?> {
        return Array(size) { null }
    }

    override fun clone(): LevelCell {
        return LevelCell(x, y, levelComponent)
     }

    override fun push(cell: LevelCell, impact: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(cell: LevelCell, impact: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subtract(cell: LevelCell, impact: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun multiply(cell: LevelCell, impact: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun divide(cell: LevelCell, impact: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun average(cell: LevelCell, impact: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun screen(cell: LevelCell, impact: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(matrix: CellMatrix<LevelCell, LevelMatrixModel>, pixmap: Pixmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mask(alpha: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun difference(cell: LevelCell, impact: Float): Float {
        if (cell.levelComponent == levelComponent) return 1f
        return 0f
    }
}
