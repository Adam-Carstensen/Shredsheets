package revert.shredsheets.models.scales;

public class HarmonicMinor extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{2, 1, 2, 2, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Harmonic Minor";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Harmonic Minor", "Locrian ♯6", "Ionian Augmented", "Romanian", "Phrygian Dominant", "Lydian ♯2", "UltraLocrian"};
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
        return new String[]{"mΔ7", "m7 ♭5", "M7+", "m7", "7", "M7", "m7o"};
    }
}
