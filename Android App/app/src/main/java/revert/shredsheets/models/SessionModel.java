package revert.shredsheets.models;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import revert.shredsheets.R;
import revert.shredsheets.enums.Instruments;
import revert.shredsheets.enums.Keys;
import revert.shredsheets.fragments.MenuFragment;
import revert.shredsheets.models.scales.MajorScale;
import revert.shredsheets.models.scales.Scale;
import revert.shredsheets.models.themes.BlueTheme;
import revert.shredsheets.models.themes.ShredsheetsTheme;

public class SessionModel {
    private static SessionModel instance;
    public FragmentActivity currentActivity;
    public Boolean isEditMode = false;
    public Boolean openSettings = false;
    public int stringCount = 6;
    private boolean useRealisticFrets = true;
    private Keys[] tuning = new Keys[]{Keys.E, Keys.B, Keys.G, Keys.D, Keys.A, Keys.E, Keys.B, Keys.G, Keys.D, Keys.A, Keys.E, Keys.B};
    private ShredsheetsTheme theme;
    private float keySwipeProgress;
    private float modeSwipeProgress;
    private Keys key = Keys.C;
    private boolean leftyMode = false;
    private int[]  viewsToRefresh = new int[]
            {
                    R.id.fretboardKeySlider,
                    R.id.fretboardFragment,
                    R.id.selectedKeyView,
                    R.id.fretboardTopRunner,
                    R.id.fretboardView,
                    R.id.fretboardBottomRunner,
                    R.id.detailsFragment,
                    R.id.notesAndIntervalsView
            };
    private Scale scale;
    private MenuFragment menuFragment;
    private Instruments instrument = Instruments.Guitar6;

    public static SessionModel getInstance() {
        if (instance == null)
            instance = new SessionModel();

        return instance;
    }

    public Keys[] getTuning() {
        return tuning;
    }

    public void setTuning(Keys[] tuning, Boolean invalidateViews) {
        this.tuning = tuning;
        if (invalidateViews) invalidateViews();
    }

    public void setModeSwipeProgress(float swipeProgress) {
        modeSwipeProgress = swipeProgress;
        //this.getScale().setMode(getModifiedSwipeMode());
    }

    public void setKeySwipeProgress(float swipeProgress) {
        keySwipeProgress = swipeProgress;
        Keys modifiedKey = getModifiedSwipeKey();
        setKey(modifiedKey);
    }

    public float getFretX(int fretNumber, float width) {
        return getFretX(fretNumber,width, getFretCount());
    }

    public float getFretX(int fretNumber, float width, int fretCount) {
        double[] fretSpacing = FretSpacingModel.GetFretSpacing(fretCount, width);

        double cumulativeSpacing = isLeftyMode() ? fretSpacing[0] : 0;

        for (int i = 0; i < fretNumber; i++) {
            cumulativeSpacing += isLeftyMode() ? fretSpacing[i + 1] : fretSpacing[i];
        }

        return (float) (isLeftyMode() ? width - cumulativeSpacing : cumulativeSpacing);
    }


    public int getModifiedSwipeMode() {
        int stepCount = getSwipeStepCount(modeSwipeProgress);
        int mode = scale.getMode();
        if (stepCount == 0) return mode;
        return mode + stepCount % scale.getIntervals().length;
    }

    public Keys getModifiedSwipeKey() {
        Keys newKey = getKey();

        int stepCount = getSwipeStepCount(keySwipeProgress);
        if (stepCount == 0) return newKey;
        //SessionModel.getInstance().getScale().setMode(0);

        int currentKeyIndex;
        for (currentKeyIndex = 0; currentKeyIndex < NotesModel.AllStandardKeys.length; currentKeyIndex++)
            if (NotesModel.AllStandardKeys[currentKeyIndex] == newKey)
                break;

        int newKeyId;
        if (currentKeyIndex + stepCount < 0) {
            newKeyId = (NotesModel.AllStandardKeys.length + (currentKeyIndex + stepCount));
        } else {
            newKeyId = currentKeyIndex + stepCount;
        }

        int standardKeysLength = NotesModel.AllStandardKeys.length;
        if (newKeyId < 0) newKeyId = standardKeysLength - ((newKeyId * -1) % standardKeysLength);

        return NotesModel.AllStandardKeys[newKeyId % NotesModel.AllStandardKeys.length];
    }

    //                                                    C             D             E   F             G             A             B   C
    private float[] fretSpacingCoefficients = new float[]{1f, .5f, .5f, 1f, .5f, .5f, 1f, 1f, .5f, .5f, 1f, .5f, .5f, 1f, .5f, .5f, 1f, 1f};

