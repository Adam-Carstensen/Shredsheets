package com.bm.maths

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.operations.Operation

class ConstrainedFloat : ConstrainedValue<Float?> {

    private var interpolation: Interpolation? = null

    @JvmOverloads
    constructor(minimum: Float?, maximum: Float?, interpolation: Interpolation = Interpolation.linear) : super(minimum, maximum) {
        this.interpolation = interpolation
    }

    @JvmOverloads
    constructor(minimum: Float?, maximum: Float?, value: Float?, interpolation: Interpolation = Interpolation.linear) : super(minimum, maximum, value) {
        this.interpolation = interpolation
    }

    fun setRandom(): Float? {
        this.value = Maths.randomFloat(minimum!!, maximum!!, interpolation!!)
        return this.value!!
    }

    override fun clone(operation: Operation<Float?>): Constraint<Float?> {
        return ConstrainedFloat(minimum, maximum, value)
    }
}
