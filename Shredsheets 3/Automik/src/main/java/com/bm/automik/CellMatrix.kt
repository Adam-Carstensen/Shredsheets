package com.bm.automik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.PixmapIO
import com.bm.automik.Cell
import com.bm.common.Size
import com.bm.maths.clusters.MapClusters
import com.bm.maths.extensions.getIndex
import com.bm.maths.extensions.relativePosition
import com.bm.maths.Maths


abstract class CellMatrix<T : Cell<T, TModel>, TModel : MatrixModel>(val model: TModel) {

    var matrixCells: Array<Array<T>> = generateMatrix(model.size)
    open var cells: Array<Array<T>> = matrixCells

    constructor(model: TModel, cells: Array<Array<T>>) : this(model) {
        this.cells = cells
   }

    val count: Int
        get() {
            val cells = cells
            return cells.size * cells[0].size
        }

    var matrixPixmap: Pixmap? = null

    fun step(stepCount: Int, edgeAwareX: Boolean, edgeAwareY: Boolean, orderedSteps: Boolean = false) {
        var i = 0
        while (i < stepCount) {
            if (orderedSteps) orderedStep(edgeAwareX, edgeAwareY);
            else randomStep(edgeAwareX, edgeAwareY)
            i++
        }
    }

    private fun randomStep(edgeAwareX: Boolean, edgeAwareY: Boolean) {
        val matrix = this

        val cells = cells
        val locations = Maths.getRandomMatrixLocations(model.size.width, model.size.height)
        for (location in locations) {
            cells[location.y.toInt()][location.x.toInt()].step(matrix, edgeAwareX, edgeAwareY)
        }
    }

    private fun orderedStep(edgeAwareX: Boolean, edgeAwareY: Boolean) {
        val cells = cells
        for (y in cells.indices)
            for (x in 0 until cells[y].size)
                cells[y][x].step(this, edgeAwareX, edgeAwareY)
    }

    var pixmapFilter: Pixmap.Filter = Pixmap.Filter.BiLinear

    var writeTexture: Boolean = false

    fun getPixmap(): Pixmap {
        if (matrixPixmap == null) {
            val matrixPixmap = Pixmap(model.size.width, model.size.height, Pixmap.Format.RGBA8888)

            for (column in cells) {
                for (cell in column) {
                    cell.draw(this, matrixPixmap)
                }
            }

            if (writeTexture) {
                val file = Gdx.files.local("${model.matrixName} - ${System.currentTimeMillis()}.png")
                PixmapIO.writePNG(file, matrixPixmap)
            }

            return matrixPixmap
        }
        return matrixPixmap!!
    }

//    fun getDifferenceMap(matrix: CellMatrix<T, TModel>): Array<FloatArray> {
//        val newItem = generate()
//        val alphaMap = Array(height) { FloatArray(width) }
//        val cells = cells
//        val newCells = generateMatrix(width, height)
//        return difference(cells, newCells, 1f)
//    }

    fun getAlphaMap(matrix: CellMatrix<T, TModel>): Array<FloatArray> {
        val alphaMap = Array(model.size.height) { FloatArray(model.size.width) }
        val cells = cells
        for (y in cells.indices) {
            val column = cells[y]
            for (x in column.indices) {
                val cell = column[x]
                alphaMap[y][x] = (if (alphaCompare(cell)) 1 else 0).toFloat()
                cell.draw(matrix, matrixPixmap!!)
            }
        }
        return alphaMap
    }

    fun difference(aCells: Array<Array<T>>, bCells: Array<Array<T>>, impact: Float): Array<FloatArray> {
        val diffs = Array(aCells.size) { FloatArray(aCells[0].size) }
        for (y in aCells.indices)
            for (x in aCells[0].indices) {
                diffs[y][x] = aCells[y][x].difference(bCells[y][x], impact)
            }
        return diffs
    }

    fun add(b: CellMatrix<T, TModel>, impact: Float) {
        add(cells, b.cells, impact)
    }

    fun add(a: Array<Array<T>>, b: Array<Array<T>>, impact: Float): Array<Array<T>> {
        for (y in a.indices) {
            for (x in a[0].indices) {
                a[y][x].add(b[y][x], impact)
            }
        }
        return a
    }

