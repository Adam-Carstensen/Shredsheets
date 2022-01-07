package com.bm.automik.alpha


import com.bm.maths.extensions.clamp
import com.bm.maths.Maths
import com.bm.automik.agents.Wanderer
import com.bm.maths.operations.Operations

class AlphaWanderer(operationStack: Array<Operations>, entropy: Float, impact: Float, edgeAwareX: Boolean, edgeAwareY: Boolean)
    : Wanderer<AlphaCell, AlphaMatrixModel>(operationStack, entropy, impact, edgeAwareX, edgeAwareY) {
    override fun getNewWanderingCell(): AlphaCell {
        if (wanderingCell == null) wanderingCell = AlphaCell(x.toInt().toFloat(), y.toInt().toFloat(), .5f)
        return wanderingCell
    }

    override fun modify(cell: AlphaCell, entropy: Float): AlphaCell {
        var value = cell.value + Maths.randomFloat(-entropy, entropy) * cell.alphaPermeability
        cell.value = value.clamp(0f,1f)
        return cell
    }

    override fun getEmptyArray(size: Int): Array<AlphaCell?> {
        return arrayOfNulls<AlphaCell?>(size)
    }

    override val stepSize: Int
        get() = kernel.stepSize
}
