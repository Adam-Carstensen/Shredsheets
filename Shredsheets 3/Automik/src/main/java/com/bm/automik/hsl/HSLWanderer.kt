package com.bm.automik.hsl

import com.bm.maths.extensions.clamp
import com.bm.automik.agents.DirectionBiasedWanderer
import com.bm.maths.Maths
import com.bm.maths.operations.Operations

class HSLWanderer : DirectionBiasedWanderer<HSLCell, HSLMatrixModel> {
    constructor(operationStack: Array<Operations>, entropy: Float, impact: Float, edgeAwareX: Boolean, edgeAwareY: Boolean)
            : super(operationStack, entropy, impact, edgeAwareX, edgeAwareY)

    constructor(operationStack: Array<Operations>, entropy: Float, impact: Float, directionBias: FloatArray, edgeAwareX: Boolean, edgeAwareY: Boolean)
            : super(operationStack, entropy, impact, directionBias, edgeAwareX, edgeAwareY)

    override fun getNewWanderingCell(): HSLCell {
        if (wanderingCell == null) wanderingCell = HSLCell(0f, 0f, Maths.randomFloat(), Maths.randomFloat(.4f, .6f), Maths.randomFloat(.4f, .6f), 1f)
        return wanderingCell
    }

    override fun modify(cell: HSLCell, entropy: Float): HSLCell {
        cell.hue = Maths.edgeAwareAdd(cell.hue, Maths.randomFloat(-entropy, entropy), 0f, 1f)
        cell.saturation = (cell.saturation + Maths.randomFloat(-entropy, entropy)).clamp( 0f, 1f)
        cell.lightness = (cell.lightness + Maths.randomFloat(-entropy, entropy)).clamp( 0f, 1f)
        return cell
    }

    override fun getEmptyArray(size: Int): Array<HSLCell?> {
        return arrayOfNulls<HSLCell?>(size)
    }

    override val stepSize: Int
        get() = kernel.stepSize
}
