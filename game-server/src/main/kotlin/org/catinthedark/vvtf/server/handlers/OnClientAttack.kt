package org.catinthedark.vvtf.server.handlers

import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const
import org.catinthedark.vvtf.shared.Const.PlayerState
import org.catinthedark.vvtf.shared.messages.Attack
import org.catinthedark.vvtf.shared.models.playerParams
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onClientAttack")

@Handler
fun onClientAttack(ev: Attack, id: String) {
    val player = Const.players[id] ?: return
    val params = playerParams[player.type] ?: return
    if (!player.canAttack) return

    player.canAttack = false
    player.isAttacking = true
    player.state = PlayerState.attack.name

    Const.gameInvoker.defer({
        player.canAttack = true
        player.isAttacking = false
        player.state = PlayerState.idle.name
        log.info("Reset ATTACK for $player")
    }, params.attackCoolDown)
}