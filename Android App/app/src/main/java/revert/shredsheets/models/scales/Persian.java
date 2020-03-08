package revert.shredsheets.models.scales;

public class Persian extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 1,3,1,1,2,3,1};
    }

    @Override
    public String getName() {
        return "Persian";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Persian", "Chromatic Dorian Inverse", "Chromatic Phrygian Inverse", "Chromatic Lydian Inverse", "Chromatic Mixolydian Inverse", "Chromatic Hypodorian Inverse", "Chromatic Hypophrygian Inverse"};
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
        return new String[] { "M7 ♭5", "M7", "m ♭♭3 ♭♭7", "mΔ7", "M7 ♯3", "M ♯5 ♭7", "o7 ♭♭3" };
    }
}
