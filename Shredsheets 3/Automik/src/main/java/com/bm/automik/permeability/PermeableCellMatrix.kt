package com.bm.automik.permeability

import com.bm.automik.CellMatrix
import com.bm.automik.MatrixModel
import com.bm.common.Size

import java.util.ArrayList

class PermeableMatrixModel(size: Size, matrixName: String) : MatrixModel(size, matrixName)

class PermeableCellMatrix(model: PermeableMatrixModel) : CellMatrix<PermeableCell, PermeableMatrixModel>(model) {

    override var cells: Array<Array<PermeableCell>> = generateMatrix(model.size)

    override fun alphaCompare(cell: PermeableCell): Boolean {
        return cell.ValueMap.get(PermeableValues.ALPHA.id)!!.value > 0f
    }

    override fun generate(): PermeableCell {
        return PermeableCell(0f, 0f, ArrayList())
    }

    override fun generateMatrix(size: Size): Array<Array<PermeableCell>> {
        return Array(size.height) { Array(size.width) { generate() } }
    }

    override fun clone(): CellMatrix<PermeableCell, PermeableMatrixModel> {
        val copy = PermeableCellMatrix(model)
        copy.cells = cloneCells()
        return copy
    }
}
