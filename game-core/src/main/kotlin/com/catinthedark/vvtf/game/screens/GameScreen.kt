package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.Const
import com.catinthedark.vvtf.game.State
import org.catinthedark.client.TCPMessage
import org.catinthedark.shared.event_bus.BusRegister
import org.catinthedark.shared.event_bus.EventBus
import org.catinthedark.shared.libgdx.control.Control
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit
import org.catinthedark.vvtf.shared.Const.Network.Client
import org.slf4j.LoggerFactory

class GameScreen(
        private val stage: Stage,
        private val hudStage: Stage
) : YieldUnit<Assets.Pack, Unit> {
    private lateinit var pack: Assets.Pack
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val state = State()

    override fun onActivate(data: Assets.Pack) {
        log.info("GameScreen started")
        BusRegister.registerPreHandler("stage", { _, message, _ ->
            return@registerPreHandler Pair(message, listOf(state, stage, pack))
        })
        pack = data

        Const.tickInvoker.periodic({
            EventBus.send("#onActivate.periodic", Const.tickInvoker, TCPMessage(state.currentMovement))
            state.currentMovement.speedX = 0f
            state.currentMovement.speedY = 0f
        }, Client.tickDelay)
    }

    override fun run(delta: Float): Unit? {
        stage.batch.managed {
            (state.gameState.players + state.gameState.me).forEach { p ->
                val t = pack.playerSkins[p.type] ?: return@forEach
                it.draw(t.texture, p.x, p.y)
            }
        }

        handleKeys()

        return null
    }

    override fun onExit() {
        BusRegister.unRegisterPreHandler("stage")
    }

    private fun handleKeys() {
        Control.onPressed({
            state.currentMovement.speedX += Const.playerParams[state.gameState.me.type]?.speedX ?: 0f
        }, Control.Button.RIGHT)

        Control.onPressed({
            state.currentMovement.speedX -= Const.playerParams[state.gameState.me.type]?.speedX ?: 0f
        }, Control.Button.LEFT)
    }
}

fun Control.onPressed(onTrue: () -> Unit, vararg buttons: Control.Button) {
    if (isPressed(*buttons)) onTrue()
}