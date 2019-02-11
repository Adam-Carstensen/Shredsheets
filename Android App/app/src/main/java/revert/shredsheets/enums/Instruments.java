package revert.shredsheets.enums;

public enum Instruments {
    Guitar6(0, "Guitar"), Guitar7(1, "7 String Guitar"), Bass4(2,"Bass"), Bass5(3,"5 String Bass"), Ukulele(4,"Ukulele");

    private int instrumentId;
    public static Instruments[] OrderedKeys = new Instruments[]{Instruments.Guitar6, Instruments.Guitar7, Instruments.Bass4, Instruments.Bass5, Instruments.Ukulele};

    private String cleanName;
    public String getCleanName()
    {
        return cleanName;
    }

    private Instruments(int instrumentId, String cleanName) {
        this.instrumentId = instrumentId;
        this.cleanName = cleanName;
    }

    public int getValue() {
        return instrumentId;
    }
}
