package org.catinthedark.vvtf.shared.models

import org.catinthedark.vvtf.shared.toMillis

data class PlayerParams(
        val speedX: Float, // in pixels/sec
        val speedY: Float,
        val canJump: Boolean,
        val jumpSpeed: Float,
        val jumpTime: Long,
        val jumpCoolDown: Long,
        val attackCoolDown: Long,
        val attackDistanceX: Float,
        val attackDistanceY: Float,
        val maxPower: Long,
        val attackPowerRatio: Float     // the ratio of attacker's power to be taken from the target per second
)

val playerParams = mapOf(
        "vampire" to PlayerParams(
                speedX = 800f,
                speedY = 0f,
                canJump = true,
                jumpSpeed = 1200f,
                jumpTime = 0.4f.toMillis(),
                jumpCoolDown = 0.6f.toMillis(),
                attackCoolDown = 1f.toMillis(),
                attackDistanceX = 64f,
                attackDistanceY = 64f,
                maxPower = 200,
                attackPowerRatio = 0.5f
        ),
        "peasant" to PlayerParams(
                speedX = 600f,
                speedY = 0f,
                canJump = false,
                jumpSpeed = 0f,
                jumpTime = 0f.toMillis(),
                jumpCoolDown = 100000f.toMillis(),
                attackCoolDown = 1f.toMillis(),
                attackDistanceX = 128f,
                attackDistanceY = 96f,
                maxPower = 100,
                attackPowerRatio = 0.5f
        )
)