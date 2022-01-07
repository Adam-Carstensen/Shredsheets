package com.bm.shredsheets.models.scales

class Blues : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(3, 2, 1, 1, 3, 2)

    override val name: String
        get() = "Blues"

    override fun getModeNames(): Array<String> {
        return arrayOf("Minor Blues", "Major Blues")
    }

    override val getEnum: Scales
        get() = Scales.Blues

    override val modalHighlighting: Array<BooleanArray>
        protected get() = arrayOf(
            booleanArrayOf(true, true, false, true, true, true),
            booleanArrayOf(true, false, true, true, false, true))

    override val degrees: Array<IntArray>
        get() = arrayOf(
            intArrayOf(1, 3, 4, 5, 5, 7),
            intArrayOf(1, 2, 3, 3, 5, 6))

    override val chords: Array<String>
        get() = arrayOf("m7", "M", "5 add ♭7", "m7", "M7", "m7")
}