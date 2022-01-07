package com.bm.shredsheets.models.scales

class HarmonicMajor : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(2, 2, 1, 2, 1, 3, 1)

    override val name: String
        get() = "Harmonic Major"

    override fun getModeNames(): Array<String> {
        return arrayOf("Harmonic Major", "Dorian ♭5", "Phrygian ♭4", "Lydian ♭3", "Mixolydian ♭9", "Lydian Augmented ♯2", "Locrian ♭♭7")
    }

    override val getEnum: Scales
        get() = Scales.HarmonicMajor

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
        get() = arrayOf("M7", "m7 ♭5", "m7", "mΔ7", "7", "M7+", "m7o")
}