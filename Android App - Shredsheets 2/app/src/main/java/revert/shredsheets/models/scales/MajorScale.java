package revert.shredsheets.models.scales;

public class MajorScale extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{2, 2, 1, 2, 2, 2, 1};
    }

    @Override
    public String getName() {
        return "Major";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Ionian (Major)", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian (Minor)", "Locrian"};
    }

    @Override
    protected boolean[][] getModalHighlighting() {
        return new boolean[][]{
                {true, false, true, false, false, true, true},
                {true, false, true, false, false, true, true},
                {true, true, true, false, false, true, true},
                {true, false, true, true, true, false, false},
                {true, false, true, false, false, false, true},
                {true, false, true, false, false, true, true},
                {true, true, true, false, true, false, true}
        };
    }

    @Override
    public int[][] getDegrees() {
        return new int[][]{
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7}
        };
    }

    @Override
    public String[] getChords() {
        return new String[]{"M7", "m7", "m7", "M7", "7", "m7", "m7ø"};
    }
}
