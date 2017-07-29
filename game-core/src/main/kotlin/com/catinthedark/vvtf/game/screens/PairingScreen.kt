package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.Const
import org.catinthedark.client.OnConnected
import org.catinthedark.shared.event_bus.BusRegister
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit

class PairingScreen(
        private val stage: Stage,
        private val host: String = "0.0.0.0",
        private val port: Int = 8080
) : YieldUnit<Assets.Pack, Assets.Pack> {
    private lateinit var pack: Assets.Pack
    private var connected = false

    override fun onActivate(data: Assets.Pack) {
        BusRegister.register(this)
        pack = data
        Const.Network.client.connect(host, port)
    }

    override fun run(delta: Float): Assets.Pack? {
        if (connected) return pack

        stage.batch.managed {
            it.draw(pack.am.get(Assets.TexturePaths.PAIRING.path, Texture::class.java), 0f, 0f)
        }
        stage.draw()
        return null
    }

    override fun onExit() {
        BusRegister.unregister(this)
        connected = false
    }

    @Handler
    fun onConnected(ev: OnConnected) {
        connected = true
    }
}