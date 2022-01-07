package com.bm.shredsheets.models

import kotlin.math.pow

object FretSpacingModel {

    var fretSpacingByWidthCache = HashMap<Float, DoubleArray>()
    fun GetFretSpacing(fretCount: Int, width: Float, realisticSpacing: Boolean = false): DoubleArray {
        var results = fretSpacingByWidthCache.get(width)
        if (results != null) return results
        results = DoubleArray(fretCount)
        if (realisticSpacing) {
            val coefficients = getFretSpacingCoefficients(fretCount)
            for (i in coefficients.indices) results[i] = coefficients[i] * width
        } else {
            for (i in 0 until fretCount) results[i] = (width / fretCount.toFloat()).toDouble()
        }
        fretSpacingByWidthCache[width] = results
        return results
    }

    fun getFretSpacingCoefficients(count: Int): DoubleArray {
        val fretSpaces = DoubleArray(count)

        //Each octave doubles the frequency and spans half the distance of the previous octave.

        //An open A string rings at 440Hz.  On a fender that's a ~34" length.
        //Same string, 12th fret = 880Hz with a 17" length.
        //Same string, 24th fret = 1660Hz with an 8.5" length.
        //It's the exponential scale.
        for (i in 0 until count) {
            fretSpaces[i] =
                2.0.pow((count - i).toDouble() / count) - 1f - (2.0.pow((count - (i + 1f)).toDouble() / count) - 1f)
        }
        return fretSpaces
    }
}