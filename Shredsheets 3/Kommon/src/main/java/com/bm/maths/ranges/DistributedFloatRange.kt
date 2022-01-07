package com.bm.maths.ranges

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.Maths

class DistributedFloatRange(override val start: Float, override val endInclusive: Float) : DistributedRange<Float, FloatRange>() {

    constructor(start: Float, endInclusive: Float, distribution: Interpolation) : this(start, endInclusive) {
        this.distribution = distribution
    }

    override fun random(): Float {
        return Maths.randomFloat(start, endInclusive, distribution)
    }
}