package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.State
import com.catinthedark.vvtf.game.screens.views.UIPower
import com.catinthedark.vvtf.game.screens.views.UITime
import org.catinthedark.shared.route_machine.YieldUnit
import org.catinthedark.vvtf.shared.messages.GameState
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.toMillis
import org.slf4j.LoggerFactory

class TestUIScreen(
        private val stage: Stage,
        private val hudStage: Stage
) : YieldUnit<Assets.Pack, Unit> {
    private lateinit var pack: Assets.Pack
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val state = State()
    private val ui = listOf(UITime(hudStage, state), UIPower(hudStage, state))

    private var time: Long = 0

    override fun onActivate(data: Assets.Pack) {
        log.info("TestIUScreen started")
        pack = data
        ui.forEach { it.onActivate(Unit) }
    }

    override fun run(delta: Float): Unit? {
        time += delta.toMillis()
        state.gameState = GameState(Player(), emptyList(), time)


        ui.forEach { it.run(delta) }

        handleKeys(delta)

        return null
    }

    override fun onExit() {
        ui.forEach { it.onExit() }
    }

    private fun handleKeys(delta: Float) {
    }
}