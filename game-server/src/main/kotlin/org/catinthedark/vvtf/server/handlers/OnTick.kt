package org.catinthedark.vvtf.server.handlers

import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.server.messages.OnTick
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("onTick")

@Handler
fun onTick(ev: OnTick) {

}