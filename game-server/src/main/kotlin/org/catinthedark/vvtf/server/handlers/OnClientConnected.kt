package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.OnClientConnected
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const.clients
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onClientConnected")

@Handler
fun onClientConnected(ev: OnClientConnected) {
    log.info("$ev")
    val address = ev.remoteAddress ?: return
    clients.put(ev.id, address)
}