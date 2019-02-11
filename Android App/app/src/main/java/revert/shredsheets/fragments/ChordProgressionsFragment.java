package revert.shredsheets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import revert.shredsheets.R;

public class ChordProgressionsFragment extends FullScreenFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chord_progressions, container, false);
    }

}
