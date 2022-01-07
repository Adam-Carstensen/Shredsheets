package com.bm.buildsite.controls

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.bm.UserInterface
import com.bm.resources.Textures
import com.kotcrab.vis.ui.widget.VisLabel
import kotlin.math.max


open class BsTitledActor(title: String, actor: Actor, textSize: Int, actorScale: Float = 1f) : Button(ButtonStyle()) {

    var titleLabel: VisLabel = VisLabel(title, UserInterface.getLabelStyle(textSize))

    var checkedOverlayTexture = Textures.getColorTexture(Color(.3f, .6f, .9f, .33f))
    var checkedOverlay = com.badlogic.gdx.scenes.scene2d.ui.Image(checkedOverlayTexture)

    init {

        addActor(titleLabel)
        addActor(actor)
        actor.setScale(actorScale)

        titleLabel.setAlignment(Align.bottomLeft)

        titleLabel.setPosition(0f, 0f, Align.bottomLeft)
        actor.setPosition(0f, titleLabel.height, Align.bottomLeft)

        var maxWidth = max(titleLabel.x + titleLabel.width, actor.x + actor.width * actor.scaleX)
        var maxHeight = max(titleLabel.y + titleLabel.height, actor.y + actor.height * actor.scaleY)

        setBounds(0f, 0f, maxWidth, maxHeight)
        setSize(maxWidth, maxHeight)

        addActor(checkedOverlay)
        checkedOverlay.setPosition(0f, 0f, Align.bottomLeft)
        checkedOverlay.setSize(maxWidth, maxHeight)
        checkedOverlay.isVisible = isChecked

        addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                checkedOverlay.isVisible = isChecked
            }
        })

    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
    }


    override fun setChecked(isChecked: Boolean) {
        super.setChecked(isChecked)
    }


}