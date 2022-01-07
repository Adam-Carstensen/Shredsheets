package com.bm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.bm.buildsite.BsUtilities
import com.kotcrab.vis.ui.VisUI
import com.bm.maths.extensions.clamp
import com.bm.factories.Vec2Factory

abstract class GameUtilities {
    init {
    }

    var worldItem: World = World(Vec2Factory.get(0f, getGameGravity()), true)
    open fun getWorld(): World {
        return worldItem
    }

    open fun getGameGravity(): Float = 0f

    var camera: OrthographicCamera = createCamera(0f, 0f)
    val bottomLeft = Vec2Factory.get()
    val cameraBottomLeft: Vector2
        get() {
            val leftExtreme = camera.position.x - camera.viewportWidth * camera.zoom / 2f
            val bottomExtreme = camera.position.y - camera.viewportHeight * camera.zoom / 2f
            return bottomLeft.set(leftExtreme, bottomExtreme)
        }

    fun resetCamera(): OrthographicCamera {
        //this.camera = createCamera(camera.position.x, camera.position.y);
        camera.viewportWidth = Gdx.graphics.width.toFloat()
        camera.viewportHeight = Gdx.graphics.height.toFloat()

        camera.update(true)

        return camera
    }

    //private var contactListener: ContactListener = getContactListener()
    //abstract fun getContactListener(): ContactListener

    protected var stageItem: Stage? = null

    fun getStage(scene: Scene): Stage {
        if (stageItem == null) setStage(scene, Stage(ExtendViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera)))
        return stageItem!!
    }

    fun setStage(scene: Scene, stage: Stage) {
        this.stageItem = stage
        Gdx.input.inputProcessor = scene.getInputProcessor()
    }

    open fun clearEverything() {
        if (stageItem != null) {
            if (VisUI.isLoaded()) VisUI.dispose()
            stageItem!!.dispose()
            stageItem = null
        }
    }

    @JvmOverloads
    fun createCamera(x: Float, y: Float, width: Float = Gdx.graphics.width.toFloat(), height: Float = Gdx.graphics.height.toFloat()): OrthographicCamera {
        val camera = OrthographicCamera(width, height)
        camera.position.set(x, y, 0f)
        camera.update()
        return camera
    }

    protected fun setCameraCenter(camera: OrthographicCamera, x: Float, y: Float, z: Float, levelWidth: Float, levelHeight: Float) {
        val cameraWidth = (camera.viewportWidth * camera.zoom)
        val halfCameraWidth = cameraWidth / 2

        val cameraHeight = (camera.viewportHeight * camera.zoom)
        val halfCameraHeight = cameraHeight / 2

        val levelOverlap = levelHeight - cameraHeight

        val minX = halfCameraWidth
        val maxX = levelWidth - halfCameraWidth

        val minY = halfCameraHeight

        val maxY = minY + levelOverlap

        if (maxY < minY) {
            return
        }

        val clampedX = x.clamp(minX, maxX)
        val clampedY = y.clamp(minY, maxY)

        camera.position.set(clampedX, clampedY, z)
        camera.update()
    }
}

