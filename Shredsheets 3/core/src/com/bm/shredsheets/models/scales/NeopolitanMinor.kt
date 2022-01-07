package com.bm.shredsheets.models.scales

class NeopolitanMinor : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(1, 2, 2, 2, 1, 3, 1)

    override val name: String
        get() = "Neopolitan Minor"

    override fun getModeNames(): Array<String> {
        return arrayOf("Neopolitan Minor", "Lydian ♯6", "Mixolydian Augmented", "Hungarian Gypsy", "Locrian Dominant", "Ionian ♯2", "UltraLocrian ♭♭3")
    }

    override val getEnum: Scales
        get() = Scales.NeopolitanMinor

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
        get() = arrayOf("mΔ7", "M7", "7+", "m7 ♭5", "7 ♭5", "M7", "♭♭3 ♭5 ♭♭7")
}