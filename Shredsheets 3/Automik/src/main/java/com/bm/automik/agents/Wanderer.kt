package com.bm.automik.agents

import com.bm.maths.clusters.MapClusters
import com.bm.automik.Cell2D
import com.bm.automik.MatrixModel
import com.bm.maths.Maths
import com.bm.maths.operations.Operations

abstract class Wanderer<T : Cell2D<T, TModel>, TModel : MatrixModel>
(var operationStack: Array<Operations>, var entropy: Float, val impact: Float, edgeAwareX: Boolean, edgeAwareY: Boolean)
    : Agent<T, TModel>(edgeAwareX, edgeAwareY) {
    var wanderingCell: T

    var clusterNeighbors = getEmptyArray(8)
    override fun getDirection(cells: Array<Array<T>>): Int {
        val neighbors = MapClusters.getNeighbors(cells, x.toInt(), y.toInt(), edgeAwareX, edgeAwareY, clusterNeighbors)
        val starting = Maths.randomIndex(0, neighbors.size - 1)
        var i = starting
        while (i - starting < neighbors.size) {
            val neighbor = neighbors[i % neighbors.size]
            if (neighbor != null) return i
            i++
        }
        return -1
    }

    init {
        wanderingCell = getNewWanderingCell()
    }

    abstract fun getNewWanderingCell(): T
    abstract fun modify(cell: T, entropy: Float): T

    override fun pathIntersected(): Boolean {
        return true
    }

    override fun act(target: T, a: Float) {
        var cell = getNewWanderingCell()
        cell = modify(cell, entropy)

        val totalImpact = impact * a
        for (operation in operationStack) {
            when (operation) {
                Operations.ADD -> target.add(cell, totalImpact)
                Operations.SUBTRACT -> target.subtract(cell, totalImpact)
                Operations.MULTIPLY -> target.multiply(cell, totalImpact)
                Operations.DIVIDE -> target.divide(cell, totalImpact)
                Operations.AVERAGE -> target.average(cell, totalImpact)
                Operations.SCREEN -> target.screen(cell, totalImpact)
            }
        }
    }
}
