package revert.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;

public class RectModule {

    private static Paint fillPaint = null;
    private static Paint borderPaint = null;

    private static Paint getFillPaint() {
        return (fillPaint == null ? fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG) : fillPaint);
    }

    private static Paint getFillPaint(int color) {
        if (fillPaint == null) fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(color);
        return fillPaint;
    }

    private static Paint getBorderPaint() {
        if (borderPaint == null) {
            borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            borderPaint.setStyle(Paint.Style.STROKE);
        }
        return borderPaint;
    }

    private static Paint getBorderPaint(int color) {
        borderPaint = getBorderPaint();
        borderPaint.setColor(color);
        return borderPaint;
    }

    public static void DrawBorderedRect(Canvas canvas, RectF rect, int fillColor, int borderColor, float borderWidth) {
        Paint borderPaint = getBorderPaint(borderColor);
        DrawBorderedRect(canvas, rect, getFillPaint(fillColor), borderPaint, borderWidth);
    }

    public static void DrawBorderedRect(Canvas canvas, RectF rect, Paint fillPaint, Paint borderPaint, float borderWidth) {
        float previousStrokeWidth = borderPaint.getStrokeWidth();
        borderPaint.setStrokeWidth(borderWidth);

        Paint.Style borderStyle = borderPaint.getStyle();
        borderPaint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, fillPaint);
        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, borderPaint);


        borderPaint.setStyle(borderStyle);
        borderPaint.setStrokeWidth(previousStrokeWidth);
    }

    public static void DrawBorderedRect(Canvas canvas, RectF rect, Paint borderPaint, float borderWidth) {
        float previousStrokeWidth = borderPaint.getStrokeWidth();
        Paint.Style borderStyle = borderPaint.getStyle();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, borderPaint);

        borderPaint.setStyle(borderStyle);
        borderPaint.setStrokeWidth(previousStrokeWidth);
    }

    public static Rect getScaledRect(Rect rect, Rect bounds, Layout.Alignment alignment) {
        float boundsRatio = bounds.width() / bounds.height(); // 4/3 = 1.33
        float rectRatio = rect.width() / rect.height(); // 2/4 = 0.5

        float coefficient = 1f;
        float delta;
        float offset = 0;

        if (boundsRatio > rectRatio) //4/3 (1.33) vs 2/4 (0.5) - height will be constrained
        {
            coefficient = bounds.height() / rect.height();   //3/4 = .75
            float newRectWidth = rect.width() * coefficient; // 2 * .75 = 1.5;
            delta = bounds.width() - newRectWidth; //4 - 1.5 = 2.5

            switch (alignment) {
                case ALIGN_CENTER:
                    offset = delta / 2; // 1.25
                    break;
                case ALIGN_NORMAL:
                    offset = 0;
                    break;
                case ALIGN_OPPOSITE:
                    offset = delta; //2.5
                    break;
            }

            return new Rect((int) offset, 0, (int) newRectWidth, bounds.height());
        }
        else //bounds = 4/3 (1.33) vs rect = 5/3 (1.66)  width will be constrained
        {
            coefficient = bounds.width() / rect.width();  //4/5 = 0.8
            float newRectHeight = rect.height() * coefficient; //3 * 0.8 = 2.4
            delta = bounds.height() - newRectHeight; //3 - 2.4 = 0.6;

            switch (alignment) {
                case ALIGN_CENTER:
                    offset = delta / 2; // 0.3
                    break;
                case ALIGN_NORMAL:

                    offset = 0;
                    break;
                case ALIGN_OPPOSITE:
                    offset = delta; //0.6
                    break;
            }

            return new Rect(0, (int) offset, bounds.width(), (int) newRectHeight);
        }
    }

    public static RectF getAlignedRect(RectF rect, RectF bounds, Layout.Alignment alignment) {
        if (rect.width() > bounds.width() || rect.height() > bounds.height())
            return getScaledRect(rect, bounds, alignment);
        if (rect.width() == bounds.width() && rect.height() == bounds.height()) return rect;

        float boundsRatio = bounds.width() / bounds.height(); // 4/3 = 1.33
        float rectRatio = rect.width() / rect.height(); // 2/4 = 0.5

        float widthOffset = (bounds.width() - rect.width()) / 2;
        float heightOffset = (bounds.height() - rect.height()) / 2;

        if (boundsRatio > rectRatio) //4/3 (1.33) vs 2/4 (0.5) - height will be constrained
        {
            //4 - 1.5 = 2.5
            switch (alignment) {
                case ALIGN_CENTER:
                    return new RectF(bounds.left + widthOffset, bounds.top + heightOffset, bounds.right - widthOffset, bounds.bottom - heightOffset);
                case ALIGN_NORMAL:
                    return new RectF(bounds.left, bounds.top, bounds.left + rect.width(), bounds.top + rect.height());
                case ALIGN_OPPOSITE:
                    return new RectF(bounds.right - rect.width(), bounds.bottom - rect.height(), bounds.right, bounds.bottom);
            }
            return rect;
        } else //bounds = 4/3 (1.33) vs rect = 5/3 (1.66)  width will be constrained
        {
            switch (alignment) {
                case ALIGN_CENTER:
                    return new RectF(bounds.left + widthOffset, bounds.top + heightOffset, bounds.right - widthOffset, bounds.bottom - heightOffset);
                case ALIGN_NORMAL:
                    return new RectF(bounds.left, bounds.top, bounds.left + rect.width(), bounds.top + rect.height());
                case ALIGN_OPPOSITE:
                    return new RectF(bounds.right - rect.width(), bounds.bottom - rect.height(), bounds.right, bounds.bottom);
            }
            return rect;
        }
    }


    public static RectF getScaledRect(RectF rect, RectF bounds, Layout.Alignment alignment) {
        float boundsRatio = bounds.width() / bounds.height(); // 4/3 = 1.33
        float rectRatio = rect.width() / rect.height(); // 2/4 = 0.5

        float coefficient = 1f;
        float delta;
        float offset = 0f;

        if (boundsRatio > rectRatio) //4/3 (1.33) vs 2/4 (0.5) - height will be constrained
        {
            coefficient = bounds.height() / rect.height();   //3/4 = .75
            float newRectWidth = rect.width() * coefficient; // 2 * .75 = 1.5;
            delta = bounds.width() - newRectWidth; //4 - 1.5 = 2.5

            switch (alignment) {
                case ALIGN_CENTER:
                    offset = delta / 2f; // 1.25
                    break;
                case ALIGN_NORMAL:
                    offset = 0;
                    break;
                case ALIGN_OPPOSITE:
                    offset = delta; //2.5
                    break;
            }
            return new RectF(bounds.left + offset, bounds.top, bounds.left + newRectWidth, bounds.bottom);
        } else //bounds = 4/3 (1.33) vs rect = 5/3 (1.66)  width will be constrained
        {
            coefficient = bounds.width() / rect.width();  //4/5 = 0.8
            float newRectHeight = rect.height() * coefficient; //3 * 0.8 = 2.4
            delta = bounds.height() - newRectHeight; //3 - 2.4 = 0.6;

            switch (alignment) {
                case ALIGN_CENTER:
                    offset = delta / 2f; // 0.3
                    break;
                case ALIGN_NORMAL:
                    offset = 0;
                    break;
                case ALIGN_OPPOSITE:
                    offset = delta; //0.6
                    break;
            }
            return new RectF(bounds.left, bounds.top + offset, bounds.right, bounds.top + newRectHeight + offset);
        }
    }
}
