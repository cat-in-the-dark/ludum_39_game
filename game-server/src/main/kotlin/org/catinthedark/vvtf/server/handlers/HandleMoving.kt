package org.catinthedark.vvtf.server.handlers

import org.catinthedark.vvtf.shared.Const
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.messages.isAttacking
import org.catinthedark.vvtf.shared.messages.isJumping
import org.catinthedark.vvtf.shared.messages.isMoving
import org.catinthedark.vvtf.shared.models.PlayerParams

fun handleMoving(player: Player, params: PlayerParams, delta: Long) {
    with(player) {
        if (x > lastX) {
            angle = 0f
        }
        if (x < lastX) {
            angle = 180f
        }
        if (!isJumping() && !isAttacking() && !isTeleporting) {
            if (isMoving()) {
                state = Const.PlayerState.walking.name
            } else {
                state = Const.PlayerState.idle.name
            }
        }
        lastX = x
    }
}
