package org.catinthedark.vvtf.shared

import java.util.*

object Const {
    val random = Random()

    object Balance {

    }

    object Network {
        object Server {
            val tickRate = 20f
            val tickDelay = (1000f / tickRate).toLong() // in milliseconds
        }

        object Client {
            val tickRate = 40f
            val tickDelay = (1000f / tickRate).toLong() // in milliseconds
        }
    }

    enum class PlayerState {
        idle, walking, running, jumping, climbing, attack, dead
    }

}