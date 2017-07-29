package com.catinthedark.vvtf.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.catinthedark.vvtf.game.VVTFGame

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true")

        LwjglApplication(VVTFGame(), LwjglApplicationConfiguration().apply {
            title = "Very Vampire Town Fighting"
            width = 1024
            height = 640
        })
    }
}
