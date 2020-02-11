package revert.shredsheets.models.scales;

public class MajorScale extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 2, 2, 1, 2, 2, 2, 1};
    }

    @Override
    public String getName() {
        return "Major";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Ionian (Major)", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aolian (Minor)", "Locrian"};
    }

    @Override
    protected boolean[] getModalHighlighting(int mode) {
        switch(mode) {
            case 0:
                return new boolean[] { true, false, true, false, false, true, true, false, false, false, false, false };
            case 1:
                return new boolean[] { true, false, true, false, false, true, true, false, false, false, false, false };
            case 2:
                return new boolean[] { true, true, true, false, false, true, true, false, false, false, false, false };
            case 3:
                return new boolean[] { true, false, true, true, true, false, false, false, false, false, false, false };
            case 4:
                return new boolean[] { true, false, true, false, false, false, true, false, false, false, false, false };
            case 5:
                return new boolean[] { true, false, true, false, false, true, true, false, false, false, false, false };
            case 6:
                return new boolean[] { true, true, true, false, true, false, true, false, false, false, false, false };
            default:
                return new boolean[] { true, false, true, false, true, false, true, false, false, false, false, false };
        }
    }
}
