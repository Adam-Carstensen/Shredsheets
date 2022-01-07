package com.bm.shredsheets

import com.badlogic.gdx.graphics.Color
import com.bm.shredsheets.models.SessionModel
import com.bm.shredsheets.models.themes.ShredsheetsTheme

object ColorModule {

    var darkColor = Color(.15f, .15f, .15f, 1f)
    var lightColor = Color(.925f, .925f, .925f, 1f)

    fun getMaximallyContrastingColor(color: Color): Color {
        var hsv = FloatArray(3)
        color.toHsv(hsv)

        if (hsv[2] > .5)
            return darkColor
        else
            return lightColor
    }

    fun getModeratelyContrastingColor(color: Int): Color {
        var colorItem = Color(color)
        val y = 299 * colorItem.r + 587 * colorItem.g + 114 * colorItem.b.toDouble() / 1000.toDouble()
        return if (y >= 128) Color(.35f, .35f, .35f, 1f) else Color(.8f, .8f, .8f, 1f)
    }

    var minDark = Color(.4f, .4f, .4f, 1f)
    var minLight = Color(.6f, .6f, .6f, 1f)
    var midDark = Color(.3f, .3f, .3f, 1f)

    fun getMinimallyContrastingColor(color: Color): Color {
        var hsv = FloatArray(3)
        color.toHsv(hsv)

        if (hsv[2] <= .4f)
            return minLight
        else if (hsv[2] >= .6f)
            return minDark
        else
            return midDark
    }

    fun getIntervalColor(intervalName: String?): Color {
        val session: SessionModel = SessionModel.instance
        val theme: ShredsheetsTheme = session.theme

        val intervalColors: Array<Color> = theme.intervalColors
        return when (intervalName) {
            "h" -> intervalColors[0]
            "W" -> intervalColors[1]
            "W+h" -> intervalColors[2]
            else -> intervalColors[2]
        }
    }
}