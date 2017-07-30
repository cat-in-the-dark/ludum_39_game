package org.catinthedark.vvtf.shared.models

import org.catinthedark.vvtf.shared.toMillis

data class PlayerParams(
        val speedX: Float, // in pixels/sec
        val speedY: Float,
        val canJump: Boolean,
        val jumpSpeed: Float,
        val jumpTime: Long,
        val jumpCoolDown: Long,
        val attackCoolDown: Long
)

val playerParams = mapOf(
        "vampire" to PlayerParams(
                800f,
                0f,
                true,
                1200f,
                0.4f.toMillis(),
                0.6f.toMillis(),
                1f.toMillis()
        ),
        "peasant" to PlayerParams(
                600f,
                0f,
                false,
                0f,
                0f.toMillis(),
                100000f.toMillis(),
                1f.toMillis()
        )
)