package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class Kill(
        val id: String,
        val byName: String,
        val victimName: String
)
