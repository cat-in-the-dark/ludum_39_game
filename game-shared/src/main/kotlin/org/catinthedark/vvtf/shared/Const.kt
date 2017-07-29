package org.catinthedark.vvtf.shared

object Const {
    object Balance {

    }

    object Network {
        object Server {
            val tickRate = 20f
            val tickDelay = (1000f / tickRate).toLong() // in milliseconds
        }

        object Client {
            val tickRate = 10f
            val tickDelay = (1000f / tickRate).toLong() // in milliseconds
        }
    }
}