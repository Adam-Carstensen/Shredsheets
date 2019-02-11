package revert.common;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;

import revert.factories.RectFFactory;
import revert.factories.RectFactory;
import revert.shredsheets.models.SessionModel;

public class TextModule {

    /**
     * fontsize = 12
     * Max - Text = N
     * 30 - 20 = 10
     * 30 - 35 = -5
     * fontSize *= 1 + ((10 / 30) = 0.333) = 15.996
     * fontSize *= 1 + ((-5 / 30) = -0.166) = 10.008
     */
    public static float GetTextSize(String text, Paint paint, float maxWidth, float maxHeight) {
        float textSize = paint.getTextSize();
        if (textSize < 10) paint.setTextSize(textSize = 10);
        if (text.length() == 0 || text.equals("")) return 0f;
        float textWidth;
        float textHeight;
        Rect bounds = new Rect();

        float ratio;
        while (true) {
            if (textSize < 1f) {
                return 1f;
            }
            if (textSize > 2000f) {
                return 2000f;
            }
            paint.getTextBounds(text, 0, text.length(), bounds);

            textHeight = bounds.bottom - bounds.top;
            textWidth = bounds.right - bounds.left;

            float maxAspectRatio = maxWidth / maxHeight;
            float textAspectRatio = textWidth / textHeight;

            //if text is 30/20 (1.5) and max is 20/20 (1.0) the text's width will be the deciding factor, otherwise the height
            if (textAspectRatio > maxAspectRatio) {
                ratio = maxWidth / textWidth; //10 / 30 = 3     30 / 10 = .33
                textSize *= ratio;
            } else {
                ratio = maxHeight / textHeight;
                textSize *= ratio;
            }

            if (ratio <= 1.1f && ratio >= 0.9f) return textSize;
            paint.setTextSize(textSize);
        }

    }

    private static int defaultTextColor = Color.parseColor("#111111");
    private static Paint textPaint;

    public static Paint getTextPaint() {
        if (textPaint == null) textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(defaultTextColor);
        return textPaint;
    }

    public static Paint getTextPaint(int color) {
        if (textPaint == null) {
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setLinearText(true);
            textPaint.setTypeface(Typeface.SANS_SERIF);
        }
        textPaint.setColor(color);
        return textPaint;
    }

    public static RectF DrawText(Canvas canvas, String value, RectF textRect, Paint paint, Layout.Alignment hAlignment, Layout.Alignment vAlignment, Boolean strokeText) {
        return DrawScaledText(canvas, value, textRect.left, textRect.top, textRect.width(), textRect.height(), paint, hAlignment, vAlignment, strokeText);
    }

    private static RectF DrawScaledText(Canvas canvas, String value, float left, float top, float width, float height, Paint paint, Layout.Alignment hAlignment, Layout.Alignment vAlignment, Boolean strokeText) {
        float textSize = GetTextSize(value, paint, width, height);
        return DrawText(canvas, value, left, top, width, height, textSize, paint, hAlignment, vAlignment, strokeText);
    }

    public static RectF DrawText(Canvas canvas, String value, RectF textRect, Paint paint, Layout.Alignment alignment, Boolean strokeText) {
        return DrawScaledText(canvas, value, textRect.left, textRect.top, textRect.width(), textRect.height(), paint, alignment, strokeText);
    }

    public static RectF DrawText(Canvas canvas, String value, float left, float top, float width, float height, float textSize, Paint paint, Layout.Alignment alignment, Boolean strokeText) {
        Rect textRect = RectFactory.instance.get();
        paint.setTextSize(textSize);
        paint.getTextBounds(value, 0, value.length(), textRect);

        float x;
        float y;

        switch (alignment) {
            case ALIGN_CENTER:
                float xDelta = width - textRect.width();
                float yDelta = height - textRect.height();
                x = left + xDelta / 2 - textRect.left;
                y = top + textRect.height() + yDelta / 2;
                break;
            case ALIGN_OPPOSITE:
                x = left + width - textRect.width() - textRect.left;
                y = top + height;
                break;
            default:
                x = left - textRect.left;
                y = top + textRect.height();
                break;
        }

        if (strokeText) {
            //paint.setShadowLayer(textSize / 2,x, y, Color.parseColor("#222222"));
            paint.setStrokeWidth(Math.max(textSize / 20, 2));
            paint.setStyle(Paint.Style.STROKE);
            int paintColor = paint.getColor();
            paint.setColor(SessionModel.getInstance().getTheme().getTextStrokeColor());
            canvas.drawText(value, x, y, paint);
            paint.setColor(paintColor);
            paint.setStyle(Paint.Style.FILL);
        }

        canvas.drawText(value, x, y, paint);

        RectF renderedTextRect = RectFFactory.instance.get(textRect);
        renderedTextRect.offset(x, y);

        RectFactory.instance.put(textRect);
        return renderedTextRect;
    }

