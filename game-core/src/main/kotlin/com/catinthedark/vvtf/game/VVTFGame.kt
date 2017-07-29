package com.catinthedark.vvtf.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.catinthedark.vvtf.game.Const.tickInvoker
import com.catinthedark.vvtf.game.screens.GameScreen
import com.catinthedark.vvtf.game.screens.PairingScreen
import com.catinthedark.vvtf.game.screens.SplashScreen
import com.catinthedark.vvtf.game.screens.TitleScreen
import com.google.common.eventbus.EventBus
import org.catinthedark.shared.event_bus.BusRegister
import org.catinthedark.shared.route_machine.RouteMachine

class VVTFGame : Game() {
    private val rm = RouteMachine()

    private lateinit var stage: Stage
    private lateinit var hudStage: Stage

    override fun create() {
        BusRegister.register("com.catinthedark.vvtf.game.handlers")

        stage = Stage(FillViewport(
                Const.Screen.WIDTH / Const.Screen.ZOOM,
                Const.Screen.HEIGHT / Const.Screen.ZOOM,
                OrthographicCamera()), SpriteBatch())

        hudStage = Stage(FitViewport(
                Const.Screen.WIDTH / Const.Screen.ZOOM,
                Const.Screen.HEIGHT / Const.Screen.ZOOM,
                OrthographicCamera()), SpriteBatch())

        val splash = SplashScreen(hudStage)
        val title = TitleScreen(hudStage)
        val game = GameScreen(stage, hudStage, tickInvoker)
        val pairing = PairingScreen(stage)

        rm.addRoute(splash, { title })
        rm.addRoute(title, { pairing })
        rm.addRoute(pairing, { game })
        rm.start(splash, Unit)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        hudStage.viewport.apply(true)
        stage.viewport.apply()
        hudStage.act(Gdx.graphics.deltaTime)
        stage.act(Gdx.graphics.deltaTime)
        tickInvoker.run(secondsToNanoseconds(Gdx.graphics.deltaTime))
        rm.run(Gdx.graphics.deltaTime)
        super.render()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        hudStage.viewport.update(width, height, true)
        stage.viewport.update(width, height)
    }

    override fun dispose() {
        Const.threadInvoker.shutdown()
        Const.tickInvoker.shutdown()
        super.dispose()
    }

    fun secondsToNanoseconds(delta: Float): Long {
        return (delta * 1000 * 1000 * 1000).toLong()
    }
}