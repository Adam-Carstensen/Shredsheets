package com.bm.shredsheets.models

import com.bm.shredsheets.enums.MusicKeys
import com.bm.shredsheets.models.scales.Scale
import com.bm.shredsheets.models.scales.ScaleNote

object NotesModel {
    var AllNotes =
        arrayOf("C", "C♯", "D♭", "D", "D♯", "E♭", "E", "E♯", "F♭", "F", "F♯", "G♭", "G", "G♯", "A♭", "A", "A♯", "B♭", "B", "B♯")
    var AllKeys: Array<MusicKeys> =
        arrayOf(MusicKeys.C, MusicKeys.C_Sharp, MusicKeys.D_Flat, MusicKeys.D, MusicKeys.D_Sharp, MusicKeys.E_Flat, MusicKeys.E, MusicKeys.E_Sharp, MusicKeys.F_Flat, MusicKeys.F, MusicKeys.F_Sharp, MusicKeys.G_Flat, MusicKeys.G, MusicKeys.G_Sharp, MusicKeys.A_Flat, MusicKeys.A, MusicKeys.A_Sharp, MusicKeys.B_Flat, MusicKeys.B, MusicKeys.B_Sharp, MusicKeys.C_Flat)



    var AllStandardNotes =
        arrayOf("C", "C♯", "D♭", "D", "D♯", "E♭", "E", "F", "F♯", "G♭", "G", "G♯", "A♭", "A", "A♯", "B♭", "B")
    var AllStandardKeys: Array<MusicKeys> =
        arrayOf(MusicKeys.C, MusicKeys.C_Sharp, MusicKeys.D_Flat, MusicKeys.D, MusicKeys.D_Sharp, MusicKeys.E_Flat, MusicKeys.E, MusicKeys.F, MusicKeys.F_Sharp, MusicKeys.G_Flat, MusicKeys.G, MusicKeys.G_Sharp, MusicKeys.A_Flat, MusicKeys.A, MusicKeys.A_Sharp, MusicKeys.B_Flat, MusicKeys.B, MusicKeys.B_Sharp, MusicKeys.C_Flat)

    private var standardKeysByIndex: HashMap<MusicKeys, Int>? = null

    fun GetStandardKeyIndexByKey(): HashMap<MusicKeys, Int> {
        if (standardKeysByIndex == null) {
            standardKeysByIndex = HashMap<MusicKeys, Int>(AllStandardKeys.size)
            for (i in AllStandardKeys.indices) standardKeysByIndex!![AllStandardKeys[i]] = i
        }
        return standardKeysByIndex!!
    }

    fun GetStandardIndexOfKey(key: MusicKeys?): Int {
        return GetStandardKeyIndexByKey()[key]!!
    }

    var NaturalNotes = arrayOf("C", "D", "E", "F", "G", "A", "B")
    var NaturalNoteKeys: Array<MusicKeys> =
        arrayOf<MusicKeys>(MusicKeys.C, MusicKeys.D, MusicKeys.E, MusicKeys.F, MusicKeys.G, MusicKeys.A, MusicKeys.B)

    var KeySliderStrings = arrayOf("C", "♯♭", "D", "♯♭", "E", "F", "♯♭", "G", "♯♭", "A", "♯♭", "B")
    var NaturalNotePositions = intArrayOf(0, 2, 4, 5, 7, 9, 11)

    private var calculatedNotesMatrix: Array<Array<String?>>? = null

    fun GetNotesMatrix(): Array<Array<String?>> {
        if (calculatedNotesMatrix != null) return calculatedNotesMatrix!!

        //19 = 7 natural notes + maximum scale degree count of 12 = 7+12 = 19
        //24 = 12 notes in the octave + 12 scale degrees in the chromatic scale
        val matrix: Array<Array<String?>> = Array(19) { arrayOfNulls<String>(24) }

        for (i in matrix.indices) {
            val noteRow = arrayOfNulls<String>(24)
            val naturalNoteKey: MusicKeys = NaturalNoteKeys[i % 7] //7 natural notes C D E F G A B
            val name: String = naturalNoteKey.cleanName
            var sharpsAndFlats: String
            var rowPosition: Int = naturalNoteKey.noteValue
            while (rowPosition < 24) {
                noteRow[rowPosition] = name
                sharpsAndFlats = ""
                run {
                    var j = 1
                    while (j < 5 && (rowPosition - j >= 0)) {
                        sharpsAndFlats += "♭"
                        noteRow[rowPosition - j] = name + sharpsAndFlats
                        j++
                    }
                }
                sharpsAndFlats = ""
                var j = 1
                while (j < 5 && (rowPosition + j < 24)) {
                    sharpsAndFlats += "♯"
                    noteRow[rowPosition + j] = name + sharpsAndFlats
                    j++
                }
                rowPosition += 12
            }
            matrix[i] = noteRow
        }

        calculatedNotesMatrix = matrix

        return matrix
    }

    fun GetScaleNotes(key: MusicKeys?, scale: Scale): Array<ScaleNote?> {
        val keyStartingPoint: Int = MusicKeys.GetNaturalNotePosition(key)
        val scaleIntervals: IntArray = scale.intervals
        val scaleNotes: Array<ScaleNote?> = arrayOfNulls<ScaleNote>(scaleIntervals.size)
        val notesMatrix = GetNotesMatrix()
        var position = keyStartingPoint
        val naturalNoteIndex: Int = MusicKeys.GetNaturalNoteIndex(key)
        val modalDegrees: IntArray = scale.modalDegrees

        //aligning intervals with degrees
        for (i in 0 until modalDegrees.size - 1) {
            val degree = modalDegrees[i]
            val interval = scaleIntervals[i]
            if (degree == 0) {
                if (interval != 0) {
                    val nextInterval = scaleIntervals[i + 1]
                    if (nextInterval == 0) {
                        scaleIntervals[i] = 0
                        scaleIntervals[i + 1] = interval
                    }
                }
            }
        }
        val degreeNames: Array<String> = scale.degreeNames!!
        for (i in scaleIntervals.indices) {
            val interval = scaleIntervals[i]
            if (modalDegrees[i] == 0) {
                position += interval
                continue
            }
            val note = ScaleNote()
            scaleNotes[i] = note
            note.scalePosition = i
            note.degree = modalDegrees[i]
            note.degreeName = degreeNames[i]
            note.chordName = scale.getChord(i)
            if (i < NaturalNotes.size) {
                note.naturalNote =
                    (naturalNoteIndex + note.degree - 1) % 7 //7 natural notes C D E F G A B
                note.naturalNoteName = NaturalNotes[note.naturalNote]
            } else {
                note.naturalNote =
                    (naturalNoteIndex + NaturalNotes.size - 1) % 7 //7 natural notes C D E F G A B
                note.naturalNoteName = NaturalNotes[note.naturalNote]
            }
            if (note.naturalNote === 0 && i != 0) note.naturalNote = scaleIntervals.size
            note.name = notesMatrix[note.naturalNote][position]
            //note.name = note.naturalNoteName + note.sharpsAndFlats;
            note.position = position % 12
            position += interval
        }
        return scaleNotes
    }

}