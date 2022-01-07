package revert.shredsheets.models.scales;

public class MelodicMinor extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 2, 1, 2, 2, 2, 2, 1};
    }

    @Override
    public String getName() {
        return "Melodic Minor";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Jazz Minor", "Dorian ♭9", "Lydian Augmented", "Lydian Dominant", "Mixolydian ♭6", "SemiLocrian", "SuperLocrian"};
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
        return new String[] { "mΔ7", "m7", "M7 ♭5", "7", "7", "m7 ♭5", "m7 ♭5" };
    }
}
