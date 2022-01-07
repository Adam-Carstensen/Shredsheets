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
        //int[] intervals = scale.getIntervals();
        String[] intervalNames = scale.getIntervalNames();
        //String[] degreeNames = scale.getDegreeNames();
        ScaleNote[] scaleNotes = NotesModel.GetScaleNotes(key, scale);
        int[] degreeColors = session.getTheme().getDegreeColors();

        int scaleNoteCount = 0;
        for (ScaleNote note : scaleNotes)
            if (note != null) scaleNoteCount++;

        ScaleNote[] cleanScaleNotes = new ScaleNote[scaleNoteCount];
        int skippedCount = 0;
        for (int i = 0; i < scaleNotes.length; i++) {
            if (scaleNotes[i] == null) skippedCount++;
            else cleanScaleNotes[i - skippedCount] = scaleNotes[i];
        }

        int height = this.getHeight();
        int width = this.getWidth();

        float noteHeight = height * .35f;
        float noteWidth = (float) width / (float) (scaleNoteCount + 1); //C-B, but we'd add another C

        float intervalHeight = height * .2f;
        float intervalWidth = noteWidth;

        float degreeHeight = height * .2f;
        float degreeWidth = noteWidth;

        float chordHeight = height * .25f;
        float chordWidth = noteWidth;

        float intervalX = noteWidth / 2f;
        float intervalY = 5f;
        float noteY = intervalHeight;
        float chordY = intervalHeight + noteHeight;
        float degreeY = intervalHeight + noteHeight + chordHeight;

        DrawIntervals(canvas, intervalNames, intervalHeight, intervalWidth, intervalX, intervalY);
        DrawNotes(canvas, cleanScaleNotes, degreeColors, noteHeight, noteWidth, noteY);
        DrawChords(canvas, cleanScaleNotes, degreeColors, chordHeight, chordWidth, chordY);
        DrawDegreeNames(canvas, cleanScaleNotes, degreeColors, degreeHeight, degreeWidth, degreeY);
    }


    private void DrawIntervals(Canvas canvas, String[] intervalNames, float intervalHeight, float intervalWidth, float intervalX, float intervalY) {
        int skippedIntervals = 0;
        for (int i = 0; i < intervalNames.length; i++) {
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

    private void DrawNotes(Canvas canvas, ScaleNote[] scaleNotes, int[] degreeColors, float noteHeight, float noteWidth, float noteY) {
        float minTextSize = Float.MAX_VALUE;
        for (int i = 0; i <= scaleNotes.length; i++) {
            ScaleNote scaleNote = scaleNotes[i % scaleNotes.length];
            RectF noteRect = RectFFactory.instance.get(i * noteWidth, noteY, (i + 1) * noteWidth, noteY + noteHeight);
            noteRect.inset(10, 10);
            float textSize = TextModule.CalculateTextSize(scaleNote.name, textPaint, noteRect.width(), noteRect.height());
            if (textSize < minTextSize) minTextSize = textSize;
            RectFFactory.instance.put(noteRect);
        }

        for (int i = 0; i <= scaleNotes.length; i++) {
            ScaleNote scaleNote = scaleNotes[i % scaleNotes.length];
            RectF noteRect = RectFFactory.instance.get(i * noteWidth, noteY, (i + 1) * noteWidth, noteY + noteHeight);
            noteRect.inset(10, 10);
            textPaint.setColor(degreeColors[scaleNote.degree - 1]);
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

    private void DrawChords(Canvas canvas, ScaleNote[] scaleNotes, int[] degreeColors, float chordHeight, float chordWidth, float chordY) {
        float minTextSize = Float.MAX_VALUE;
        for (int i = 0; i < scaleNotes.length; i++) {
            ScaleNote scaleNote = scaleNotes[i];
            String chordName = scaleNote.name + scaleNote.chordName;
            RectF chordRect = RectFFactory.instance.get(i * chordWidth, chordY, (i + 1) * chordWidth, chordY + chordHeight);
            chordRect.inset(5, 5);
            float textSize = TextModule.CalculateTextSize(chordName, textPaint, chordRect.width(), chordRect.height());
            if (textSize < minTextSize) minTextSize = textSize;
            RectFFactory.instance.put(chordRect);
        }

        // <= because the 1st is repeated
        for (int i = 0; i <= scaleNotes.length; i++) {
            ScaleNote scaleNote = scaleNotes[i % scaleNotes.length];
            String chordName = scaleNote.name + scaleNote.chordName;
            RectF chordRect = RectFFactory.instance.get(i * chordWidth, chordY, (i + 1) * chordWidth, chordY + chordHeight);
            chordRect.inset(10, 10);
            textPaint.setColor(degreeColors[scaleNote.degree - 1]);
            RectF drawnRect = TextModule.DrawText(canvas, chordName, chordRect.left, chordRect.top, chordRect.width(), chordRect.height(), minTextSize, textPaint,
                    Layout.Alignment.ALIGN_CENTER, Layout.Alignment.ALIGN_CENTER, true);

            if (SessionModel.getInstance().debugLayout)
                canvas.drawRect(chordRect, borderPaint);

            if (SessionModel.getInstance().debugLayout)
                canvas.drawRect(drawnRect, borderPaint);

            RectFFactory.instance.put(chordRect);
            RectFFactory.instance.put(drawnRect);
        }
    }

    private void DrawDegreeNames(Canvas canvas, ScaleNote[] scaleNotes, int[] degreeColors, float degreeHeight, float degreeWidth, float degreeY) {
        int skippedIntervals = 0;
        //int[] modalDegrees = session.getScale().getModalDegrees();

        for (int i = 0; i <= scaleNotes.length; i++) {
//            if (scaleNotes[i % scaleNotes.length] == 0) {
//                skippedIntervals++;
//                continue;
//            }

            ScaleNote scaleNote = scaleNotes[i % scaleNotes.length];

            float degreeX = (i - skippedIntervals) * degreeWidth;
            RectF degreeRect = RectFFactory.instance.get(degreeX, degreeY, degreeX + degreeWidth, degreeY + degreeHeight);
            degreeRect.inset(5, 5);

            int intervalColor = degreeColors[scaleNote.degree - 1];
            textPaint.setColor(intervalColor);

            RectF drawnRect = TextModule.DrawText(canvas, scaleNote.degreeName, degreeRect, textPaint, Layout.Alignment.ALIGN_CENTER, Layout.Alignment.ALIGN_CENTER, false);
            RectFFactory.instance.put(drawnRect);

            RectFFactory.instance.put(degreeRect);
        }
    }
}
