package revert.shredsheets.views.fretboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;

import java.util.HashMap;

import revert.common.ColorModule;
import revert.common.RectModule;
import revert.common.TextModule;
import revert.factories.RectFFactory;
import revert.shredsheets.enums.Keys;
import revert.shredsheets.models.FretSpacingModel;
import revert.shredsheets.models.NotesModel;
import revert.shredsheets.models.scales.ScaleNote;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.themes.ShredsheetsTheme;
import revert.shredsheets.views.GenericView;

public class FretboardView extends GenericView {

    HashMap<Integer, ScaleNote> scaleNotesByPosition = new HashMap<>();

    public FretboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetXmlAttributes(context, attrs);
    }

    private void SetXmlAttributes(Context context, AttributeSet attrs) {
        //TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FretboardView, 0, 0);
        //SessionModel session = SessionModel.getInstance();
        //session.setKey(Keys.OrderedKeys[array.getInt(R.styleable.FretboardView_key, 0)]);
    }

    protected void onDraw(Canvas canvas) {
        SessionModel session = SessionModel.getInstance();
        int fretCount = session.getFretCount();
        ShredsheetsTheme theme = session.getTheme();

        ScaleNote[] scaleNotes = NotesModel.GetScaleNotes(session.getKey(), session.getScale());
        scaleNotesByPosition.clear();
        for (ScaleNote note : scaleNotes)
            if (note != null) scaleNotesByPosition.put(note.position, note);

        borderPaint.setColor(theme.getBorderColor());

        RectModule.DrawBorderedRect(canvas, RectFFactory.instance.get(0, 0, getWidth(), getHeight()), borderPaint, 2);

        double[] fretSpacing = FretSpacingModel.GetFretSpacing(fretCount, this.getWidth());

        Boolean[] degreeHighlightingVector = theme.getDegreeHighlightingVector();
        int[] degreeColors = theme.getDegreeColors();

        float fretHeight = (float) this.getHeight() / (float) session.stringCount;
        Keys[] tuning = session.getTuning();

        for (int stringNumber = 0; stringNumber < session.stringCount; stringNumber++) {
            for (int fretNumber = 0; fretNumber < fretSpacing.length; fretNumber++) {

                float fretWidth = (float) fretSpacing[fretNumber];
                float fretY = stringNumber * fretHeight;
                float fretX = session.getFretX(fretNumber, getWidth());

                RectF spaceRect = RectFFactory.instance.get(fretX, fretY, fretX + fretWidth, fretY + fretHeight);

                int tuningIndex = session.stringCount >= 6 ? stringNumber : 6 - session.stringCount + stringNumber;
                int tuningNote = tuning[tuningIndex].getValue();
                //With 6 strings and up, strings are added to the top of the stack (EADGBe becomes BEADGBe)
                //Below 6 strings, they're removed from the bottom (EADGBe becomes EADG)
                int fretNote = (tuningNote + fretNumber) % 12;

                ScaleNote note = scaleNotesByPosition.get(fretNote);

                int highlightingColor;
                if (note != null && degreeHighlightingVector[note.degree]) //should the degree be highlighted
                    highlightingColor = degreeColors[note.degree]; //if so, give it the appropriate color
                else
                    highlightingColor = theme.getBaseFretColor(fretNumber);

                if (note == null) highlightingColor = theme.getEmptyFretColor();

                RectModule.DrawBorderedRect(canvas, spaceRect, highlightingColor, borderPaint.getColor(), 1);

                if (note != null) {
                    RectF textSpaceRect = RectFFactory.instance.get(spaceRect.left, spaceRect.top, spaceRect.right, spaceRect.bottom);
                    textSpaceRect.inset(5, 5);

                    RectF noteRect = RectFFactory.instance.get(0, 0, textSpaceRect.width() * .9f, fretHeight * .5f);

                    if (note.name.length() < 4) {
                        RectF textRect = TextModule.DrawText(canvas, note.name, textSpaceRect, noteRect, ColorModule.getMaximallyContrastingColor(highlightingColor), Layout.Alignment.ALIGN_NORMAL, true);
                        RectFFactory.instance.put(textRect);
                    }

                    RectF scaleDegreeRect = RectFFactory.instance.get(0, 0, textSpaceRect.width() * .8f, fretHeight * .2f);

                    RectF textRect = TextModule.DrawText(canvas, note.degreeName, textSpaceRect, scaleDegreeRect, ColorModule.getModeratelyContrastingColor(highlightingColor), Layout.Alignment.ALIGN_OPPOSITE, false);

                    RectFFactory.instance.put(textRect);
                    RectFFactory.instance.put(textSpaceRect);
                    RectFFactory.instance.put(noteRect);
                    RectFFactory.instance.put(scaleDegreeRect);
                }

                RectFFactory.instance.put(spaceRect);
            }
        }
    }
}