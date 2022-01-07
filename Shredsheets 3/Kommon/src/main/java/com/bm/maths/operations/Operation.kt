package com.bm.maths.operations

abstract class Operation<T> {

    var presetB: T

    constructor(b: T) {
        this.presetB = b
    }

    abstract fun perform(a: T, b: T, impact: Float): T

    fun perform(a: T): T {
        return perform(a, this.presetB!!, 1f)
    }

    fun perform(a: T, impact: Float): T {
        return perform(a, this.presetB!!, impact)
    }

}
