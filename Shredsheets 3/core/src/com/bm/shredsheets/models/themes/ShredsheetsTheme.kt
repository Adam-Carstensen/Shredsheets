package com.bm.shredsheets.models.themes

import com.badlogic.gdx.graphics.Color
import com.bm.shredsheets.models.Paint

abstract class ShredsheetsTheme {
    var defaultVector =
        booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false)
    abstract var degreeHighlightingVector: BooleanArray?

    abstract val borderPaint: Paint?
    abstract val runnerBorderColor: Color
    abstract val runnerBorderPaint: Paint?
    abstract val borderColor: Color
    abstract val scaleBorderPaint: Paint?
    abstract val degreeColors: Array<Color>

    abstract fun getBaseFretColor(fretNumber: Int): Color
    abstract fun getFretboardRunnerPaintColor(fretNumber: Int): Color

    //Got replaced by a maximal color difference algorithm... may come back
    abstract fun getFretboardRunnerTextColor(fretNumber: Int): Color
    abstract val borderStrokeWidth: Float
    abstract val intervalColors: Array<Color>
    abstract val emptyFretColor: Color
    abstract val fretboardBorderColor: Color
    abstract val textStrokeColor: Color

    companion object {
        val themes: Array<ShredsheetsTheme>
            get() = arrayOf(DefaultTheme())
    }
}
