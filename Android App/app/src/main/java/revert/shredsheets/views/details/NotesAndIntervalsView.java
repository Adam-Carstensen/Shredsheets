package revert.shredsheets.views.details;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;

import revert.common.ColorModule;
import revert.common.TextModule;
import revert.factories.RectFFactory;
import revert.shredsheets.enums.Keys;
import revert.shredsheets.models.NotesModel;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.scales.ScaleNote;
import revert.shredsheets.models.scales.Scale;
import revert.shredsheets.views.GenericView;

public class NotesAndIntervalsView extends GenericView {

    public NotesAndIntervalsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Scale scale = session.getScale();
        Keys key = session.getKey();
        int[] intervals = scale.getIntervals();
        String[] intervalNames = scale.getIntervalNames();
        String[] degreeNames = scale.getDegreeNames();
        ScaleNote[] scaleNotes = NotesModel.GetScaleNotes(key, scale);
        int[] degreeColors = session.getTheme().getDegreeColors();

        int scaleNoteCount = 0;
        for (ScaleNote note : scaleNotes)
            if (note != null) scaleNoteCount++;

        ScaleNote[] cleanScaleNotes = new ScaleNote[scaleNoteCount];
        int skippedCount = 0;
        for (int i = 0; i < scaleNotes.length; i++)
        {
            if (scaleNotes[i] == null) skippedCount++;
            else cleanScaleNotes[i - skippedCount] = scaleNotes[i];
        }

        int height = this.getHeight();
        int width = this.getWidth();

        float noteHeight = height * .55f;
        float noteWidth = (float)width / (float)(scaleNoteCount + 1); //C-B, but we'd add another C

        float intervalHeight = height * .25f;
        float intervalWidth = noteWidth;

        float degreeHeight = height * .2f;
        float degreeWidth = noteWidth;

        float intervalX = noteWidth / 2f;
        float intervalY = 5f;
        float noteY = intervalHeight;
        float degreeY = intervalHeight + noteHeight;

        DrawNotes(canvas, degreeColors, cleanScaleNotes, noteHeight, noteWidth, noteY);
        DrawIntervals(canvas, intervalNames, intervalHeight, intervalWidth, intervalX, intervalY);
        DrawDegreeNames(canvas, intervals, degreeNames, degreeColors, degreeHeight, degreeWidth, degreeY);
    }

    private void DrawDegreeNames(Canvas canvas, int[] intervals, String[] degreeNames, int[] degreeColors, float degreeHeight, float degreeWidth, float degreeY) {
        int skippedIntervals = 0;
        for (int i = 0; i <= degreeNames.length; i++)
        {
            if (intervals[i % intervals.length] == 0) {
                skippedIntervals++;
                continue;
            }

            float degreeX = (i - skippedIntervals) * degreeWidth;
            RectF degreeRect = RectFFactory.instance.get(degreeX, degreeY, degreeX + degreeWidth, degreeY + degreeHeight);
            degreeRect.inset(5, 5);

            int intervalColor = degreeColors[session.getScale().getDegree(i % intervals.length)];
            textPaint.setColor(intervalColor);

            if (degreeNames[i % intervals.length] != null && degreeNames[i % intervals.length] != "") {
                RectF drawnRect = TextModule.DrawText(canvas, degreeNames[ i % intervals.length], degreeRect, textPaint, Layout.Alignment.ALIGN_CENTER, Layout.Alignment.ALIGN_CENTER, false);
                RectFFactory.instance.put(drawnRect);
            }
            RectFFactory.instance.put(degreeRect);
        }
    }

    private void DrawIntervals(Canvas canvas, String[] intervalNames, float intervalHeight, float intervalWidth, float intervalX, float intervalY) {
        int skippedIntervals = 0;
        for (int i = 0; i < intervalNames.length; i++)
        {
            if (intervalNames[i] == "" || intervalNames[i].equals("0")) {
                skippedIntervals++;
                continue;
            }

            textPaint.setColor(ColorModule.getIntervalColor(intervalNames[i]));
            float updatedX = intervalX + (i - skippedIntervals) * intervalWidth;
            RectF intervalRect = RectFFactory.instance.get(updatedX, intervalY, updatedX + intervalWidth, intervalY + intervalHeight);
            intervalRect.inset(5, 5);
            RectF drawnRect = TextModule.DrawText(canvas, intervalNames[i], intervalRect, textPaint, Layout.Alignment.ALIGN_CENTER, Layout.Alignment.ALIGN_CENTER, false);
            RectFFactory.instance.put(intervalRect);
            RectFFactory.instance.put(drawnRect);
        }
    }

    private void DrawNotes(Canvas canvas, int[] degreeColors, ScaleNote[] cleanScaleNotes, float noteHeight, float noteWidth, float noteY) {
        float minTextSize = Float.MAX_VALUE;
        for (int i = 0; i <= cleanScaleNotes.length; i++) {
            ScaleNote scaleNote = cleanScaleNotes[i % cleanScaleNotes.length];
            RectF noteRect = RectFFactory.instance.get(i * noteWidth, noteY, (i + 1) * noteWidth, noteY + noteHeight);
            noteRect.inset(10, 10);
            float textSize = TextModule.CalculateTextSize(scaleNote.name, textPaint, noteRect.width(), noteRect.height());
            if (textSize < minTextSize) minTextSize = textSize;
            RectFFactory.instance.put(noteRect);
        }

        for (int i = 0; i <= cleanScaleNotes.length; i++) {
            ScaleNote scaleNote = cleanScaleNotes[i % cleanScaleNotes.length];
            RectF noteRect = RectFFactory.instance.get(i * noteWidth, noteY, (i + 1) * noteWidth, noteY + noteHeight);
            noteRect.inset(10, 10);
            textPaint.setColor(degreeColors[scaleNote.degree]);
            RectF drawnRect = TextModule.DrawText(canvas, scaleNote.name, noteRect.left, noteRect.top, noteRect.width(), noteRect.height(), minTextSize, textPaint,
                    Layout.Alignment.ALIGN_CENTER, Layout.Alignment.ALIGN_CENTER, true);

            if (SessionModel.getInstance().debugLayout)
                canvas.drawRect(noteRect, borderPaint);

            if (SessionModel.getInstance().debugLayout)
                canvas.drawRect(drawnRect, borderPaint);

            RectFFactory.instance.put(noteRect);
            RectFFactory.instance.put(drawnRect);
        }
    }
}
