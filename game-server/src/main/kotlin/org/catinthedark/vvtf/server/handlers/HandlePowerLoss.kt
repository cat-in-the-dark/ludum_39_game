package org.catinthedark.vvtf.server.handlers

import org.catinthedark.vvtf.shared.Const
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.models.PlayerParams
import org.catinthedark.vvtf.shared.toSeconds
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("handlePowerLoss")

fun handlePowerLoss(player: Player, params: PlayerParams, delta: Long) {
    val wasAlive = player.power > 0
    player.power -= params.powerLossInTime * delta.toSeconds()
    if (player.power <= 0) {
        player.power = 0f

    }

    if (player.power == 0f) {
        if (wasAlive) {
            log.info("'{}' dead beause of power loss", player.name)
        }
        player.state = Const.PlayerState.dead.name
    }
}
