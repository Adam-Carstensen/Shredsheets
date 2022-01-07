package com.bm.shredsheets.models.scales

class MajorScale : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(2, 2, 1, 2, 2, 2, 1)
    override val name: String
        get() = "Major"


    override fun getModeNames(): Array<String> {
        return arrayOf("Ionian (Major)", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian (Minor)", "Locrian")
    }

    override val getEnum: Scales
        get() = Scales.Major

    override val modalHighlighting: Array<BooleanArray>
        protected get() = arrayOf(
            booleanArrayOf(true, false, true, false, false, true, true),
            booleanArrayOf(true, false, true, false, false, true, true),
            booleanArrayOf(true, true, true, false, false, true, true),
            booleanArrayOf(true, false, true, true, true, false, false),
            booleanArrayOf(true, false, true, false, false, false, true),
            booleanArrayOf(true, false, true, false, false, true, true),
            booleanArrayOf(true, true, true, false, true, false, true)
        )

    override val degrees: Array<IntArray>
        get() = arrayOf(
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7),
            intArrayOf(1, 2, 3, 4, 5, 6, 7)
        )

    override val chords: Array<String>
        get() = arrayOf("M7", "m7", "m7", "M7", "7", "m7", "m7ø")
}

