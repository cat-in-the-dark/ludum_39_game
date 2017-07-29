package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.Const
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit

class PairingScreen(
        private val stage: Stage,
        private val host: String = "0.0.0.0",
        private val port: Int = 8080
) : YieldUnit<AssetManager, AssetManager> {
    private lateinit var am: AssetManager

    override fun onActivate(data: AssetManager) {
        am = data
        Const.Network.client.connect(host, port)
    }

    override fun run(delta: Float): AssetManager? {
        stage.batch.managed {
            it.draw(am.get(Assets.Names.PAIRING, Texture::class.java), 0f, 0f)
        }
        stage.draw()
        return null
    }

    override fun onExit() {

    }
}