package com.bm.maths.ranges

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.Maths

class DistributedIntRange(override val start: Int, override val endInclusive: Int, distribution: Interpolation) : DistributedRange<Int, IntRange>(distribution) {
    override fun random(): Int {
        return Maths.randomInt(start, endInclusive, distribution)
    }
}