package revert.shredsheets.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.themes.ShredsheetsTheme;

public abstract class GenericView extends View {
    public Context context = null;
    public int y;
    public int x;
    public int drawnWidth;
    public int drawnHeight;
    //0 = unknown, 1 = portrait, 2 = landscape
    public int orientation;
    private float swipePosition;

    public boolean isPortrait() {
        return orientation == 1;
    }

    protected Paint backgroundPaint;
    protected Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint textPaint;

    protected final SessionModel session = SessionModel.getInstance();
    private ShredsheetsTheme theme = SessionModel.getInstance().getTheme();

    public GenericView(Context context) {
        super(context, new EmptyAttributes());
    }

    public GenericView(Context context, LinearLayout.LayoutParams layoutParams) {
        this(context, new EmptyAttributes());
        this.setLayoutParams(layoutParams);
    }

    public GenericView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
    }


    public void ChangeOrientation(Resources resources) {
        ChangeOrientation(resources.getConfiguration().orientation);
    }

    public void ChangeOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.x = left;
        this.y = top;
        this.orientation = getResources().getConfiguration().orientation;
    }

    public float getSwipePosition() {
        return swipePosition;
    }

    public void setSwipePosition(float swipePosition) {
        this.swipePosition = swipePosition;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = drawnWidth != 0 ? drawnWidth : 50;
        int desiredHeight = drawnHeight != 0 ? drawnHeight : 50;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }
}
