package com.bm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.Batch
import com.bm.buildsite.BsUtilities
import com.bm.buildsite.controls.IDialogController
import com.bm.dialogs.ModalDialogView
import java.util.*

abstract class Scene : IDialogController {
    var loaded = false

    /**
     * Overriding methods responsible for setting the loaded field appropriately
     */
    abstract fun load(): Boolean
    abstract fun draw(batch: Batch)
    abstract fun unload()

    open fun getInputProcessor(): InputProcessor {
        return BsUtilities.getStage(this)
    }

    var dialogs = Stack<ModalDialogView>()
    override var activeDialogs: Stack<ModalDialogView>
        get() = dialogs
        set(value) {
            dialogs = value
        }


    override fun closeAllDialogs() {
        for (dialog in dialogs)
            dialog.remove()
        dialogs.clear()
    }

    override fun closeDialog() {
        closeDialog(activeDialogs.peek())
    }

    override fun closeDialog(dialog: ModalDialogView) {
        activeDialogs.remove(dialog)
        dialog.remove()
    }


    override fun showDialog(dialog: ModalDialogView) {
        activeDialogs.add(dialog)
        dialogs.add(dialog)


        BsUtilities.getStage(this).addActor(dialog)
    }


    companion object {
        var BLANK = BlankScene()
    }
}

class BlankScene : Scene() {
    override fun load(): Boolean {
        return true
    }

    override fun draw(batch: Batch) {
        Gdx.gl.glClearColor(0f,0f,0f,1f)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)
        val stage = BsUtilities.getStage(this)

        //ui
        stage.act()
        stage.draw()
    }

    override fun unload() {
    }



}