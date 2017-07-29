package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class Player(
        val id: String,
        val name: String,
        val type: String, // "peasant" or "vampire"
        var x: Float,
        var y: Float,
        var angle: Float,
        var state: String
)