package revert.shredsheets.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import revert.common.ColorModule;
import revert.shredsheets.R;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.themes.ShredsheetsTheme;

public class ThemesFragment extends FullScreenFragment {

    private LinearLayout highlightingContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View highlightingLayout = inflater.inflate(R.layout.fragment_themes, container, false);

        final SessionModel session = SessionModel.getInstance();
        final ShredsheetsTheme theme = session.getTheme();
        final int[] degreeColors = theme.getDegreeColors();
        boolean[] highlightingVector = theme.getDegreeHighlightingVector();

        highlightingContainer = highlightingLayout.findViewById(R.id.highlightingColorsContainer);

        String[] degreeNames = session.getScale().getDegreeNames();

        for (int i = 0; i < degreeNames.length; i++) {
            Button button = new Button(session.currentActivity);
            button.setTag(i);
            button.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

            ColorDegreeButton(button, highlightingVector[i], degreeColors, i);
            button.setText(degreeNames[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean[] newVector = SessionModel.getInstance().getTheme().getDegreeHighlightingVector();
                    newVector[((int) v.getTag())] = !newVector[((int) v.getTag())];
                    SessionModel.getInstance().getTheme().setDegreeHighlightingVector(newVector);
                    Update(false);
                }
            });

            highlightingContainer.addView(button);
        }

        highlightingLayout.findViewById(R.id.highlighting_button_135).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, false, true, false, true, false, false, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_1357).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, false, true, false, true, false, true, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_125).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, true, false, false, true, false, false, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_1257).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, true, false, false, true, false, true, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_145).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, false, false, true, true, false, false, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_1457).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, false, false, true, true, false, true, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, false, false, false, true, false, false, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_136).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, false, true, false, false, true, false, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_13579).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, true, true, false, true, false, true, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_1357913).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, true, true, false, true, true, true, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_1234567).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, true, true, true, true, true, true, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_none).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { false, false, false, false, false, false, false, false, false, false, false, false });
                Update();
            }
        });

        highlightingLayout.findViewById(R.id.highlighting_button_rootnote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().getTheme().setDegreeHighlightingVector(new boolean[] { true, false, false, false, false, false, false, false, false, false, false, false });
                Update();
            }
        });


        getDialog().setTitle("Colors & Highlighting");
        return highlightingLayout;
    }

    private void Update() {
        Update(true);
    }

    private void Update(boolean dismiss)
    {
        SessionModel session = SessionModel.getInstance();
        int[] degreeColors = session.getTheme().getDegreeColors();
        boolean[] degreeHighlightingVector = session.getTheme().getDegreeHighlightingVector();

        for (int i = 0; i < degreeHighlightingVector.length; i++)
        {
            View degreeItem = highlightingContainer.findViewWithTag(i);
            if (degreeItem == null) break;
            ColorDegreeButton((Button)degreeItem, degreeHighlightingVector[i], degreeColors, i);
        }
        session.invalidateViews();
        if (dismiss) {
            session.getMenuFragment().dismiss();
            dismiss();
        }
    }


    private void ColorDegreeButton(Button button, boolean shouldHighlight, int[] degreeColors, int degree) {
        int backgroundColor = Color.LTGRAY;
        if (shouldHighlight) backgroundColor = degreeColors[degree];
        button.setTextColor(ColorModule.getMaximallyContrastingColor(backgroundColor));
        button.setBackgroundColor(backgroundColor);
    }
}
