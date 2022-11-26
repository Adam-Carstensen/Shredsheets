package revert.shredsheets.views.fretboard;

import androidx.fragment.app.FragmentManager;
import android.view.GestureDetector;
import android.view.MotionEvent;

import revert.shredsheets.fragments.MenuFragment;
import revert.shredsheets.models.SessionModel;

public class FretboardGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        ShowSettings();
        return true;
    }

    public static void ShowSettings() {
        MenuFragment menuFragment = new MenuFragment();
        SessionModel.getInstance().setMenuFragment(menuFragment);
        FragmentManager fragmentManager = SessionModel.getInstance().currentActivity.getSupportFragmentManager();
        menuFragment.show(fragmentManager, "Shredsheets Settings");
    }

    public static boolean isTwoFinger = false;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        SessionModel session = SessionModel.getInstance();

        switch (e2.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN: //two fingers
                FretboardGestureListener.isTwoFinger = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                FretboardGestureListener.isTwoFinger = false;
                break;
        }

        int negativeLogToggle = 1;
        if (distanceX > 0) negativeLogToggle = -1;

        float keySwipeProgress = (float) Math.log(Math.abs(distanceX)) * negativeLogToggle;
        if (keySwipeProgress == Float.NEGATIVE_INFINITY || keySwipeProgress == Float.POSITIVE_INFINITY || Float.isNaN(keySwipeProgress))
            return true;

        if (session.isLeftyMode()) keySwipeProgress *= -1;

        if (isTwoFinger) {
            session.setModeSwipeProgress(keySwipeProgress);
        } else {
            session.setKeySwipeProgress(keySwipeProgress);
        }

        return true;
    }

}
