package com.bm.shredsheets.fretboard

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.bm.extensions.inset
import com.bm.shredsheets.ColorModule
import com.bm.shredsheets.TextModule
import com.bm.shredsheets.enums.MusicKeys
import com.bm.shredsheets.models.NotesModel
import com.bm.shredsheets.models.SessionModel
import com.bm.shredsheets.models.scales.Scale
import com.bm.shredsheets.models.scales.ScaleNote

class NotesAndIntervalsView : Actor() {

    init {

    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (batch == null) return

        var session = SessionModel.instance

        val scale: Scale = session.scale
        val key: MusicKeys = session.key
        //int[] intervals = scale.getIntervals();
        //int[] intervals = scale.getIntervals();
        val intervalNames: Array<String> = scale.intervalNames

        //String[] degreeNames = scale.getDegreeNames();
        //String[] degreeNames = scale.getDegreeNames();
        val scaleNotes: Array<ScaleNote?> = NotesModel.GetScaleNotes(key, scale)

        var scaleNoteCount = 0
        for (note in scaleNotes) if (note != null) scaleNoteCount++

        val noteWidth = width / (scaleNoteCount + 1).toFloat() //C-B, but we'd add another C

        val noteHeight = height * .4f
        val intervalHeight = height * .2f
        val degreeHeight = height * .15f
        val chordHeight = height * .25f

        val intervalX = noteWidth / 2f
        val intervalY = 5f
        val chordY = intervalHeight + noteHeight
        val degreeY = intervalHeight + noteHeight + chordHeight

        DrawIntervals(batch!!, intervalNames, intervalHeight, noteWidth, intervalX, intervalY)
        DrawNotes(batch!!, scaleNotes, SessionModel.instance.theme.degreeColors, noteHeight, noteWidth, intervalHeight)
        DrawChords(batch!!, scaleNotes, SessionModel.instance.theme.degreeColors, chordHeight, noteWidth, chordY)
        DrawDegreeNames(batch!!, scaleNotes, SessionModel.instance.theme.degreeColors, degreeHeight, noteWidth, degreeY)
    }

    val intervalRectangle = Rectangle()
    private fun DrawIntervals(batch: Batch, intervalNames: Array<String>, intervalHeight: Float, intervalWidth: Float, intervalX: Float, intervalY: Float) {
        var skippedIntervals = 0
        for (i in intervalNames.indices) {
            if (intervalNames[i] === "" || intervalNames[i] == "0") {
                skippedIntervals++
                continue
            }

            val updatedX = intervalX + (i - skippedIntervals) * intervalWidth
            intervalRectangle.set(this.x + updatedX, this.y + intervalY, intervalWidth, intervalHeight)
            intervalRectangle.inset(5f, 10f)

            var intervalColor = ColorModule.getIntervalColor(intervalNames[i])

            TextModule.DrawText(batch, intervalRectangle, intervalNames[i], SessionModel.instance.drawFont, Align.center, intervalColor)
        }
    }

    val noteRectangle = Rectangle()
    private fun DrawNotes(batch: Batch, scaleNotes: Array<ScaleNote?>, degreeColors: Array<Color>, noteHeight: Float, noteWidth: Float, noteY: Float) {
        var minTextSize = Float.MAX_VALUE
        for (i in 0..scaleNotes.size) {
            val scaleNote = scaleNotes[i % scaleNotes.size] ?: continue
            noteRectangle.set(this.x + i * noteWidth,this.y + noteY, noteWidth, noteHeight)
            noteRectangle.inset(10f, 10f)
            val textSize = TextModule.CalculateTextSize(scaleNote!!.name!!, SessionModel.instance.drawFont, noteRectangle.width, noteRectangle.height)
            if (textSize < minTextSize) minTextSize = textSize
        }

        for (i in 0..scaleNotes.size) {
            val scaleNote = scaleNotes[i % scaleNotes.size] ?: continue
            if (scaleNote.name == null) continue;

            noteRectangle.set(this.x + i * noteWidth,this.y + noteY, noteWidth, noteHeight)
            noteRectangle.inset(10f, 10f)

            TextModule.DrawText(batch, noteRectangle, scaleNote.name!!, SessionModel.instance.drawFont, Align.center, degreeColors[scaleNote.degree - 1])
        }
    }

    val chordRectangle = Rectangle()
    private fun DrawChords(batch: Batch, scaleNotes: Array<ScaleNote?>, degreeColors: Array<Color>, chordHeight: Float, chordWidth: Float, chordY: Float) {
        var minTextSize = Float.MAX_VALUE
        var i = 0
        for (index in scaleNotes.indices) {
            val scaleNote = scaleNotes[index] ?: continue
            val chordName = scaleNote.name + scaleNote.chordName
            chordRectangle.set(this.x + i * chordWidth,this.y + chordY, chordWidth, chordHeight)
            chordRectangle.inset(5f, 5f)
            val textSize = TextModule.CalculateTextSize(chordName, SessionModel.instance.drawFont, chordRectangle.width, chordRectangle.height)
            if (textSize < minTextSize) minTextSize = textSize
            i++
        }

        i  = 0
        // <= because the 1st is repeated
        for (index in 0..scaleNotes.size) {
            val scaleNote = scaleNotes[index % scaleNotes.size] ?: continue
            val chordName = scaleNote.name + scaleNote.chordName
            chordRectangle.set(this.x + i * chordWidth, this.y + chordY, chordWidth, chordHeight)
            chordRectangle.inset(10f, 10f)

            TextModule.DrawText(batch, chordRectangle, chordName, SessionModel.instance.drawFont, Align.center, degreeColors[scaleNote.degree - 1])
            i++
        }
    }


    var degreeRect: Rectangle? = null
    private fun DrawDegreeNames(batch: Batch, scaleNotes: Array<ScaleNote?>, degreeColors: Array<Color>, degreeHeight: Float, degreeWidth: Float, degreeY: Float) {
        val skippedIntervals = 0
        //int[] modalDegrees = session.getScale().getModalDegrees();
        for (i in 0..scaleNotes.size) {
//            if (scaleNotes[i % scaleNotes.length] == 0) {
//                skippedIntervals++;
//                continue;
//            }
            val scaleNote: ScaleNote = scaleNotes[i % scaleNotes.size] ?: continue
            val degreeX = (i - skippedIntervals) * degreeWidth
            degreeRect = Rectangle(this.x + degreeX, this.y + degreeY, degreeWidth, degreeHeight)
            degreeRect!!.inset(5f, 10f)
            val intervalColor = degreeColors[scaleNote.degree - 1]

            TextModule.DrawText(batch, degreeRect!!, scaleNote.degreeName!!, SessionModel.instance.drawFont, Align.center, intervalColor)
        }
    }

}
