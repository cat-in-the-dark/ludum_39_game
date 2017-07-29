package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.shared.libgdx.control.Control
import org.catinthedark.shared.route_machine.YieldUnit
import org.slf4j.LoggerFactory

class TestControlScreen(
        private val stage: Stage
) : YieldUnit<AssetManager, Unit> {
    private lateinit var am: AssetManager
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onActivate(data: AssetManager) {
        log.info("TestControl started")
        this.am = data
    }

    override fun run(delta: Float): Unit? {
        log.debug(Control.pressed().toString())
        if (Control.isPressed(Control.Button.UP)) log.debug("UP")
        if (Control.isPressed(Control.Button.UP, Control.Button.RIGHT)) log.debug("UP-RIGHT")
        if (Control.isPressed(Control.Button.UP, Control.Button.BUTTON0)) log.debug("UP-BUTTON0")
        return null
    }

    override fun onExit() {
    }

}