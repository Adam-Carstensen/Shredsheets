package revert.shredsheets.models;

import java.util.HashMap;

import revert.shredsheets.enums.Keys;
import revert.shredsheets.models.scales.Scale;
import revert.shredsheets.models.scales.ScaleNote;

public class NotesModel {
    public static String[] AllNotes = {"C", "C♯", "D♭", "D", "D♯", "E♭", "E", "E♯", "F♭", "F", "F♯", "G♭", "G", "G♯", "A♭", "A", "A♯", "B♭", "B", "B♯"};
    public static Keys[] AllKeys = {Keys.C, Keys.C_Sharp, Keys.D_Flat, Keys.D, Keys.D_Sharp, Keys.E_Flat, Keys.E, Keys.E_Sharp, Keys.F_Flat, Keys.F, Keys.F_Sharp, Keys.G_Flat, Keys.G, Keys.G_Sharp, Keys.A_Flat, Keys.A, Keys.A_Sharp, Keys.B_Flat, Keys.B, Keys.B_Sharp};

    public static String[] AllStandardNotes = {"C", "C♯", "D♭", "D", "D♯", "E♭", "E", "F", "F♯", "G♭", "G", "G♯", "A♭", "A", "A♯", "B♭", "B"};
    public static Keys[] AllStandardKeys = {Keys.C, Keys.C_Sharp, Keys.D_Flat, Keys.D, Keys.D_Sharp, Keys.E_Flat, Keys.E, Keys.F, Keys.F_Sharp, Keys.G_Flat, Keys.G, Keys.G_Sharp, Keys.A_Flat, Keys.A, Keys.A_Sharp, Keys.B_Flat, Keys.B};

    private static HashMap<Keys, Integer> standardKeysByIndex = null;

    public static HashMap<Keys, Integer> GetStandardKeyIndexByKey() {
        if (standardKeysByIndex == null) {
            standardKeysByIndex = new HashMap<>(AllStandardKeys.length);
            for (int i = 0; i < AllStandardKeys.length; i++)
                standardKeysByIndex.put(AllStandardKeys[i], i);
        }

        return standardKeysByIndex;
    }

    public static int GetStandardIndexOfKey(Keys key) {
        return GetStandardKeyIndexByKey().get(key);
    }

    public static String[] NaturalNotes = {"C", "D", "E", "F", "G", "A", "B"};
    public static Keys[] NaturalNoteKeys = {Keys.C, Keys.D, Keys.E, Keys.F, Keys.G, Keys.A, Keys.B};

    public static String[] KeySliderStrings = {"C", "♯♭", "D", "♯♭", "E", "F", "♯♭", "G", "♯♭", "A", "♯♭", "B"};
    public static int[] NaturalNotePositions = {0, 2, 4, 5, 7, 9, 11};

    private static String[][] calculatedNotesMatrix = null;

    public static String[][] GetNotesMatrix() {
        if (calculatedNotesMatrix != null) return calculatedNotesMatrix;

        //19 = 7 natural notes + maximum scale degree count of 12 = 7+12 = 19
        //24 = 12 notes in the octave + 12 scale degrees in the chromatic scale
        String[][] matrix = new String[19][24];


        for (int i = 0; i < matrix.length; i++) {
            String[] noteRow = new String[24];
            Keys naturalNoteKey = NaturalNoteKeys[i % 7]; //7 natural notes C D E F G A B

            String name = naturalNoteKey.name();
            String sharpsAndFlats;

            int rowPosition = naturalNoteKey.getValue();

            while (rowPosition < 24) {
                noteRow[rowPosition] = name;
                sharpsAndFlats = "";
                for (int j = 1; j < 5 & ((rowPosition - j) >= 0); j++) {
                    sharpsAndFlats += "♭";
                    noteRow[rowPosition - j] = name + sharpsAndFlats;
                }
                sharpsAndFlats = "";
                for (int j = 1; j < 5 & ((rowPosition + j) < 24); j++) {
                    sharpsAndFlats += "♯";
                    noteRow[rowPosition + j] = name + sharpsAndFlats;
                }
                rowPosition += 12;
            }
            matrix[i] = noteRow;
        }

        return calculatedNotesMatrix = matrix;
    }

    public static ScaleNote[] GetScaleNotes(Keys key, Scale scale) {
        int keyStartingPoint = Keys.GetNaturalNotePosition(key);
        int[] scaleIntervals = scale.getIntervals();

        ScaleNote[] scaleNotes = new ScaleNote[scaleIntervals.length];

        String[][] notesMatrix = GetNotesMatrix();

        int position = keyStartingPoint;

        int naturalNoteIndex = Keys.GetNaturalNoteIndex(key);

        int[] modalDegrees = scale.getModalDegrees();

        //aligning intervals with degrees
        for (int i = 0; i < modalDegrees.length - 1; i++) {
            int degree = modalDegrees[i];
            int interval = scaleIntervals[i];

            if (degree == 0) {
                if (interval != 0) {
                    int nextInterval = scaleIntervals[i+1];
                    if (nextInterval == 0) {
                        scaleIntervals[i] = 0;
                        scaleIntervals[i + 1] = interval;
                    }
                }
            }
        }

        String[] degreeNames = scale.getDegreeNames();

        for (int i = 0; i < scaleIntervals.length; i++) {
            int interval = scaleIntervals[i];

            if (modalDegrees[i] == 0) {
                position += interval;
                continue;
            }

            ScaleNote note = new ScaleNote();
            scaleNotes[i] = note;
            note.scalePosition = i;

            note.degree = modalDegrees[i];
            note.degreeName = degreeNames[i];
            note.chordName = scale.getChord(i);
            if (i < NaturalNotes.length) {
                note.naturalNote = (naturalNoteIndex + note.degree - 1) % 7; //7 natural notes C D E F G A B
                note.naturalNoteName = NaturalNotes[note.naturalNote];
            } else {
                note.naturalNote = (naturalNoteIndex + NaturalNotes.length - 1) % 7; //7 natural notes C D E F G A B
                note.naturalNoteName = NaturalNotes[note.naturalNote];
            }

//            if (note.naturalNote == 0 && i != 0) note.naturalNote = scaleIntervals.length;

            note.name = notesMatrix[note.naturalNote][position];
            //note.name = note.naturalNoteName + note.sharpsAndFlats;
            note.position = position % 12;
            position += interval;
        }
        return scaleNotes;
    }
}