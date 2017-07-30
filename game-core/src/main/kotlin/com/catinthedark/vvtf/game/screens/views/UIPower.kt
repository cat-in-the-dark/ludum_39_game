package com.catinthedark.vvtf.game.screens.views

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Const
import com.catinthedark.vvtf.game.State
import org.catinthedark.shared.route_machine.YieldUnit
import org.catinthedark.vvtf.shared.models.playerParams

class UIPower(
        private val hudStage: Stage,
        private val state: State
) : YieldUnit<Unit, Unit> {

    private val shapeRender = ShapeRenderer()
    private val bar = HudPowerBar()

    override fun onActivate(data: Unit) {

    }

    override fun run(delta: Float): Unit? {
        val target = state.gameState.me
        val params = playerParams[target.type] ?: return null

        shapeRender.projectionMatrix = hudStage.batch.projectionMatrix
        shapeRender.transformMatrix = hudStage.batch.transformMatrix
        shapeRender.begin(ShapeRenderer.ShapeType.Filled)
        shapeRender.setColor(0.83f, 0.1f, 0.1f, 1f)
        bar.render(shapeRender, target.power, params.maxPower, Const.UI.powerBarPos, Const.UI.powerBarWh)
        shapeRender.end()
        return null
    }

    override fun onExit() {

    }

    class HudPowerBar(private val vertical: Boolean = false) {
        fun render(renderer: ShapeRenderer, value: Long, max: Long, pos: Vector2, wh: Vector2) {
            if (vertical) {
                renderer.rect(pos.x, pos.y, wh.x, wh.y * value / max)
            } else {
                renderer.rect(pos.x, pos.y, wh.x * value / max, wh.y)
            }
        }
    }
}