package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.shared.invokers.TickInvoker
import org.catinthedark.shared.libgdx.control.Control
import org.catinthedark.shared.route_machine.YieldUnit
import org.slf4j.LoggerFactory

class TestControlScreen(
        private val stage: Stage,
        private val tickInvoker: TickInvoker
) : YieldUnit<AssetManager, Unit> {
    private lateinit var am: AssetManager
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onActivate(data: AssetManager) {
        log.info("TestControl started")
        this.am = data
    }

    override fun run(delta: Float): Unit? {
        log.debug(Control.pressed().toString())
        return null
    }

    override fun onExit() {
    }

}