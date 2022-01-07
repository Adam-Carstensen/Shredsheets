package revert.shredsheets.fragments;

import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.view.ViewGroup;

public class FullScreenFragment extends DialogFragment {
    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
