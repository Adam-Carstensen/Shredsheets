package com.bm.shredsheets.fretboard

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import com.bm.extensions.inset
import com.bm.TextModule
import com.bm.shredsheets.models.SessionModel

class SelectedKeyView : Actor() {

    init {

    }

    var noteRectangle = Rectangle()
    var scaleHeaderRectangle = Rectangle()
    var scaleRectangle = Rectangle()
    var modeHeaderRectangle = Rectangle()
    var modeRectangle = Rectangle()



    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (batch == null) return

        noteRectangle.set(this.x, this.y, width * .25f, height)
        noteRectangle.inset(10f, 10f)

        scaleHeaderRectangle.set(this.x + width * .25f, this.y + height * .5f, width * .75f, height * .2f)
        scaleRectangle.set(this.x + width * .25f, this.y + height * .7f, width * .75f, height * .3f)


        modeHeaderRectangle.set(this.x + width * .25f, this.y, width * .75f, height * .2f)
        modeRectangle.set(this.x + width * .25f, this.y + height * .2f, width * .75f, height * .3f)

        scaleHeaderRectangle.inset(10f, 15f)
        scaleRectangle.inset(10f, 15f)

        modeHeaderRectangle.inset(10f, 10f)
        modeRectangle.inset(10f, 15f)

        TextModule.DrawText(batch, scaleHeaderRectangle, "Scale", SessionModel.instance.drawFont, Align.left, SessionModel.instance.theme.degreeColors[4])
        TextModule.DrawText(batch, scaleRectangle, SessionModel.instance.scale.name, SessionModel.instance.drawFont, Align.left, SessionModel.instance.theme.degreeColors[4])

        TextModule.DrawText(batch, modeHeaderRectangle, "Mode", SessionModel.instance.drawFont, Align.left, SessionModel.instance.theme.degreeColors[SessionModel.instance.scale.mode])
        TextModule.DrawText(batch, modeRectangle, SessionModel.instance.scale.currentModeName, SessionModel.instance.drawFont, Align.left, SessionModel.instance.theme.degreeColors[SessionModel.instance.scale.mode])

        TextModule.DrawText(batch, noteRectangle, SessionModel.instance.key.cleanName, SessionModel.instance.selectedNoteFont, Align.center, SessionModel.instance.theme.degreeColors[0])




    }
}