    private float swipeRemainder = 0f;

    public int getSwipeStepCount(float progress) {
        int fretCount = getFretCount();

        float wholeFretWidth = getWidth() / (float) fretCount;
        swipeRemainder += progress;

        int notePosition = NotesModel.GetStandardIndexOfKey(getKey());

        int stepCount = 0;

        while (Math.abs(swipeRemainder) > wholeFretWidth / 2f) {
            int coefficientIndex = notePosition + stepCount;

            while (coefficientIndex < 0) coefficientIndex += fretSpacingCoefficients.length;

            float currentFretCoefficient = fretSpacingCoefficients[coefficientIndex];
            if (swipeRemainder > 0) {
                if (swipeRemainder <= wholeFretWidth * currentFretCoefficient) break;

                swipeRemainder -= wholeFretWidth * currentFretCoefficient;
                stepCount++;
            } else {
                if (swipeRemainder >= (wholeFretWidth * currentFretCoefficient * -1)) break;

                swipeRemainder += wholeFretWidth * currentFretCoefficient;
                stepCount--;
            }

        }

        return stepCount;
    }

    //0 = unknown, 1 = portrait, 2 = landscape
    public boolean isPortrait(Context context) {
        if (context == null) return true;
        Resources resources = context.getResources();
        if (resources != null) {
            Configuration config = resources.getConfiguration();
            if (config != null) return config.orientation == 1;
        }
        return true;
    }

    public int getWidth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            return currentActivity.getResources().getConfiguration().screenWidthDp;
        } else {
            DisplayMetrics displayMetrics = currentActivity.getResources().getDisplayMetrics();
            return (int) Math.ceil((float)displayMetrics.widthPixels / displayMetrics.density);
        }
    }

    public int getHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            return currentActivity.getResources().getConfiguration().screenHeightDp;
        } else {
            DisplayMetrics displayMetrics = currentActivity.getResources().getDisplayMetrics();
            return (int) Math.ceil((float)displayMetrics.heightPixels / displayMetrics.density);
        }
    }

    public int getFretCount() {
        return isPortrait(currentActivity) ? 13 : 25;
    }

    public Keys getKey() {
        return key;
    }

    public void setKey(Keys key) {
        if (this.key == key) return;
        this.key = key;
        invalidateViews();
    }

    public void invalidateViews() {
        for (int viewId : viewsToRefresh) {
            View view = currentActivity.findViewById(viewId);
            if (view != null) view.invalidate();
        }
    }

    public Scale getScale() {
        return (scale != null) ? scale : (scale = new MajorScale());
    }

    public void setScale(Scale scale) {
        this.scale = scale;
        SessionModel.getInstance().invalidateViews();
    }

    public void setMainActivity(FragmentActivity mainActivity) {
        currentActivity = mainActivity;
    }

    public ShredsheetsTheme getTheme() {
        return theme != null ? theme : (theme = new BlueTheme());
    }

    public void setTheme(ShredsheetsTheme theme) {
        this.theme = theme;
    }

    public void setStringCount(int stringCount, Boolean invalidateViews) {
        this.stringCount = stringCount;
        if (invalidateViews) invalidateViews();
    }

    public boolean getUseRealisticFrets() {
        return useRealisticFrets;
    }

    public void setUseRealisticFrets(boolean useRealisticFrets, Boolean invalidateViews) {
        this.useRealisticFrets = useRealisticFrets;
        if (invalidateViews) invalidateViews();
    }

    public void setDefaults() {
        this.setKey(Keys.C);
        this.theme.setDegreeHighlightingVector( this.theme.defaultVector);
        this.setTuning(TuningModel.getStandardTuning(), true);
        this.setStringCount(6, true);
        this.setScale(new MajorScale());
        setInstrument(Instruments.Guitar6);
        this.setUseRealisticFrets(true, false);
    }

    public void setMenuFragment(MenuFragment menuFragment) {
        this.menuFragment = menuFragment;
    }

    public MenuFragment getMenuFragment() {
        return menuFragment;
    }

    public void setInstrument(Instruments instrument) {
        this.instrument = instrument;
    }

    public Instruments getInstrument() {
        return instrument;
    }

    public boolean getOpenSettings() {
        return openSettings;
    }

    public void setOpenSettings(boolean value) {
        openSettings = value;
    }

    public boolean isLeftyMode() {
        return leftyMode;
    }

    public void setLeftyMode(boolean leftyMode) {
        if (leftyMode == this.leftyMode) return;
        this.leftyMode = leftyMode;
        invalidateViews();
    }
}