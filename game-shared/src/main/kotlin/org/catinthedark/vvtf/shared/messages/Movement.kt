package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class Movement(
        val speedX: Float,
        val speedY: Float,
        val angle: Float,
        val state: String
)