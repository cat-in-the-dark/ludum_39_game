package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class Kill(
        val victimId: String,
        val aggressorId: String,
        val victimName: String,
        val aggressorName: String
)
