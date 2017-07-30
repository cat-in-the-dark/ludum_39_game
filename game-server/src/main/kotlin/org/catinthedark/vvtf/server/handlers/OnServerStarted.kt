package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.ServerStarted
import org.catinthedark.shared.event_bus.EventBus
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const.invoker
import org.catinthedark.vvtf.server.messages.OnTick
import org.catinthedark.vvtf.shared.Const.Network.Server.tickDelay
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onServerStarted")

@Handler
fun onServerStarted(ev: ServerStarted) {
    log.info("Server started: $ev")

    var lastTick = System.currentTimeMillis()
    invoker.periodic({
        val currentTime = System.currentTimeMillis()
        EventBus.send("#serverTick", invoker, OnTick(currentTime - lastTick))
        lastTick = currentTime
    }, tickDelay)
}