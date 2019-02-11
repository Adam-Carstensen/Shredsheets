package revert.shredsheets.models.scales;

public class Neopolitan extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 1, 2, 2, 2, 2, 2, 1};
    }

    @Override
    public String getName() {
        return "Neopolitan";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Neopolitan", "Leading Whole-Tone", "Lydian Augmented Dominant", "Lydian Dominant b6", "Major Locrian", "SemiLocrian b4", "SuperLocrian bb3"};
    }
}
