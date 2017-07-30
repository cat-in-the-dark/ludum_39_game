package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message
import org.catinthedark.vvtf.shared.Const
import org.catinthedark.vvtf.shared.Const.random

@Message
data class Player(
        val id: String = "",
        val name: String = "",
        val type: String = "ghost", // "peasant" or "vampire"
        var x: Float = 0f,
        var y: Float = 0f,
        var lastX: Float = 0f,
        var lastY: Float = 0f,
        var angle: Float = 0f,
        var state: String = Const.PlayerState.idle.name,
        var jumpingTime: Long = 0,
        var canJump: Boolean = false,
        var isJumping: Boolean = false,
        var canAttack: Boolean = false,
        var isAttacking: Boolean = false,
        var power: Float = 100f
)

fun Player.isJumping(): Boolean {
    return isJumping
}

fun Player.isAttacking(): Boolean {
    return state == Const.PlayerState.attack.name
}

fun Player.isMoving(): Boolean {
    return x != lastX
}

fun Vampire(id: String) = Player(
        id = id,
        angle = 0f,
        name = getVampireName(),
        type = "vampire",
        state = Const.PlayerState.idle.name,
        x = 0f,
        y = 0f,
        canJump = true,
        jumpingTime = 0,
        canAttack = true,
        power = 200f
)

fun Peasant(id: String) = Player(
        id = id,
        angle = 0f,
        name = getPeasantName(),
        type = "peasant",
        state = Const.PlayerState.idle.name,
        x = 0f,
        y = 0f,
        canJump = false,
        jumpingTime = 0,
        canAttack = true,
        power = 100f
)


fun getVampireName(): String {
    val names = listOf("Herma", "Alvira", "Kiara", "Sarah", "Magena", "Aurora", "Hilda", "Kolfinna", "Lucilla", "Abigale")
    return names[random.nextInt(names.size)]
}

fun getPeasantName(): String {
    val names = listOf("Maryell Konnight", "Seiua Hamoncourt", "Athelyna Broccoli", "Gaztain Zabarte", "Gregoria Cros")
    return names[random.nextInt(names.size)]
}