package org.catinthedark.vvtf.server.handlers

import org.catinthedark.vvtf.shared.Const
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.models.PlayerParams
import org.catinthedark.vvtf.shared.toSeconds

fun handleJumping(player: Player, params: PlayerParams, delta: Long) {
    if (player.state != Const.PlayerState.jumping.name) return
    player.jumpingTime += delta

    when {
        player.jumpingTime >= params.jumpTime -> {
            player.state = Const.PlayerState.idle.name
            player.jumpingTime = 0
            player.y = player.lastY
        }

        player.jumpingTime >= params.jumpTime / 2 -> {
            player.y -= params.jumpSpeed * delta.toSeconds()
            if (player.y <= player.lastY) player.y = player.lastY // TODO: detect collisions
        }

        else -> {
            player.y += params.jumpSpeed * delta.toSeconds()
        }
    }
}