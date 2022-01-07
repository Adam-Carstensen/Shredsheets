package com.bm.shredsheets.models.scales

class MelodicMinor : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(2, 1, 2, 2, 2, 2, 1)

    override val name: String
        get() = "Melodic Minor"

    override fun getModeNames(): Array<String> {
        return arrayOf("Jazz Minor", "Dorian ♭9", "Lydian Augmented", "Lydian Dominant", "Mixolydian ♭6", "SemiLocrian", "SuperLocrian")
    }

    override val getEnum: Scales
        get() = Scales.MelodicMinor

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
        get() = arrayOf("mΔ7", "m7", "M7 ♭5", "7", "7", "m7 ♭5", "m7 ♭5")
}