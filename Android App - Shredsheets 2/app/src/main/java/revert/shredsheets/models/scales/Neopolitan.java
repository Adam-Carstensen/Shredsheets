package revert.shredsheets.models.scales;

public class Neopolitan extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 1, 2, 2, 2, 2, 2, 1};
    }

    @Override
    public String getName() {
        return "Neopolitan";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Neopolitan", "Leading Whole-Tone", "Lydian Augmented Dominant", "Lydian Dominant ♭6", "Major Locrian", "SemiLocrian ♭4", "SuperLocrian ♭♭3"};
    }

    @Override
    public int[][] getDegrees() {
        return new int[][] {
                { 1, 2, 3, 4, 5, 6, 7 },
                { 1, 2, 3, 4, 5, 6, 7 },
                { 1, 2, 3, 4, 5, 6, 7 },
                { 1, 2, 3, 4, 5, 6, 7 },
                { 1, 2, 3, 4, 5, 6, 7 },
                { 1, 2, 3, 4, 5, 6, 7 },
                { 1, 2, 3, 4, 5, 6, 7 }
        };
    }

    @Override
    public String[] getChords() {
        return new String[] { "mΔ7", "M7+", "M+ ♭7", "M ♭7", "7 ♭5", "m7 ♭5", "♭♭3 ♭5 ♭7" };
    }
}
