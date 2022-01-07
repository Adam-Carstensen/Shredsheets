package com.bm.shredsheets.models.scales

class Augmented : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(3, 1, 3, 1, 3, 1)
    override val name: String
        get() = "Augmented"

    override fun getModeNames(): Array<String> {
        return arrayOf("Minor-Third Half-Step", "Half-Step Minor-Third")
    }

    override val getEnum: Scales
        get() = Scales.Augmented

    override val modalHighlighting: Array<BooleanArray>
        protected get() = arrayOf(
            booleanArrayOf(true, true, true, false, true, true),
            booleanArrayOf(true, true, false, false, true, false))

    override val degrees: Array<IntArray>
        get() = arrayOf(
            intArrayOf(1, 3, 3, 5, 5, 7),
            intArrayOf(1, 2, 3, 4, 6, 6))

    override val chords: Array<String>
        get() = arrayOf("M7", "M+♭♭7", "M7", "M+♭♭7", "M7", "M+♭♭7")
}