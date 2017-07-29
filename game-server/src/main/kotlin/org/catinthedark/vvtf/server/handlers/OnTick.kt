package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.TCPMessage
import org.catinthedark.shared.event_bus.EventBus
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const
import org.catinthedark.vvtf.server.messages.OnTick
import org.catinthedark.vvtf.shared.messages.GameState
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onTick")

@Handler
fun onTick(ev: OnTick) {
    Const.gameInvoker.run(ev.delta)
    Const.time += ev.delta
    Const.players.forEach { id, player ->
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