package com.bm.maths.operations.floats

import com.bm.maths.Maths

class Pow(b: Float) : FloatOperation(b) {
    override fun perform(a: Float, b: Float, impact: Float): Float {
        return Maths.average(a, Math.pow(a.toDouble(), b.toDouble()).toFloat(), impact)
    }
}
