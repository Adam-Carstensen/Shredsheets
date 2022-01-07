package com.bm.resources

import com.badlogic.gdx.math.Interpolation

object Daylight {

    fun GetLightness(time: Float): Float {
        if (time < 0 || time > 1) return 1f

        if (time < .5f) {
            return Interpolation.fade.apply(time * 2)
        }
        val timeDifference = time - .5f
        return Interpolation.fade.apply(1 - timeDifference * 2)
    }

}
