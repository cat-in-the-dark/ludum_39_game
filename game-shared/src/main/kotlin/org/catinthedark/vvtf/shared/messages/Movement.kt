package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message
import org.catinthedark.vvtf.shared.Const

@Message
data class Movement(
        var deltaX: Float = 0f,
        var deltaY: Float = 0f,
        var angle: Float = 0f,
        var state: String = Const.PlayerState.idle.name
)