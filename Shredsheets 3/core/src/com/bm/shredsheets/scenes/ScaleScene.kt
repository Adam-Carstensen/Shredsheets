package com.bm.shredsheets.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.bm.Scene
import com.bm.buildsite.BsUtilities
import com.bm.buildsite.controls.IDialogController
import com.bm.shredsheets.fretboard.FretboardRunnerView
import com.bm.shredsheets.fretboard.FretboardView
import com.bm.shredsheets.fretboard.MenuButton
import com.bm.shredsheets.fretboard.NotesAndIntervalsView
import com.bm.shredsheets.fretboard.SelectedKeyView
import com.bm.shredsheets.keyslider.KeySliderView
import com.bm.shredsheets.mainmenu.MenuDialog
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTable

class ScaleScene : Scene(), IDialogController {
    private lateinit var table: VisTable
    lateinit var stage: Stage

    var menuButtonDown = false;

    override fun load(): Boolean {
        VisUI.load(VisUI.SkinScale.X1)

        stage = BsUtilities.getStage(this)
        Gdx.input.inputProcessor = stage

        //stage.isDebugAll = true

        table = VisTable()
        //table.debug = true

        table.setSize(stage.width, stage.height)
        table.setPosition(0f, stage.height , Align.topLeft)

//        var mainMenuView = MainMenuView()
//        table.add(mainMenuView).width(stage.width).height(stage.height)

        table.row().grow()
        var keySliderView = KeySliderView()
        table.add(keySliderView).width(stage.width).height(stage.height * .1f).colspan(3)

        table.row()
        var fretboardRunnerView1 = FretboardRunnerView()
        table.add(fretboardRunnerView1).width(stage.width).height(stage.height * .05f).colspan(3)

        table.row()
        var fretboardView = FretboardView()
        table.add(fretboardView).width(stage.width).height(stage.height * .50f).colspan(3)

        table.row()
        var fretboardRunnerView2 = FretboardRunnerView()
        table.add(fretboardRunnerView2).width(stage.width).height(stage.height * .05f).colspan(3)

        table.row()
        var selectedKeyView = SelectedKeyView()
        table.add(selectedKeyView).width(stage.width * .45f).height(stage.height * .3f).colspan(1)

        var notesAndIntervalsView = NotesAndIntervalsView()
        table.add(notesAndIntervalsView).width(stage.width * .49f).height(stage.height * .3f).colspan(1)


        var menuButton = MenuButton()
        table.add(menuButton).width(stage.width * .06f).height(stage.height * .3f).colspan(1)

        var scaleScene = this

        menuButton.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                super.touchUp(event, x, y, pointer, button)
                if (menuButtonDown) {

                    println("Launching menu")

                    var menuDialog = MenuDialog(scaleScene)
                    scaleScene.showDialog(menuDialog)

                    menuButtonDown = false
                }
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                menuButtonDown = true
                return true
            }
        })

        stage.addActor(table)
        return true;
    }

    override fun draw(batch: Batch) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)

        //ui
        stage.act()
        stage.draw()
    }

    override fun unload() {
        BsUtilities.clearEverything()
    }


}