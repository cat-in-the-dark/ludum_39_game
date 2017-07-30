package org.catinthedark.vvtf.server.handlers

import org.catinthedark.vvtf.shared.Const
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.models.PlayerParams
import org.slf4j.LoggerFactory
import java.lang.Math.abs
import java.lang.Math.min

private val log = LoggerFactory.getLogger("handleAttack")

fun handleAttack(player: Player, params: PlayerParams, delta: Long) {
    if (!player.isAttacking) return

    player.state = Const.PlayerState.attack.name

    val targets = findAttackTargets(player, params)

    for (target in targets) {
        target.state = Const.PlayerState.underAttack.name
        when(player.type) {
            "vampire" -> vampireAttack(player, target)
            "peasant" -> peasantAttack(player, target)
        }
        if (target.power == 0L) {
            target.state = Const.PlayerState.dead.name
            log.info("{} dead", target.name)
        }
    }
}

fun vampireAttack(vampire: Player, victim: Player) {
    val powerTransfer = min(victim.power, vampire.power / 2)    // TODO?
    vampire.power += powerTransfer
    victim.power -= powerTransfer
    log.info("Vampire {} bit {} for {}", vampire.name, victim.name, powerTransfer)
}

fun peasantAttack(peasant: Player, target: Player) {
    val powerTransfer = min(target.power, peasant.power)        // TODO?
    target.power -= powerTransfer
    log.info("Peasant {} pricked {} for {}", peasant.name, target.name, powerTransfer)
}

fun findAttackTargets(player: Player, params: PlayerParams): List<Player> {
    val targets = mutableListOf<Player>()

    for ((id, otherPlayer) in org.catinthedark.vvtf.server.Const.players) {
        if (otherPlayer === player) {
            continue
        }

        val directionSign = when(player.angle) {
            0f -> +1
            180f -> -1
            else -> 0
        }

        val distanceX = (otherPlayer.x - player.x) * directionSign
        val distanceY = (otherPlayer.y - player.y)
        if (distanceX >= 0 && distanceX <= params.attackDistanceX
                && abs(distanceY) <= params.attackDistanceY) {
            targets.add(otherPlayer)
        }

    }

    return targets
}