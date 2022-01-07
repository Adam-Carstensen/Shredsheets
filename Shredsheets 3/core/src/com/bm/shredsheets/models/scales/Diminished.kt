package com.bm.shredsheets.models.scales

class Diminished : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(2, 1, 2, 1, 2, 1, 2, 1)

    override val name: String
        get() = "Diminished"

    override fun getModeNames(): Array<String> {
        return arrayOf("Whole Half", "Half Whole")
    }

    override val getEnum: Scales
        get() = Scales.Diminished

    override val modalHighlighting: Array<BooleanArray>
        protected get() = arrayOf(
            booleanArrayOf(true, false, true, false, true, false, true, false),
            booleanArrayOf(true, true, false, true, false, true, false, true))

    override val degrees: Array<IntArray>
        get() = arrayOf(
            intArrayOf(1, 2, 3, 4, 5, 5, 6, 7),
            intArrayOf(1, 2, 3, 3, 4, 5, 6, 7))

    override val chords: Array<String>
        get() = arrayOf("m7o", "m7o", "m7o", "m7o", "m7o", "m7o", "m7o", "m7o")
}