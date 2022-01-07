package revert.shredsheets.models.scales;

import revert.common.TextModule;
import revert.shredsheets.models.SessionModel;

public abstract class Scale {
    private String[] modeNames = null;
    private int mode = getDefaultMode();

    protected int getDefaultMode() {
        return 0;
    }

    public abstract int[] getScaleIntervals();

    public int[] getIntervals() {
        int[] intervals = getScaleIntervals();
        int[] modalIntervals = new int[intervals.length];

        int modalOffset = 0;
        int modeIndex = getMode();
        if (modeIndex == 0) return intervals;
        for (int i = 0; i < intervals.length; i++) {
            if (intervals[i] == 0) modalOffset++;
            if (i - modalOffset == modeIndex) break;
        }

        for (int i = 0; i < modalIntervals.length; i++) {
            int modalPosition = (i + modeIndex + modalOffset);
            modalIntervals[i] = intervals[modalPosition % intervals.length];
        }

        return modalIntervals;
    }

    public int GetDegree(int degree) {
        return degree;
    }

    String[] degreeNames = null;

    //Degree names are all based on a comparison to the Ionian's intervals, which is the default mode of Major
    private int[] ionianCumulativeIntervals = new int[]{0, 2, 4, 5, 7, 9, 11};

    public String[] getDegreeNames() {
        //TODO: Document and refactor
        if (degreeNames != null) return degreeNames;

        int[] intervals = getIntervals();
        int intervalCumulative = 0;//intervals[0];

        int[] degrees = getModalDegrees(); // getDegrees();
        degreeNames = new String[intervals.length];
        //degreeNames[0] = "1st";

//        //aligning intervals with degrees
//        for (int i = 0; i < degrees.length - 1; i++) {
//            int degree = degrees[i];
//            int interval = intervals[i];
//
//            if (degree == 0) {
//                if (interval != 0) {
//                    int nextInterval = intervals[i+1];
//                    if (nextInterval == 0) {
//                        intervals[i] = 0;
//                        intervals[i + 1] = interval;
//                    }
//                }
//            }
//        }


        for (int i = 0; i < degrees.length; i++) {
            int degree = degrees[i];

            if (degree == 0) {
                degreeNames[i] = "";
                intervalCumulative += intervals[i];
                continue;
            }

            int ionianCumulative = ionianCumulativeIntervals[degree - 1];

            String degreeName = "";

            if (intervalCumulative < ionianCumulative)
                for (int j = 0; j < ionianCumulative - intervalCumulative; j++)
                    degreeName += "♭";
            else if (intervalCumulative > ionianCumulative)
                for (int j = 0; j < intervalCumulative - ionianCumulative; j++)
                    degreeName += "♯";

            degreeName += TextModule.getOrdinallySuffexedNumber(degree); //0 based array, 1 based degree naming system

            degreeNames[i] = degreeName;

            int intervalStepSize = intervals[i];
            //if (intervalStepSize == 0) degreeNames[i] = "";
            intervalCumulative += intervalStepSize;
        }

        return degreeNames;
    }

    public String[] getIntervalNames() {
        int[] intervals = getIntervals();
        return getIntervalNames(intervals);
    }

    public String[] getIntervalNames(int[] intervals) {
        String[] names = new String[intervals.length];

        int skippedIntervals = 0;
        for (int i = 0; i < intervals.length; i++) {
            int interval = intervals[i];
            names[i - skippedIntervals] = getIntervalName(interval);
        }

        return names;
    }

    public String getIntervalName(int i) {
        switch (i) {
            case 1:
                return "h";
            case 2:
                return "W";
            case 3:
                return "W+h";
            case 4:
                return "W+W";
            default:
                return String.valueOf(i);
        }
    }

    public abstract String getName();

    public abstract String[] getModeNames();

    public String getCurrentModeName() {
        modeNames = getModeNames();
        if (modeNames == null) return TextModule.getOrdinallySuffexedNumber(getMode()) + " mode";
        String modeName = modeNames[getMode() % modeNames.length];
        return modeName;
    }

    @Override
    public String toString() {
        return getName();
    } // + (((modeNames = getModeNames()) == null) ? "" : " (" + modeNames[mode] + ")");

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        //if (this.mode == mode) return;
        this.mode = mode;
        this.degreeNames = null;

