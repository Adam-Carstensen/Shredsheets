package com.bm.automik.alpha

import com.badlogic.gdx.graphics.Color
import com.bm.automik.CellMatrix
import com.bm.common.Size


class AlphaMatrix(model: AlphaMatrixModel) : CellMatrix<AlphaCell, AlphaMatrixModel>( model) {

//    private var matrixCells: Array<Array<AlphaCell>> = generateMatrix(width, height)
//    override var cells: Array<Array<AlphaCell>> = matrixCells

    var color = Color.BLACK

    fun getAlphaMap(): Array<FloatArray> {
        val map = Array(cells.size) { FloatArray(cells[0].size) }
        for (y in cells.indices)
            for (x in 0 until cells[y].size) {
                map[y][x] = cells[y][x].value
            }

        return map
    }

    fun getAlphaMap(cutoff: Float): Array<FloatArray> {
        val map = Array(cells.size) { FloatArray(cells[0].size) }
        for (y in cells.indices)
            for (x in cells[y].indices) {
                if (cells[y][x].value > cutoff) {
                    map[y][x] = 1f
                } else {
                    map[y][x] = 0f
                }
            }

        return map
    }

    override fun alphaCompare(cell: AlphaCell): Boolean {
        return cell.value >= model.alphaThreshold
    }

    override fun generate(): AlphaCell {
        return AlphaCell(0f, 0f, 0f)
    }

    override fun generateMatrix(size: Size): Array<Array<AlphaCell>> {
        return Array(size.height) { y -> Array(size.width) { x -> AlphaCell(x.toFloat(), y.toFloat(), model.map[y][x]) } }
    }

    override fun clone(): CellMatrix<AlphaCell, AlphaMatrixModel> {
        val copy = AlphaMatrix(model)
        copy.cells = cloneCells()
        return copy
    }


}
