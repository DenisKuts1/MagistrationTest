import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL3
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import math.ObjImporter
import math.Shader
import java.awt.event.KeyListener

object Engine: GLEventListener, KeyListener {

    lateinit var defaultShader: Shader
    val mesh = ObjImporter.import("/objects/sphere.obj")
    override fun keyTyped(e: java.awt.event.KeyEvent?) {

    }

    override fun keyPressed(e: java.awt.event.KeyEvent?) {

    }

    override fun keyReleased(e: java.awt.event.KeyEvent?) {

    }


    override fun reshape(p0: GLAutoDrawable?, p1: Int, p2: Int, p3: Int, p4: Int) {

    }

    override fun display(drawable: GLAutoDrawable) {
        val gl = drawable.gl.gL4
        println(1)
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT or GL3.GL_DEPTH_BUFFER_BIT)
        defaultShader.use()
        mesh.draw(gl, defaultShader)
        defaultShader.unbind()
    }

    override fun init(drawable: GLAutoDrawable) {
        val gl = drawable.gl.gL4
        gl.swapInterval = 0
        gl.glViewport(0, 0, 500, 500)
        defaultShader = Shader(gl, "/shaders/default_vertex_shader.glsl", "/shaders/default_fragment_shader.glsl")
        mesh.init(gl)
        gl.glEnable(GL3.GL_CULL_FACE)
        gl.glCullFace(GL3.GL_BACK)
        gl.glFrontFace(GL3.GL_CW)
        gl.glEnable(GL2.GL_DEPTH_TEST)
        gl.glDepthMask(true)

    }

    override fun dispose(p0: GLAutoDrawable?) {

    }
}