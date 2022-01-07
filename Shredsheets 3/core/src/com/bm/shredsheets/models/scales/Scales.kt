package com.bm.shredsheets.models.scales

enum class Scales(val value: Int, val scale: Scale) {
    Major(0, MajorScale()), Pentatonic(1, Pentatonic()), Blues(2, Blues()), Augmented(3, Augmented()), Diminished(4, Diminished()),
    HarmonicMinor(5, HarmonicMinor()), HarmonicMajor(6, HarmonicMajor()), MelodicMinor(7, MelodicMinor()), Neopolitan(8, Neopolitan()),
    NeopolitanMinor(9, NeopolitanMinor()), Hungarian(10, Hungarian()), Persian(11, Persian()), DoubleHarmonic(12, DoubleHarmonic());

    companion object {
        fun fromInt(value: Int): Scale = values().first { it.value == value }.scale
    }



}