package revert.shredsheets.models.scales;

public class PentatonicMajor extends Scale {
    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 2, 2, 3, 0, 2, 3, 0};
    }

    @Override
    public String getName() {
        return "Pentatonic Major";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Major Pentatonic", "Suspended Pentatonic", "Man Gong", "Ritusen", "Minor Pentatonic"};
    }

}
