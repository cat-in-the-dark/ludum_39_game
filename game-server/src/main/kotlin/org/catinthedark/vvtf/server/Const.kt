package org.catinthedark.vvtf.server

import org.catinthedark.shared.invokers.DeferrableInvoker
import org.catinthedark.shared.invokers.SimpleInvoker
import org.catinthedark.shared.invokers.TickInvoker
import java.net.SocketAddress

object Const {
    val invoker: DeferrableInvoker = SimpleInvoker()
    val gameInvoker: DeferrableInvoker = TickInvoker()

    val clients: MutableMap<String, SocketAddress> = mutableMapOf()
}