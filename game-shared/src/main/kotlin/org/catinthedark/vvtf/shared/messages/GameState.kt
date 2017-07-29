package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class GameState(
        val me: Player,
        val players: List<Player>,
        val time: Long
)