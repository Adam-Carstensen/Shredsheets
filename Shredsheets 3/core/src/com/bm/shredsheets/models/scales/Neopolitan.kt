package com.bm.shredsheets.models.scales

class Neopolitan : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(1, 2, 2, 2, 2, 2, 1)

    override val name: String
        get() = "Neopolitan"

    override fun getModeNames(): Array<String> {
        return arrayOf("Neopolitan", "Leading Whole-Tone", "Lydian Augmented Dominant", "Lydian Dominant ♭6", "Major Locrian", "SemiLocrian ♭4", "SuperLocrian ♭♭3")
    }

    override val getEnum: Scales
        get() = Scales.Neopolitan

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
        get() = arrayOf("mΔ7", "M7+", "M+ ♭7", "M ♭7", "7 ♭5", "m7 ♭5", "♭♭3 ♭5 ♭7")
}