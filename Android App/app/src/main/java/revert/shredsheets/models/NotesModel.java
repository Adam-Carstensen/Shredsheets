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

    public static String[] WholeNotes = {"C", "D", "E", "F", "G", "A", "B"};
    public static Keys[] WholeNoteKeys = {Keys.C, Keys.D, Keys.E, Keys.F, Keys.G, Keys.A, Keys.B};

    public static String[] KeySliderStrings = {"C", "♯♭", "D", "♯♭", "E", "F", "♯♭", "G", "♯♭", "A", "♯♭", "B"};
    public static int[] WholeNotePositions = {0, 2, 4, 5, 7, 9, 11};

    private static String[][] calculatedNotesMatrix = null;

    public static String[][] GetNotesMatrix() {
        if (calculatedNotesMatrix != null) return calculatedNotesMatrix;

        //19 = 7 whole notes + maximum scale degree count of 12 = 7+12 = 19
        //24 = 12 notes in the octave + 12 scale degrees in the chromatic scale
        String[][] matrix = new String[19][24];


        for (int i = 0; i < matrix.length; i++) {
            String[] noteRow = new String[24];
            Keys wholeNoteKey = WholeNoteKeys[i % 7]; //7 whole notes C D E F G A B

            String name = wholeNoteKey.name();
            String sharpsAndFlats;

            int rowPosition = wholeNoteKey.getValue();

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
        int keyStartingPoint = Keys.GetWholeNotePosition(key);
        int[] scaleIntervals = scale.getIntervals();

        ScaleNote[] scaleNotes = new ScaleNote[scaleIntervals.length];

        String[][] notesMatrix = GetNotesMatrix();

        int position = keyStartingPoint;

        int wholeNoteIndex = Keys.GetWholeNoteIndex(key);
        String[] degreeNames = scale.getDegreeNames();

        for (int i = 0; i < scaleIntervals.length; i++) {
            int interval = scaleIntervals[i];

            if (interval != 0) {
                ScaleNote note = new ScaleNote();
                scaleNotes[i] = note;

                note.degree = scale.getDegree(i);
                note.degreeName = degreeNames[i];
                if (i < WholeNotes.length) {
                    note.wholeNote = (wholeNoteIndex + i) % 7; //7 whole notes C D E F G A B
                    note.wholeNoteName = WholeNotes[note.wholeNote];
                } else {
                    note.wholeNote = (wholeNoteIndex + WholeNotes.length - 1) % 7; //7 whole notes C D E F G A B
                    note.wholeNoteName = WholeNotes[note.wholeNote];
                }

                if (note.wholeNote == 0 && i != 0) note.wholeNote = scaleIntervals.length;

                note.name = notesMatrix[note.wholeNote][position];
                //note.name = note.wholeNoteName + note.sharpsAndFlats;
                note.position = position % 12;
            }

            position += interval;
        }
        return scaleNotes;
    }


}

