package revert.shredsheets.views.keySlider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;

import revert.common.TextModule;
import revert.factories.RectFFactory;
import revert.shredsheets.models.FretSpacingModel;
import revert.shredsheets.models.NotesModel;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.views.GenericView;

public class KeySliderView extends GenericView {

    public KeySliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeySliderView(Context context) {
        super(context);
    }

    private Paint selectedKeyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw C #b D #b E #b F #b G #b A #b B #b C || #b D #b E #b F #b G #b A #b B#b C

        //Allocate increased size for selected key

        SessionModel session = SessionModel.getInstance();
        selectedKeyPaint.setColor(session.getTheme().getDegreeColors()[0]);
        textPaint.setColor(Color.parseColor("#444454"));
        borderPaint.setColor(Color.parseColor("#404050"));

        double[] fretSpacing = FretSpacingModel.GetFretSpacing(session.getFretCount(), this.getWidth());

        float height = getHeight();

        for (int scaleStepNumber = 0; scaleStepNumber < fretSpacing.length; scaleStepNumber++) {
            float fretWidth = (float) fretSpacing[scaleStepNumber];

            float x = session.getFretX(scaleStepNumber, getWidth());

            canvas.drawLine(x, 0, x, height, borderPaint);
            String keyValue = NotesModel.KeySliderStrings[scaleStepNumber % 12];

            RectF fretBounds = RectFFactory.instance.get(x, 0, x + fretWidth, getHeight());
            RectF textBounds = RectFFactory.instance.get(fretBounds);
            textBounds.inset(textBounds.width() * .1f, textBounds.width() * .1f);

            Paint fretPaint = textPaint;
            if (scaleStepNumber % 12 == session.getKey().getValue()) {
                fretPaint = selectedKeyPaint;
                keyValue = session.getKey().getCleanName();
            }

            RectF textRect = TextModule.DrawText(canvas, keyValue, fretBounds, textBounds, fretPaint, Layout.Alignment.ALIGN_CENTER, false);

            RectFFactory.instance.put(textRect);
            RectFFactory.instance.put(fretBounds);
            RectFFactory.instance.put(textBounds);
        }

        this.drawnWidth = getWidth();
        this.drawnHeight = (int) height;

        borderPaint.setColor(SessionModel.getInstance().getTheme().getBorderColor());
    }
}
