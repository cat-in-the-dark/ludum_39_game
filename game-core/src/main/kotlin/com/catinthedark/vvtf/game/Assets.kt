package com.catinthedark.vvtf.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont

object Assets {
    fun load(): AssetManager {
        return AssetManager().apply {
            val textures = listOf(Names.LOGO, Names.TITLE, Names.PAIRING)

            load("fonts/tahoma-10.fnt", BitmapFont::class.java)
            textures.forEach { load(it, Texture::class.java) }
        }
    }

    object Names {
        val LOGO = "textures/logo.png"
        val TITLE = "textures/logo.png"
        val PAIRING = "textures/pairing.png"
    }
}