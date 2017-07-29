package com.catinthedark.vvtf.game.handlers

import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.shared.messages.GameState
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("OnGameState")

@Handler
fun onGameState(ev: GameState) {
    log.info("$ev")
}