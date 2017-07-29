package org.catinthedark.vvtf.server

import org.catinthedark.server.TCPServer
import org.catinthedark.shared.event_bus.BusRegister
import org.catinthedark.shared.serialization.KryoCustomizer
import org.catinthedark.vvtf.server.Const.invoker

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            BusRegister.register("org.catinthedark.vvtf.server.handlers")
            val kryo = KryoCustomizer.buildAndRegister("org.catinthedark.vvtf.shared.messages")
            val server = TCPServer(kryo, Const.Network.host, Const.Network.port, invoker)
            server.run() // it locks thread. Do not place code after it
            // Nothing to do here!!!
        }
    }
}