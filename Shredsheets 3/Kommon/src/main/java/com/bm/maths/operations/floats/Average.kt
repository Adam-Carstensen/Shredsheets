package com.bm.maths.operations.floats

import com.bm.maths.Maths

class Average(b: Float) : FloatOperation(b) {

    override fun perform(a: Float, b: Float, impact: Float): Float {
        return Maths.average(a, b, impact)
    }
}