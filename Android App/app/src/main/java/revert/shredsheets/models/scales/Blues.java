package revert.shredsheets.models.scales;

public class Blues extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3, 2, 1, 1, 3, 2};
    }

    @Override
    public String getName() {
        return "Blues";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{ "Minor Blues", "Major Blues" };
    }

    @Override
    protected boolean[][] getModalHighlighting() {
        return new boolean[][]{
                {true, true, false, true, true, true},
                {true, false, true, true, false, true},
        };
    }

    @Override
    public int[][] getDegrees() {
        return new int[][] {
                { 1,3,4,5,5,7 },
                { 1,2,3,3,5,6 }
        };
    }

    @Override
    public String[] getChords() {
        return new String[]{"m7", "M", "5 add ♭7", "m7", "M7", "m7"};
    }
}
