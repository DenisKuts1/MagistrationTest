package math

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL3
import com.jogamp.opengl.GL4
import com.jogamp.opengl.math.Matrix4
import com.jogamp.opengl.util.texture.Texture
import com.jogamp.opengl.util.texture.TextureIO
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Mesh(vertices: ArrayList<Vertex>, indices: ArrayList<Int>, val textureFiles: HashMap<String, String>) {

    private val VAO = IntArray(1)
    private val VBO = IntArray(1)
    private val EBO = IntArray(1)
    val vertices = FloatBuffer.allocate(vertices.size * 8)
    val indices = IntBuffer.allocate(vertices.size * 8)
    val textures = HashMap<String, Texture>()

    init {
        vertices.forEach { vertex ->
            (0..7).forEach {
                this.vertices.put(vertex[it])
            }
        }
        indices.forEach { index ->
            this.indices.put(index)
        }
    }

    fun init(gl: GL4) {
        with(gl) {
            glGenVertexArrays(1, VAO, 0)
            glGenBuffers(1, VBO, 0)
            glGenBuffers(1, EBO, 0)

            glBindVertexArray(VAO[0])

            glBindBuffer(GL.GL_ARRAY_BUFFER, VBO[0])
            glBufferData(GL.GL_ARRAY_BUFFER, vertices.capacity() * 8 * 4L, vertices, GL.GL_DYNAMIC_DRAW)

            glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, EBO[0])
            glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, indices.capacity() * 4L, indices, GL.GL_DYNAMIC_DRAW)

            glEnableVertexAttribArray(0)
            glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 8, 0)

            glEnableVertexAttribArray(1)
            glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, 8, 3)

            glEnableVertexAttribArray(2)
            glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, 8, 5)

            glBindVertexArray(0)

            textureFiles.keys.forEach { type ->
                textures[type] = textureFromFile(gl, textureFiles[type]!!)
            }
        }
    }

    fun draw(gl: GL4, shader: Shader){
        with(gl) {
            textures.forEach { key, texture ->
                texture.enable(gl)
                texture.bind(gl)
                shader.setInt(key, texture.textureObject)
            }
            val transform = Matrix4()
            transform.translate(0f,0.2f, 0.5f)
            shader.setMatrix4f("transform", transform)
            glBindVertexArray(VAO[0])
            glDrawElements(GL.GL_TRIANGLES, indices.capacity(), GL.GL_UNSIGNED_INT, 0)
            glBindVertexArray(0)

            textures.forEach { _, texture ->
                texture.disable(gl)
            }
        }
    }

    fun textureFromFile(gl: GL4, path: String): com.jogamp.opengl.util.texture.Texture {
        gl.run {
            val texture = TextureIO.newTexture(Model::class.java.getResource(path), false, ".png")
            texture.setTexParameteri(gl, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR)
            texture.setTexParameteri(gl, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR)
            texture.setTexParameteri(gl, GL3.GL_TEXTURE_WRAP_S, GL3.GL_CLAMP_TO_EDGE)
            texture.setTexParameteri(gl, GL3.GL_TEXTURE_WRAP_T, GL3.GL_CLAMP_TO_EDGE)
            return texture
        }
    }

}