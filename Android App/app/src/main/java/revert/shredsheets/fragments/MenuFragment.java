package revert.shredsheets.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import revert.common.DialogModule;
import revert.common.DialogResponseListener;
import revert.shredsheets.R;
import revert.shredsheets.enums.Instruments;
import revert.shredsheets.enums.Keys;
import revert.shredsheets.models.NotesModel;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.StringModel;
import revert.shredsheets.models.TuningModel;
import revert.shredsheets.models.scales.Scale;
import revert.shredsheets.models.scales.Scales;

public class MenuFragment extends FullScreenFragment implements View.OnClickListener {

    private View menuView;

    public MenuFragment() {
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
    }

    public Context getContext() {
        return SessionModel.getInstance().currentActivity;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Window dialogWindow = getDialog().getWindow();
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogWindow.getAttributes());
        lp.width = SessionModel.getInstance().getWidth();
        lp.height = SessionModel.getInstance().getHeight();
        dialogWindow.setAttributes(lp);

        final SessionModel session = SessionModel.getInstance();

        menuView = inflater.inflate(R.layout.main_menu, container, false);

        ((Button)menuView.findViewById(R.id.menuStringCountButton)).setText(session.stringCount + " strings");
        ((Button)menuView.findViewById(R.id.menuTuningButton)).setText(TuningModel.getTuningName(session.getTuning()));

        setFonts();

        menuView.findViewById(R.id.menuDefaultsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel session = SessionModel.getInstance();
                session.setDefaults();
                dismiss();
            }
        });

        menuView.findViewById(R.id.menuKeyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogModule.BeginGetDialogSelection(getActivity(), getActivity().getLayoutInflater(), "Select a Key", NotesModel.AllStandardKeys, NotesModel.AllStandardNotes, new DialogResponseListener<Dialog, Keys>() {
                    @Override
                    public void onItemClicked(Dialog sender, Keys item) {
                        if (item != null) SessionModel.getInstance().setKey(item);
                        sender.dismiss();
                        dismiss();
                    }
                });
            }
        });

        PopulateSpinners(session);

        menuView.findViewById(R.id.menuStringCountButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogModule.BeginGetDialogSelection(getContext(), getLayoutInflater(), "Select a String Count", StringModel.AllStringCounts, new DialogResponseListener<Dialog, Integer>() {
                    @Override
                    public void onItemClicked(Dialog sender, Integer item) {
                        SessionModel.getInstance().setStringCount(item, true);
                        sender.dismiss();
                        dismiss();
                    }
                });
            }
        });

        menuView.findViewById(R.id.menuTuningButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuningFragment tuningFragment = new TuningFragment();
                android.support.v4.app.FragmentManager fragmentManager = SessionModel.getInstance().currentActivity.getSupportFragmentManager();
                tuningFragment.show(fragmentManager, "TuningFragment");
            }
        });

        menuView.findViewById(R.id.menuHighlightingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighlightingFragment highlightingFragment = new HighlightingFragment();
                android.support.v4.app.FragmentManager fragmentManager = SessionModel.getInstance().currentActivity.getSupportFragmentManager();
                highlightingFragment.show(fragmentManager, "HighlightingFragment");
            }
        });

//        menuView.findViewById(R.id.menuThemesButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ThemesFragment themesFragment = new ThemesFragment();
//                android.support.v4.app.FragmentManager fragmentManager = SessionModel.getInstance().currentActivity.getSupportFragmentManager();
//                themesFragment.show(fragmentManager, "ThemesFragment");
//            }
//        });

        menuView.findViewById(R.id.menuHelpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFragment helpFragment = new HelpFragment();
                android.support.v4.app.FragmentManager fragmentManager = SessionModel.getInstance().currentActivity.getSupportFragmentManager();
                helpFragment.show(fragmentManager, "HelpFragment");
            }
        });

        ToggleButton toggle = menuView.findViewById(R.id.realisticFretSpacingToggle);
        toggle.setChecked(SessionModel.getInstance().getUseRealisticFrets());

        menuView.findViewById(R.id.realisticFretSpacingToggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean useRealisticFrets = ((ToggleButton) v).isChecked();
                SessionModel.getInstance().setUseRealisticFrets(useRealisticFrets, true);
            }
        });

        ToggleButton leftyToggle = menuView.findViewById(R.id.leftyToggle);
        leftyToggle.setChecked(!session.isLeftyMode());

        menuView.findViewById(R.id.leftyToggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean lefty = !((ToggleButton) v).isChecked();
                SessionModel.getInstance().setLeftyMode(lefty);
                dismiss();
            }
        });

