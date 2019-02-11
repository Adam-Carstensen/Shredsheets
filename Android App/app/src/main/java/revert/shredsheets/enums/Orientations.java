package revert.shredsheets.enums;

public enum Orientations {
    Undefined(0), Portrait(1), Landscape(2);
    public static Orientations[] Values = new Orientations[]{Undefined, Portrait, Landscape};
    private int value;

    private Orientations(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Orientations getOrientation() {
        return getOrientation(value);
    }

    public static Orientations getOrientation(int value) {
        if (value >= Values.length) return Values[0];
        return Values[value];
    }

    public int getFretCount() {
        if (value < 2) return 13;
        return 25;
    }
}
