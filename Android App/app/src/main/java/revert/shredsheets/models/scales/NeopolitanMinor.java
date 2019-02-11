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
        return new String[]{"Neopolitan Minor", "Lydian #6", "Mixolydian Augmented", "Hungarian Gypsy", "Locrian Dominant", "Ionian #2", "UltraLocrian bb3"};
    }
}
