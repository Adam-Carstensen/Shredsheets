package revert.shredsheets.models.scales;

public class MinorBlues extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3, 0, 2, 1, 1, 3, 2};
    }

    @Override
    public String getName() {
        return "Minor Blues";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Ionian", "Phrygian", "Lydian", "Mixolydian", "Aolian", "Locrian"};
    }



    @Override
    public int getDegree(int i) {
        if (i == 5) return 4;
        return i;
    }


}
