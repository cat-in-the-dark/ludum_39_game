package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message
import org.catinthedark.vvtf.shared.Const

@Message
data class Player(
        val id: String = "",
        val name: String = "",
        val type: String = "ghost", // "peasant" or "vampire"
        var x: Float = 0f,
        var y: Float = 0f,
        var angle: Float = 0f,
        var state: String = Const.PlayerState.idle.name
)