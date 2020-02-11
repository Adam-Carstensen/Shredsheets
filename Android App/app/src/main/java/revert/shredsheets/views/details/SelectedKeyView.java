package revert.shredsheets.views.details;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.AttributeSet;

import revert.common.TextModule;
import revert.factories.RectFFactory;
import revert.shredsheets.models.themes.ShredsheetsTheme;
import revert.shredsheets.views.GenericView;

public class SelectedKeyView extends GenericView {

    public SelectedKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        ShredsheetsTheme theme = session.getTheme();

        //TODO: Refactor & document

        textPaint = TextModule.getTextPaint(theme.getBorderColor(), Typeface.SANS_SERIF, true);

        textPaint.setStyle(Paint.Style.STROKE);

        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeMiter(10);
        textPaint.setStrokeWidth(5);

        RectF noteRect = RectFFactory.instance.get(0, 0, (float) getWidth() * .35f, getHeight());
        noteRect.inset(20, 30);

        int[] degreeColors = theme.getDegreeColors();
        textPaint.setColor(theme.getDegreeColors()[0]);
        RectF drawnNoteRect = TextModule.DrawText(canvas, session.getKey().getCleanName(), noteRect, textPaint, Layout.Alignment.ALIGN_NORMAL, Layout.Alignment.ALIGN_CENTER, true);

        float scaleX = drawnNoteRect.right + drawnNoteRect.width() * .1f;

        textPaint.setTypeface(Typeface.create("sans-serif-smallcaps", Typeface.NORMAL));
        textPaint.setColor(degreeColors[session.getScale().getMode()]);
        int modeInset = 10;
        RectF modeRect = RectFFactory.instance.get(scaleX,  drawnNoteRect.top, getWidth() - modeInset, drawnNoteRect.top + drawnNoteRect.height() * .3f);
        RectF modeTextRect = TextModule.DrawText(canvas, session.getScale().getCurrentModeName(), modeRect, textPaint, Layout.Alignment.ALIGN_NORMAL, Layout.Alignment.ALIGN_NORMAL, true);

        RectF modeLabelRectDimensions = RectFFactory.instance.get(scaleX, modeTextRect.bottom + modeTextRect.height() * .1f, modeRect.right, modeTextRect.bottom + modeTextRect.height() * .5f);
        RectF modeLabelRect = TextModule.DrawText(canvas, "Mode", modeLabelRectDimensions, textPaint, Layout.Alignment.ALIGN_NORMAL, Layout.Alignment.ALIGN_NORMAL, false);

        float scaleY = modeLabelRect.bottom + modeLabelRect.height() * .4f;
        RectF scaleRect = RectFFactory.instance.get(scaleX, scaleY, getWidth() - 10, scaleY + modeTextRect.height() * .8f);

        textPaint.setColor(degreeColors[4]);
        RectF scaleTextRect = TextModule.DrawText(canvas, session.getScale().getName(), scaleRect, textPaint, Layout.Alignment.ALIGN_NORMAL, Layout.Alignment.ALIGN_NORMAL, true);

        RectF scaleLabelRect = RectFFactory.instance.get(scaleX, scaleTextRect.bottom + scaleTextRect.height() * .1f, scaleRect.right, scaleTextRect.bottom + scaleTextRect.height() * .5f);
        RectF drawnScaleLabelRect = TextModule.DrawText(canvas, "Scale", scaleLabelRect, textPaint, Layout.Alignment.ALIGN_NORMAL, Layout.Alignment.ALIGN_NORMAL, false);

        drawnWidth = (int) (modeTextRect.right > scaleTextRect.right ? modeTextRect.right : scaleTextRect.right);
        drawnHeight = (int)drawnNoteRect.bottom;

        RectFFactory.instance.put(drawnScaleLabelRect);
        RectFFactory.instance.put(noteRect);
        RectFFactory.instance.put(drawnNoteRect);
        RectFFactory.instance.put(modeRect);
        RectFFactory.instance.put(modeTextRect);
        RectFFactory.instance.put(modeLabelRectDimensions);
        RectFFactory.instance.put(modeLabelRect);
        RectFFactory.instance.put(scaleRect);
        RectFFactory.instance.put(scaleTextRect);
        RectFFactory.instance.put(scaleLabelRect);
    }
}