package revert.shredsheets.models.scales;

public class DoubleHarmonic extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{1, 3, 1, 2, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Double Harmonic";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Double Harmonic Major", "Lydian ♯2 ♯6", "Ultraphrygian", "Hungarian Minor", "Oriental", "Ionian Augmented ♯2", "Locrian ♭♭3 ♭♭7"};
    }

    @Override
    protected boolean[][] getModalHighlighting() {
        return new boolean[][]{
                {true, true, true, false, false, true, true},
                {true, false, true, true, false, true, true},
                {true, true, false, false, false, true, true},
                {true, false, true, true, true, false, true},
                {true, true, true, false, true, true, false},
                {true, true, true, false, false, true, true},
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
        return new String[]{"M7", "M7", "m♭♭7", "mΔ7", "7♭5", "M7+", "♭♭3 ♭5 ♭♭7"};
    }
}
