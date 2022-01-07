package com.bm.buildsite.controls

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.bm.UserInterface
import com.bm.resources.Textures
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import kotlin.math.max


open class BsToggleButton(var actor: Actor, var groupName: String, align: Int = Align.center,  actorScale: Float = 1f) : Button(ButtonStyle()) {

    var checkedOverlayTexture = Textures.getColorTexture(Color(.3f, .6f, .9f, .33f))
    var checkedOverlay = com.badlogic.gdx.scenes.scene2d.ui.Image(checkedOverlayTexture)

    init {
        var buttonGroup: java.util.ArrayList<BsToggleButton>? = buttonGroups[groupName]
        if (buttonGroup == null) buttonGroup = ArrayList<BsToggleButton>()
        buttonGroup.add(this)
        buttonGroups[groupName] = buttonGroup

        actor.setScale(actorScale)

        var layoutTable = VisTable()
        layoutTable.setFillParent(true)
        addActor(layoutTable)

        layoutTable.add(actor).align(align)

        var maxWidth = actor.x + actor.width * actor.scaleX
        var maxHeight = actor.y + actor.height * actor.scaleY

        checkedOverlay.setSize(maxWidth, maxHeight)

        setBounds(0f, 0f, maxWidth, maxHeight)
        setSize(maxWidth, maxHeight)

        layoutTable.addActor(checkedOverlay)
        checkedOverlay.isVisible = isChecked

        var thisButton = this

        addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                checkedOverlay.isVisible = isChecked
                if (isChecked) {
                    for (button: BsToggleButton in buttonGroup)
                    {
                        if (button == thisButton) continue
                        button.isChecked = false
                    }
                }

            }
        })

    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        checkedOverlay.setPosition(actor.x, actor.y, Align.bottomLeft)
        super.draw(batch, parentAlpha)
    }


    override fun setChecked(isChecked: Boolean) {
        super.setChecked(isChecked)
    }


    companion object {
        val buttonGroups: HashMap<String, ArrayList<BsToggleButton>> = HashMap()

    }

}