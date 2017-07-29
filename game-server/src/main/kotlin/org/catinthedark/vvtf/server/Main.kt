package org.catinthedark.vvtf.server

import org.catinthedark.server.TCPServer
import org.catinthedark.shared.serialization.KryoCustomizer

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val kryo = KryoCustomizer.buildAndRegister("org.catinthedark.vvtf.shared.messages")
            val server = TCPServer(kryo, "0.0.0.0", 8080, Consts.invoker)

            server.run()
        }
    }
}