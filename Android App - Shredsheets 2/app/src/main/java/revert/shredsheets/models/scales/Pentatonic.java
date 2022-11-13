package revert.shredsheets.models.scales;

public class Pentatonic extends Scale {

    @Override
    public int[] getScaleIntervals() {
        return new int[]{ 3, 2, 2, 3, 2 };
    }

    @Override
    public String getName() {
        return "Pentatonic";
    }

    @Override
    public String[] getModeNames() {
        return new String[]{ "Pentatonic Minor","Pentatonic Major", "Suspended Pentatonic", "Man Gong", "Ritusen" };
    }

    @Override
    protected boolean[][] getModalHighlighting() {
        return new boolean[][]{
                {true, true, false, true, true},
                {true, false, true, true, false},
                {true, false, false, true, true},
                {true, true, false, false, true},
                {true, false, false, true, false},
         };
    }


    @Override
    public String[] getChords() {
        return new String[] { "m7", "M", "5 ♭7", "♭3 ♭7", "5" };
    }

    @Override
    public int[][] getDegrees() {
        return new int[][] {
                { 1, 3, 4, 5, 7 },
                { 1, 2, 3, 5, 6 },
                { 1, 2, 4, 5, 7 },
                { 1, 3, 4, 6, 7 },
                { 1, 2, 4, 5, 6 }
        };
    }
}
