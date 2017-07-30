package com.catinthedark.vvtf.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
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
            SoundPaths.values().forEach { load<Sound>(it.path) }
            MusicPaths.values().forEach { load<Music>(it.path) }
        }
    }

    fun pack(am: AssetManager): Pack {
        val vampire = PlayerSkin(am.get(TexturePaths.VAMPIRE))
        val peasant = PlayerSkin(am.get(TexturePaths.PEASANT))

        val vampireSound = SoundPack(
            walk = am.get(MusicPaths.WALK),
            dead = am.get(SoundPaths.GIRL_DEAD),
            run = am.get(SoundPaths.FAST_RUN),
            touched = am.get(SoundPaths.GIRL_TOUCHED)
        )
        val peasantSound = SoundPack(
            walk = am.get(MusicPaths.WALK),
            dead = am.get(SoundPaths.MAN_DEAD),
            run = am.get(SoundPaths.FAST_RUN),
            touched = am.get(SoundPaths.MAN_TOUCHED)
        )

        return Pack(
            am,
            mapOf(
                "vampire" to vampire,
                "peasant" to peasant
            ),
            mapOf(
                "vampire" to vampireSound,
                "peasant" to peasantSound
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

    enum class SoundPaths(val path: String) {
        FAST_RUN("sounds/fast_run.wav"),
        MAN_DEAD("sounds/man_dead.wav"),
        GIRL_DEAD("sounds/girl_dead.wav"),
        GIRL_TOUCHED("sounds/girl_hited.wav"),
        MAN_TOUCHED("sounds/man_hited.wav")
    }

    enum class MusicPaths(val path: String) {
        WALK("sounds/walk.wav")
    }

    data class SoundPack(
        val walk: Music,
        val run: Sound,
        val dead: Sound,
        val touched: Sound
    )

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
        val playerSkins: Map<String, PlayerSkin>,
        val playerSounds: Map<String, SoundPack>
    )
}
