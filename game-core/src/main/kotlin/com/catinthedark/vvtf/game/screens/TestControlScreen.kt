package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import org.catinthedark.shared.libgdx.control.Control
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit
import org.slf4j.LoggerFactory

class TestControlScreen(
        private val stage: Stage
) : YieldUnit<Assets.Pack, Assets.Pack> {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private lateinit var am: AssetManager
    private lateinit var font: BitmapFont

    override fun onActivate(data: Assets.Pack) {
        log.info("TestControl started")
        this.am = Assets.load()
        font = BitmapFont()
        font.data.setScale(6f)
    }

    override fun run(delta: Float): Assets.Pack? {
        val pressed = Control.pressed()

        stage.batch.managed {
            if (pressed.contains(Control.Button.UP)) {
                font.draw(it, "UP", 230f, 350f + 128f)
            }
            if (pressed.contains(Control.Button.DOWN)) {
                font.draw(it, "DOWN", 230f, 350f - 128f)
            }
            if (pressed.contains(Control.Button.LEFT)) {
                font.draw(it, "LEFT", 230f - 128f, 350f)
            }
            if (pressed.contains(Control.Button.RIGHT)) {
                font.draw(it, "RIGHT", 230f + 128f, 350f)
            }
            if (pressed.contains(Control.Button.BUTTON0)) {
                font.draw(it, "BUTTON0", 550f, 600f)
            }
            if (pressed.contains(Control.Button.BUTTON1)) {
                font.draw(it, "BUTTON1", 550f, 600f - 128f)
            }
            if (pressed.contains(Control.Button.BUTTON2)) {
                font.draw(it, "BUTTON2", 550f, 600f - 128f - 128f)
            }
            if (pressed.contains(Control.Button.BUTTON3)) {
                font.draw(it, "BUTTON3", 550f, 600f - 128f - 128f - 128f)
            }
        }

//        log.debug(Control.pressed().toString())
//        if (Control.isPressed(Control.Button.UP)) log.debug("UP")
//        if (Control.isPressed(Control.Button.UP, Control.Button.RIGHT)) log.debug("UP-RIGHT")
//        if (Control.isPressed(Control.Button.UP, Control.Button.BUTTON0)) log.debug("UP-BUTTON0")
        return null
    }

    override fun onExit() {
    }

}