    fun subtract(b: CellMatrix<T, TModel>, impact: Float) {
        subtract(cells, b.cells, impact)
    }

    fun subtract(a: Array<Array<T>>, b: Array<Array<T>>, impact: Float): Array<Array<T>> {
        for (y in a.indices) {
            for (x in a[0].indices) {
                a[y][x].subtract(b[y][x], impact)
            }
        }
        return a
    }

    fun multiply(b: CellMatrix<T, TModel>, impact: Float) {
        multiply(cells, b.cells, impact)
    }

    fun multiply(a: Array<Array<T>>, b: Array<Array<T>>, impact: Float): Array<Array<T>> {
        val result = generateMatrix(Size(a[0].size, a.size))

        for (aY in a.indices) {
            val aRow = a[aY]
            val aRowVector = getRowVector(a, aY)

            val relativeY = aY.relativePosition(a.size)
            val yIndex = relativeY.getIndex(a)

            for (aX in aRow.indices) {

                val relativeX = aX.relativePosition(aRow.size)
                val xIndex = relativeX.getIndex(aRow)

                val columnVector = getColumnVector(b, xIndex)
                result[yIndex][xIndex] = dotProduct(aRowVector, columnVector, impact)
            }
        }
        //a[y][x].multiply(b[y][x], impact);
        return result
    }

    fun dotProduct(a: Array<T>, b: Array<T>, impact: Float): T {
        var result: T? = null
        for (i in a.indices) {
            val aItem = a[i]

            for (j in b.indices) {
                val bItem = b[j]

                aItem.multiply(bItem, impact)
                if (result == null)
                    result = aItem
                else
                    result.add(aItem, impact)
            }
        }

        return result as T
    }

    fun getColumnVector(matrix: Array<Array<T>>, columnIndex: Int): Array<T> {
        val column = arrayOfNulls<Any>(matrix.size)
        for (y in matrix.indices) {
            val row = matrix[y]
            column[y] = row[columnIndex]
        }
        return column as Array<T>
    }

    fun getRowVector(matrix: Array<Array<T>>, y: Int): Array<T> {
        return matrix[y]
    }

    fun divide(b: CellMatrix<T, TModel>, impact: Float) {
        divide(cells, b.cells, impact)
    }

    fun divide(a: Array<Array<T>>, b: Array<Array<T>>, impact: Float): Array<Array<T>> {
        for (y in a.indices) {
            for (x in a[0].indices) {
                a[y][x].multiply(b[y][x], impact)
            }
        }
        return a
    }

    fun screen(b: CellMatrix<T, TModel>, impact: Float) {
        screen(cells, b.cells, impact)
    }

    fun screen(a: Array<Array<T>>, b: Array<Array<T>>, impact: Float): Array<Array<T>> {
        for (y in a.indices) {
            for (x in a[0].indices) {
                a[y][x].screen(b[y][x], impact)
            }
        }
        return a
    }

    fun mask(maskMatrix: Array<FloatArray>) {
        val cells = cells

        var relativeX: Float
        var relativeY: Float

        for (y in cells.indices) {
            val row = cells[y]
            relativeY = y.toFloat() / cells.size.toFloat()
            val maskRow = maskMatrix[relativeY.getIndex(maskMatrix)]

            for (x in row.indices) {
                val cell = row[x]
                relativeX = x.toFloat() / row.size.toFloat()
                val maskValue = maskRow[relativeX.getIndex(maskRow)]
                cell.mask(maskValue)
            }
        }
    }

    abstract fun alphaCompare(cell: T): Boolean

    protected abstract fun generate(): T

    protected abstract fun generateMatrix(size: Size): Array<Array<T>>

    fun getCell(x: Int, y: Int, direction: Int): T {
        return MapClusters.getNeighbor(cells, x, y, direction)!!
    }

    abstract fun clone(): CellMatrix<T, TModel>

    fun cloneCells(): Array<Array<T>> {
        val copy = cells.clone()
        for (y in cells.indices) {
            for (x in cells[y].indices) {
                copy[y][x] = cells[y][x].clone()
            }
        }
        return copy
    }

}
