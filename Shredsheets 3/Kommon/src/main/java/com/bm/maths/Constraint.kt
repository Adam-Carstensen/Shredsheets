package com.bm.maths

import com.bm.maths.operations.Operation

open class Constraint<T>(var minimum: T, var maximum: T) {

    fun clone(): Constraint<T> {
        return Constraint(minimum, maximum)
    }

    open fun clone(operation: Operation<T>): Constraint<T> {

        return Constraint(minimum, maximum)
    }

    fun setConstraint(constraint: Constraint<T>) {
        this.minimum = constraint.minimum
        this.maximum = constraint.maximum
    }

    fun modifyConstraints(operation: Operation<T>) {
        minimum = operation.perform(minimum)
        maximum = operation.perform(maximum)
    }

}
