package com.catinthedark.vvtf.game.handlers

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.State
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.shared.messages.GameState
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("OnGameState")

@Handler(preHandlerPath = "stage")
fun onGameState(ev: GameState, state: State, stage: Stage, pack: Assets.Pack) {
    log.info("$ev")
    state.gameState = ev
}