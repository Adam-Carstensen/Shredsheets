package revert.shredsheets.views.fretboard;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class FretboardTouchListener implements View.OnTouchListener {

    public GestureDetector detector;

    public FretboardGestureListener listener;

    public FretboardTouchListener(Context context)
    {
        this.detector = new GestureDetector(context, listener = new FretboardGestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
    }
}
