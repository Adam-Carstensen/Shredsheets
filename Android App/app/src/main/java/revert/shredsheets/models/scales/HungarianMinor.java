package revert.shredsheets.models.scales;

public class HungarianMinor extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 2, 1, 3, 1, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Hungarian Minor";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Hungarian Minor", "Oriental", "Ionian Augmented #2", "Locrian bb3 bb7", "Double Harmonic", "Lydian #6 #2", "Alt natural 5 bb7"};
    }
}
