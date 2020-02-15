package revert.shredsheets.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import revert.shredsheets.enums.Keys;

public class TuningModel {
    public static Keys[] getStandardTuning() {
        return new Keys[]{Keys.E, Keys.B, Keys.G, Keys.D, Keys.A, Keys.E, Keys.B, Keys.F_Sharp, Keys.C_Sharp, Keys.G_Sharp, Keys.D_Sharp, Keys.A_Sharp};
    }

    public static Keys[] getDroppedDTuning() {
        return new Keys[]{Keys.E, Keys.B, Keys.G, Keys.D, Keys.A, Keys.D, Keys.A, Keys.D, Keys.A, Keys.D, Keys.A, Keys.D};
    }

    public static Keys[] getNewStandardTuning() {
        return new Keys[]{Keys.G, Keys.E, Keys.A, Keys.D, Keys.G, Keys.C, Keys.G, Keys.E, Keys.A, Keys.D, Keys.G, Keys.C};
    }

    public static Keys[] getOpenCTuning() {
        return new Keys[]{Keys.E, Keys.C, Keys.G, Keys.C, Keys.G, Keys.C, Keys.E, Keys.C, Keys.G, Keys.C, Keys.G, Keys.C};
    }

    public static Keys[] getOpenDTuning() {
        return new Keys[]{Keys.D, Keys.A, Keys.D, Keys.F_Sharp, Keys.A, Keys.D, Keys.D, Keys.A, Keys.D, Keys.F_Sharp, Keys.A, Keys.D};
    }

    public static Keys[] getOpenETuning() {
        return new Keys[]{Keys.E, Keys.B, Keys.E, Keys.G_Sharp, Keys.B, Keys.E, Keys.E, Keys.B, Keys.E, Keys.G_Sharp, Keys.B, Keys.E};
    }

    public static Keys[] getOpenFTuning() {
        return new Keys[]{Keys.C, Keys.F, Keys.C, Keys.F, Keys.A, Keys.F, Keys.C, Keys.F, Keys.C, Keys.F, Keys.A, Keys.F};
    }

    public static Keys[] getOpenGTuning() {
        return new Keys[]{Keys.D, Keys.G, Keys.D, Keys.G, Keys.B, Keys.D, Keys.D, Keys.G, Keys.D, Keys.G, Keys.B, Keys.D};
    }

    public static Keys[] getOpenATuning() {
        return new Keys[]{Keys.E, Keys.A, Keys.C_Sharp, Keys.E, Keys.A, Keys.E, Keys.E, Keys.A, Keys.C_Sharp, Keys.E, Keys.A, Keys.E};
    }

    public static Keys[] getOpenBTuning() {
        return new Keys[]{Keys.B, Keys.F_Sharp, Keys.B, Keys.F_Sharp, Keys.B, Keys.D_Sharp, Keys.B, Keys.F_Sharp, Keys.B, Keys.F_Sharp, Keys.B, Keys.D_Sharp};
    }

    public static Keys[] getUkuleleStandardTuning() {
        return new Keys[]{Keys.E, Keys.B, Keys.A, Keys.E, Keys.C, Keys.G, Keys.E, Keys.A, Keys.G, Keys.C, Keys.E, Keys.G};
    }

    public static Keys[] getFiveStringBassTuning() {
        return new Keys[]{Keys.B, Keys.G, Keys.D, Keys.A, Keys.E, Keys.B, Keys.G, Keys.D, Keys.A, Keys.E, Keys.B, Keys.G};
    }

    public static Keys[] getEightStringAlternateTuning() {
        return new Keys[]{Keys.E, Keys.B, Keys.G, Keys.D, Keys.A, Keys.E, Keys.B, Keys.E, Keys.D, Keys.A, Keys.E, Keys.B};
    }

    public static HashMap<Keys[], String> getTunings() {
        HashMap<Keys[], String> tunings = new HashMap<>(13);
        tunings.put(getStandardTuning(), "Standard Tuning");
        tunings.put(getDroppedDTuning(), "Drop D");
        tunings.put(getNewStandardTuning(), "New Standard");
        tunings.put(getOpenCTuning(), "Open C");
        tunings.put(getOpenDTuning(), "Open D");
        tunings.put(getOpenETuning(), "Open E");
        tunings.put(getOpenFTuning(), "Open F");
        tunings.put(getOpenGTuning(), "Open G");
        tunings.put(getOpenATuning(), "Open A");
        tunings.put(getOpenBTuning(), "Open B");
        tunings.put(getUkuleleStandardTuning(), "Ukulele Standard");
        tunings.put(getFiveStringBassTuning(), "5str Bass Standard");
        tunings.put(getEightStringAlternateTuning(), "8str Alternate");
        return tunings;
    }

    public static String getTuningName(Keys[] keys) {
        Set<Map.Entry<Keys[], String>> tunings = getTunings().entrySet();

        for (Map.Entry<Keys[], String> tuning : tunings) {
            if (Arrays.equals(keys, tuning.getKey())) {
                return tuning.getValue();
            }
        }

        StringBuilder tuningBuilder = new StringBuilder();
        for (Keys key : keys) {
            tuningBuilder.append(key);
        }
        return "Tuning: " + tuningBuilder.toString();
    }
}
