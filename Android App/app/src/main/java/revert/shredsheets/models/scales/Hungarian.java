package revert.shredsheets.models.scales;

public class Hungarian extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3, 1, 2, 1, 2, 1, 2};
    }

    @Override
    public String getName() {
        return "Hungarian";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Hungarian", "SuperLocrian bb6 bb7", "Harmonic Minor b5", "SuperLocrian #6", "Jazz Minor #5", "Dorian b9 #11", "Lydian Augmented #3"};
    }
}
