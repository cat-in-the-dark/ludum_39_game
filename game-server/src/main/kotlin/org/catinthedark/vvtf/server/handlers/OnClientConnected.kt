package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.OnClientConnected
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const.clients
import org.catinthedark.vvtf.server.Const.players
import org.catinthedark.vvtf.shared.messages.Player
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
        Player(
                id = id,
                angle = 0f,
                name = getVampireName(),
                type = "vampire",
                state = "idle",
                x = 0f,
                y = 0f
        )
    } else {
        Player(
                id = id,
                angle = 0f,
                name = getPeasantName(),
                type = "peasant",
                state = "idle",
                x = 0f,
                y = 0f
        )
    }
}

fun getVampireName(): String {
    return "Vampire"
}

fun getPeasantName(): String {
    return "Peasant"
}