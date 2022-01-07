package com.bm.automik.graph

import com.bm.automik.Cell
import com.bm.automik.MatrixModel
import com.bm.common.Size

import java.util.ArrayList

class CellGraphModel(size: Size, matrixName: String) : MatrixModel(size, matrixName) {

}


abstract class GraphCell<T : GraphCell<T>> : Cell<T, CellGraphModel>() {

    private var cellEdges: ArrayList<CellEdge<*>>? = null
    fun getCellEdges(): ArrayList<CellEdge<*>> {
        if (cellEdges == null) {
            cellEdges = ArrayList<CellEdge<*>>()
        }
        return cellEdges!!
    }

    fun addEdge(cell: T) {
        val edge = CellEdge(this, cell)
        getCellEdges().add(edge)
        cell.mutualEdge(edge)
    }

    protected fun mutualEdge(edge: CellEdge<*>) {
        getCellEdges().add(edge)
    }

    fun clone(retainEdges: Boolean): T {
        val item = clone()
        if (!retainEdges) item.getCellEdges().clear()
        return item
    }
}

