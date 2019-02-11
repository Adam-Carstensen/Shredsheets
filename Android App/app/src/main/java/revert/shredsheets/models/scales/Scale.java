package revert.shredsheets.models.scales;

import revert.common.TextModule;
import revert.shredsheets.models.SessionModel;

public abstract class Scale {
    private String[] modeNames = null;
    private int mode = 0;

    public abstract int[] getScaleIntervals();

    public int[] getIntervals() {
        int[] intervals = getScaleIntervals();
        int[] modalIntervals = new int[intervals.length];

        for (int i = 0; i < modalIntervals.length; i++) {
            {
                int modalPosition = (i + getMode());
                modalIntervals[i] = intervals[modalPosition % intervals.length];
            }
        }


        return modalIntervals;
    }

    public int GetDegree(int degree) {
        return degree;
    }

    String[] degreeNames = null;

    //Degree names are all based on a comparison to the Ionian's intervals, which is the default mode of Major
    private int[] ionianCumulativeIntervals = new int[]{ 0, 2, 4, 5, 7, 9, 11 };

    public String[] getDegreeNames() {
        //TODO: Document and refactor
        if (degreeNames != null) return degreeNames;

        int[] intervals = getIntervals();
        int intervalCumulative = intervals[0];

        degreeNames = new String[intervals.length];
        degreeNames[0] = "1st";

        for (int i = 1; i < degreeNames.length; i++) {
            int modifiedDegree = getDegree(i);

            if (modifiedDegree >= ionianCumulativeIntervals.length) modifiedDegree = ionianCumulativeIntervals.length - 1;
            int ionianCumulative = ionianCumulativeIntervals[modifiedDegree];

            String degreeName = "";

            if (intervalCumulative < ionianCumulative)
                for (int j = 0; j < ionianCumulative - intervalCumulative; j++)
                    degreeName += "b";
            else if (intervalCumulative > ionianCumulative)
                for (int j = 0; j < intervalCumulative - ionianCumulative; j++)
                    degreeName += "#";

            degreeName += TextModule.getOrdinallySuffexedNumber(modifiedDegree + 1); //0 based array, 1 based degree naming system

            degreeNames[i] = degreeName;

            int intervalStepSize = intervals[i];
            if (intervalStepSize == 0) degreeNames[i] = "";
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
        if (this.mode == mode) return;
        this.mode = mode;
        this.degreeNames = null;

//        FragmentActivity fretboardActivity = SessionModel.getInstance().fretboardActivity;
//        if (fretboardActivity != null) fretboardActivity.getIntent().putExtra("mode", mode);
        SessionModel.getInstance().invalidateViews();
    }


    public int getDegree(int i) {
        return i;
    }
}
