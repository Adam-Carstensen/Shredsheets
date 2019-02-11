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
        return new String[]{"Jazz Minor", "Dorian b9", "Lydian Augmented", "Lydian Dominant", "Mixolydian b6", "SemiLocrian", "SuperLocrian"};
    }
}
