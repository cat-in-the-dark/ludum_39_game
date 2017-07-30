package org.catinthedark.vvtf.server.handlers

import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const
import org.catinthedark.vvtf.shared.Const.PlayerState
import org.catinthedark.vvtf.shared.messages.Jump
import org.catinthedark.vvtf.shared.models.playerParams
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("OnClientJumping")

@Handler
fun onClientJumping(ev: Jump, id: String) {
    val player = Const.players[id] ?: return
    val params = playerParams[player.type] ?: return
    if (!player.canJump) {
        log.info("$id can't jump: $player, $params")
        return
    }

    player.isJumping = true
    player.jumpingTime = 0
    player.canJump = false
    player.state = PlayerState.jumping.name
    player.lastY = player.y // TODO: remove when add collisions

    Const.gameInvoker.defer({
        player.isJumping = false
        player.canJump = true
        player.jumpingTime = 0
        log.info("Reset JUMP for $player")
    }, params.jumpCoolDown)
}