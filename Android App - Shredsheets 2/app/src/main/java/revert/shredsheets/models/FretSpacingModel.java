package revert.shredsheets.models;

public class FretSpacingModel {

    public static double[] GetFretSpacing(int fretCount, float width) {
        double[] results = new double[fretCount];
        if (SessionModel.getInstance().getUseRealisticFrets()) {
            double[] coefficients = getFretSpacingCoefficients(fretCount);
            for (int i = 0; i < coefficients.length; i++)
                results[i] = coefficients[i] * width;
        } else {
            for (int i = 0; i < fretCount; i++) results[i] = (width / (float)fretCount) +.5;
        }
        return results;
    }

    public static double[] getFretSpacingCoefficients(int count) {
        double[] fretSpaces = new double[count];

        //Each octave doubles the frequency and spans half the distance of the previous octave.

        //An open A string rings at 440Hz.  On a fender that's a ~34" length.
        //Same string, 12th fret = 880Hz with a 17" length.
        //Same string, 24th fret = 1660Hz with an 8.5" length.
        //It's the exponential scale.

        for (int i = 0; i < count; i++) {
            fretSpaces[i] = (Math.pow(2, (double) (count - i) / count) - 1) - (Math.pow(2, (double) (count - (i + 1)) / count) - 1);
        }
        return fretSpaces;
    }
}
