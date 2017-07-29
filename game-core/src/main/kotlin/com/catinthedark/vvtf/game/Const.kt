package com.catinthedark.vvtf.game

import org.catinthedark.client.TCPClient
import org.catinthedark.shared.invokers.SimpleInvoker
import org.catinthedark.shared.invokers.TickInvoker
import org.catinthedark.shared.serialization.KryoCustomizer

object Const {
    val tickInvoker = TickInvoker() // UI executor
    val threadInvoker = SimpleInvoker() // async executor

    object Screen {
        val WIDTH = 1024
        val HEIGHT = 640
        val ZOOM = 1f
    }

    object Network {
        val kryo = KryoCustomizer.buildAndRegister("org.catinthedark.vvtf.shared.messages")
        val client = TCPClient(kryo, tickInvoker)
    }
}