package com.bm.maths

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Quaternion

class InterpolationPair(val left: Interpolation, val right: Interpolation, val invertRight: Boolean = true) : Interpolation() {

    override fun apply(a: Float): Float {
        var effectiveAlpha: Float
        if (a <= .5f) { //if we're on the first half, use the 'left' interpolation
            effectiveAlpha = a * 2f
            if (invertRight) return left.apply(0f, 1f, effectiveAlpha) //end at the high point
            return left.apply(0f, 1f, effectiveAlpha) * .5f //end at the midPoint
        } else { //if we're on the second half, use the 'right' interpolation
            effectiveAlpha = (a - .5f) * 2f
            if (invertRight) return 1f - right.apply(0f, 1f, effectiveAlpha) //start at the high point
            return right.apply(0f, 1f, effectiveAlpha) * .5f + .5f //start at the midPoint
        }
    }

    companion object {
        val NormalDistribution = InterpolationPair(pow2Out, pow2In)
    }
}
