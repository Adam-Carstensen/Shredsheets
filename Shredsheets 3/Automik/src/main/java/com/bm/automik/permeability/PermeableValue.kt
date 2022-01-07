package com.bm.automik.permeability

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.Maths


class PermeableValue(
        var id: Int,
        var minimum: Float,
        var maximum: Float, //minimum and maximum combine into porosity
        var interpolation: Interpolation, //0-1 representing how much of the maximum skew can occur per timestep, per neighbor
        var permeability: Float,
        var permeationProbability: Float
) {
    var value: Float = 0f

    constructor(id: Int, minimum: Float, maximum: Float, permeability: Float, permeationProbability: Float) : this(id, minimum, maximum, Interpolation.linear, permeability, permeationProbability)

    init {
        this.value = Maths.randomFloat(minimum, maximum, interpolation)
    }

    fun clone(): PermeableValue {
        return PermeableValue(id, minimum, maximum, permeability, permeationProbability)
    }

}
