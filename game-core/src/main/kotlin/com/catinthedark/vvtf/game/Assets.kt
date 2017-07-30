package com.catinthedark.vvtf.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.catinthedark.shared.libgdx.AnimationUtils
import org.catinthedark.vvtf.shared.Const.PlayerState

object Assets {
    fun load(): AssetManager {
        return AssetManager().apply {
            load<BitmapFont>("fonts/tahoma-10.fnt")
            TexturePaths.values().forEach { load<Texture>(it.path) }
        }
    }

    fun pack(am: AssetManager): Pack {
        val vampire = PlayerSkin(am.get(TexturePaths.VAMPIRE))
        val peasant = PlayerSkin(am.get(TexturePaths.PEASANT))

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
        PEASANT("textures/peasant.png"),
        DEMO("textures/demo_cast.png"),
        WHITE("textures/white.png"),
        BACKGROUND1("textures/fon1.jpg")
    }

    data class PlayerSkin(
            val texture: Texture
    ) {
        private val frames = TextureRegion.split(texture, 128, 128)
        private val idle = AnimationUtils.loopingAnimation(Const.UI.animationSpeed, frames,
                0 to 0, 0 to 1, 0 to 2)
        private val walking = AnimationUtils.loopingAnimation(Const.UI.animationSpeed, frames,
                1 to 0, 1 to 1, 1 to 2, 1 to 3, 1 to 4)
        private val jumping = AnimationUtils.loopingAnimation(Const.UI.animationSpeed, frames,
                2 to 0, 2 to 1, 2 to 2, 2 to 3, 2 to 4)
        private val attack = AnimationUtils.loopingAnimation(Const.UI.animationSpeed, frames,
                3 to 0, 3 to 1, 3 to 2, 3 to 3, 3 to 4)

        private var animationCounter = 0f

        fun texture(state: String, delta: Float): TextureRegion {
            animationCounter += delta
            val frames = when (PlayerState.valueOf(state)) {
                PlayerState.idle -> idle
                PlayerState.walking -> walking
                PlayerState.jumping -> jumping
                PlayerState.attack -> attack
                else -> idle
            }
            return frames.getKeyFrame(animationCounter)
        }
    }

    data class Pack(
            val am: AssetManager,
            val playerSkins: Map<String, PlayerSkin>
    )
}
