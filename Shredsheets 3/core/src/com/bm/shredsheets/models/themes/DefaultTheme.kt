package com.bm.shredsheets.models.themes

import com.badlogic.gdx.graphics.Color
import com.bm.shredsheets.models.Paint
import com.bm.shredsheets.models.SessionModel


class DefaultTheme() : ShredsheetsTheme() {
    private var highlightedDegrees: BooleanArray? = null

    //80A8C9
    //3788CB
    //6AD81C
    //2943C6
    //642C76
    override var degreeColors: Array<Color> = arrayOf(
            Color(55f / 255f, 136f / 255f, 203f / 255f, 1f),  //80A8C9
            Color(211f / 255f, 181f / 255f, 233f / 255f, 1f),
            Color(244f / 255f, 218f / 255f, 62f / 255f, 1f),  //3788CB
            Color(181f / 255f, 103f / 255f, 245f / 255f, 1f),  //6AD81C
            Color(117f / 255f, 230f / 255f, 4f / 255f, 1f),  //2943C6
            Color(100f / 255f, 44f / 255f, 118f / 255f, 1f),  //642C76
            Color(255f / 255f, 102f / 255f, 255f / 255f, 1f),
            Color(175f / 255f, 225f / 255f, 170f / 255f, 1f),
            Color(100f / 255f, 225f / 255f, 90f / 255f, 1f),
            Color(30f / 255f, 225f / 255f, 10f / 255f, 1f),
            Color(225f / 255f, 100f / 255f, 10f / 255f, 1f),
            Color(225f / 255f, 100f / 255f, 75f / 255f, 1f))

    override var borderColor: Color = Color(.75f, .75f, .75f, 1f)
    override var borderStrokeWidth: Float = 2f
    override var runnerBorderColor: Color = Color(.65f, .65f, .75f, 1f)
    var runnerStrokeWidth: Float = 3.5f

    override var intervalColors: Array<Color> = arrayOf(
                Color(.35f, .35f, .35f, 1f),
                Color(.55f, .55f, .55f, 1f),
                Color(.75f, .75f, .75f, 1f),
                Color(.75f, .75f, .75f, 1f),
                Color(.75f, .75f, .75f, 1f),
                Color(.75f, .75f, .75f, 1f),
                Color(.75f, .75f, .75f, 1f))

    //supports up to max 12 degrees
    override var degreeHighlightingVector: BooleanArray?
        get() {
            return if (highlightedDegrees != null) highlightedDegrees else (defaultVector.also {
                highlightedDegrees = it
            }) //supports up to max 12 degrees
        }
        set(values) {
            highlightedDegrees = values
            SessionModel.instance.invalidateViews()
        }

    override var borderPaint: Paint? = null
        get() {
            if (field == null) {
                field = Paint()
                field!!.color = borderColor
            }
            return field
        }

    override var runnerBorderPaint: Paint? = null
        get() {
            if (field == null) {
                field = Paint()
                field!!.color = runnerBorderColor
            }
            return field
        }


    override var scaleBorderPaint: Paint? = null
        get() {
            if (field == null) {
                field = Paint()
                field!!.color = Color(.78f, .78f, .78f, 1f)
                field!!.strokeWidth = 10f
            }
            return scaleBorderPaint
        }


    override fun getBaseFretColor(fretNumber: Int): Color {
        when (fretNumber) {
            0, 12, 24 -> return  Color(.61f, .61f, .61f, 1f)
            3, 5, 7, 9, 15, 17, 19, 21 -> return Color(.81f, .81f, .88f, 1f)
            else -> return Color(.93f, .93f, .93f, 1f)
        }
    }

    override fun getFretboardRunnerPaintColor(fretNumber: Int): Color {
        when (fretNumber) {
            0, 12, 24 -> return Color(.25f, .25f, .25f, 1f)
            3, 5, 7, 9, 15, 17, 19, 21 -> return Color(.36f, .36f, .36f, 1f)
            else -> return Color(.93f, .93f, .93f, 1f)
        }
    }

    //Got replaced by a maximal color difference algorithm... may come back
    override fun getFretboardRunnerTextColor(fretNumber: Int): Color {
        when (fretNumber) {
            0, 12, 24 -> return Color(.2f, .2f, .2f, 1f)
            3, 5, 7, 9, 15, 17, 19, 21 -> return Color(.34f, .34f, .34f, 1f)
            else -> return Color(.5f, .5f, .5f, 1f)
        }
    }

    override val emptyFretColor: Color
        get() {
            return Color(.10f, .10f, .10f, 1f)
        }
    override val fretboardBorderColor: Color
        get() {
            return Color(.02f, .02f, .02f, 1f)
        }
    override val textStrokeColor: Color
        get() {
            return Color(.05f, .05f, .05f, 1f)
        }
}
