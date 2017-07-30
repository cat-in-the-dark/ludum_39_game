package org.catinthedark.vvtf.server.handlers

import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const
import org.catinthedark.vvtf.shared.messages.Attack
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onClientAttack")

@Handler
fun onClientAttack(ev: Attack, id: String) {
    val player = Const.players[id] ?: return
    log.debug("Attack by {}", id)
    //TODO
}