package com.bm.maths.operations

import com.bm.maths.Maths

enum class Operations(val mapKey: Int) {
    ADD(1),
    SUBTRACT(2),
    MULTIPLY(3),
    DIVIDE(4),
    AVERAGE(5),
    SCREEN(6);


    companion object {


        var AddOperations = arrayOf(ADD)
        var SubtractOperations = arrayOf(SUBTRACT)
        var MultiplyOperations = arrayOf(MULTIPLY)
        var DivideOperations = arrayOf(DIVIDE)
        var AverageOperations = arrayOf(AVERAGE)
        var ScreenOperations = arrayOf(SCREEN)

        fun GetRandomAddSubtractAverage(): Array<Operations> {
            val value = Maths.randomIndex(1, 3)

            when (value) {
                1 -> return AddOperations
                2 -> return SubtractOperations
                3 -> return AverageOperations
                else -> {
                }
            }
            return AddOperations
        }

        @JvmOverloads
        fun GetRandomAddOrSubtract(addProbability: Float = .5f): Array<Operations> {
            return if (Maths.randomBoolean(addProbability))
                AddOperations
            else
                SubtractOperations
        }

        fun GetRandom(): Array<Operations> {
            val value = Maths.randomIndex(1, 6)

            when (value) {
                1 -> return AddOperations
                2 -> return SubtractOperations
                3 -> return MultiplyOperations
                4 -> return DivideOperations
                5 -> return AverageOperations
                6 -> return ScreenOperations
                else -> {
                }
            }
            return AddOperations
        }
    }


}