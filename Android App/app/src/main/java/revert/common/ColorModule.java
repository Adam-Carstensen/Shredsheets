package revert.common;

import android.graphics.Color;

import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.themes.ShredsheetsTheme;

public class ColorModule {

    public static int getHsvContrastingColor(int color) {
        float[] hsv = new float[3]; //{ h = 0..360, s = 0..1, v = 0..1 }
        Color.colorToHSV(color, hsv);
        float saturation = hsv[1];
        float value = hsv[2];
        float combined = saturation * value;
        int rgbValues = (int) (255 * combined);
        return Color.rgb(rgbValues, rgbValues, rgbValues);
    }

    public static int getContrastingColor(int color) {
        return Color.rgb(255 - Color.red(color), 255 - Color.green(color), 255 - Color.blue(color));
    }

    public static int getMaximallyContrastingColor(int color) {
        double y = (double)(299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / (double)1000;
        return y >= 128 ? Color.parseColor("#333333") : Color.parseColor("#F0F0F0");
    }

    public static int getModeratelyContrastingColor(int color) {
        double y = (double)(299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / (double)1000;
        return y >= 128 ? Color.parseColor("#555555") : Color.parseColor("#CCCCCC");
    }

    public static int getMinimallyContrastingColor(int color) {
        double y = (double)(299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / (double)1000;
        return y >= 128 ? Color.parseColor("#888888") : Color.parseColor("#666666");
    }


    public static int getIntervalColor(String intervalName) {
        SessionModel session = SessionModel.getInstance();
        ShredsheetsTheme theme = session.getTheme();

        int[] intervalColors = theme.getIntervalColors();

        switch (intervalName) {
            case "h":
                return intervalColors[0];
            case "W":
                return intervalColors[1];
            case "W+h":
                return intervalColors[2];
            default:
                return intervalColors[2];
        }
    }
}