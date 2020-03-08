package revert.shredsheets.models.scales;

public class Hungarian extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3, 1, 2, 1, 2, 1, 2};
    }

    @Override
    public String getName() {
        return "Hungarian";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Hungarian", "SuperLocrian ♭♭6 ♭♭7", "Harmonic Minor ♭5", "SuperLocrian ♯6", "Jazz Minor ♯5", "Dorian ♭9 ♯11", "Lydian Augmented ♯3"};
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
        return new String[]{"7", "m7o", "mΔ7 ♭5", "m7ø", "mΔ7+", "m7", "Δ7+♯3"};
    }
}
