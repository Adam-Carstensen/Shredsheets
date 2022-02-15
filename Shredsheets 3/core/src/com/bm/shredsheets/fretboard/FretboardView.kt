package com.bm.shredsheets.fretboard

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.AudioDevice
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.utils.Align
import com.bm.extensions.inset
import com.bm.shredsheets.RectModule
import com.bm.TextModule
import com.bm.shredsheets.enums.MusicKeys
import com.bm.shredsheets.models.FretSpacingModel
import com.bm.shredsheets.models.NotesModel
import com.bm.shredsheets.models.SessionModel
import com.bm.shredsheets.models.scales.ScaleNote
import com.bm.shredsheets.models.themes.ShredsheetsTheme
import kotlin.concurrent.thread
import kotlin.math.pow

class FretboardView : Actor() {

    var lastTouched = Pair(0, 0)

    init {
        touchable = Touchable.enabled

        addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                //Dampening Pedal
                //wavePool.clearPool()
                lastTouched = Pair(-1, -1)
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val tuning = SessionModel.instance.getTuning()
                lastTouched = getFretAtCoordinate(x, y)

                var tuningNote = tuning[lastTouched.second].noteValue
                val fretNote = (tuningNote + lastTouched.first) % 12


                var newFrequency = tuning[lastTouched.second].frequency * 2.0f.pow(lastTouched.first / 12f)

                val note = scaleNotesByPosition[fretNote] ?: return true

                var thread = thread {
                    //var frequency = MusicKeys.getFrequency(note.scalePosition)

                    var newWave: ShortArray = WaveForm.getGuitarWave(SessionModel.instance.bufferLengthSeconds, newFrequency, .5f)
                    //var newWave: ShortArray = WaveForm.getGuitarWave(bufferLengthSeconds, frequency, .5f)
                    //var newWave: ShortArray = WaveForm.getSeventhGuitarChordWave(bufferLengthSeconds, frequency, note.scalePosition, .5f)
                    SessionModel.instance.wavePool.queueWaveForm(newWave)
                    SessionModel.instance.wavePool.playWavePool()
                }

                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                super.touchDragged(event, x, y, pointer)

                var touchPair = getFretAtCoordinate(x, y)

                if (lastTouched == touchPair)
                    return

                lastTouched = touchPair

                touchDown(event, x, y, pointer, 0)
            }

            fun getFretAtCoordinate(x: Float, y: Float): Pair<Int, Int> {
                val fretSpacing: DoubleArray = FretSpacingModel.GetFretSpacing(SessionModel.instance.fretCount, width)
                val fretHeight = height / SessionModel.instance.stringCount.toFloat()

                var noteIndexX = 0
                var distanceFromNut = 0.0
                for ((i, item) in fretSpacing.withIndex()) {
                    distanceFromNut += item
                    if (distanceFromNut > x) {
                        noteIndexX = i
                        break
                    }
                }

                var noteIndexY = 0
                var distanceFromEdge = 0.0
                for (i in 1..SessionModel.instance.stringCount) {
                    distanceFromEdge += fretHeight
                    if (distanceFromEdge > y) {
                        noteIndexY = SessionModel.instance.stringCount - i
                        break
                    }
                }

                var touchPair = Pair(noteIndexX, noteIndexY)
                return touchPair
            }

        })
    }

    private val fretBorderColor: Color = Color(.25f, .25f, .25f, 1f)
    var scaleNotesByPosition: HashMap<Int, ScaleNote> = HashMap()

    var noteRect = Rectangle()
    var scaleDegreeRect = Rectangle()
    var spaceRect = Rectangle()
    var outsideBorderRect = Rectangle()

    private var outsideBorderColor = Color(.25f, .25f, .25f, 1f)
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (batch == null) return
        var session = SessionModel.instance

        val theme: ShredsheetsTheme = session.theme

        val scaleNotes = NotesModel.GetScaleNotes(session.key, session.scale)

        scaleNotesByPosition.clear()
        for (note in scaleNotes)
            if (note != null) scaleNotesByPosition[note.position] = note

        outsideBorderRect.set(0f, 0f, width, height)

        //Outside border
        RectModule.DrawBorderedRect(batch, outsideBorderRect, Color.WHITE, outsideBorderColor, 2f)


        val fretSpacing: DoubleArray = FretSpacingModel.GetFretSpacing(session.fretCount, width)

        val degreeHighlightingVector = theme.degreeHighlightingVector

        val fretHeight = height / session.stringCount.toFloat()
        val tuning: Array<MusicKeys> = session.getTuning()

        for (stringNumber in 0 until session.stringCount) {
            for (fretNumber in fretSpacing.indices) {

                //Strings are saved highest to lowest, and rendered highest on the screen to lowest
                var stringIndex = session.stringCount - stringNumber - 1
                val fretWidth = fretSpacing[fretNumber].toFloat()
                val fretY = this.y + (stringIndex * fretHeight)
                val fretX: Float = session.getFretX(fretNumber, width)

                spaceRect.set(fretX, fretY, fretWidth, fretHeight)

                val tuningIndex: Int
                if (session.stringCount >= 6)
                    tuningIndex = stringNumber
                else if (session.stringCount == 5)
                    tuningIndex = stringNumber + 2
                else
                    tuningIndex = 6 - session.stringCount + stringNumber

                val tuningNote: Int = tuning[tuningIndex].noteValue

                //With 6 strings and up, strings are added to the top of the stack (EADGBe becomes BEADGBe)
                //Below 6 strings, they're removed from the bottom (EADGBe becomes EADG)
                val fretNote = (tuningNote + fretNumber) % 12

                val note = scaleNotesByPosition[fretNote]

                var highlightingColor: Color
                highlightingColor = if (note != null && degreeHighlightingVector!![note.scalePosition]) //should the degree be highlighted
                    SessionModel.instance.theme.degreeColors[note.degree - 1] //if so, give it the appropriate color
                else theme.getBaseFretColor(fretNumber)

                if (note == null) highlightingColor = theme.emptyFretColor

                RectModule.DrawBorderedRect(batch, spaceRect, highlightingColor, fretBorderColor, 1f)

                if (note?.name == null) continue

                noteRect.set(spaceRect.x, spaceRect.y + fretHeight * .3f, spaceRect.width, fretHeight * .7f)
                noteRect.inset(5f, 5f)

                scaleDegreeRect.set(spaceRect.x, spaceRect.y, spaceRect.width, fretHeight * .3f)
                scaleDegreeRect.inset(5f, 5f)


                TextModule.DrawText(batch, noteRect, note.name!!, SessionModel.instance.boldFont, Align.topLeft, Color.BLACK)
                TextModule.DrawText(batch, scaleDegreeRect, note.degreeName.toString(), SessionModel.instance.drawFont, Align.bottomRight)


                //println("Writing degree name: ${note.degreeName} in rectangle: ${scaleDegreeRect}")
                //TODO add text color  ... textSpaceRect, scaleDegreeRect, ColorModule.getModeratelyContrastingColor(highlightingColor), android.text.Layout.Alignment.ALIGN_OPPOSITE, false)


            }

        }

    }


}