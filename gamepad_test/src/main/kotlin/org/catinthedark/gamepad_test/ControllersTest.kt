package org.catinthedark.gamepad_test

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import org.slf4j.LoggerFactory


class ControllersTest: InputAdapter(), ApplicationListener {

    private var descriptor: String? = null
    private var skin: Skin? = null
    private var ui: Table? = null
    private var stage: Stage? = null
    private var scrollPane: ScrollPane? = null

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun create() {
        setupUi()
    }

    internal fun print(message: String) {
        logger.info(message)
    }

    internal fun clear() {
//        console?.clear()
    }

    private fun setupUi() {
        // setup a tiny ui with a console and a clear button.
//        skin = Skin(Gdx.files.internal("uiskin.json"))
        stage = Stage()
        ui = Table()
        ui?.setSize(Gdx.graphics.getWidth().toFloat(), Gdx.graphics.getHeight().toFloat())
//        console = List(skin)
//        scrollPane = ScrollPane(console)
//        scrollPane?.setScrollbarsOnTop(true)
//        val clear = TextButton("Clear", skin)
//        ui?.add(scrollPane)?.expand(true, true)?.fill()
//        ui?.row()
//        ui?.add(clear)?.expand(true, false)?.fill()
        stage?.addActor(ui)
//        clear.addListener(object : ClickListener() {
//            override fun clicked(event: InputEvent, x: Float, y: Float) {
//                clear()
//            }
//        })
        Gdx.input.setInputProcessor(stage)
    }

    override fun resize(width: Int, height: Int) {
        ui?.setSize(width.toFloat(), height.toFloat())
        ui?.invalidate()
        stage?.getViewport()?.update(width, height, true)
    }

    internal var initialized = false

    private fun initialize() {
        if (initialized) return
        // print the currently connected controllers to the console
        print("Controllers: " + Controllers.getControllers().size)
        var i = 0
        for (controller in Controllers.getControllers()) {
            print("#" + i++ + ": " + controller.getName())
        }
        if (Controllers.getControllers().size == 0) print("No controllers attached")

        // setup the listener that prints events to the console
        Controllers.addListener(object : ControllerListener {
            fun indexOf(controller: Controller): Int {
                return Controllers.getControllers().indexOf(controller, true)
            }

            override fun connected(controller: Controller) {
                print("connected " + controller.getName())
                var i = 0
                for (c in Controllers.getControllers()) {
                    print("#" + i++ + ": " + c.getName())
                }
            }

            override fun disconnected(controller: Controller) {
                print("disconnected " + controller.getName())
                var i = 0
                for (c in Controllers.getControllers()) {
                    print("#" + i++ + ": " + c.getName())
                }
                if (Controllers.getControllers().size == 0) print("No controllers attached")
            }

            override fun buttonDown(controller: Controller, buttonIndex: Int): Boolean {
                print("#" + indexOf(controller) + ", button " + buttonIndex + " down")
                return false
            }

            override fun buttonUp(controller: Controller, buttonIndex: Int): Boolean {
                print("#" + indexOf(controller) + ", button " + buttonIndex + " up")
                return false
            }

            override fun axisMoved(controller: Controller, axisIndex: Int, value: Float): Boolean {
                print("#" + indexOf(controller) + ", axis " + axisIndex + ": " + value)
                return false
            }

            override fun povMoved(controller: Controller, povIndex: Int, value: PovDirection): Boolean {
                print("#" + indexOf(controller) + ", pov " + povIndex + ": " + value)
                return false
            }

            override fun xSliderMoved(controller: Controller, sliderIndex: Int, value: Boolean): Boolean {
                print("#" + indexOf(controller) + ", x slider " + sliderIndex + ": " + value)
                return false
            }

            override fun ySliderMoved(controller: Controller, sliderIndex: Int, value: Boolean): Boolean {
                print("#" + indexOf(controller) + ", y slider " + sliderIndex + ": " + value)
                return false
            }

            override fun accelerometerMoved(controller: Controller, accelerometerIndex: Int, value: Vector3): Boolean {
                // not printing this as we get to many values
                return false
            }
        })
        initialized = true
    }

    override fun render() {
        initialize()
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage?.act(Gdx.graphics.getDeltaTime())
        stage?.draw()
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

fun main(args: Array<String>) {
    System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true")
    LwjglApplication(ControllersTest(), LwjglApplicationConfiguration().apply {
        title = "Controllers test"
        width = 1161
        height = 652
    })
}
