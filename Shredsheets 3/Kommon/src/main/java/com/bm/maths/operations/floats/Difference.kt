package com.bm.maths.operations.floats

import com.bm.maths.extensions.difference
import com.bm.maths.Maths

class Difference(b: Float) : FloatOperation(b) {
    override fun perform(a: Float, b: Float, impact: Float): Float {
        return Maths.average(a, a.difference(b), impact)
    }
}
