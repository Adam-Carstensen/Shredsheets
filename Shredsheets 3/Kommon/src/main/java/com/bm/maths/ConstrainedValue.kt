package com.bm.maths

import com.bm.maths.operations.Operation

open class ConstrainedValue<T> : Constraint<T> {
    var value: T? = null

    constructor(minimum: T, maximum: T) : super(minimum, maximum) {}

    constructor(minimum: T, maximum: T, value: T) : super(minimum, maximum) {
        this.value = value
    }

    override fun clone(operation: Operation<T>): Constraint<T> {
        return ConstrainedValue(minimum, maximum, value!!)
    }

}
