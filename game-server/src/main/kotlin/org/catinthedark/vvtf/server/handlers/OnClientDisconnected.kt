package org.catinthedark.vvtf.server.handlers

import org.catinthedark.server.OnClientDisconnected
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.Const.clients
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onClientDisconnected")

@Handler
fun onClientDisconnected(ev: OnClientDisconnected) {
    log.info("$ev")
    clients.remove(ev.id)
}