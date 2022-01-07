package com.bm.shredsheets.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.bm.Scene
import com.bm.factories.Vec2Factory
import com.bm.shredsheets.ShredsheetsGame
import com.bm.shredsheets.ShredsheetsTextures
import com.bm.shredsheets.ShredsheetsUtilities

class ShredsheetsTitleScene : Scene() {
    override fun load(): Boolean {
        var stage = ShredsheetsUtilities.getStage(this)
        Gdx.app.input.inputProcessor = stage
        var titleImage = Image(TextureRegion(ShredsheetsTextures.titleScreenImage))
        titleImage.setPosition(100f, 100f)
        stage.addActor(titleImage)

        var playButton = ImageButton(TextureRegionDrawable(TextureRegion(ShredsheetsTextures.playButtonTexture)))
        var playScale = .75f
        playButton.setSize(playButton.width * playScale, playButton.height * playScale)
        var rightPadding = playButton.width * .25f

        stage.addActor(playButton)
        playButton.setPosition(stage.width - playButton.width - rightPadding, stage.height * .5f + rightPadding)

        playButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                println("Play Button Touch Up")
                ShredsheetsGame.Instance.setScene(ScaleScene())
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })


        var designButton = ImageButton(TextureRegionDrawable(TextureRegion(ShredsheetsTextures.designButtonTexture)))
        var designScale = .75f
        designButton.setSize(designButton.width * designScale, designButton.height * designScale)
        stage.addActor(designButton)
        designButton.setPosition(stage.width - designButton.width - rightPadding, stage.height * .5f - rightPadding - designButton.height)

        designButton.addListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                println("Design Button Touch Up")
                ShredsheetsGame.Instance.setScene(ScaleScene())
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }
        })
        return true
    }

    override fun draw(batch: Batch) {
        Gdx.gl.glClearColor(1f,1f,1f,1f)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)
        val stage = ShredsheetsUtilities.getStage(this)

        //ui
        stage.act()
        stage.draw()
    }

    override fun unload() {
        ShredsheetsUtilities.clearEverything()
    }


    private val screenPosition: Vector2
        get() {
            return Vec2Factory.get(32f, Gdx.graphics.height - 32f)
        }

}