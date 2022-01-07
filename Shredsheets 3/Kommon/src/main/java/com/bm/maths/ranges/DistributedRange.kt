package com.bm.maths.ranges

import com.badlogic.gdx.math.Interpolation

abstract class DistributedRange<T : Comparable<T>, TRange : ClosedRange<T>>(var distribution: Interpolation = Interpolation.linear) : ClosedRange<T> {

    /**
     * Should always return a new random value
     */
    abstract fun random(): T

    var resolvedValue: T? = null

    /**
     * Generates a random value, stores it, and always returns that value
     */
    fun get(): T {
        if (resolvedValue == null) {
            resolvedValue = random()
        }
        return resolvedValue!!
    }
}