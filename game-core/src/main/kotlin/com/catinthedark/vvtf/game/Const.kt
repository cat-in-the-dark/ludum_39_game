package com.catinthedark.vvtf.game

import com.badlogic.gdx.math.Vector2
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

    object UI {
        val animationSpeed = 0.2f

        val powerBarPos = Vector2(78f, 600f)
        val powerBarWh = Vector2(252f, 25f)

        val timerPos = Vector2(450f, 600f)
        val notificationsPos = Vector2(650f, 600f)
    }
}
