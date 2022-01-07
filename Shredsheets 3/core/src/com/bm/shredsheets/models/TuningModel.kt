package com.bm.shredsheets.models

import com.bm.shredsheets.enums.MusicKeys
import com.bm.shredsheets.enums.setFrequency
import java.util.*

object TuningModel {
    val standardTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.E.setFrequency(329.63f), MusicKeys.B.setFrequency(246.94f), MusicKeys.G.setFrequency(196.00f), MusicKeys.D.setFrequency(146.83f), MusicKeys.A.setFrequency(110.00f), MusicKeys.E.setFrequency(82.41f),
            MusicKeys.B.setFrequency(61.74f), MusicKeys.F_Sharp.setFrequency(23.12f))
    val droppedDTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.E, MusicKeys.B, MusicKeys.G, MusicKeys.D, MusicKeys.A, MusicKeys.D, MusicKeys.A, MusicKeys.D)
    val newStandardTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.G, MusicKeys.E, MusicKeys.A, MusicKeys.D, MusicKeys.G, MusicKeys.C, MusicKeys.G, MusicKeys.E)
    val openCTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.E, MusicKeys.C, MusicKeys.G, MusicKeys.C, MusicKeys.G, MusicKeys.C, MusicKeys.E, MusicKeys.C)
    val openDTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.D, MusicKeys.A, MusicKeys.D, MusicKeys.F_Sharp, MusicKeys.A, MusicKeys.D, MusicKeys.D, MusicKeys.A)
    val openETuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.E, MusicKeys.B, MusicKeys.E, MusicKeys.G_Sharp, MusicKeys.B, MusicKeys.E, MusicKeys.E, MusicKeys.B)
    val openFTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.C, MusicKeys.F, MusicKeys.C, MusicKeys.F, MusicKeys.A, MusicKeys.F, MusicKeys.C, MusicKeys.F)
    val openGTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.D, MusicKeys.G, MusicKeys.D, MusicKeys.G, MusicKeys.B, MusicKeys.D, MusicKeys.D, MusicKeys.G)
    val openATuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.E, MusicKeys.A, MusicKeys.C_Sharp, MusicKeys.E, MusicKeys.A, MusicKeys.E, MusicKeys.E, MusicKeys.A)
    val openBTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.B, MusicKeys.F_Sharp, MusicKeys.B, MusicKeys.F_Sharp, MusicKeys.B, MusicKeys.D_Sharp, MusicKeys.B, MusicKeys.F_Sharp)
    val ukuleleStandardTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.E, MusicKeys.B, MusicKeys.A, MusicKeys.E, MusicKeys.C, MusicKeys.G, MusicKeys.E, MusicKeys.A)
    val fiveStringBassTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.B, MusicKeys.G, MusicKeys.D, MusicKeys.A, MusicKeys.E, MusicKeys.B, MusicKeys.G, MusicKeys.D)
    val eightStringAlternateTuning: Array<MusicKeys>
        get() = arrayOf(MusicKeys.E, MusicKeys.B, MusicKeys.G, MusicKeys.D, MusicKeys.A, MusicKeys.E, MusicKeys.B, MusicKeys.E)
    val tunings: HashMap<Array<MusicKeys>, String>
        get() {
            val tunings: HashMap<Array<MusicKeys>, String> = HashMap(13)
            tunings[standardTuning] = "Standard Tuning"
            tunings[droppedDTuning] = "Drop D"
            tunings[newStandardTuning] = "New Standard"
            tunings[openCTuning] = "Open C"
            tunings[openDTuning] = "Open D"
            tunings[openETuning] = "Open E"
            tunings[openFTuning] = "Open F"
            tunings[openGTuning] = "Open G"
            tunings[openATuning] = "Open A"
            tunings[openBTuning] = "Open B"
            tunings[ukuleleStandardTuning] = "Ukulele Standard"
            tunings[fiveStringBassTuning] = "5str Bass Standard"
            tunings[eightStringAlternateTuning] = "8str Alternate"
            return tunings
        }

    fun getTuningName(keys: Array<MusicKeys?>): String {
        val tunings: Set<Map.Entry<Array<MusicKeys>, String>> = tunings.entries
        for ((key, value) in tunings) {
            if (Arrays.equals(keys, key)) {
                return value
            }
        }
        val tuningBuilder = StringBuilder()
        for (key in keys) {
            tuningBuilder.append(key)
        }
        return "Tuning: $tuningBuilder"
    }
}
