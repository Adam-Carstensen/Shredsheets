package revert.shredsheets.models.scales;

public class HarmonicMajor extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{2, 2, 1, 2, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Harmonic Major";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Harmonic Major", "Dorian ♭5", "Phrygian ♭4", "Lydian ♭3", "Mixolydian ♭9", "Lydian Augmented ♯2", "Locrian ♭♭7"};
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
        return new String[]{"M7", "m7 ♭5", "m7", "mΔ7", "7", "M7+", "m7o"};
    }
}
