package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class Movement(
        var speedX: Float = 0f,
        var speedY: Float = 0f,
        var angle: Float = 0f,
        var state: String = "idle"
)