package revert.shredsheets.models.scales;

public class PentatonicMinor extends Scale {
    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3, 0, 2, 2, 3, 0, 2};
    }

    @Override
    public String getName() {
        return "Pentatonic Minor";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Ionian", "Dorian", "Phrygian", "Mixolydian", "Aolian"};
    }

    @Override
    public int getDegree(int i) {
        if (i == 5) return 6;
        return i;
    }
}
