package com.bm.shredsheets.fretboard

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.bm.extensions.inset
import com.bm.resources.Textures
import com.bm.shredsheets.ColorModule
import com.bm.shredsheets.RectModule
import com.bm.shredsheets.TextModule
import com.bm.shredsheets.models.FretSpacingModel
import com.bm.shredsheets.models.SessionModel
import com.bm.shredsheets.models.themes.ShredsheetsTheme
import com.kotcrab.vis.ui.widget.VisTable
import space.earlygrey.shapedrawer.ShapeDrawer

class FretboardRunnerView : Actor() {

    init {


    }

    var drawer: ShapeDrawer? = null
    var runnerRectangle = Rectangle()
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        var margin = 10f

        val theme: ShredsheetsTheme = SessionModel.instance.theme

        val fretSpacing: DoubleArray = FretSpacingModel.GetFretSpacing(SessionModel.instance.fretCount, width)

        if (batch == null) return
        if (drawer == null || drawer!!.batch != batch) {
            println("Creating new drawer")
            drawer = ShapeDrawer(batch, Textures.getWhiteTexture())
        }

        for (i in fretSpacing.indices) {
            var fretWidth = fretSpacing[i]
            var x: Float = SessionModel.instance.getFretX(i, width)

            val fretText = if (i == 0) "Open" else i.toString()

            runnerRectangle.set(this.x + x, this.y, fretWidth.toFloat(), height)

            val fretColor = theme.getFretboardRunnerPaintColor(i)

            RectModule.DrawBorderedRect(batch!!, runnerRectangle, fretColor, Color(.15f, .15f, .15f, 1f), 1f)

            var textColor = ColorModule.getMaximallyContrastingColor(fretColor)

            runnerRectangle.inset(5f, 5f)
            TextModule.DrawText(batch!!, runnerRectangle, fretText, SessionModel.instance.drawFont, Align.center, textColor)

            drawer!!.line(x, this.y, x, this.y + height, SessionModel.instance.verticalLineColor, 2f)

        }


    }


}