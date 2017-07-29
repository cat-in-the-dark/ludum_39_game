package org.catinthedark.vvtf.server.handlers

import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const
import org.catinthedark.vvtf.shared.messages.Movement
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onClientMovement")

@Handler
fun onClientMovement(ev: Movement, id: String) {
    val player = Const.players[id] ?: return
    player.x += ev.speedX // TODO: validate speed
    player.y += ev.speedY
}