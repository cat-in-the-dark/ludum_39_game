package com.catinthedark.vvtf.game.handlers

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.Const
import com.catinthedark.vvtf.game.Notification
import com.catinthedark.vvtf.game.State
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.vvtf.shared.messages.Kill
import org.catinthedark.vvtf.shared.toMillis

@Handler(preHandlerPath = "stage")
fun onKilled(ev: Kill, state: State, stage: Stage, pack: Assets.Pack) {
    // TODO: play sound
    val n = Notification("${ev.byName} KILL ${ev.victimName}")
    state.notifications.add(n)

    Const.tickInvoker.defer({
        state.notifications.remove(n)
    }, 6f.toMillis())
}
