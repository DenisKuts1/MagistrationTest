package app

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL3
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import engine.Camera
import engine.Model
import engine.Shader
import engine.Simulator
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec4.Vec4
import java.awt.event.KeyListener


object Engine : GLEventListener, KeyListener {

    lateinit var defaultShader: Shader
    //val model = Model()
    val camera = Camera()

    var fov = 60f
    var projectionMatrix = glm.perspective(glm.radians(fov), 1f, 0.1f, 100f)

    var forward = 0
    var right = 0
    var lastTime = 0L

    override fun keyTyped(e: java.awt.event.KeyEvent?) {

    }

    override fun keyPressed(e: java.awt.event.KeyEvent) {
        when (e.keyChar) {
            'w' -> forward = 1
            's' -> forward = -1
            'd' -> right = 1
            'a' -> right = -1
        }

    }

    override fun keyReleased(e: java.awt.event.KeyEvent) {
        when (e.keyChar) {
            'w' -> forward = 0
            's' -> forward = 0
            'd' -> right = 0
            'a' -> right = 0
        }
    }


    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
        val gl = drawable.gl.gL4
        gl.glViewport(0, 0, width, height)
    }

    override fun display(drawable: GLAutoDrawable) {
        val time = System.currentTimeMillis()
        val delta = (time - lastTime) / 1000f
        lastTime = time
        camera.move(delta, forward, right)
        val gl = drawable.gl.gL4
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f)
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT or GL3.GL_DEPTH_BUFFER_BIT)
        defaultShader.use()
        //projectionMatrix.times(camera.viewMatrix).times(model.modelMatrix).times(Vec4(-0.5f,0.5f,-2f, 0f)).app.print()
        Simulator.spheres.forEach { sphere ->
            val mvp = projectionMatrix * camera.viewMatrix * sphere.model.modelMatrix
            defaultShader.setMatrix4f("mvp", mvp)
            defaultShader.setVec4f("color", Vec4(sphere.color.x, sphere.color.y, sphere.color.z, 1f))
            sphere.model.draw(gl, defaultShader)
        }

        defaultShader.unbind()

        if(Simulator.running){
            Simulator.moveRandomCircle()
            println(1)
        }
    }

    override fun init(drawable: GLAutoDrawable) {
        val gl = drawable.gl.gL4
        gl.swapInterval = 0
        gl.glViewport(0, 0, 500, 500)
        defaultShader = Shader(gl, "/shaders/default_vertex_shader.glsl", "/shaders/default_fragment_shader.glsl")
        Simulator.mesh.init(gl, defaultShader)
        /*gl.glEnable(GL3.GL_CULL_FACE)
        gl.glCullFace(GL3.GL_BACK)
        gl.glFrontFace(GL3.GL_CW)*/
        gl.glEnable(GL2.GL_DEPTH_TEST)
        gl.glDepthMask(true)
        lastTime = System.currentTimeMillis()

    }

    override fun dispose(p0: GLAutoDrawable?) {

    }
}

fun Mat4.print() {
    array.forEachIndexed { index, fl ->
        if (index % 4 == 0) println()
        print("$fl ")
    }
}

fun Vec4.print() {
    array.forEachIndexed { index, fl ->
        print("$fl ")
    }
    println()
}