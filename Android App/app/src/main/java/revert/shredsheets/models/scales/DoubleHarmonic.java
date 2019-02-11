package revert.shredsheets.models.scales;

public class DoubleHarmonic extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 1, 3, 1, 2, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Double Harmonic";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Double Harmonic Major", "Lydian #2 #6", "UltraPhrygian", "Hungarian Minor", "Oriental", "Ionian Augmented #2", "Locrian bb3 bb7"};
    }
}
