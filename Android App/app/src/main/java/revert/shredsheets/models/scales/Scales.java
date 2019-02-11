package revert.shredsheets.models.scales;

import java.util.HashMap;
import java.util.Map;

public enum Scales {
    Major(0, "Major"),
    PentatonicMinor(1, "Pentatonic Minor"),
    PentatonicMajor(2, "Pentatonic Major"),
    MinorBlues(3, "Minor Blues"),
    MajorBlues(4, "Major Blues"),
    HarmonicMinor(5, "Harmonic Minor"),
    MelodicMinor(6, "Melodic Minor"),
    DoubleHarmonic(7, "Double Harmonic"),
    Diminished(8, "Diminished"),
    Augmented(9, "Augmented"),
    Hungarian(10, "Hungarian"),
    HungarianMinor(11, "Hungarian Minor"),
    Persian(12, "Persian"),
    Neopolitan(13, "Neopolitan"),
    NeopolitanMinor(14, "Neopolitan Minor");

    public static Map<String, Scale> ScalesByName = new HashMap<String, Scale>()
    {
        { put("Major", new MajorScale());}
        { put("Pentatonic Minor", new PentatonicMinor());}
        { put("Pentatonic Major", new PentatonicMajor());}
        { put("Minor Blues", new MinorBlues());}
        { put("Major Blues", new MajorBlues());}
        { put("Harmonic Minor", new HarmonicMinor());}
        { put("Melodic Minor", new MelodicMinor());}
        { put("Double Harmonic", new DoubleHarmonic());}
        { put("Diminished", new Diminished());}
        { put("Augmented", new Augmented());}
        { put("Hungarian", new Hungarian());}
        { put("Hungarian Minor", new HungarianMinor());}
        { put("Persian", new Persian());}
        { put("Neopolitan", new Neopolitan());}
        { put("Neopolitan Minor", new NeopolitanMinor());}
    };


    public static Scale[] AllScales = new Scale[]
    {
        new MajorScale(),
        new PentatonicMinor(),
        new PentatonicMajor(),
        new MinorBlues(),
        new MajorBlues(),
        new HarmonicMinor(),
        new MelodicMinor(),
        new DoubleHarmonic(),
        new Diminished(),
        new Augmented(),
        new Hungarian(),
        new HungarianMinor(),
        new Persian(),
        new Neopolitan(),
        new NeopolitanMinor(),
    };

    private final int scaleIndex;
    private final String cleanName;

    Scales(int scale, String cleanName) {
        this.scaleIndex = scale;
        this.cleanName = cleanName;
    }
}
