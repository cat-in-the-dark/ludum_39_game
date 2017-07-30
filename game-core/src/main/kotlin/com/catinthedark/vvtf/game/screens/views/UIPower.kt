package com.catinthedark.vvtf.game.screens.views

import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.State
import org.catinthedark.shared.route_machine.YieldUnit

class UIPower(
        private val hudStage: Stage,
        private val state: State
) : YieldUnit<Unit, Unit> {

    override fun onActivate(data: Unit) {

    }

    override fun run(delta: Float): Unit? {

        return null
    }

    override fun onExit() {

    }
}