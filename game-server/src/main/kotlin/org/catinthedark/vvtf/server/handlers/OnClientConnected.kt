package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.OnClientConnected
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const.clients
import org.catinthedark.vvtf.server.Const.players
import org.catinthedark.vvtf.shared.messages.Peasant
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.messages.Vampire
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onClientConnected")

@Handler
fun onClientConnected(ev: OnClientConnected) {
    log.info("$ev")
    val address = ev.remoteAddress ?: return
    clients.put(ev.id, address)

    players.put(ev.id, createPlayer(ev.id))
}

fun createPlayer(id: String): Player {
    return if (players.isEmpty()) {
        Vampire(id)
    } else {
        Peasant(id)
    }
}