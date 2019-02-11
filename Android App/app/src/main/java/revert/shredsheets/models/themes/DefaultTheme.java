package revert.shredsheets.models.themes;

import android.graphics.Color;
import android.graphics.Paint;

import revert.shredsheets.models.SessionModel;

public class DefaultTheme extends ShredsheetsTheme {

    private Boolean[] highlightedDegrees = null;

    private int[] degreeColors = null;

    private int borderColor = Color.parseColor("#222222");
    private float borderStrokeWidth = 2f;

    public int runnerBorderColor = Color.parseColor("#AAAABB");
    public float runnerStrokeWidth = 3.5f;
    private int[] intervalColors;


    public DefaultTheme() {
    }

    @Override
    public int getRunnerBorderColor() {
        return runnerBorderColor;
    }

    @Override
    public Boolean[] getDegreeHighlightingVector() {
        return highlightedDegrees != null ? highlightedDegrees :
                (highlightedDegrees = defaultVector); //supports up to max 12 degrees
    }

    @Override
    public void setDegreeHighlightingVector(Boolean[] values) {
        highlightedDegrees = values;
        SessionModel.getInstance().invalidateViews();
    }


    private Paint borderPaint = null;
    @Override
    public Paint getBorderPaint() {
        if (borderPaint == null) {
            borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setColor(getBorderColor());
        }
        return borderPaint;
    }

    private Paint runnerBorderPaint = null;
    @Override
    public Paint getRunnerBorderPaint() {
        if (runnerBorderPaint == null) {
            runnerBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            runnerBorderPaint.setStyle(Paint.Style.STROKE);
            runnerBorderPaint.setColor(Color.parseColor("#111111"));
        }
        return runnerBorderPaint;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }
    public void setBorderColor(int color) {
        borderColor = color;
    }

    private Paint scaleBorderPaint = null;
    @Override
    public Paint getScaleBorderPaint() {
        if (scaleBorderPaint != null) return scaleBorderPaint;

        scaleBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleBorderPaint.setColor(Color.rgb(200,200,200));
        scaleBorderPaint.setStyle(Paint.Style.STROKE);
        scaleBorderPaint.setStrokeWidth(10);
        return scaleBorderPaint;
    }

    public void setScaleBorderPaint(Paint scaleBorderPaint) {
        this.scaleBorderPaint = scaleBorderPaint;
    }

    @Override
    public int[] getDegreeColors() {
        return degreeColors != null ? degreeColors :
                (degreeColors = new int[]{
                        Color.rgb(55, 136, 203), //3788CB
                        Color.rgb(228, 210, 242),
                        Color.rgb(244, 218, 62), //F4DA3E
                        Color.rgb(181, 103, 245), //6AD81C
                        Color.rgb(106, 216, 28),
                        Color.rgb(100, 44, 118), //642C76
                        Color.rgb(255, 102, 255),
                        Color.rgb(175, 225, 170),
                        Color.rgb(100, 225, 90),
                        Color.rgb(30, 225, 10),
                        Color.rgb(225, 100, 10),
                        Color.rgb(225, 100, 75),
                });
    }

    public void setDegreeColors(int[] colors) {
        degreeColors = colors;
        SessionModel.getInstance().invalidateViews();
    }

    @Override
    public int getBaseFretColor(int fretNumber) {
        switch (fretNumber) {
            case 0:
            case 12:
            case 24:
                return Color.parseColor("#999999");
            case 3:
            case 5:
            case 7:
            case 9:
            case 15:
            case 17:
            case 19:
            case 21:
                return Color.parseColor("#CCCCDD");
            default:
                return Color.parseColor("#e9e9e9");
        }
    }

    @Override
    public int getFretboardRunnerPaintColor(int fretNumber) {
        switch (fretNumber) {
            case 0:
            case 12:
            case 24:
                return Color.parseColor("#404040");
            case 3:
            case 5:
            case 7:
            case 9:
            case 15:
            case 17:
            case 19:
            case 21:
                return Color.parseColor("#5c5c5c");
            default:
                return Color.parseColor("#d9d9d9");
        }
    }

    //Got replaced by a maximal color difference algorithm... may come back
    @Override
    public int getFretboardRunnerTextColor(int fretNumber) {
        switch (fretNumber) {
            case 0:
            case 12:
            case 24:
                return Color.parseColor("#333333");
            case 3:
            case 5:
            case 7:
            case 9:
            case 15:
            case 17:
            case 19:
            case 21:
                return Color.parseColor("#555555");
            default:
                return Color.parseColor("#777777");
        }
    }

    @Override
    public float getBorderStrokeWidth() {
        return borderStrokeWidth;
    }

    public void setBorderStrokeWidth(float borderStrokeWidth) {
        this.borderStrokeWidth = borderStrokeWidth;
    }

    @Override
    public int[] getIntervalColors() {
        if (intervalColors == null) intervalColors = new int[] {
                Color.parseColor("#777777"),
                Color.parseColor("#A0A0A0"),
                Color.parseColor("#BBBBBB"),
                Color.parseColor("#BBBBBB"),
                Color.parseColor("#BBBBBB") ,
                Color.parseColor("#BBBBBB") ,
                Color.parseColor("#BBBBBB")
        };
        return intervalColors;
    }

    public void setIntervalColors(int[] intervalColors) {
        this.intervalColors = intervalColors;
    }

    @Override
    public int getEmptyFretColor() {
        return Color.parseColor("#1e1918");
    }

    @Override
    public int getFretboardBorderColor() { return Color.parseColor("#030303");
    }

    @Override
    public int getTextStrokeColor() {
        return Color.parseColor("#090909");
    }

}
