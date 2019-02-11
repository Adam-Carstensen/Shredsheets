package revert.shredsheets.models.themes;

import android.graphics.Paint;

public abstract class ShredsheetsTheme {
    public ShredsheetsTheme() {
    }

    public static ShredsheetsTheme[] getThemes() {
        return new ShredsheetsTheme[] { new DefaultTheme(), new BlueTheme() };
    }

    public Boolean[] defaultVector = new Boolean[]{true, false, true, false, true, false, true, false, false, false, false, false};

    public abstract Boolean[] getDegreeHighlightingVector();
    public abstract void setDegreeHighlightingVector(Boolean[] values);

    public abstract Paint getBorderPaint();

    public abstract int getRunnerBorderColor();
    public abstract Paint getRunnerBorderPaint();

    public abstract int getBorderColor();

    public abstract Paint getScaleBorderPaint();

    public abstract int[] getDegreeColors();

    public abstract int getBaseFretColor(int fretNumber);

    public abstract int getFretboardRunnerPaintColor(int fretNumber);

    //Got replaced by a maximal color difference algorithm... may come back
    public abstract int getFretboardRunnerTextColor(int fretNumber);

    public abstract float getBorderStrokeWidth();

    public abstract int[] getIntervalColors();

    public abstract int getEmptyFretColor();

    public abstract int getFretboardBorderColor();

    public abstract int getTextStrokeColor();

}
