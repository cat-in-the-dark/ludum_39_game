package com.catinthedark.vvtf.game.handlers

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.State
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.shared.messages.Touched

@Handler(preHandlerPath = "stage")
fun onTouched(ev: Touched, state: State, stage: Stage, pack: Assets.Pack) {
    val victim = if (state.gameState.me.id == ev.victimId) {
        state.gameState.me
    } else {
        state.gameState.players.firstOrNull { it.id == ev.victimId }
    } ?: return

    pack.playerSounds[victim.type]?.touched?.play()
}
