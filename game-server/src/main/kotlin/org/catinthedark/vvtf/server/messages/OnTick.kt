package org.catinthedark.vvtf.server.messages

import java.io.Serializable

data class OnTick(
        val delta: Long
): Serializable