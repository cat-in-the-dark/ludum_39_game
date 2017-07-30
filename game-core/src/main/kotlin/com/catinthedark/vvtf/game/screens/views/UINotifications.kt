package com.catinthedark.vvtf.game.screens.views

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.catinthedark.vvtf.game.Const
import com.catinthedark.vvtf.game.State
import org.catinthedark.shared.route_machine.YieldUnit

class UINotifications(
    private val hudStage: Stage,
    private val state: State
) : YieldUnit<Unit, Unit> {
    private val font = BitmapFont()
    private val label = Label(" ", Label.LabelStyle(font, Color.RED))
    private val container = Container(label)

    override fun onActivate(data: Unit) {
        label.fontScaleX = 1.5f
        label.fontScaleY = 1.5f

        container.left()
        container.top()

        container.x = Const.UI.notificationsPos.x
        container.y = Const.UI.notificationsPos.y

        hudStage.addActor(container)
    }

    override fun run(delta: Float): Unit? {
        val sb = StringBuilder()
        state.notifications.forEach {
            sb.append("${it.text}\n")
        }
        label.setText(sb)

        return null
    }

    override fun onExit() {
        container.remove()
    }
}
