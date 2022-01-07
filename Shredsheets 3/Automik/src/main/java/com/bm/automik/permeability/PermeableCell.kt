package com.bm.automik.permeability

import com.badlogic.gdx.graphics.Pixmap
import com.bm.maths.clusters.MapClusters
import com.bm.maths.Maths
import com.bm.automik.Cell2D
import com.bm.automik.CellMatrix
import com.bm.common.HSLColor
import com.bm.maths.extensions.difference

import java.util.ArrayList
import java.util.HashMap

class PermeableCell(x: Float, y: Float, values: Collection<PermeableValue>) : Cell2D<PermeableCell, PermeableMatrixModel>(x, y) {

    var ValueMap: MutableMap<Int, PermeableValue> = HashMap()

    init {
        for (value in values) ValueMap[value.id] = value
    }

    override fun step(matrix: CellMatrix<PermeableCell, PermeableMatrixModel>, edgeAwareX: Boolean, edgeAwareY: Boolean) {
        val neighbors = MapClusters.getNeighbors(matrix.cells, x.toInt(), y.toInt(), edgeAwareX, edgeAwareY)
        for (i in neighbors.indices) {

            val neighbor = neighbors[i] ?: continue
            neighbor.push(this)
        }
    }

    override fun clone(): PermeableCell {
        val values = ArrayList<PermeableValue>()

        for (value in ValueMap.values) {
            values.add(value.clone())
        }

        return PermeableCell(x, y, values)
    }

    //diffuses the values from the passed Cell onto the values of this cell
    override fun push(cell: PermeableCell, impact: Float) {
        for (pushedValue in cell.ValueMap.values) {
            val localValue = ValueMap[pushedValue.id] ?: continue

            //can't modify a variable if no such variable exists
            if (Maths.randomBoolean(localValue.permeationProbability))
                continue //if the trigger isn't met for a cell to permeate into the neighbor, it's skipped

            localValue.value = Maths.average(localValue.value, pushedValue.value, impact)
        }
    }

    override fun draw(matrix: CellMatrix<PermeableCell, PermeableMatrixModel>, pixmap: Pixmap) {
        val rgb = HSLColor.toRGB(ValueMap[PermeableValues.HUE.id]!!.value, ValueMap[PermeableValues.SATURATION.id]!!.value, ValueMap[PermeableValues.LIGHTNESS.id]!!.value)
        pixmap.setColor(rgb.r, rgb.g, rgb.b, 1f)
        pixmap.drawPixel(x.toInt(), y.toInt())

    }

    override fun mask(alpha: Float) {
        if (alpha <= 0) ValueMap.clear()
    }

    override fun add(cell: PermeableCell, impact: Float) {
        for (myValue in ValueMap.values) {
            val cellValue = cell.ValueMap[myValue.id]
            if (cellValue != null) {
                myValue.value = myValue.value + cellValue.value * impact
            }
        }
    }

    override fun subtract(cell: PermeableCell, impact: Float) {
        for (myValue in ValueMap.values) {
            val cellValue = cell.ValueMap[myValue.id]
            if (cellValue != null) {
                myValue.value = myValue.value - cellValue.value * impact
            }
        }

    }

    override fun multiply(cell: PermeableCell, impact: Float) {
        for (myValue in ValueMap.values) {
            val cellValue = cell.ValueMap[myValue.id]
            if (cellValue != null) {
                myValue.value = Maths.average(myValue.value, myValue.value * cellValue.value, impact)
            }
        }

    }

    override fun divide(cell: PermeableCell, impact: Float) {
        for (myValue in ValueMap.values) {
            val cellValue = cell.ValueMap[myValue.id]
            if (cellValue != null) {
                myValue.value = Maths.average(myValue.value, myValue.value / cellValue.value, impact)
            }
        }
    }

    override fun average(cell: PermeableCell, impact: Float) {
        for (myValue in ValueMap.values) {
            val cellValue = cell.ValueMap[myValue.id]
            if (cellValue != null) {
                myValue.value = Maths.average(myValue.value, cellValue.value, impact)
            }
        }
    }

    override fun screen(cell: PermeableCell, impact: Float) {
        for (myValue in ValueMap.values) {
            val cellValue = cell.ValueMap[myValue.id]
            if (cellValue != null) {
                myValue.value = Maths.average(myValue.value, Maths.screen(myValue.value, cellValue.value), impact)
            }
        }
    }

    override fun difference(cell: PermeableCell, impact: Float): Float {
        var difference = 0f
        var metrics = 0f

        for (pushedValue in cell.ValueMap.values) {
            val localValue = ValueMap[pushedValue.id]
            metrics++
            if (localValue == null) continue
            if (localValue.minimum != pushedValue.minimum || localValue.maximum != pushedValue.maximum) return 0f
            val range = localValue.maximum - localValue.minimum
            val localDif = localValue.value.difference(pushedValue.value)
            difference += localDif / range
        }
        return difference / metrics
    }

    override fun getEmptyArray(size: Int): Array<PermeableCell?> {
        return arrayOfNulls(size)
    }
}
