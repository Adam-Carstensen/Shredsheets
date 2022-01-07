package revert.shredsheets.models.scales;

public class Diminished extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{2, 1, 2, 1, 2, 1, 2, 1};
    }

    @Override
    public String getName() {
        return "Diminished";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Whole Half", "Half Whole"};
    }

    @Override
    protected boolean[][] getModalHighlighting() {
        return new boolean[][]{
                {true, false, true, false, true, false, true, false},
                {true, true, false, true, false, true, false, true},
        };
    }

    @Override
    public int[][] getDegrees() {
        return new int[][]
                {
                        {1, 2, 3, 4, 5, 5, 6, 7},
                        {1, 2, 3, 3, 4, 5, 6, 7},
                };
    }

    @Override
    public String[] getChords() {
        return new String[]{"m7o", "m7o", "m7o", "m7o", "m7o", "m7o", "m7o", "m7o"};
    }
}
