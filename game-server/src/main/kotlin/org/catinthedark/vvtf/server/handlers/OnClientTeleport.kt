package org.catinthedark.vvtf.server.handlers

import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const
import org.catinthedark.vvtf.shared.messages.Teleport
import org.catinthedark.vvtf.shared.models.playerParams
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onClientTeleport")

@Handler
fun onClientTeleport(ev: Teleport, id: String) {
    val player = Const.players[id] ?: return
    val params = playerParams[player.type] ?: return
    if (!player.canTeleport) return

    player.isTeleporting = true
    player.teleportingTime = 0
    player.canTeleport = false
    player.state = org.catinthedark.vvtf.shared.Const.PlayerState.running.name

    Const.gameInvoker.defer({
        player.isTeleporting = false
        player.canTeleport = true
        player.teleportingTime = 0
        log.info("Reset TELEPORT for $player")
    }, params.teleportCoolDown)
}
