package revert.shredsheets.models.scales;

public class MajorScale extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 2, 2, 1, 2, 2, 2, 1};
    }

    @Override
    public String getName() {
        return "Major";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Ionian (Major)", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aolian (Minor)", "Locrian"};
    }
}
