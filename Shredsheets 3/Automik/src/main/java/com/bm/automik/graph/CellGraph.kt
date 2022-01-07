package com.bm.automik.graph

import java.util.ArrayList

abstract class CellGraph<T : GraphCell<T>>(private val baseCells: Float, private val divisions: Float, private val entropy: Float) {

    internal var cells: ArrayList<T>

    init {
        cells = generateCells()
    }

    fun generateCells(): ArrayList<T> {
        cells = ArrayList()

        run {
            var i = 0
            while (i < baseCells) {
                val cell = generateCell()
                cells.add(cell)
                i++
            }
        }

        for (i in cells.indices) {
            val a = cells[i]
            for (j in i + 1 until cells.size) {
                val b = cells[j]
                if (a !== b) {
                    a.addEdge(b)
                }
            }
        }

        var i = 0
        while (i < divisions) {
            for (cell in cells) {
                val clone = cell.clone(false)
                clone.addEdge(cell)
            }
            i++
        }

        return cells
    }

    abstract fun generateCell(): T

}
