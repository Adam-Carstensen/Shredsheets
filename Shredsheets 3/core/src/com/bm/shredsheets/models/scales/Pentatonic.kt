package com.bm.shredsheets.models.scales

class Pentatonic : Scale() {
    override val scaleIntervals: IntArray
        get() = intArrayOf(3, 2, 2, 3, 2)

    override val name: String
        get() = "Pentatonic"

    override fun getModeNames(): Array<String> {
        return arrayOf("Pentatonic Minor", "Pentatonic Major", "Suspended Pentatonic", "Man Gong", "Ritusen")
    }

    override val getEnum: Scales
        get() = Scales.Pentatonic

    override val chords: Array<String>
        get() = arrayOf("m7", "M", "5 ♭7", "♭3 ♭7", "5")

    override val degrees: Array<IntArray>
        get() = arrayOf(
            intArrayOf(1, 3, 4, 5, 7),
            intArrayOf(1, 2, 3, 5, 6),
            intArrayOf(1, 2, 4, 5, 7),
            intArrayOf(1, 3, 4, 6, 7),
            intArrayOf(1, 2, 4, 5, 6))
}