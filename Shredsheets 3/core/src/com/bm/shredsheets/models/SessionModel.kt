package com.bm.shredsheets.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.AudioDevice
import com.badlogic.gdx.graphics.Color
import com.bm.BuildSiteFonts
import com.bm.shredsheets.enums.Instruments
import com.bm.shredsheets.enums.MusicKeys
import com.bm.shredsheets.fretboard.WaveForm
import com.bm.shredsheets.fretboard.WavePool
import com.bm.shredsheets.models.scales.MajorScale
import com.bm.shredsheets.models.scales.Scale
import com.bm.shredsheets.models.themes.DefaultTheme
import com.bm.shredsheets.models.themes.ShredsheetsTheme
import kotlin.math.abs

class SessionModel {

    var audioDevice: AudioDevice = Gdx.audio.newAudioDevice(WaveForm.sampleRate, true)
    var bufferLengthSeconds = 1.5f
    var wavePool = WavePool(audioDevice, bufferLengthSeconds, WaveForm.sampleRate)

    var debugMode = false

    var selectedNoteFont = BuildSiteFonts.FreeSansBold_Unicode

    var drawFont = BuildSiteFonts.FreeSans_Unicode
    var boldFont = BuildSiteFonts.FreeSansBold_Unicode

    var verticalLineColor: Color = Color(.6f, .6f, .7f, 1f)

    var openSettings = false
    var stringCount = 6
    var useRealisticFrets = false

    private var tuning: Array<MusicKeys> = TuningModel.standardTuning
    var theme: ShredsheetsTheme = DefaultTheme()

    private var keySwipeProgress = 0f
    private var modeSwipeProgress = 0f
    var key: MusicKeys = MusicKeys.C
        set(key) {
            if (this.key === key) return
            field = key
            invalidateViews()
        }
    var isLeftyMode = false
        set(leftyMode) {
            if (leftyMode == isLeftyMode) return
            field = leftyMode
            invalidateViews()
        }

    //TODO: update refreshing views
    private val viewsToRefresh = intArrayOf(0)

    var instrument: Instruments = Instruments.Guitar6

    fun getTuning(): Array<MusicKeys> {
        return tuning
    }

    private var tuningFrequencies: FloatArray = FloatArray(tuning.size)

    fun setTuning(tuning: Array<MusicKeys>, invalidateViews: Boolean) {
        this.tuning = tuning

        tuningFrequencies  = FloatArray(tuning.size)



        if (invalidateViews) invalidateViews()
    }

    fun setModeSwipeProgress(swipeProgress: Float) {
        modeSwipeProgress = swipeProgress
        //this.getScale().setMode(getModifiedSwipeMode());
    }

    fun setKeySwipeProgress(swipeProgress: Float) {
        keySwipeProgress = swipeProgress
        val modifiedKey: MusicKeys = modifiedSwipeKey
        key = modifiedKey
    }

    fun getFretX(fretNumber: Int, width: Float): Float {
        return getFretX(fretNumber, width, fretCount)
    }

    fun getFretX(fretNumber: Int, width: Float, fretCount: Int): Float {
        val fretSpacing: DoubleArray = FretSpacingModel.GetFretSpacing(fretCount, width)
        var cumulativeSpacing: Double = if (isLeftyMode) fretSpacing[0] else 0.0
        for (i in 0 until fretNumber) {
            cumulativeSpacing += if (isLeftyMode) fretSpacing[i + 1] else fretSpacing[i]
        }
        return (if (isLeftyMode) width - cumulativeSpacing else cumulativeSpacing).toFloat()
    }

    val modifiedSwipeMode: Int
        get() {
            val stepCount = getSwipeStepCount(modeSwipeProgress)
            val mode = scale!!.mode
            return if (stepCount == 0) mode else mode + stepCount % scale.intervals.size
        }

    //SessionModel.getInstance().getScale().setMode(0);
    val modifiedSwipeKey: MusicKeys
        get() {
            val newKey: MusicKeys = key
            val stepCount = getSwipeStepCount(keySwipeProgress)
            if (stepCount == 0) return newKey
            //SessionModel.getInstance().getScale().setMode(0);
            var currentKeyIndex: Int
            currentKeyIndex = 0
            while (currentKeyIndex < NotesModel.AllStandardKeys.size) {
                if (NotesModel.AllStandardKeys.get(currentKeyIndex) === newKey) break
                currentKeyIndex++
            }
            var newKeyId: Int
            newKeyId = if (currentKeyIndex + stepCount < 0) {
                NotesModel.AllStandardKeys.size + (currentKeyIndex + stepCount)
            } else {
                currentKeyIndex + stepCount
            }
            val standardKeysLength: Int = NotesModel.AllStandardKeys.size
            if (newKeyId < 0) newKeyId = standardKeysLength - newKeyId * -1 % standardKeysLength
            return NotesModel.AllStandardKeys.get(newKeyId % NotesModel.AllStandardKeys.size)
        }

    //               C             D             E   F             G             A             B   C
    private val fretSpacingCoefficients = floatArrayOf(1f, .5f, .5f, 1f, .5f, .5f, 1f, 1f, .5f, .5f, 1f, .5f, .5f, 1f, .5f, .5f, 1f, 1f)
    private var swipeRemainder = 0f
    fun getSwipeStepCount(progress: Float): Int {
        val fretCount = fretCount
        val wholeFretWidth = width / fretCount.toFloat()
        swipeRemainder += progress
        val notePosition: Int = NotesModel.GetStandardIndexOfKey(key)
        var stepCount = 0
        while (abs(swipeRemainder) > wholeFretWidth / 2f) {
            var coefficientIndex = notePosition + stepCount
            while (coefficientIndex < 0) coefficientIndex += fretSpacingCoefficients.size
            val currentFretCoefficient = fretSpacingCoefficients[coefficientIndex]
            if (swipeRemainder > 0) {
                if (swipeRemainder <= wholeFretWidth * currentFretCoefficient) break
                swipeRemainder -= wholeFretWidth * currentFretCoefficient
                stepCount++
            } else {
                if (swipeRemainder >= wholeFretWidth * currentFretCoefficient * -1) break
                swipeRemainder += wholeFretWidth * currentFretCoefficient
                stepCount--
            }
        }
        return stepCount
    }

    //0 = unknown, 1 = portrait, 2 = landscape
    val portrait: Boolean = true

    val width: Int
        get() = Gdx.graphics.width

    val height: Int
        get() = Gdx.graphics.height

    val fretCount: Int
        get() = if (portrait) 13 else 25

    fun invalidateViews() {
        for (viewId in viewsToRefresh) {
            //TODO: Refresh views
        }
    }

    var scale: Scale = MajorScale()
        get() = field
        set(value) {
            field = value
            field.mode = 0
            invalidateViews()
        }

//    fun setMainActivity(mainActivity: FragmentActivity?) {
//        currentActivity = mainActivity
//    }

    fun setStringCount(stringCount: Int, invalidateViews: Boolean) {
        this.stringCount = stringCount
        if (invalidateViews) invalidateViews()
    }

    fun setUseRealisticFrets(useRealisticFrets: Boolean, invalidateViews: Boolean) {
        this.useRealisticFrets = useRealisticFrets
        if (invalidateViews) invalidateViews()
    }

    fun setDefaults() {
        key = MusicKeys.C
        setTuning(TuningModel.standardTuning, true)
        setStringCount(6, true)
        scale = MajorScale()
        instrument = Instruments.Guitar6
        setUseRealisticFrets(false, false)
    }

    companion object {
        var instance: SessionModel = SessionModel()
    }
}