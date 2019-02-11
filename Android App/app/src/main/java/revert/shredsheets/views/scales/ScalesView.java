package revert.shredsheets.views.scales;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import revert.shredsheets.models.SessionModel;
import revert.shredsheets.views.GenericView;

public class ScalesView extends GenericView {

    public ScalesView(Context context) {
        super(context);
    }

    public ScalesView(Context context, LinearLayout.LayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ScalesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = SessionModel.getInstance().getTheme().getScaleBorderPaint();
        float width = (float)getWidth();
        float height = (float)getHeight();

        float gearCenterRadiusCoefficient = .1f;
        float gearOuterRadiusCoefficient = .9f;
        float gearToothHeightCoefficient = .2f;

        float centerX = width / 2f;
        float centerY = height / 2f;

        float maxSize = Math.min(width, height);

        float gearCenterSize = maxSize*gearCenterRadiusCoefficient;

        RectF gearCenterRect = new RectF(centerX - gearCenterSize, centerY - gearCenterSize, centerX + gearCenterSize, centerY + gearCenterSize);

        canvas.drawArc(gearCenterRect, 0, 360, false, paint);
    }
}
