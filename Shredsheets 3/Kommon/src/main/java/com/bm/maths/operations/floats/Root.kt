package com.bm.maths.operations.floats

import com.bm.maths.Maths

class Root(b: Float) : FloatOperation(b) {

    override fun perform(a: Float, b: Float, impact: Float): Float {
        return Maths.average(a, Math.pow(Math.E, Math.log(a.toDouble()) / b).toFloat(), impact)
    }
}
