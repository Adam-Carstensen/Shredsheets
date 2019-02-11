package revert.shredsheets.models.scales;

public class MajorBlues extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3, 0, 1, 1, 3, 2, 1};
    }

    @Override
    public String getName() {
        return "Major Blues";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Ionian", "Phrygian", "Lydian", "Mixolydian", "Aolian", "Locrian"};
    }



    @Override
    public int getDegree(int i) {
        if (i == 3) return 4;
        return i;
    }


}