    public static RectF DrawText(Canvas canvas, String value, float left, float top, float width, float height, float textSize, Paint paint, Layout.Alignment hAlignment, Layout.Alignment vAlignment, Boolean strokeText) {
        Rect textRect = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(value, 0, value.length(), textRect);

        float x;
        float y;

        switch (hAlignment) {
            case ALIGN_CENTER:
                float xDelta = width - textRect.width();
                x = left + xDelta / 2 - textRect.left;
                break;
            case ALIGN_OPPOSITE:
                x = left + width - textRect.width() - textRect.left;
                break;
            default:
                x = left - textRect.left;
                break;
        }

        switch (vAlignment) {
            case ALIGN_CENTER:
                float yDelta = height - textRect.height();
                y = top + textRect.height() + yDelta / 2;
                break;
            case ALIGN_OPPOSITE:
                y = top + height;
                break;
            default:
                y = top + textRect.height();
                break;
        }

        if (strokeText) {
            paint.setStrokeWidth(Math.max(textSize / 20, 2));
            paint.setStyle(Paint.Style.STROKE);
            int paintColor = paint.getColor();
            paint.setColor(SessionModel.getInstance().getTheme().getTextStrokeColor());
            canvas.drawText(value, x, y, paint);
            paint.setColor(paintColor);
            paint.setStyle(Paint.Style.FILL);
        }

        canvas.drawText(value, x, y, paint);

        RectF renderedTextRect = new RectF(textRect);
        renderedTextRect.offset(x, y);
        return renderedTextRect;
    }

    public static RectF DrawScaledText(Canvas canvas, String value, float left, float top, float width, float height, Paint paint, Layout.Alignment alignment, Boolean strokeText) {
        float textSize = GetTextSize(value, paint, width, height);
        return DrawText(canvas, value, left, top, width, height, textSize, paint, alignment, strokeText);
    }

//    private static RectF DrawScaledText(Canvas canvas, String value, RectF scaledRect, Paint paint, Layout.Alignment alignment) {
//        return DrawScaledText(canvas, value, scaledRect.left, scaledRect.top, scaledRect.right - scaledRect.left, scaledRect.bottom - scaledRect.top, paint, alignment);
//    }

//    public static RectF DrawScaledText(Canvas canvas, String value, RectF container, RectF textDimensions, Paint paint, Layout.Alignment alignment) {
//        RectF scaledRect = RectModule.getScaledRect(textDimensions, container, alignment);
//        return DrawScaledText(canvas, value, scaledRect, paint, alignment);
//    }

    public static RectF DrawText(Canvas canvas, String value, RectF container, RectF textDimensions, Paint paint, Layout.Alignment alignment, Boolean strokeText) {
        RectF alignedRect = RectModule.getAlignedRect(textDimensions, container, alignment);
        return DrawText(canvas, value, alignedRect, paint, alignment, strokeText);
    }

    public static RectF DrawText(Canvas canvas, String value, RectF container, RectF textDimensions, int color, Layout.Alignment alignment, Boolean strokeText) {
        return DrawText(canvas, value, container, textDimensions, getTextPaint(color), alignment, strokeText);
    }


//    public static RectF DrawScaledText(Canvas canvas, String value, RectF container, RectF textDimensions, int color, Layout.Alignment alignment) {
//        return DrawScaledText(canvas, value, container, textDimensions, getTextPaint(color), alignment);
//    }
//
//    public static RectF DrawText(Canvas canvas, String value, RectF container, int color, Layout.Alignment alignment) {
//        return DrawText(canvas, value, container, getTextPaint(color), alignment);
//    }

    public static String getOrdinallySuffexedNumber(int value)
    {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (value % 100) {
            case 11:
            case 12:
            case 13:
                return value + "th";
            default:
                return value + sufixes[value % 10];
        }
    }

}