//        Spinner spinner = (Spinner)menuView.findViewById(R.id.scaleSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.scalesArray, R.layout.support_simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        getDialog().setTitle("Shredsheets Settings");
        ConfigureInstrumentButtons();
        return menuView;
    }

    private void PopulateSpinners(SessionModel session) {
        Scale currentScale = session.getScale();
        int currentScalePosition = 0;
        final String[] scaleTitles = new String[Scales.AllScales.length];
        for (int i = 0; i < Scales.AllScales.length; i++) {
            Scale scale = Scales.AllScales[i];
            if (scale.toString() == currentScale.toString()) {
                currentScalePosition = i;
                scaleTitles[i] = "Scale: " + scale.toString();
            } else scaleTitles[i] = scale.toString();
        }

        final String[] scaleDescriptions = new String[Scales.AllScales.length];
        for (int i = 0; i < Scales.AllScales.length; i++) {
            Scale scale = Scales.AllScales[i];
            String intervalNames = "";
            for (String intervalName : scale.getIntervalNames()) {
                if (intervalName.equals("0")) continue;
                intervalNames += intervalName + " ";
            }
            scaleDescriptions[i] = intervalNames;
        }

        final Spinner scaleSpinner = menuView.findViewById(R.id.scale_spinner);
        ArrayAdapter<String> scaleAdapter = new ArrayAdapter<String>(session.currentActivity, R.layout.spinner_view_layout, scaleTitles) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView)super.getView(position, convertView, parent);
                view.setTextColor(Color.WHITE);
                return view;
            }
        };
        scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scaleSpinner.setAdapter(scaleAdapter);
        scaleSpinner.setSelection(currentScalePosition);
        scaleSpinner.post(new Runnable() {
            public void run() {
                scaleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        SessionModel.getInstance().setScale(Scales.AllScales[i]);
                        dismiss();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });

        String[] modeNames = currentScale.getModeNames().clone();
        int currentMode = currentScale.getMode();
        modeNames[currentMode] = "Mode: " + modeNames[currentMode];

        final Spinner modeSpinner = menuView.findViewById(R.id.mode_spinner);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(session.currentActivity, R.layout.spinner_view_layout, modeNames){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView)super.getView(position, convertView, parent);
                view.setTextColor(Color.WHITE);
                return view;
            }
        };
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setSelection(currentMode);
        modeSpinner.post(new Runnable() {
            public void run() {
                modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        SessionModel.getInstance().getScale().setMode(i);
                        dismiss();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });
    }

    private void ConfigureInstrumentButtons() {
        RadioButton guitarButton = menuView.findViewById(R.id.guitar6RadioButton);
        guitarButton.setText(Html.fromHtml("Guitar"));
        RadioButton bassButton = menuView.findViewById(R.id.bass4RadioButton);
        bassButton.setText(Html.fromHtml("Bass"));
        RadioButton ukuleleButton = menuView.findViewById(R.id.ukuleleRadioButton);
        ukuleleButton.setText(Html.fromHtml("Ukulele"));

        switch (SessionModel.getInstance().getInstrument()) {
            case Bass4:
                bassButton.setChecked(true);
                bassButton.setText(Html.fromHtml("<b>Bass</b>"));
//                SessionModel.getInstance().setStringCount(4, false);
//                SessionModel.getInstance().setTuning(TuningModel.getStandardTuning(), false);
                break;
            case Ukulele:
                ukuleleButton.setChecked(true);
                ukuleleButton.setText(Html.fromHtml("<b>Ukulele</b>"));
//                SessionModel.getInstance().setStringCount(4, false);
//                SessionModel.getInstance().setTuning(TuningModel.getUkuleleStandardTuning(), false);
                break;
            case Guitar6:
                guitarButton.setChecked(true);
                guitarButton.setText(Html.fromHtml("<b>Guitar</b>"));
//                SessionModel.getInstance().setStringCount(6, false);
//                SessionModel.getInstance().setTuning(TuningModel.getStandardTuning(), false);
                break;
            default:
                break;
        }

        ((RadioGroup) menuView.findViewById(R.id.instrumentButtonGroup)).setOnCheckedChangeListener(getCheckChangedListener());
    }

    private RadioGroup.OnCheckedChangeListener getCheckChangedListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                SessionModel session = SessionModel.getInstance();

                RadioButton guitarButton = menuView.findViewById(R.id.guitar6RadioButton);
                if (guitarButton.isChecked()) {
                    guitarButton.setText(Html.fromHtml("<b>Guitar</b>"));
                    session.setInstrument(Instruments.Guitar6);
                    session.setStringCount(6, false);
                    session.setTuning(TuningModel.getStandardTuning(), true);
                } else guitarButton.setText(Html.fromHtml("Guitar"));

                RadioButton bassButton = menuView.findViewById(R.id.bass4RadioButton);
                if (bassButton.isChecked()) {
                    bassButton.setText(Html.fromHtml("<b>Bass</b>"));
                    session.setInstrument(Instruments.Bass4);
                    session.setStringCount(4, false);
                    session.setTuning(TuningModel.getStandardTuning(), true);
                } else bassButton.setText(Html.fromHtml("Bass"));

                RadioButton ukuleleButton = menuView.findViewById(R.id.ukuleleRadioButton);
                if (ukuleleButton.isChecked()) {
                    ukuleleButton.setText(Html.fromHtml("<b>Ukulele</b>"));
                    session.setInstrument(Instruments.Ukulele);
                    session.setStringCount(4, false);
                    session.setTuning(TuningModel.getUkuleleStandardTuning(), true);
                } else ukuleleButton.setText(Html.fromHtml("Ukulele"));

                ((Button)menuView.findViewById(R.id.menuStringCountButton)).setText(session.stringCount + " strings");
                ((Button)menuView.findViewById(R.id.menuTuningButton)).setText(TuningModel.getTuningName(session.getTuning()));
            }
        };
    }

    private void setFonts() {
    }

    public void onModeMenuItemClicked() {
        final Scale scale = SessionModel.getInstance().getScale();
        final int[] intervals = scale.getIntervals();
        Integer[] degrees = new Integer[intervals.length];
        for (int i = 0; i < intervals.length; i++) degrees[i] = i;

        DialogModule.BeginGetDialogSelection(getContext(), getLayoutInflater(), scale.getName() + " Modes", degrees, scale.getModeNames(), new DialogResponseListener<Dialog, Integer>() {
            @Override
            public void onItemClicked(Dialog sender, Integer item) {

                int skippedIntervals = 0;
                for (int i = 0; i < item; i++) {
                    if (intervals[i] == 0) skippedIntervals++;
                }

                scale.setMode(item + skippedIntervals);
                sender.dismiss();
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void dismiss() {
        SessionModel.getInstance().setMenuFragment(null);
        super.dismiss();
    }
}

