package revert.shredsheets.models.scales;

import java.util.HashMap;
import java.util.Map;

public enum Scales {
    Major(0, "Major"),
    PentatonicMinor(1, "Pentatonic"),
    Blues(3, "Blues"),
    HarmonicMinor(4, "Harmonic Minor"),
    HarmonicMajor(5, "Harmonic Major"),
    MelodicMinor(6, "Melodic Minor"),
    DoubleHarmonic(7, "Double Harmonic"),
    Diminished(8, "Diminished"),
    Augmented(9, "Augmented"),
    Hungarian(10, "Hungarian"),
    Persian(11, "Persian"),
    Neopolitan(12, "Neopolitan"),
    NeopolitanMinor(13, "Neopolitan Minor"),
    Triads(14, "Triads");

    public static Map<String, Scale> ScalesByName = new HashMap<String, Scale>()
    {
        { put("Major", new MajorScale());}
        { put("Pentatonic", new Pentatonic());}
        { put("Blues", new Blues());}
        { put("Harmonic Minor", new HarmonicMinor());}
        { put("Harmonic Major", new HarmonicMajor());}
        { put("Melodic Minor", new MelodicMinor());}
        { put("Double Harmonic", new DoubleHarmonic());}
        { put("Diminished", new Diminished());}
        { put("Augmented", new Augmented());}
        { put("Hungarian", new Hungarian());}
        { put("Persian", new Persian());}
        { put("Neopolitan", new Neopolitan());}
        { put("Neopolitan Minor", new NeopolitanMinor());}
        { put("Triads", new Triads());}
    };

    public static Scale[] AllScales = new Scale[]
    {
        new MajorScale(),
        new Pentatonic(),
        new Blues(),
        new HarmonicMinor(),
        new HarmonicMajor(),
        new MelodicMinor(),
        new DoubleHarmonic(),
        new Diminished(),
        new Augmented(),
        new Hungarian(),
        new Persian(),
        new Neopolitan(),
        new NeopolitanMinor(),
        new Triads()
    };

    private final int scaleIndex;
    private final String cleanName;

    Scales(int scale, String cleanName) {
        this.scaleIndex = scale;
        this.cleanName = cleanName;
    }
}
