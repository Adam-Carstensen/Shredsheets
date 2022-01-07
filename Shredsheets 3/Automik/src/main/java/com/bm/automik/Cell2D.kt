package com.bm.automik

import com.bm.automik.Cell
import com.bm.maths.clusters.MapClusters
import com.bm.maths.operations.Operation
import java.util.*

abstract class Cell2D<T : Cell<T, TModel>, TModel : MatrixModel>(var x: Float, var y: Float) : Cell<T, TModel>() {

    abstract fun getEmptyArray(size: Int): Array<T?>

    var neighborArray: Array<T?>

    init {
        neighborArray = getEmptyArray(8)
    }

    override fun step(matrix: CellMatrix<T, TModel>, operationQueue: Queue<Operation<T>>, impact: Float, edgeAwareX: Boolean, edgeAwareY: Boolean) {
        val neighbors = MapClusters.getNeighbors(matrix.cells, x.toInt(), y.toInt(), edgeAwareX, edgeAwareY, neighborArray)

        for (i in neighbors.indices) {
            val neighbor = neighbors[i] ?: continue
            for (operation in operationQueue) {
                operation.perform(this as T, neighbor, impact)
            }
        }
    }

    //diffuses the values from the passed Cell onto the values of this cell
    override fun step(matrix: CellMatrix<T, TModel>, edgeAwareX: Boolean, edgeAwareY: Boolean) {
        val neighbors = MapClusters.getNeighbors(matrix.cells, x.toInt(), y.toInt(), edgeAwareX, edgeAwareY, neighborArray)

        for (i in neighbors.indices) {
            val neighbor = neighbors[i] ?: continue
            neighbor.push(this as T)
        }
    }
}
