package com.bm.automik.agents

import com.bm.maths.clusters.MapClusters
import com.bm.maths.extensions.randomIndex
import com.bm.maths.extensions.rotate
import com.bm.maths.extensions.setProbability
import com.bm.automik.Cell2D
import com.bm.automik.MatrixModel
import com.bm.maths.Maths
import com.bm.maths.operations.Operations

abstract class DirectionBiasedWanderer<T : Cell2D<T, TModel>, TModel : MatrixModel> : Wanderer<T, TModel> {
    constructor(operationStack: Array<Operations>, entropy: Float, impact: Float, edgeAwareX: Boolean, edgeAwareY: Boolean) : super(operationStack, entropy, impact, edgeAwareX, edgeAwareY) {
        this.directionBias = DEFAULT_BIAS
    }

    constructor(operationStack: Array<Operations>, entropy: Float, impact: Float, directionBias: FloatArray, edgeAwareX: Boolean, edgeAwareY: Boolean) : super(operationStack, entropy, impact, edgeAwareX, edgeAwareY) {
        this.directionBias = directionBias
    }

    private var directionBias: FloatArray

    var rememberDirection = false
    var directionShiftProbability = 0f

    var biasedClusterNeighbors = getEmptyArray(8)

    override fun getDirection(cells: Array<Array<T>>): Int {
        val neighbors = MapClusters.getNeighbors(cells, x.toInt(), y.toInt(), edgeAwareX, edgeAwareY, biasedClusterNeighbors)
        val starting = directionBias.randomIndex()
        if (Maths.randomBoolean(directionShiftProbability)) directionBias = directionBias.rotate(1)

        var i = starting
        while (i - starting < neighbors.size) {
            val awareI = i % neighbors.size
            val neighbor = neighbors[awareI]
            if (neighbor != null) {
                if (rememberDirection) {
                    val bias = directionBias[awareI]
                    directionBias.setProbability(awareI, bias + (1 - bias) * .2f)
                }
                return i
            }
            i++
        }
        return -1
    }

    companion object {
        const val defaultBias = 1f / 8f

        //bottom left counter clockwise
        private val DEFAULT_BIAS = floatArrayOf(defaultBias, defaultBias, defaultBias, defaultBias, defaultBias, defaultBias, defaultBias, defaultBias)
    }
}
