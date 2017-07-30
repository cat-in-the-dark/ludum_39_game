package com.catinthedark.vvtf.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g3d.Shader
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.catinthedark.vvtf.game.Assets
import org.catinthedark.shared.libgdx.managed
import org.catinthedark.shared.route_machine.YieldUnit
import org.slf4j.LoggerFactory


class GameScreen(
        private val stage: Stage,
        private val hudStage: Stage
) : YieldUnit<AssetManager, Unit> {
    private lateinit var am: AssetManager
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

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onActivate(data: AssetManager) {
        log.info("GameScreen started")
        ShaderProgram.pedantic = false
        this.am = data
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

        val camera = OrthographicCamera(1024f, 640f)
        camera.translate(512f, 320f)
        camera.update()
        tiledMapRenderer.setView(camera)
        shapeRender = ShapeRenderer()

        val cam2 = OrthographicCamera(1024f, 640f)
        // cam2.translate(512f, 320f)
        cam2.update()
        //shapeRender.transformMatrix = cam2.combined

        fboWall = FrameBuffer(Pixmap.Format.RGBA8888, 1024, 640, false)

    }

    override fun run(delta: Float): Unit? {
        // val gl = Gdx.graphics.gL20
        // gl.glClearColor(0f, 0f, 0f, 0f);

        fbo.begin()
        sceneBatch.managed {
            sceneBatch.draw(am.get(Assets.Names.BACKGROUND1, Texture::class.java),0f, 0f, 1024f, 640f)
            tiledMapRenderer.render()
        }
        fbo.end()

        fboWall.begin()
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
        val castTex =  fboWall.colorBufferTexture // am.get(Assets.Names.DEMO, Texture::class.java)
        val backTex = am.get(Assets.Names.BACKGROUND1, Texture::class.java);


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
        shader.setUniform2fv("mousePos", floatArrayOf(Gdx.input.x.toFloat(), Gdx.input.y.toFloat()), 0, 2)
        fboTex.texture.bind(0)
        fboBatch.draw(fboTex, 0f, 0f, 1024f, 640f)
        fboBatch.end();
        return null
    }

    override fun onExit() {
        shader.dispose()
        mesh.dispose()
    }
}