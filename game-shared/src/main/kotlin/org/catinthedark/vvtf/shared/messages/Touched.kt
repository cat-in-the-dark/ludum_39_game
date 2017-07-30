package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class Touched(
    val victimId: String,
    val aggressorId: String,
    val type: String = "default"
)
