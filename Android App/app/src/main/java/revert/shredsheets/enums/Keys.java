package revert.shredsheets.enums;

public enum Keys {
    C(0, "C"), C_Sharp(1, "C#"), D_Flat(1,"Db"), D(2,"D"), D_Sharp(3,"D#"), E_Flat(3,"Eb"), E(4,"E"), F_Flat(4,"Fb"), E_Sharp(5,"E#"), F(5,"F"), F_Sharp(6,"F#"), G_Flat(6, "Gb"), G(7, "G"), G_Sharp(8, "G#"), A_Flat(8, "Ab"), A(9, "A"), A_Sharp(10, "A#"), B_Flat(10, "Bb"), B(11, "B"), C_Flat(11, "Cb"), B_Sharp(0, "B#");

    private int key;
    public static Keys[] OrderedKeys = new Keys[]{Keys.C, Keys.C_Sharp, Keys.D_Flat, Keys.D, Keys.D_Sharp, Keys.E_Flat, Keys.E, Keys.F_Flat, Keys.E_Sharp, Keys.F, Keys.F_Sharp, Keys.G_Flat, Keys.G, Keys.G_Sharp, Keys.A_Flat, Keys.A, Keys.A_Sharp, Keys.B_Flat, Keys.B, Keys.C_Flat, Keys.B_Sharp };

    private String cleanName;
    public String getCleanName()
    {
        return cleanName;
    }

    private Keys(int key, String cleanName) {
        this.key = key;
        this.cleanName = cleanName;
    }

    public int getValue() {
        return key;
    }


    public static String GetWholeNoteName(Keys key) {
        switch (key) {
            case C_Flat:
            case C:
            case C_Sharp:
                return "C";
            case D_Flat:
            case D:
            case D_Sharp:
                return "D";
            case E_Flat:
            case E:
            case E_Sharp:
                return "E";
            case F_Flat:
            case F:
            case F_Sharp:
                return "F";
            case G_Flat:
            case G:
            case G_Sharp:
                return "G";
            case A_Flat:
            case A:
            case A_Sharp:
                return "A";
            case B_Flat:
            case B:
            case B_Sharp:
                return "B";
            default:
                return "X";
        }
    }


    public static int GetWholeNoteIndex(Keys key) {
        switch (key) {
            case C_Flat:
            case C:
            case C_Sharp:
                return 0;
            case D_Flat:
            case D:
            case D_Sharp:
                return 1;
            case E_Flat:
            case E:
            case E_Sharp:
                return 2;
            case F_Flat:
            case F:
            case F_Sharp:
                return 3;
            case G_Flat:
            case G:
            case G_Sharp:
                return 4;
            case A_Flat:
            case A:
            case A_Sharp:
                return 5;
            case B_Flat:
            case B:
            case B_Sharp:
                return 6;
            default:
                return 0;
        }
    }


    public static int GetWholeNotePosition(Keys key) {
        switch (key) {
            case C:
            case B_Sharp:
                return 0;
            case C_Sharp:
            case D_Flat:
                return 1;
            case D:
                return 2;
            case D_Sharp:
            case E_Flat:
                return 3;
            case E:
                return 4;
            case E_Sharp:
            case F:
                return 5;
            case F_Sharp:
            case G_Flat:
                return 6;
            case G:
                return 7;
            case G_Sharp:
            case A_Flat:
                return 8;
            case A:
                return 9;
            case A_Sharp:
            case B_Flat:
                return 10;
            case B:
            case C_Flat:
                return 11;
            default:
                return 0;
        }
    }
}
