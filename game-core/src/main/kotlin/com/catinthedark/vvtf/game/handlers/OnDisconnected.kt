package com.catinthedark.vvtf.game.handlers

import org.catinthedark.client.OnConnectionFailure
import org.catinthedark.client.OnDisconnected
import org.catinthedark.shared.event_bus.Handler
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("OnDisconnected")

@Handler
fun onDisconnected(ev: OnDisconnected) {
    log.info("$ev")
}

@Handler
fun onConnectionFailure(ev: OnConnectionFailure) {
    log.error("ConnectionFailed: $ev")
}