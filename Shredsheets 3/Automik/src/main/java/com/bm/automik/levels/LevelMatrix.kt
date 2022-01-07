package com.bm.automik.levels

import com.bm.automik.CellMatrix
import com.bm.common.Size

class LevelMatrix(model: LevelMatrixModel) : CellMatrix<LevelCell, LevelMatrixModel>(model) {

//    private var matrixCells: Array<Array<LevelCell>> = generateMatrix(width, height)
//    override var cells: Array<Array<LevelCell>> = matrixCells

    constructor(model: LevelMatrixModel, cells: Array<Array<LevelCell>>) : this(model) {
        this.cells = cells
    }

    override fun generate(): LevelCell {
        return LevelCell(0f,0f)
    }

    override fun generateMatrix(size: Size): Array<Array<LevelCell>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clone(): CellMatrix<LevelCell, LevelMatrixModel> {
        var newMatrix = LevelMatrix(model, cells)
        return newMatrix
    }

    override fun alphaCompare(cell: LevelCell): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}