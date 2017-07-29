package com.catinthedark.vvtf.game.handlers

import org.catinthedark.client.OnConnected
import org.catinthedark.shared.event_bus.Handler
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("OnConnected")

@Handler
fun onConnected(ev: OnConnected) {
    log.info("$ev")
}