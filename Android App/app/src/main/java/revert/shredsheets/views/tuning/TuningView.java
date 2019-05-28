package revert.shredsheets.views.tuning;

import android.content.Context;
import android.view.View;

import revert.shredsheets.R;
import revert.shredsheets.models.SessionModel;

public class TuningView {

    public static View getView(Context context) {
        SessionModel session = SessionModel.getInstance();
        return session.currentActivity.getLayoutInflater().inflate(R.layout.fragment_tuning, null);
    }
}
