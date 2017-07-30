package com.catinthedark.vvtf.game.screens.views

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.catinthedark.vvtf.game.State
import org.catinthedark.shared.route_machine.YieldUnit
import org.catinthedark.vvtf.shared.toSeconds

class UITime(
        private val hudStage: Stage,
        private val state: State
) : YieldUnit<Unit, Unit> {
    private val font = BitmapFont()
    private val label = Label(" ", Label.LabelStyle(font, Color.WHITE))

    override fun onActivate(data: Unit) {
        label.x = 500f
        label.y = 550f
        label.fontScaleX = 1.5f
        label.fontScaleY = 1.5f
        hudStage.addActor(label)
    }

    override fun run(delta: Float): Unit? {
        label.setText(formatTime(state.gameState.time))

        return null
    }

    override fun onExit() {
        label.remove()
    }

    private fun formatTime(time: Long): String {
        val seconds = time.toSeconds()
        val min = (seconds / 60).toInt()
        val sec = (seconds % 60).toInt()
        return if (sec < 10) {
            "$min:0$sec"
        } else {
            "$min:$sec"
        }
    }
}