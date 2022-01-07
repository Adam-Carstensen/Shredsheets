package revert.shredsheets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.core.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import revert.shredsheets.fragments.HelpFragment;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.views.fretboard.FretboardGestureListener;

public class FretboardActivity extends FragmentActivity {

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionModel sessionModel = SessionModel.getInstance();
        sessionModel.setMainActivity(this);
        setContentView(R.layout.activity_fretboard);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mDetector = new GestureDetectorCompat(this, new FretboardGestureListener());

        if (sessionModel.getOpenSettings()){
            FretboardGestureListener.ShowSettings();
            sessionModel.setOpenSettings(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void onYoutubeButtonClicked(View view) {
        String url = "https://www.youtube.com/channel/UCA4aAjbeGgXdq_Ac04j0OWw";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void onGithubButtonClicked(View view) {
        String url = "https://github.com/Adam-Carstensen/Shredsheets";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void onHelpButtonClicked(View view) {
        HelpFragment helpFragment = new HelpFragment();
        helpFragment.show(getSupportFragmentManager(), "HelpFragment");
    }
}