package com.bm.shredsheets.models.scales

class DoubleHarmonic : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(1, 3, 1, 2, 1, 3, 1)
    override val name: String
        get() = "Double Harmonic"

    override fun getModeNames(): Array<String> {
        return arrayOf("Double Harmonic Major", "Lydian ♯2 ♯6", "Ultraphrygian", "Hungarian Minor", "Oriental", "Ionian Augmented ♯2", "Locrian ♭♭3 ♭♭7")
    }

    override val getEnum: Scales
        get() = Scales.DoubleHarmonic

    override val modalHighlighting: Array<BooleanArray>
        protected get() = arrayOf(
            booleanArrayOf(true, true, true, false, false, true, true),
            booleanArrayOf(true, false, true, true, false, true, true),
            booleanArrayOf(true, true, false, false, false, true, true),
            booleanArrayOf(true, false, true, true, true, false, true),
            booleanArrayOf(true, true, true, false, true, true, false),
            booleanArrayOf(true, true, true, false, false, true, true),
            booleanArrayOf(true, true, true, false, true, false, true))

    override val degrees: Array<IntArray>
        get() = arrayOf(
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7))

    override val chords: Array<String>
        get() = arrayOf("M7", "M7", "m♭♭7", "mΔ7", "7♭5", "M7+", "♭♭3 ♭5 ♭♭7")
}