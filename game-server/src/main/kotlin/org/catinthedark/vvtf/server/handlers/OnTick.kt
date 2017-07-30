package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.TCPMessage
import org.catinthedark.shared.event_bus.EventBus
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const
import org.catinthedark.vvtf.server.messages.OnTick
import org.catinthedark.vvtf.shared.Const.PlayerState
import org.catinthedark.vvtf.shared.messages.GameState
import org.catinthedark.vvtf.shared.messages.Player
import org.catinthedark.vvtf.shared.models.PlayerParams
import org.catinthedark.vvtf.shared.models.playerParams
import org.catinthedark.vvtf.shared.toSeconds
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onTick")

@Handler
fun onTick(ev: OnTick) {
    Const.gameInvoker.run(ev.delta)
    Const.time += ev.delta
    Const.players.forEach { id, player ->
        val params = playerParams[player.type] ?: return@forEach
        handlePlayer(player, params, ev.delta)

        val gameState = GameState(
                me = player,
                players = Const.players.values - player,
                time = Const.time
        )
        EventBus.send("#onTick", Const.invoker, TCPMessage(
                gameState, id
        ))
    }
}

private fun handlePlayer(player: Player, params: PlayerParams, delta: Long) {
    handleJumping(player, params, delta)
    handleMoving(player, params, delta)
}