package org.catinthedark.shared.libgdx.control

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerAdapter
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import org.slf4j.LoggerFactory

object Control {

    enum class Type {
        KEYBOARD, CONTROLLER
    }

    enum class Button {
        UP, DOWN, LEFT, RIGHT, BUTTON0, BUTTON1, BUTTON2, BUTTON3
    }

    private val logger = LoggerFactory.getLogger(javaClass)

    private val types = mutableSetOf<Type>(Type.KEYBOARD)
    private var controller: Controller? = null

    init {
        val controllers = Controllers.getControllers()
        if (controllers.size > 0) {
            controller = controllers[0]
            types.add(Type.CONTROLLER)
            logger.info("Controller found: {}", controller?.name)
        }
        Controllers.addListener(object: ControllerAdapter() {
            override fun connected(controller: Controller?) {
                Control.controller = controller
                types.add(Type.CONTROLLER)
                logger.info("Controller connected: {}", Control.controller?.name)
            }

            override fun disconnected(controller: Controller?) {
                Control.controller = null
                types.remove(Type.CONTROLLER)
                logger.info("Controller disconnected: {}", controller?.name)
            }
        })
    }

    fun avaiableTypes(): Set<Type> {
        return types
    }

    fun pressed(): Set<Button> {
        val controllerPressed = controllerButtons(*Button.values())
        val keyboardPressed = keyboardButtons(*Button.values())
        return controllerPressed union keyboardPressed
    }

    fun isPressed(vararg buttons: Button): Boolean {
        val controllerPressed = buttons intersect controllerButtons(*buttons)
        val keyboardPressed = buttons intersect keyboardButtons(*buttons)
        return (controllerPressed union keyboardPressed).isNotEmpty()
    }

    private fun controllerButtons(vararg buttons: Button): Set<Button> {
        if (controller == null) {
            return setOf()
        }
        val pressedButtons = mutableSetOf<Button>()

        val direction = controller?.getPov(0)
        pressedButtons.addAll(when (direction) {
            PovDirection.center -> setOf()
            PovDirection.east -> setOf(Button.RIGHT)
            PovDirection.north -> setOf(Button.UP)
            PovDirection.northEast -> setOf(Button.UP, Button.RIGHT)
            PovDirection.northWest -> setOf(Button.UP, Button.LEFT)
            PovDirection.south -> setOf(Button.DOWN)
            PovDirection.southEast -> setOf(Button.DOWN, Button.RIGHT)
            PovDirection.southWest -> setOf(Button.DOWN, Button.LEFT)
            PovDirection.west -> setOf(Button.LEFT)
            else -> setOf()
        })

        return pressedButtons
    }

    private fun keyboardButtons(vararg buttons: Button): Set<Button> {
        val pressedButtons = mutableSetOf<Button>()

        for (button in buttons) {
            val keyCode = when (button) {
                Button.UP -> Input.Keys.W
                Button.DOWN -> Input.Keys.S
                Button.LEFT -> Input.Keys.A
                Button.RIGHT -> Input.Keys.D
                Button.BUTTON0 -> Input.Keys.SPACE
                Button.BUTTON1 -> Input.Keys.SHIFT_LEFT
                Button.BUTTON2 -> Input.Keys.SHIFT_RIGHT
                Button.BUTTON3 -> Input.Keys.ENTER
            }
            if (Gdx.input.isKeyPressed(keyCode)) {
                pressedButtons.add(button)
            }
        }

        return pressedButtons
    }

}