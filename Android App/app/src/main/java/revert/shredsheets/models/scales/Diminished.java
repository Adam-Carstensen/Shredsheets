package revert.shredsheets.models.scales;

public class Diminished extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 2, 1, 2, 1, 2, 1, 2, 1};
    }

    @Override
    public String getName() {
        return "Diminished";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Diminished", "Octotonic"};
    }
}
