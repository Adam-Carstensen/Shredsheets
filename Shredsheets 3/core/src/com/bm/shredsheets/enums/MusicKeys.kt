package com.bm.shredsheets.enums

import com.bm.shredsheets.models.SessionModel
import kotlin.math.pow

fun MusicKeys.setFrequency(frequency: Float): MusicKeys {
    var newKey = MusicKeys(this.index, this.noteValue, this.cleanName)
    newKey.frequency = frequency
    return newKey
}

class MusicKeys(val index: Int, val noteValue: Int, val cleanName: String) {

    var frequency: Float = 0f

    companion object {
        var C = MusicKeys(0, 0, "C")
        var C_Sharp = MusicKeys(1, 1, "C♯")
        var D_Flat = MusicKeys(2,1, "D♭")
        var D = MusicKeys(3,2, "D")
        var D_Sharp = MusicKeys(4,3, "D♯")
        var E_Flat = MusicKeys(5,3, "E♭")
        var E = MusicKeys(6, 4, "E")
        var F_Flat = MusicKeys(7, 4, "F♭")
        var E_Sharp = MusicKeys(8,5, "E♯")
        var F = MusicKeys(9,5, "F")
        var F_Sharp = MusicKeys(10,6, "F♯")
        var G_Flat = MusicKeys(11,6, "G♭")
        var G = MusicKeys(12,7, "G")
        var G_Sharp = MusicKeys(13,8, "G♯")
        var A_Flat = MusicKeys(14,8, "A♭")
        var A = MusicKeys(15,9, "A")
        var A_Sharp = MusicKeys(16,10, "A♯")
        var B_Flat = MusicKeys(17, 10, "B♭")
        var B = MusicKeys(18, 11, "B")
        var C_Flat = MusicKeys(19, 11, "C♭")
        var B_Sharp = MusicKeys(20, 0, "B♯")

        var OrderedKeys = arrayOf(C, C_Sharp, D_Flat, D, D_Sharp, E_Flat, E, F_Flat, E_Sharp, F, F_Sharp, G_Flat, G, G_Sharp, A_Flat, A, A_Sharp, B_Flat, B, C_Flat, B_Sharp)

        fun getFrequency(key: MusicKeys): Float {
            return 130.8f * 2f.pow(key.noteValue.toFloat() / 12f)
        }

        fun getFrequency(scalePosition: Int): Float {
            var intervalSum = 0;

            for ((i, interval) in SessionModel.instance.scale.intervals.withIndex())
            {
                if (i == scalePosition) break;
                intervalSum += interval
            }

            return 130.8f * 2f.pow(intervalSum / 12f)
        }


        fun fromInt(value: Int): MusicKeys = OrderedKeys.first { it.index == value }

        fun GetNaturalNoteName(key: MusicKeys?): String {
            return when (key) {
                C_Flat, C, C_Sharp -> "C"
                D_Flat, D, D_Sharp -> "D"
                E_Flat, E, E_Sharp -> "E"
                F_Flat, F, F_Sharp -> "F"
                G_Flat, G, G_Sharp -> "G"
                A_Flat, A, A_Sharp -> "A"
                B_Flat, B, B_Sharp -> "B"
                else -> "X"
            }
        }

        fun GetNaturalNoteIndex(key: MusicKeys?): Int {
            return when (key) {
                D_Flat, D, D_Sharp -> 1
                E_Flat, E, E_Sharp -> 2
                F_Flat, F, F_Sharp -> 3
                G_Flat, G, G_Sharp -> 4
                A_Flat, A, A_Sharp -> 5
                B_Flat, B, B_Sharp -> 6
                C_Flat, C, C_Sharp -> 0
                else -> 0
            }
        }

        fun GetNaturalNotePosition(key: MusicKeys?): Int {
            return when (key) {
                C_Sharp, D_Flat -> 1
                D -> 2
                D_Sharp, E_Flat -> 3
                E, F_Flat -> 4
                E_Sharp, F -> 5
                F_Sharp, G_Flat -> 6
                G -> 7
                G_Sharp, A_Flat -> 8
                A -> 9
                A_Sharp, B_Flat -> 10
                B, C_Flat -> 11
                C, B_Sharp -> 0
                else -> 0
            }
        }
    }
}
