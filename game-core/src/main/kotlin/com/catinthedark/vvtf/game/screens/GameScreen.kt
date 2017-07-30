package com.catinthedark.vvtf.game.screens


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import com.catinthedark.vvtf.game.Const
import com.catinthedark.vvtf.game.State
import com.catinthedark.vvtf.game.screens.views.UINotifications
import com.catinthedark.vvtf.game.screens.views.UIPower
import com.catinthedark.vvtf.game.screens.views.UITime
import org.catinthedark.client.TCPMessage
import org.catinthedark.shared.event_bus.BusRegister
import org.catinthedark.shared.event_bus.EventBus
import org.catinthedark.shared.libgdx.control.Control
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit
import org.catinthedark.vvtf.shared.Const.Network.Client
import org.catinthedark.vvtf.shared.Const.PlayerState
import org.catinthedark.vvtf.shared.messages.Attack
import org.catinthedark.vvtf.shared.messages.Jump
import org.catinthedark.vvtf.shared.models.playerParams
import org.slf4j.LoggerFactory


class GameScreen(
        private val stage: Stage,
        private val hudStage: Stage
) : YieldUnit<Assets.Pack, Unit> {
    private lateinit var pack: Assets.Pack

    private val log = LoggerFactory.getLogger(this.javaClass)
    private val state = State()
    private val ui = listOf(UITime(hudStage, state), UIPower(hudStage, state), UINotifications(hudStage, state))
    private lateinit var shader: ShaderProgram
    private lateinit var mesh: Mesh
    private lateinit var fbo: FrameBuffer
    private lateinit var fboTex: TextureRegion
    private lateinit var sceneBatch: SpriteBatch
    private lateinit var fboBatch: SpriteBatch
    private val tiledMap = TmxMapLoader().load("maps/1.tmx")
    private val tiledMapRenderer = OrthogonalTiledMapRenderer(tiledMap)
    private lateinit var shapeRender: ShapeRenderer
    private lateinit var fboWall: FrameBuffer
    val camera = OrthographicCamera(1024f, 640f)

    override fun onActivate(data: Assets.Pack) {
        log.info("GameScreen started")

        BusRegister.registerPreHandler("stage", { _, message, _ ->
            return@registerPreHandler Pair(message, listOf(state, stage, pack))
        })
        pack = data
        ui.forEach { it.onActivate(Unit) }

        Const.tickInvoker.periodic({
            EventBus.send("#onActivate.periodic", Const.tickInvoker, TCPMessage(state.currentMovement))
            state.currentMovement.deltaX = 0f
        }, Client.tickDelay)

        ShaderProgram.pedantic = false

        this.shader = ShaderProgram(Gdx.files.internal("shaders/light.vert"), Gdx.files.internal("shaders/light.frag"))

        if (!shader.isCompiled) throw Exception(shader.log)

        this.mesh = Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.TexCoords(0))

        mesh.setVertices(floatArrayOf(
                -512f, -320f, 0f, 0f, 1f,
                512f, -320f, 0f, 1f, 1f,
                512f, 320f, 0f, 1f, 0f,
                -512f, 320f, 0f, 0f, 0f))
        mesh.setIndices(shortArrayOf(0, 1, 2, 2, 3, 0))

        fbo = FrameBuffer(Pixmap.Format.RGBA8888, 1024, 640, true)
        fboTex = TextureRegion(fbo.colorBufferTexture)
        fboTex.flip(false, true)

        sceneBatch = SpriteBatch()
        fboBatch = SpriteBatch()

        println(SpriteBatch().shader.vertexShaderSource)
        println(SpriteBatch().shader.fragmentShaderSource)

        camera.position.x = 512f
        camera.position.y = 320f
        camera.update()
        tiledMapRenderer.setView(camera)
        shapeRender = ShapeRenderer()

        val cam2 = OrthographicCamera(1024f, 640f)
        // cam2.translate(512f, 320f)
        cam2.update()
        //shapeRender.transformMatrix = cam2.combined

        fboWall = FrameBuffer(Pixmap.Format.RGBA8888, 1024, 640, false)
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
    }

    fun normalizedCamPos(x: Float, y: Float): Vector2 {
        return Vector2(x - 512f, y - 320f)
    }

    override fun run(delta: Float): Unit? {
        // val gl = Gdx.graphics.gL20
        val maxPlayerCamDist = 300;
        val npos = normalizedCamPos(camera.position.x, camera.position.x)
        val ppos = Vector2(state.gameState.me.x, state.gameState.me.y)
        val LEFT_DIST = 300f;
        val RIGHT_DIST = 30f;
        // println("dist = "  + (ppos.x - npos.x) )
        if (ppos.x - npos.x > LEFT_DIST && npos.x < 3200f) {
            camera.position.x = npos.x + 512f + (ppos.x - npos.x - LEFT_DIST)
            //camera.position.y = ppos.y + 320f
            camera.update()
            tiledMapRenderer.setView(camera)
            //  println(camera.position.x)
        }

        println("dist = " + (ppos.x - npos.x))

        if (ppos.x - npos.x < RIGHT_DIST && npos.x > 0f) {
            camera.position.x = npos.x + 512f + (ppos.x - npos.x - RIGHT_DIST)
            camera.update()
            tiledMapRenderer.setView(camera)
            //  println(camera.position.x)
        }

        val scam = OrthographicCamera(1024f, 640f)
        scam.position.x = camera.position.x
        scam.position.y = camera.position.y
        scam.update()

        fbo.begin()
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        sceneBatch.managed {
            //sceneBatch.draw(am.get(Assets.Names.BACKGROUND1, Texture::class.java),0f, 0f, 1024f, 640f)
            tiledMapRenderer.render()
            stage.batch.projectionMatrix = scam.combined
            stage.batch.managed {
                (state.gameState.players + state.gameState.me).forEach { p ->
                    val skin = pack.playerSkins[p.type] ?: return@forEach
                    val texture = skin.texture(p.state, delta)
                    if ((p.angle == 180f) xor texture.isFlipX) {
                        texture.flip(true, false)
                    }

                    it.draw(texture, p.x, p.y)
                }
            }
        }
        fbo.end()

        fboWall.begin()
        shapeRender.projectionMatrix = scam.combined
        shapeRender.begin(ShapeRenderer.ShapeType.Filled)
        shapeRender.setColor(1f, 1f, 1f, 1f)
        shapeRender.rect(0f, 0f, 1024f, 640f)

        shapeRender.setColor(0f, 0f, 0f, 0f)

        val walls = tiledMap.layers["walls"]

        for (i in 0..walls.objects.count - 1) {
            val props = walls.objects[i].properties
            val x = props["x"] as Float
            val w = props["width"] as Float
            val h = props["height"] as Float
            val y = props["y"] as Float

            shapeRender.rect(x, y, w, h)
        }
        shapeRender.end()
        fboWall.end()

        // gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val whiteTex = fbo.colorBufferTexture // am.get(Assets.Names.WHITE, Texture::class.java)
        val castTex = fboWall.colorBufferTexture // am.get(Assets.Names.DEMO, Texture::class.java)
        val backTex = pack.am.get(Assets.TexturePaths.BACKGROUND1.path, Texture::class.java);


//        shader.begin();
//        whiteTex.bind(0)
//        castTex.bind(1)
//        shader.setUniformi("u_textureMain", 0);
//        //shader.setUniformi("u_textureCast", 1);
//        shader.setUniform2fv("screenSize", floatArrayOf(1024f, 640f), 0, 2)
//        //shader.setUniform2fv("mousePos", floatArrayOf(Gdx.input.x.toFloat(), Gdx.input.y.toFloat()), 0, 2)
//        //println("w = " + fboTex.regionWidth)
//        //println("h = " + fboTex.regionHeight)
//
//        mesh.render(shader, GL20.GL_TRIANGLES);
//
//        shader.end();

//        sceneBatch.managed {
//            val tr = TextureRegion(castTex)
//            tr.flip(false, true)
//            it.draw(tr, 0f, 0f, 1024f, 640f)
//        }

        fboBatch.shader = shader
        fboBatch.begin()
        castTex.bind(1)
        backTex.bind(2);
        shader.setUniformi("u_textureCast", 1)
        shader.setUniformi("u_textureBack", 2)
        shader.setUniform2fv("screenSize", floatArrayOf(1024f, 640f), 0, 2)
        shader.setUniform2fv("mousePos",
            floatArrayOf(state.gameState.me.x + 64f, 640f - state.gameState.me.y - 64f), 0, 2)
            // TODO: recalculate player position to screen coordinates
        fboTex.texture.bind(0)
        fboBatch.draw(fboTex, 0f, 0f, 1024f, 640f)
        fboBatch.end();

        ui.forEach { it.run(delta) }

        handleKeys(delta)
        handleSounds(delta)

        return null
    }

    override fun onExit() {
        shader.dispose()
        mesh.dispose()
        BusRegister.unRegisterPreHandler("stage")
        ui.forEach { it.onExit() }
    }

    private fun handleKeys(delta: Float) {
//        log.debug(delta.toString())

        val playerParams = playerParams[state.gameState.me.type] ?: return

        Control.onPressed(Control.Button.RIGHT, {
            with(state.currentMovement) {
                deltaX += delta * playerParams.speedX
                angle = 0f
                state = PlayerState.walking.name    //?
            }

        })

        Control.onPressed(Control.Button.LEFT, {
            with(state.currentMovement) {
                deltaX -= delta * playerParams.speedX
                angle = 180f
                state = PlayerState.walking.name    //?
            }
        })

        Control.onPressed(Control.Button.UP, {
            if (!state.gameState.me.canJump) return@onPressed

            log.info("JUMP!")
            state.gameState.me.canJump = false // to prevent often sending to the server
            EventBus.send("GameScreen#handleKeys", Const.tickInvoker, TCPMessage(
                    Jump()
            ))
        })

        with(state.currentMovement) {
            if (deltaX == 0f && deltaY == 0f) {
                state = PlayerState.idle.name
            }
        }

        Control.onPressed(Control.Button.BUTTON0, {
            if (!state.gameState.me.canAttack) return@onPressed
            log.info("ATTACK!")
            state.gameState.me.canAttack = false
            EventBus.send("#handleKeys.attack", Const.tickInvoker, TCPMessage(Attack()))
        })

        Control.onPressed(Control.Button.BUTTON2, {
            if (!state.gameState.me.canTeleport) return@onPressed
            log.info("TELEPORT")
            state.gameState.me.canTeleport = false
            pack.playerSounds[state.gameState.me.type]?.run?.play()
        })
    }

    private fun handleSounds(delta: Float) {
        val walkMe = pack.playerSounds[state.gameState.me.type]?.walk
        if (state.gameState.me.state == PlayerState.walking.name) {
            walkMe?.play()
        } else {
            walkMe?.pause()
        }
    }
}

fun Control.onPressed(onTrue: () -> Unit, vararg buttons: Control.Button) {
    if (isPressed(*buttons)) onTrue()
}

fun Control.onPressed(button: Control.Button, onTrue: () -> Unit) {
    if (isPressed(button)) onTrue()
}
