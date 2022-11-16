package revert.shredsheets.models.scales;

public class Triads extends Scale {

    @Override
    public int[] getScaleIntervals() {
        if (super.getMode() == 0) return new int[]{ 4, 3, 5 };
        else return new int[]{ 3, 4, 5 };
    }

    @Override
    public String getName() {
        return "Triads";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{ "Major Triads", "Minor Triads" };
    }

    @Override
    protected boolean[][] getModalHighlighting() {
        return new boolean[][]{
                {true, true, true},
                {true, true, true},
         };
    }

    @Override
    public int[] getIntervals() {
        int[] intervals = getScaleIntervals();

        return intervals;
    }

    @Override
    public String[] getChords() {
        if (super.getMode() == 0) return new String[] { "M7", "m7", "7" };
        else return new String[] { "m7", "M7", "m7" };
    }

    @Override
    public String getChord(int i) {
        String[] chords = getChords();
        return chords[i % chords.length];
    }

    @Override
    public int[][] getDegrees() {
        return new int[][] {
                { 1, 3, 5 },
                { 1, 3, 5 }
        };
    }
}
