package com.bm.automik.vectors

import com.badlogic.gdx.math.Vector2
import com.bm.automik.CellMatrix
import com.bm.automik.MatrixModel
import com.bm.common.Size

class Vector2MatrixModel(size: Size, matrixName: String) : MatrixModel(size, matrixName) {

}

class Vector2Matrix(model: Vector2MatrixModel) : CellMatrix<Vector2Cell, Vector2MatrixModel>(model) {
    override fun alphaCompare(cell: Vector2Cell): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generate(): Vector2Cell {
        return Vector2Cell(0f,0f, Vector2())
    }

    override fun generateMatrix(size: Size): Array<Array<Vector2Cell>> {
        return Array(size.height) { y -> Array(size.width) { x -> Vector2Cell(x.toFloat(), y.toFloat(), Vector2()) } }
    }

    override fun clone(): CellMatrix<Vector2Cell, Vector2MatrixModel> {
        val copy = Vector2Matrix(model)
        copy.cells = cloneCells()
        return copy
    }

    override var cells: Array<Array<Vector2Cell>> = generateMatrix(model.size)

}