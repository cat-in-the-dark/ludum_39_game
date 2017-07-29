package com.catinthedark.vvtf.game.handlers

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import org.catinthedark.shared.event_bus.Handler
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.vvtf.shared.messages.GameState
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("OnGameState")

@Handler(preHandlerPath = "stage")
fun onGameState(ev: GameState, stage: Stage, pack: Assets.Pack) {
    log.info("$ev")
    val p = pack.playerSkins[ev.me.type] ?: return
    stage.batch.managed {
        it.draw(p.texture, ev.me.x, ev.me.y)
    }
}