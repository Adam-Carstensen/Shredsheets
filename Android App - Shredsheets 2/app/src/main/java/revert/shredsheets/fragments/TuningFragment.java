package revert.shredsheets.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import revert.common.DialogModule;
import revert.common.DialogResponseListener;
import revert.shredsheets.R;
import revert.shredsheets.enums.Keys;
import revert.shredsheets.models.NotesModel;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.TuningModel;

public class TuningFragment extends FullScreenFragment {

    LinearLayout tuningView = null;

    Map<Integer, Button> tuningButtonsByString = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Select a Tuning");
        tuningView = (LinearLayout) inflater.inflate(R.layout.fragment_tuning, container, false);

        tuningButtonsByString.put(0, (Button) tuningView.findViewById(R.id.tuning_string_button_1));
        tuningButtonsByString.put(1, (Button) tuningView.findViewById(R.id.tuning_string_button_2));
        tuningButtonsByString.put(2, (Button) tuningView.findViewById(R.id.tuning_string_button_3));
        tuningButtonsByString.put(3, (Button) tuningView.findViewById(R.id.tuning_string_button_4));
        tuningButtonsByString.put(4, (Button) tuningView.findViewById(R.id.tuning_string_button_5));
        tuningButtonsByString.put(5, (Button) tuningView.findViewById(R.id.tuning_string_button_6));
        tuningButtonsByString.put(6, (Button) tuningView.findViewById(R.id.tuning_string_button_7));
        tuningButtonsByString.put(7, (Button) tuningView.findViewById(R.id.tuning_string_button_8));
        tuningButtonsByString.put(8, (Button) tuningView.findViewById(R.id.tuning_string_button_9));
        tuningButtonsByString.put(9, (Button) tuningView.findViewById(R.id.tuning_string_button_10));
        tuningButtonsByString.put(10, (Button) tuningView.findViewById(R.id.tuning_string_button_11));
        tuningButtonsByString.put(11, (Button) tuningView.findViewById(R.id.tuning_string_button_12));

        setTuningDialogHandlers();
        setTuningEventHandlers();

        SessionModel session = SessionModel.getInstance();

        tuningView.findViewById(R.id.tuning_string_layout_1).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_2).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_3).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_4).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_5).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_6).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_7).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_8).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_9).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_10).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_11).setVisibility(View.VISIBLE);
        tuningView.findViewById(R.id.tuning_string_layout_12).setVisibility(View.VISIBLE);

        switch (session.stringCount) {
            case 1:
                tuningView.findViewById(R.id.tuning_string_layout_5).setVisibility(View.GONE);
            case 2:
                tuningView.findViewById(R.id.tuning_string_layout_4).setVisibility(View.GONE);
            case 3:
                tuningView.findViewById(R.id.tuning_string_layout_3).setVisibility(View.GONE);
            case 4:
                tuningView.findViewById(R.id.tuning_string_layout_2).setVisibility(View.GONE);
            case 5:
                tuningView.findViewById(R.id.tuning_string_layout_1).setVisibility(View.GONE);
            case 6:
                tuningView.findViewById(R.id.tuning_string_layout_7).setVisibility(View.GONE);
            case 7:
                tuningView.findViewById(R.id.tuning_string_layout_8).setVisibility(View.GONE);
            case 8:
                tuningView.findViewById(R.id.tuning_string_layout_9).setVisibility(View.GONE);
            case 9:
                tuningView.findViewById(R.id.tuning_string_layout_10).setVisibility(View.GONE);
            case 10:
                tuningView.findViewById(R.id.tuning_string_layout_11).setVisibility(View.GONE);
            case 11:
                tuningView.findViewById(R.id.tuning_string_layout_12).setVisibility(View.GONE);
            default:
                break;
        }

        switch (session.stringCount) {
            case 1:
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_6)).setText("1st string");
                break;
            case 2:
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_5)).setText("1st string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_6)).setText("2nd string");
                break;
            case 3:
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_4)).setText("1st string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_5)).setText("2nd string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_6)).setText("3rd string");
                break;
            case 4:
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_3)).setText("1st string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_4)).setText("2nd string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_5)).setText("3rd string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_6)).setText("4th string");
                break;
            case 5:
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_2)).setText("1st string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_3)).setText("2nd string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_4)).setText("3rd string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_5)).setText("4th string");
                ((TextView) tuningView.findViewById(R.id.tuning_string_label_6)).setText("5th string");
                break;
        }

        if (session.stringCount != 4)
            tuningView.findViewById(R.id.tuning_button_gCEA).setVisibility(View.GONE);

        if (session.stringCount != 5)
            tuningView.findViewById(R.id.tuning_button_BEADG).setVisibility(View.GONE);

        if (session.stringCount != 8)
            tuningView.findViewById(R.id.tuning_button_EBEADGBe).setVisibility(View.GONE);

        return tuningView;
    }

    private void setTuningEventHandlers() {
        tuningView.findViewById(R.id.tuning_button_EBEADGBe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getEightStringAlternateTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_BEADG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getFiveStringBassTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_gCEA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getUkuleleStandardTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_eadgbe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getStandardTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_dropD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getDroppedDTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_newStandard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getNewStandardTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_openC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getOpenCTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_openD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getOpenDTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_openE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getOpenETuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_openF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getOpenFTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_openG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getOpenGTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_openA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getOpenATuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });

        tuningView.findViewById(R.id.tuning_button_openB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setTuning(TuningModel.getOpenBTuning(), true);
                Update();
                dismiss();
                SessionModel.getInstance().getMenuFragment().dismiss();
            }
        });
    }

    public void setTuningDialogHandlers() {
        SessionModel sessionModel = SessionModel.getInstance();
        Keys[] tuning = sessionModel.getTuning();

        for (final Map.Entry<Integer, Button> tuningButton : tuningButtonsByString.entrySet()) {
            tuningButton.getValue().setText(tuning[tuningButton.getKey()].getCleanName());

            tuningButton.getValue().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogModule.BeginGetDialogSelection(getActivity(), getActivity().getLayoutInflater(), "Select a Key",
                            NotesModel.AllStandardKeys, NotesModel.AllStandardNotes, new DialogResponseListener<Dialog, Keys>() {
                                @Override
                                public void onItemClicked(Dialog sender, Keys item) {
                                    Keys[] currentTuning = SessionModel.getInstance().getTuning();
                                    currentTuning[tuningButton.getKey()] = item;
                                    SessionModel.getInstance().setTuning(currentTuning, true);
                                    Update();
                                }
                            });
                }
            });
        }
    }

    private void Update() {
        SessionModel session = SessionModel.getInstance();
        Keys[] tuning = session.getTuning();

        for (final Map.Entry<Integer, Button> tuningButton : tuningButtonsByString.entrySet()) {
            tuningButton.getValue().setText(tuning[tuningButton.getKey()].getCleanName());
        }
    }

    private void SetButtonText(int stringNumber, View button) {
        if (button instanceof Button)
            ((Button) button).setText(SessionModel.getInstance().getTuning()[stringNumber].getCleanName());
    }


}
