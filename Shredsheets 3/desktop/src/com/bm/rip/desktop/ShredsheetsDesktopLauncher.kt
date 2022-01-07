package com.bm.rip.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.bm.shredsheets.ShredsheetsGame
import java.awt.GraphicsEnvironment
import kotlin.math.roundToInt

object ShredsheetsDesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Shredsheets 3"
        config.useGL30 = true
        config.resizable = true
        config.samples = 3
        config.vSyncEnabled = true
        config.fullscreen = false

        //Hard coded to run on the primary monitor.  Multi monitor support would require an option to switch screenDevices
        var primaryMonitor = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices[0];
        var screenWidth = primaryMonitor.displayMode.width
        var screenHeight = primaryMonitor.displayMode.height

        if (config.fullscreen) {
            config.width = screenWidth
            config.height = screenHeight
        } else {
            config.width = 1920
            config.height = 1080

            config.x = ((screenWidth * .5f)  - (config.width * .5f)).roundToInt()
            config.y = ((screenHeight * .5f)  - (config.height * .5f)).roundToInt()
        }

        LwjglApplication(ShredsheetsGame(), config)
    }
}
