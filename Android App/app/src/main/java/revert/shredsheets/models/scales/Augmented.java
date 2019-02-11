package revert.shredsheets.models.scales;

public class Augmented extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3,1,3,1,3,1};
    }

    @Override
    public String getName() {
        return "Augmented";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Augmented", "Augmented Inverse"};
    }
}