        boolean[] modalHighlighting = getModalHighlighting()[mode];
        SessionModel.getInstance().getTheme().setDegreeHighlightingVector(modalHighlighting);

//        FragmentActivity fretboardActivity = SessionModel.getInstance().fretboardActivity;
//        if (fretboardActivity != null) fretboardActivity.getIntent().putExtra("mode", mode);
        SessionModel.getInstance().invalidateViews();
    }

    protected boolean[][] getModalHighlighting() {
        return new boolean[][] {
                {true, false, true, false, true, false, true, false, false, false, false, false},
                {true, false, true, false, true, false, true, false, false, false, false, false},
                {true, false, true, false, true, false, true, false, false, false, false, false},
                {true, false, true, false, true, false, true, false, false, false, false, false},
                {true, false, true, false, true, false, true, false, false, false, false, false},
                {true, false, true, false, true, false, true, false, false, false, false, false},
                {true, false, true, false, true, false, true, false, false, false, false, false}
        };
    }

    public abstract int[][] getDegrees();

    public int[] getModalDegrees() {
        int[] intervals = getIntervals();
        int[][] degrees = getDegrees();
        return degrees[mode];
//        if (mode == 0) return degrees;
//        int[] rotatedDegrees = new int[degrees.length];
//        for (int i = 0; i < degrees.length; i++) {
//            rotatedDegrees[i] = degrees[(i + mode) % degrees.length];
//        }
//
//        int[] modalDegrees = new int[degrees.length];
//
//        modalDegrees[0] = 1;
//
//        boolean second = intervals[0] != 0;
//        if (second) modalDegrees[1] = 2;
//
//        boolean third = intervals[1] != 0;
//
//        boolean fourth = intervals[2] != 0;
//
////        int fourthDegreePosition = addUntilIndex(intervals, 4);
////        boolean flatFifth = fourthDegreePosition == 6;
////        if (flatFifth) {
////            if (fourth) modalDegrees[4] = 5;
////            else modalDegrees[4] = 4;
////        }
//
//        //int fifthDegreePosition = addUntilIndex(intervals, 6);
//        boolean fifth = intervals[3] != 0;
//
//        if (fourth && !fifth) {
//            int fourthDegreePosition = addUntilIndex(intervals, 4);
//            if (fourthDegreePosition == 7) {
//                fourth = false;
//                fifth = true;
//            }
//        }
//
//        if (third && !fourth) {
//            int fourthDegreePosition = addUntilIndex(intervals, 3);
//            if (fourthDegreePosition == 5) {
//                third = false;
//                fourth = true;
//            }
//        }
//
//        if (third) modalDegrees[2] = 3;
//        if (fourth) modalDegrees[3] = 4;
//        if (fifth) modalDegrees[4] = 5;
//
//        boolean sixth = intervals[4] != 0;
//        if (sixth) {
//            int sixthDegreePosition = addUntilIndex(intervals, 5);
//            if (sixthDegreePosition == 7) {
//                modalDegrees[5] = 5;
//                sixth = false;
//            }
//        }
//
//        boolean seventh = intervals[5] != 0;
//
//        if ((sixth & !seventh) || intervals.length == 6) {
//            int sixthDegreePosition = addUntilIndex(intervals, 5);
//            if (sixthDegreePosition >= 10) {
//                sixth = false;
//                seventh = true;
//            }
//        }
//
//        if (sixth && seventh && intervals[6] == 0) {
//            int finalDegreePosition = addUntilIndex(intervals, 6);
//            if (finalDegreePosition >= 10) {
//                sixth = false;
//                seventh = true;
//            } else {
//                sixth = true;
//                seventh = false;
//            }
//
//        }
//
////        if (intervals.length == 6) {
////            int finalDegreePosition = addUntilIndex(intervals, intervals.length);
////            if (finalDegreePosition >= 10)
////        }
//
//
//        if (sixth) modalDegrees[5] = 6;
//        if (seventh) modalDegrees[6] = 7;
//
//        return modalDegrees;

//
//        int mode = getMode();
//        for (int i = 0; i < modalDegrees.length; i++) {
//            modalDegrees[i] = degrees[(mode + i) % (modalDegrees.length)] - mode;
//        }
//        return modalDegrees;
    }

    public int addUntilIndex(int[] items, int endExclusive) {
        int sum = 0;
        for (int i = 0; i < endExclusive; i++)
            sum += items[i];
        return sum;
    }

    //public abstract String[] getChords();

    public String[] getChords() {
        return new String[]{"M7", "m7", "m7", "M7", "7", "m7", "m7ø"};
    }

    public String getChord(int i) {
        String[] chords = getChords();
        return chords[(i + getMode()) % chords.length];
    }
}
