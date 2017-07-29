package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.shared.route_machine.YieldUnit
import org.slf4j.LoggerFactory

class GameScreen(
        private val stage: Stage,
        private val hudStage: Stage
): YieldUnit<AssetManager, Unit> {
    private lateinit var am: AssetManager
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onActivate(data: AssetManager) {
        log.info("GameScreen started")
        this.am = data
    }

    override fun run(delta: Float): Unit? {
        return null
    }

    override fun onExit() {

    }
}