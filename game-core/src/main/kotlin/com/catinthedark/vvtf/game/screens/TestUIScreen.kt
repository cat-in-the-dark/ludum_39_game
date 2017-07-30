package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.Const
import com.catinthedark.vvtf.game.Notification
import com.catinthedark.vvtf.game.State
import com.catinthedark.vvtf.game.handlers.onKilled
import com.catinthedark.vvtf.game.screens.views.UINotifications
import com.catinthedark.vvtf.game.screens.views.UIPower
import com.catinthedark.vvtf.game.screens.views.UITime
import org.catinthedark.shared.route_machine.YieldUnit
import org.catinthedark.vvtf.shared.messages.GameState
import org.catinthedark.vvtf.shared.messages.Kill
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
    private val ui = listOf(UITime(hudStage, state), UIPower(hudStage, state), UINotifications(hudStage, state))

    private var time: Long = 0

    override fun onActivate(data: Assets.Pack) {
        log.info("TestIUScreen started")
        pack = data
        state.gameState = GameState(Player(type = "vampire"), emptyList(), time)
        ui.forEach { it.onActivate(Unit) }

        var i = 0
        Const.tickInvoker.periodic({
            i += 1
            onKilled(Kill("", "$i - Vampire", "Peasant"), state, hudStage, pack)
        }, 2f.toMillis())
    }

    override fun run(delta: Float): Unit? {
        time += delta.toMillis()
        state.gameState.me.power -= 1
        if (state.gameState.me.power <= 0) state.gameState.me.power = 100f


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
