package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class GameState(
        val me: Player = Player(),
        val players: List<Player> = emptyList(),
        val time: Long = 0L
)