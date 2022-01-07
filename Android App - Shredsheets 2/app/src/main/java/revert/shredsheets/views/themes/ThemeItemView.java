package revert.shredsheets.views.themes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;

import revert.common.ColorModule;
import revert.common.RectModule;
import revert.common.TextModule;
import revert.common.extensions.StringExtensions;
import revert.factories.RectFFactory;
import revert.shredsheets.models.FretSpacingModel;
import revert.shredsheets.models.themes.ShredsheetsTheme;
import revert.shredsheets.views.GenericView;

public class ThemeItemView extends GenericView {

    private ShredsheetsTheme theme;

    public ThemeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemeItemView(Context context) {
        super(context);
    }

    public ThemeItemView(Context context, ShredsheetsTheme theme) {
        super(context);
        this.theme = theme;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        borderPaint = theme.getRunnerBorderPaint();

        float height = getHeight();
        int width = getWidth();

        double[] degreeSpacing = FretSpacingModel.GetFretSpacing(12, width);

        for (int degreeSpacingItem = 0; degreeSpacingItem < degreeSpacing.length; degreeSpacingItem++) {
            float fretWidth = (float) degreeSpacing[degreeSpacingItem];
            //canvas.drawLine(position, 0, position, height, borderPaint);

            float x = session.getFretX(degreeSpacingItem, getWidth(), 12);

            RectF spaceRect = RectFFactory.instance.get(x, 0, x + fretWidth, height);
            RectF textRect = RectFFactory.instance.get(x + fretWidth * .15f, height * .15f, x + fretWidth * .85f, height * .85f);

            int fretColor = theme.getFretboardRunnerPaintColor(degreeSpacingItem);
            RectModule.DrawBorderedRect(canvas, spaceRect, fretColor, Color.parseColor("#222222"), 1);

            textPaint.setColor(ColorModule.getMaximallyContrastingColor(fretColor));

            String fretText = StringExtensions.getOrdinal(degreeSpacingItem + 1);

            RectF drawnTextRect = TextModule.DrawText(canvas, fretText, spaceRect, textRect, textPaint, Layout.Alignment.ALIGN_CENTER, false);

            RectFFactory.instance.put(spaceRect);
            RectFFactory.instance.put(textRect);
            RectFFactory.instance.put(drawnTextRect);
        }

        RectF borderRect = RectFFactory.instance.get(0, 0, width, height);
        RectModule.DrawBorderedRect(canvas, borderRect, theme.getRunnerBorderPaint(), 2f);
        RectFFactory.instance.put(borderRect);
        drawnWidth = width;
        drawnHeight = (int) height;
    }
}
