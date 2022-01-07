package com.bm.shredsheets.models.scales

class HarmonicMinor : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(2, 1, 2, 2, 1, 3, 1)

    override val name: String
        get() = "Harmonic Minor"

    override fun getModeNames(): Array<String> {
        return arrayOf("Harmonic Minor", "Locrian ♯6", "Ionian Augmented", "Romanian", "Phrygian Dominant", "Lydian ♯2", "UltraLocrian")
    }

    override val getEnum: Scales
        get() = Scales.HarmonicMinor

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
        get() = arrayOf("mΔ7", "m7 ♭5", "M7+", "m7", "7", "M7", "m7o")
}