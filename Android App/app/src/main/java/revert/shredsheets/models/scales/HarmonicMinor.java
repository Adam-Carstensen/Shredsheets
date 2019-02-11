package revert.shredsheets.models.scales;

public class HarmonicMinor extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 2, 1, 2, 2, 1, 3, 1};
    }

    @Override
    public String getName() {
        return "Harmonic Minor";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{"Harmonic Minor", "Locrian #6", "Ionian Augmented", "Romanian", "Phrygian Dominant", "Lydian #2", "UltraLocrian"};
    }
}
