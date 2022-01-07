package revert.shredsheets.models.scales;

public class NeopolitanMinor extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 1, 2, 2, 2, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Neopolitan Minor";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Neopolitan Minor", "Lydian ♯6", "Mixolydian Augmented", "Hungarian Gypsy", "Locrian Dominant", "Ionian ♯2", "UltraLocrian ♭♭3"};
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
        return new String[] { "mΔ7", "M7", "7+", "m7 ♭5", "7 ♭5", "M7", "♭♭3 ♭5 ♭♭7" };
    }
}
