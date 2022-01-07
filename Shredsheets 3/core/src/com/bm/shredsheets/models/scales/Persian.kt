package com.bm.shredsheets.models.scales

class Persian : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(1, 3, 1, 1, 2, 3, 1)

    override val name: String
        get() = "Persian"

    override fun getModeNames(): Array<String> {
        return arrayOf("Persian", "Chromatic Dorian Inverse", "Chromatic Phrygian Inverse", "Chromatic Lydian Inverse", "Chromatic Mixolydian Inverse", "Chromatic Hypodorian Inverse", "Chromatic Hypophrygian Inverse")
    }

    override val getEnum: Scales
        get() = Scales.Persian

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
        get() = arrayOf("M7 ♭5", "M7", "m ♭♭3 ♭♭7", "mΔ7", "M7 ♯3", "M ♯5 ♭7", "o7 ♭♭3")
}