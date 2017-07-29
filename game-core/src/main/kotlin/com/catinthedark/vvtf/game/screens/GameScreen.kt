package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import org.catinthedark.shared.event_bus.BusRegister
import org.catinthedark.shared.route_machine.YieldUnit
import org.slf4j.LoggerFactory

class GameScreen(
        private val stage: Stage,
        private val hudStage: Stage
) : YieldUnit<Assets.Pack, Unit> {
    private lateinit var pack: Assets.Pack
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onActivate(data: Assets.Pack) {
        log.info("GameScreen started")
        BusRegister.registerPreHandler("stage", { _, message, _ ->
            return@registerPreHandler Pair(message, listOf(stage, pack))
        })
        this.pack = data
    }

    override fun run(delta: Float): Unit? {
        return null
    }

    override fun onExit() {
        BusRegister.unRegisterPreHandler("stage")
    }
}