package com.bm


import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bm.buildsite.BsUtilities
import kotlin.math.min

abstract class Application : ApplicationAdapter() {
    lateinit var batch: Batch
    lateinit var uiBatch: Batch

    private var accumulator = 0f

    init {
        Instance = this
    }

    override fun create() {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        batch = SpriteBatch()
        uiBatch = SpriteBatch()
    }

    override fun render() {
        val frameTime = min(Gdx.graphics.deltaTime, 0.1f)
        accumulator += frameTime

        while (accumulator >= TIME_STEP) {
            accumulator -= TIME_STEP

            batch!!.projectionMatrix = BsUtilities.camera.combined

            Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)

            var scene = getScene()

            batch!!.begin()
            scene.draw(batch!!)
            batch!!.end()
        }
    }

    open fun getScene(): Scene {
        if (!currentScene.loaded) currentScene.loaded = currentScene.load()
        return currentScene
    }

    fun setScene(scene: Scene) {
        println("Switching scenes.")
        if (currentScene != null) currentScene.unload()
        currentScene = scene
    }


    override fun dispose() {
        if (batch != null) batch!!.dispose()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        BsUtilities.resetCamera()
        //GameUtilities.get_stage().setViewport(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), GameUtilities.getCamera()));
    }

    companion object {
        const val TIME_STEP = 1 / 60f
        lateinit var Instance: Application
        var currentScene: Scene = Scene.BLANK
    }
}
