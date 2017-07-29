package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit

class SplashScreen(
        private val stage: Stage
) : YieldUnit<Unit, Assets.Pack> {
    lateinit var am: AssetManager

    override fun onActivate(data: Unit) {
        am = Assets.load()
    }

    override fun run(delta: Float): Assets.Pack? {
        if (am.update()) {
            return Assets.pack(am)
        } else {
            Gdx.app.log("SplashScreen", "Loading assets...${am.progress}")
        }
        if (am.isLoaded(Assets.TexturePaths.LOGO.path, Texture::class.java)) {
            stage.batch.managed {
                it.draw(am.get(Assets.TexturePaths.LOGO.path, Texture::class.java), 0f, 0f)
            }
        }
        stage.draw()

        return null
    }

    override fun onExit() {

    }
}