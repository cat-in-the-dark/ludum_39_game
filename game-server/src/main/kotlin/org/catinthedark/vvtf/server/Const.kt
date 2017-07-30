package org.catinthedark.vvtf.server

import org.catinthedark.shared.invokers.SimpleInvoker
import org.catinthedark.shared.invokers.TickInvoker
import org.catinthedark.vvtf.shared.messages.Player
import java.net.SocketAddress

object Const {
    val invoker = SimpleInvoker()
    val gameInvoker = TickInvoker()

    val clients: MutableMap<String, SocketAddress> = mutableMapOf()
    val players: MutableMap<String, Player> = mutableMapOf()

    var time: Long = 0

    object Network {
        val port = 8080
        val host = "0.0.0.0"
    }
}