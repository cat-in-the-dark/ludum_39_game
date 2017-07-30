package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.TCPMessage
import org.catinthedark.shared.event_bus.EventBus
import org.catinthedark.vvtf.shared.Const
import org.catinthedark.vvtf.server.Const.invoker
import org.catinthedark.vvtf.shared.messages.Touched
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.messages.direction
import org.catinthedark.vvtf.shared.models.PlayerParams
import org.catinthedark.vvtf.shared.toSeconds
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
            "vampire" -> vampireAttack(player, params, delta, target)
            "peasant" -> peasantAttack(player, params, delta, target)
        }
        if (target.power == 0f) {
            target.state = Const.PlayerState.dead.name
            log.info("'{}' dead", target.name)
        }
    }
}

private fun vampireAttack(vampire: Player, params: PlayerParams, delta: Long, victim: Player) {
    val powerTransfer = min(victim.power,
            vampire.power * params.attackPowerRatio * delta.toSeconds())
    vampire.power += powerTransfer
    victim.power -= powerTransfer
    log.info("Vampire '{}' bit '{}' for {}", vampire.name, victim.name, powerTransfer)
    EventBus.send("HandleAttack#vampireAttack", invoker, TCPMessage(Touched(
        victimId = victim.id,
        aggressorId = vampire.id
    )))
}

private fun peasantAttack(peasant: Player, params: PlayerParams, delta: Long, target: Player) {
    val powerTransfer = min(target.power,
            peasant.power * params.attackPowerRatio * delta.toSeconds())
    target.power -= powerTransfer
    log.info("Peasant '{}' pricked '{}' for {}", peasant.name, target.name, powerTransfer)
}

private fun findAttackTargets(player: Player, params: PlayerParams): List<Player> {
    val targets = mutableListOf<Player>()

    for ((id, otherPlayer) in org.catinthedark.vvtf.server.Const.players) {
        if (otherPlayer === player) {
            continue
        }

        val distanceX = (otherPlayer.x - player.x) * player.direction()
        val distanceY = (otherPlayer.y - player.y)
        if (distanceX >= 0 && distanceX <= params.attackDistanceX   // implicit min distance is 0
                && abs(distanceY) <= params.attackDistanceY) {
            targets.add(otherPlayer)
        }

    }

    return targets
}
