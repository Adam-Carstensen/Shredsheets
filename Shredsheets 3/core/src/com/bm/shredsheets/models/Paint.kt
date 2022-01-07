package com.bm.shredsheets.models

import com.badlogic.gdx.graphics.Color

///Acts as a paint brush holding details about rendering geometries
class Paint {

    var style: Style = Style.STROKE

    var antiAliasFlag: Boolean = true

    var color: Color = Color.BLACK

    var strokeWidth: Float = 1f

    enum class Style(val nativeInt: Int) {
        FILL(0),
        STROKE(1),
        FILL_AND_STROKE(2);
    }


}