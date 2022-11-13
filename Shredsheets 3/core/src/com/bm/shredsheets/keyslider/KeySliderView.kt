package com.bm.shredsheets.keyslider

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.Align
import com.bm.BuildSiteFonts
import com.bm.extensions.inset
import com.bm.resources.Textures
import com.bm.TextModule
import com.bm.shredsheets.models.FretSpacingModel
import com.bm.shredsheets.models.NotesModel
import com.bm.shredsheets.models.SessionModel
import space.earlygrey.shapedrawer.ShapeDrawer

class KeySliderView : Actor() {

    init {
        addListener(object : InputListener() {

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return super.touchDown(event, x, y, pointer, button)
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                super.touchDragged(event, x, y, pointer)

                SessionModel.instance.setKeySwipeProgress(x);
            }
        })
    }

    var drawer: ShapeDrawer? = null
    var sliderRectangle = Rectangle()
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        var fretSpacing = FretSpacingModel.GetFretSpacing(SessionModel.instance.fretCount, width)

        var margin = 10f

        if (batch == null) return
        if (drawer == null || drawer!!.batch != batch) {
            println("Creating new drawer")
            drawer = ShapeDrawer(batch, Textures.getWhiteTexture())
        }

        for (i in fretSpacing.indices) {
            var fretWidth = fretSpacing[i]
            var x: Float = SessionModel.instance.getFretX(i, width)

            drawer!!.line(x, this.y, x, this.y + height, SessionModel.instance.verticalLineColor, 2f)

            val keyValue: String = NotesModel.KeySliderStrings[i % 12]
            sliderRectangle.set(x, this.y, fretWidth.toFloat(), height)

            sliderRectangle.inset(10f, 10f)

            if (SessionModel.instance.key.cleanName == keyValue) {
                TextModule.DrawText(batch, sliderRectangle, keyValue, BuildSiteFonts.FreeSerif_Unicode, Align.center, Color(.3f, .6f, .9f, 1f))
            } else {
                TextModule.DrawText(batch, sliderRectangle, keyValue, BuildSiteFonts.FreeSerif_Unicode, Align.center, Color.BLACK)
            }
        }
    }


}