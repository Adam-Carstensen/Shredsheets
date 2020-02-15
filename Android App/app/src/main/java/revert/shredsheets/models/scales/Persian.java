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
}
