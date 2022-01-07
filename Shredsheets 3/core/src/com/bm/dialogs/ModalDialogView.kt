package com.bm.dialogs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.bm.UserInterface
import com.bm.buildsite.controls.IDialogController
import com.bm.resources.Textures
import java.util.*

open class ModalDialogView(var title: String, var innerActor: Actor, var controller: IDialogController, var innerWidgetPadding: Float = 16f, var backgroundOpacity: Float = 0.5f) : Table() {
    //TODO: ModalDialogView

    private var closeButton: TextButton

    var innerContainer = Table()

    init {
        dialogStack.push(this)
        background = Textures.getColorDrawable(Color(0f, 0f, 0f, backgroundOpacity))
        innerContainer.background = Textures.getColorDrawable(Color.WHITE)

        innerContainer.addActor(innerActor)

        innerActor.setPosition(innerWidgetPadding, innerWidgetPadding, Align.bottomLeft)

        var closeButtonStyle = UserInterface.getCloseModalButtonStyle()

        closeButton = TextButton("X", closeButtonStyle)

        //closeButton.setPosition((Gdx.graphics.width - innerWidgetPadding).toFloat(), (Gdx.graphics.height - innerWidgetPadding).toFloat(), Align.topRight)


        var dialog = this

        closeButton.touchable = Touchable.enabled

        closeButton.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                controller.closeDialog(dialog)
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

        touchable = Touchable.enabled

        addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })

        addActor(closeButton)

        addActor(innerContainer)

        innerContainer.setPosition(0f,0f, Align.center)

        innerContainer.setSize(innerActor.width + innerWidgetPadding * 2, innerActor.height + innerWidgetPadding * 2)
        centerInnerActor()

    }

    override fun act(delta: Float) {
        super.act(delta)
        centerInnerActor()

        setPosition(0f, 0f)
        setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        innerContainer.setSize(innerActor.width + innerWidgetPadding * 2, innerActor.height + innerWidgetPadding * 2)


        var midX = Gdx.graphics.width / 2f
        var midY = Gdx.graphics.height / 2f

        closeButton.setPosition(midX + innerContainer.width / 2f, midY + innerContainer.height / 2f)
    }

    private fun centerInnerActor() {
        var innerWidth = innerActor.width + innerWidgetPadding * 2;
        var innerHeight = innerActor.height + innerWidgetPadding * 2;

        innerContainer.setPosition((Gdx.graphics.width / 2f) - (innerWidth / 2f), (Gdx.graphics.height / 2f) - (innerHeight / 2f))
    }

    companion object {
        var dialogStack: Stack<ModalDialogView> = Stack()
    }

}