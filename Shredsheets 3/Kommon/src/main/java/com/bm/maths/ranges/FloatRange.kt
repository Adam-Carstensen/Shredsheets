package com.bm.maths.ranges

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.Maths

class FloatRange(override val start: Float, override val endInclusive: Float) : ClosedRange<Float>
{
    //override fun contains(value: Float): Boolean = value in start..endInclusive

    open fun random(): Float {
        return Maths.randomFloat(start, endInclusive)
    }

    open fun random(distribution: Interpolation): Float {
        return Maths.randomFloat(start, endInclusive, distribution)
    }

    companion object {
        /** An empty range of values of type Int. */
        val empty: FloatRange = FloatRange(0f, 1f)
    }
}


