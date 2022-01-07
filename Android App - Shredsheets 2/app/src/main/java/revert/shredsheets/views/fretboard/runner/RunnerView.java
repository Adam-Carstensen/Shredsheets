package revert.shredsheets.views.fretboard.runner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;

import revert.common.ColorModule;
import revert.common.RectModule;
import revert.common.TextModule;
import revert.factories.RectFFactory;
import revert.shredsheets.models.FretSpacingModel;
import revert.shredsheets.models.themes.ShredsheetsTheme;
import revert.shredsheets.views.GenericView;

public class RunnerView extends GenericView {
    public RunnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RunnerView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ShredsheetsTheme theme = session.getTheme();

        borderPaint = theme.getRunnerBorderPaint();

        float height = getHeight();
        int width = getWidth();

        double[] fretSpacing = FretSpacingModel.GetFretSpacing(session.getFretCount(), width);

        for (int scaleStepNumber = 0; scaleStepNumber < fretSpacing.length; scaleStepNumber++) {
            float fretWidth = (float) fretSpacing[scaleStepNumber];
            //canvas.drawLine(position, 0, position, height, borderPaint);

            float x = session.getFretX(scaleStepNumber, getWidth());

            RectF spaceRect = RectFFactory.instance.get(x, 0, x + fretWidth, height);
            RectF textRect = RectFFactory.instance.get(x + fretWidth * .15f, height * .15f, x + fretWidth * .85f, height * .85f);

            int fretColor = theme.getFretboardRunnerPaintColor(scaleStepNumber);
            RectModule.DrawBorderedRect(canvas, spaceRect, fretColor, Color.parseColor("#222222"), 1);

            textPaint.setColor(ColorModule.getMaximallyContrastingColor(fretColor));

            String fretText;
            if (scaleStepNumber == 0) {
                fretText = "Open";
            } else {
                fretText = String.valueOf(scaleStepNumber);
            }

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
