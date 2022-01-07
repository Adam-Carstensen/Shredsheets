package revert.shredsheets.models.scales;

public class Augmented extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{3, 1, 3, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Augmented";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Minor-Third Half-Step", "Half-Step Minor-Third"};
    }

    @Override
    protected boolean[][] getModalHighlighting() {
        return new boolean[][]{
                {true, true, true, false, true, true},
                {true, true, false, false, true, false},
        };
    }

    @Override
    public int[][] getDegrees() {
        return new int[][]{
                {1, 3, 3, 5, 5, 7},
                {1, 2, 3, 4, 6, 6},
        };
    }

    @Override
    public String[] getChords() {
        return new String[]{"M7", "M+♭♭7", "M7", "M+♭♭7", "M7", "M+♭♭7"};
    }
}
