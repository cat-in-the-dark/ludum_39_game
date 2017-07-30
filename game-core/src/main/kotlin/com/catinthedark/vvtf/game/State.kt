package com.catinthedark.vvtf.game

import org.catinthedark.vvtf.shared.messages.GameState
import org.catinthedark.vvtf.shared.messages.Movement

data class State(
    var gameState: GameState = GameState(),
    val currentMovement: Movement = Movement(),
    val notifications: MutableList<Notification> = mutableListOf()
)

data class Notification(
    val text: String
)
