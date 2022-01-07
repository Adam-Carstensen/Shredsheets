package com.bm.automik.agents

import com.bm.automik.Cell2D
import com.bm.automik.MatrixModel
import com.bm.automik.kernels.Kernel
import com.bm.automik.kernels.PointKernel
import com.bm.maths.Maths
import com.bm.common.NeighborDirections

abstract class Agent<T : Cell2D<T, TModel>, TModel : MatrixModel>(var edgeAwareX: Boolean, var edgeAwareY: Boolean) {

    protected var x: Float = 0f
    protected var y: Float = 0f

    var kernel: Kernel = PointKernel.Default

    abstract fun getEmptyArray(size: Int): Array<T?>
    abstract fun getDirection(cells: Array<Array<T>>): Int
    abstract val stepSize: Int

    fun meander(cells: Array<Array<T>>, steps: Int): Boolean {
        val startX = Maths.randomIndex(0, cells[0].size)
        val startY = Maths.randomIndex(0, cells.size / 2)
        return meander(cells, startX, startY, steps)
    }

    fun meander(cells: Array<Array<T>>, revolutions: Int, stepsPerRevolution: Int): Boolean {
        for (i in 0 until revolutions) {
            val startX = Maths.randomIndex(0, cells[0].size - 1)
            val startY = Maths.randomIndex(0, cells.size - 1)
            if (!meander(cells, startX, startY, stepsPerRevolution)) return false
        }
        return true
    }

    fun meander(cells: Array<Array<T>>, revolutions: Int, stepsPerRevolution: Int, startX: Int, startY: Int): Boolean {
        for (i in 0 until revolutions) {
            if (!meander(cells, startX, startY, stepsPerRevolution)) return false
        }
        return true
    }

    fun meander(cells: Array<Array<T>>, startX: Int, startY: Int, steps: Int): Boolean {
        x = startX.toFloat()
        y = startY.toFloat()

        val brushMap = kernel.map

        var step = 0
        while (step < steps) {
            val direction = getDirection(cells)
            if (direction == -1) {
                if (!pathIntersected()) break
                step += stepSize
                continue
            }

            var targetX = x.toInt()
            var targetY = y.toInt()

            for (brushY in brushMap.indices) {

                val brushMapRow = brushMap[brushY]
                var brushTargetY = targetY + brushY

                if (edgeAwareY) brushTargetY %= cells.size
                if (brushTargetY >= cells.size) break

                val brushTargetRow = cells[brushTargetY]
                for (brushX in brushMapRow.indices) {

                    val targetImpact = brushMapRow[brushX]
                    if (targetImpact == 0f) continue

                    var brushTargetX = targetX + brushX
                    if (edgeAwareX) brushTargetX %= brushTargetRow.size
                    if (brushTargetX >= brushTargetRow.size) break

                    val brushTarget = brushTargetRow[brushTargetX]
                    act(brushTarget, targetImpact)
                }
            }

            var i = 0
            while (i < stepSize) {
                targetX = (cells[0].size + NeighborDirections.GetNeighborX(targetX, direction)) % cells[0].size
                targetY = (cells.size + NeighborDirections.GetNeighborY(targetY, direction)) % cells.size
                i++
            }

            x = targetX.toFloat()
            y = targetY.toFloat()
            step += stepSize
        }

        return true
    }

    abstract fun pathIntersected(): Boolean

    abstract fun act(target: T, a: Float)


}
