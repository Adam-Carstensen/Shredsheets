package com.bm.automik

import com.badlogic.gdx.graphics.Pixmap
import com.bm.maths.operations.Operation
import java.util.Queue

abstract class Cell<T : Cell<T, TModel>, TModel : MatrixModel> {

    abstract fun step(matrix: CellMatrix<T, TModel>, operationQueue: Queue<Operation<T>>, impact: Float, edgeAwareX: Boolean, edgeAwareY: Boolean)

    //diffuses the values from the passed Cell onto the values of this cell
    abstract fun step(matrix: CellMatrix<T, TModel>, edgeAwareX: Boolean, edgeAwareY: Boolean)

    fun push(cell: T) {
        push(cell, 1f)
    }

    abstract fun clone(): T

    abstract fun push(cell: T, impact: Float)

    abstract fun add(cell: T, impact: Float)

    abstract fun subtract(cell: T, impact: Float)

    abstract fun multiply(cell: T, impact: Float)

    abstract fun divide(cell: T, impact: Float)

    abstract fun average(cell: T, impact: Float)

    abstract fun screen(cell: T, impact: Float)

    abstract fun difference(cell: T, impact: Float): Float

    abstract fun draw(matrix: CellMatrix<T, TModel>, pixmap: Pixmap)

    abstract fun mask(alpha: Float)
}
