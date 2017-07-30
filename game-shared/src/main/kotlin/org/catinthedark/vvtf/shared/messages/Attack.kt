package org.catinthedark.vvtf.shared.messages

import org.catinthedark.shared.serialization.Message

@Message
data class Attack(
        var type: String = "default"
)