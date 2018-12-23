package engine

import com.jogamp.common.nio.Buffers
import com.jogamp.opengl.GL
import com.jogamp.opengl.GL3
import com.jogamp.opengl.GL4
import com.jogamp.opengl.math.Matrix4
import com.jogamp.opengl.util.texture.Texture
import com.jogamp.opengl.util.texture.TextureIO
import java.nio.Buffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Mesh(vertices: ArrayList<Vertex>, indices: ArrayList<Int>, val textureFiles: HashMap<String, String>) {

    private val VAO = IntArray(1)
    private val VBO = IntArray(1)
    private val EBO = IntArray(1)
    val vertices = FloatBuffer.wrap(vertices.toFloatArray())
    val indices = IntBuffer.wrap(indices.toIntArray())
    val textures = HashMap<String, Texture>()


    fun init(gl: GL4, shader: Shader) {
        with(gl) {
            shader.use()
            glGenVertexArrays(1, VAO, 0)
            glGenBuffers(1, VBO, 0)
            glGenBuffers(1, EBO, 0)

            glBindVertexArray(VAO[0])

            glBindBuffer(GL.GL_ARRAY_BUFFER, VBO[0])
            glBufferData(GL.GL_ARRAY_BUFFER, vertices.capacity() * 8 * 4L, vertices, GL.GL_STATIC_DRAW)

            glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, EBO[0])
            glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, indices.capacity() * 4L, indices, GL.GL_STATIC_DRAW)


            glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 8 * 4, 0)
            glEnableVertexAttribArray(0)


            glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, 8 * 4, 3 * 4)
            glEnableVertexAttribArray(1)

            glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, 8 * 4, 5 * 4)
            glEnableVertexAttribArray(2)
            glBindVertexArray(0)
            shader.unbind()

            textureFiles.keys.forEach { type ->
                textures[type] = textureFromFile(gl, textureFiles[type]!!)
                textures[type]!!.bind(gl)
                shader.setInt(type, textures[type]!!.textureObject)
            }
        }
    }

    fun draw(gl: GL4, shader: Shader) {
        with(gl) {
            textures.forEach { key, texture ->
                texture.enable(gl)
                texture.bind(gl)
                //shader.setInt(key, texture.textureObject)
                //println(texture.textureObject)
            }
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

fun ArrayList<Vertex>.toFloatArray(): FloatArray{
    val floatArray = FloatArray(size * 8)
    forEachIndexed { index, vertex ->
        (0..7).forEach { i ->
            floatArray[index * 8 + i] = vertex[i]
        }
    }
    return floatArray
}