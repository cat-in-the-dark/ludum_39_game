package com.catinthedark.vvtf.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont

object Assets {
    fun load(): AssetManager {
        return AssetManager().apply {
            load<BitmapFont>("fonts/tahoma-10.fnt")
            TexturePaths.values().forEach { load<Texture>(it.path) }
        }
    }

    fun pack(am: AssetManager): Pack {
        val vampire = PlayerSkin(am.get(TexturePaths.VAMPIRE.path, Texture::class.java))
        val peasant = PlayerSkin(am.get(TexturePaths.PEASANT.path, Texture::class.java))

        return Pack(
                am,
                mapOf(
                        "vampire" to vampire,
                        "peasant" to peasant
                )
        )
    }

    enum class TexturePaths(val path: String) {
        LOGO("textures/logo.png"),
        TITLE("textures/logo.png"),
        PAIRING("textures/pairing.png"),
        VAMPIRE("textures/vampire.png"),
        PEASANT("textures/peasant.png")
    }

    data class PlayerSkin(
            val texture: Texture
    )

    data class Pack(
            val am: AssetManager,
            val playerSkins: Map<String, PlayerSkin>
    )
}
