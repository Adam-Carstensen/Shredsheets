package com.bm.shredsheets.models.scales

class Hungarian : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(3, 1, 2, 1, 2, 1, 2)

    override val name: String
        get() = "Hungarian"

    override fun getModeNames(): Array<String> {
        return arrayOf("Hungarian", "SuperLocrian ♭♭6 ♭♭7", "Harmonic Minor ♭5", "SuperLocrian ♯6", "Jazz Minor ♯5", "Dorian ♭9 ♯11", "Lydian Augmented ♯3")
    }

    override val getEnum: Scales
        get() = Scales.Hungarian

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
        get() = arrayOf("7", "m7o", "mΔ7 ♭5", "m7ø", "mΔ7+", "m7", "Δ7+♯3")
}