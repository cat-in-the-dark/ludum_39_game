package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit

class TitleScreen(
        private val stage: Stage
) : YieldUnit<Assets.Pack, Assets.Pack> {
    private lateinit var pack: Assets.Pack

    private var time = 0f

    override fun onActivate(data: Assets.Pack) {
        pack = data
        time = 0f
    }

    override fun run(delta: Float): Assets.Pack? {
        stage.batch.managed {
            it.draw(pack.am.get(Assets.TexturePaths.TITLE.path, Texture::class.java), 0f, 0f)
        }
        stage.draw()
        time += delta
        if (time > 0.5) return pack
        return null
    }

    override fun onExit() {

    }
}