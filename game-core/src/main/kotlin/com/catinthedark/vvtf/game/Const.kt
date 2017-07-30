package com.catinthedark.vvtf.game

import org.catinthedark.client.TCPClient
import org.catinthedark.shared.invokers.TickInvoker
import org.catinthedark.shared.serialization.KryoCustomizer

object Const {
    val tickInvoker = TickInvoker() // UI executor

    object Screen {
        val WIDTH = 1024
        val HEIGHT = 640
        val ZOOM = 1f
    }

    object Network {
        val kryo = KryoCustomizer.buildAndRegister("org.catinthedark.vvtf.shared.messages")
        val client = TCPClient(kryo, tickInvoker)
    }

    data class PlayerParams(
            val speedX: Float,      // in pixels/sec
            val speedY: Float
    )

    val playerParams = mapOf(
            "vampire" to PlayerParams(800f, 800f),
            "peasant" to PlayerParams(600f, 600f)
    )

    object UI {
        val animationSpeed = 0.2f
    }

}