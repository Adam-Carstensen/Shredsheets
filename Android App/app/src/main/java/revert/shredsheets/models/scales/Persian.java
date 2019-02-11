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
        return new String[]{"Persian", "Mode 2", "Mode 3", "Mode 4", "Mode 5", "Mode 6", "Mode 7"};
    }
}
