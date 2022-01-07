package com.bm.shredsheets.fretboard

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.bm.resources.Textures
import com.bm.shredsheets.ShredsheetsGame
import com.bm.shredsheets.mainmenu.MenuButtonClicked
import com.bm.shredsheets.models.SessionModel
import com.bm.shredsheets.models.themes.ShredsheetsTheme
import com.bm.shredsheets.scenes.ScaleScene
import space.earlygrey.shapedrawer.ShapeDrawer

class MenuButton : Actor() {


    var shapeDrawer: ShapeDrawer? = null

    var circleColor = Color(.25f, .25f, .25f,1f)

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (shapeDrawer == null || shapeDrawer!!.batch != batch) shapeDrawer = ShapeDrawer(batch, Textures.getWhiteTexture())

        var circleX = this.x + this.width / 2f
        var circleY1 = this.y + (this.height / 4f)
        var circleY2 = this.y + (this.height / 4f) * 2f
        var circleY3 = this.y + (this.height / 4f) * 3f

        shapeDrawer!!.filledCircle(circleX, circleY1, this.width * .2f, circleColor)
        shapeDrawer!!.setColor(SessionModel.instance.theme.degreeColors[0])
        shapeDrawer!!.circle(circleX, circleY1, this.width * .2f, 3f)
        shapeDrawer!!.filledCircle(circleX, circleY2, this.width * .2f, circleColor)
        shapeDrawer!!.setColor(SessionModel.instance.theme.degreeColors[2])
        shapeDrawer!!.circle(circleX, circleY2, this.width * .2f, 3f)
        shapeDrawer!!.filledCircle(circleX, circleY3, this.width * .2f, circleColor)
        shapeDrawer!!.setColor(SessionModel.instance.theme.degreeColors[4])
        shapeDrawer!!.circle(circleX, circleY3, this.width * .2f, 3f)

    }

}