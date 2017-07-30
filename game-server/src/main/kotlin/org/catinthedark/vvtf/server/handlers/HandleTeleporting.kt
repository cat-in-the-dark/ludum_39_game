package org.catinthedark.vvtf.server.handlers

import org.catinthedark.vvtf.shared.Const
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.messages.direction
import org.catinthedark.vvtf.shared.models.PlayerParams
import org.catinthedark.vvtf.shared.toSeconds

fun handleTeleporting(player: Player, params: PlayerParams, delta: Long) {
    if (!player.isTeleporting) return
    player.state = Const.PlayerState.running.name
    player.teleportingTime += delta

    if (player.teleportingTime >= params.teleportingTime) {
        player.isTeleporting = false
        player.state = Const.PlayerState.idle.name
        player.teleportingTime = 0
    } else {
        player.x += params.teleportSpeed * delta.toSeconds() * player.direction()
    }
}
