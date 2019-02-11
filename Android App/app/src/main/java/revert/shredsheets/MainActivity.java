package revert.shredsheets;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import revert.shredsheets.enums.Instruments;
import revert.shredsheets.enums.Keys;
import revert.shredsheets.fragments.HelpFragment;
import revert.shredsheets.models.SessionModel;
import revert.shredsheets.models.TuningModel;
import revert.shredsheets.models.scales.MajorScale;
import revert.shredsheets.models.scales.PentatonicMinor;
import revert.shredsheets.models.scales.Scale;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onResume() {
        super.onResume();
        ConfigureRadioButtons();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.CMajorButton)).setText(Html.fromHtml("<font color='#FFFFFF'>C</font><small><small><small><small> <font color='#555555'>Major</font></small></small></small></small>"));
        ((Button) findViewById(R.id.AMinorButton)).setText(Html.fromHtml("<font color='#FFFFFF'>A</font><small><small><small><small> <font color='#999999'>Minor</font></small></small></small></small>"));
        ((Button) findViewById(R.id.EPentatonicMinorButton)).setText(Html.fromHtml("<font color='#FFFFFF'>E</font><br/><small><small><small><small><small> <font color='#555555'>Pentatonic Minor</font></small></small></small></small></small>"));
        ((Button) findViewById(R.id.FretboardNavigationButton)).setText(Html.fromHtml("<font color='#FFFFFF'>More</font><small><small><small><small><br/><font color='#555555'>Scales, Modes, & Tunings</font></small></small></small></small>"));
        ((Button) findViewById(R.id.HelpButton)).setText(Html.fromHtml("Help<br/><small><small><small><small><font color='#999999'>Shredsheets & music theory</font></small></small></small></small>"));

        SessionModel.getInstance().setMainActivity(this);

        ConfigureRadioButtons();
        final Intent defaultIntent = new Intent(this, FretboardActivity.class);

        ((RadioGroup) findViewById(R.id.instrumentButtonGroup)).setOnCheckedChangeListener(getCheckChangedListener());

        findViewById(R.id.shredsheetsTitleText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionModel.getInstance().setOpenSettings(false);
                startActivity(defaultIntent);
            }
        });
    }

    private RadioGroup.OnCheckedChangeListener getCheckChangedListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton guitarButton = findViewById(R.id.guitar6RadioButton);
                if (guitarButton.isChecked()) {
                    guitarButton.setText(Html.fromHtml("<b>Guitar</b>"));
                    SessionModel.getInstance().setInstrument(Instruments.Guitar6);
                    SessionModel.getInstance().setStringCount(6, false);
                    SessionModel.getInstance().setTuning(TuningModel.getStandardTuning(), false);
                } else guitarButton.setText(Html.fromHtml("Guitar"));

                RadioButton bassButton = findViewById(R.id.bass4RadioButton);
                if (bassButton.isChecked()) {
                    bassButton.setText(Html.fromHtml("<b>Bass</b>"));
                    SessionModel.getInstance().setInstrument(Instruments.Bass4);
                    SessionModel.getInstance().setStringCount(4, false);
                    SessionModel.getInstance().setTuning(TuningModel.getStandardTuning(), false);
                } else bassButton.setText(Html.fromHtml("Bass"));

                RadioButton ukuleleButton = findViewById(R.id.ukuleleRadioButton);
                if (ukuleleButton.isChecked()) {
                    ukuleleButton.setText(Html.fromHtml("<b>Ukulele</b>"));
                    SessionModel.getInstance().setInstrument(Instruments.Ukulele);
                    SessionModel.getInstance().setStringCount(4, false);
                    SessionModel.getInstance().setTuning(TuningModel.getUkuleleStandardTuning(), false);
                } else ukuleleButton.setText(Html.fromHtml("Ukulele"));
            }
        };
    }

    private void ConfigureRadioButtons() {
        SessionModel session = SessionModel.getInstance();
        RadioButton guitarButton = findViewById(R.id.guitar6RadioButton);
        guitarButton.setText(Html.fromHtml("Guitar"));
        RadioButton bassButton = findViewById(R.id.bass4RadioButton);
        bassButton.setText(Html.fromHtml("Bass"));
        RadioButton ukuleleButton = findViewById(R.id.ukuleleRadioButton);
        ukuleleButton.setText(Html.fromHtml("Ukulele"));
        switch (SessionModel.getInstance().getInstrument()) {
            case Bass4:
                bassButton.setChecked(true);
                bassButton.setText(Html.fromHtml("<b>Bass</b>"));
                session.setInstrument(Instruments.Bass4);
                session.setStringCount(4, false);
                session.setTuning(TuningModel.getStandardTuning(), false);
                break;
            case Ukulele:
                ukuleleButton.setChecked(true);
                ukuleleButton.setText(Html.fromHtml("<b>Ukulele</b>"));
                session.setInstrument(Instruments.Ukulele);
                session.setStringCount(4, false);
                session.setTuning(TuningModel.getUkuleleStandardTuning(), false);
                break;
            case Guitar6:
                guitarButton.setChecked(true);
                guitarButton.setText(Html.fromHtml("<b>Guitar</b>"));
                session.setInstrument(Instruments.Guitar6);
                session.setStringCount(6, false);
                session.setTuning(TuningModel.getStandardTuning(), false);
                break;
            default:
                break;
        }
    }

    public void onFretboardNavigationButtonClicked(View view) {
        SessionModel.getInstance().setOpenSettings(true);
        Intent intent = new Intent(this, FretboardActivity.class);
        startActivity(intent);
    }

    public void onHelpButtonClicked(View view) {
        HelpFragment helpFragment = new HelpFragment();
        helpFragment.show(getSupportFragmentManager(), "HelpFragment");
    }

    public void onCMajorButtonClicked(View view) {
        StartFretboardActivity(Keys.C, new MajorScale(), 0);
    }

    public void onAMinorButtonClicked(View view) {
        StartFretboardActivity(Keys.A, new MajorScale(), 5);
    }

    public void onEPentatonicMinorButtonClicked(View view) {
        StartFretboardActivity(Keys.E, new PentatonicMinor(), 0);
    }

    private void StartFretboardActivity(Keys key, Scale scale, int mode) {
        SessionModel session = SessionModel.getInstance();
        session.setKey(key);
        scale.setMode(mode);
        session.setScale(scale);
        Intent intent = new Intent(this, FretboardActivity.class);
        startActivity(intent);
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
}
