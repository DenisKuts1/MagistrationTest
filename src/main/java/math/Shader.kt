package math

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL4
import java.io.BufferedReader
import java.io.FileReader
import java.nio.ByteBuffer

class Shader(val gl: GL4, vertexPath: String, fragmentPath: String) {

    val vertexShader: Int
    val fragmentShader: Int
    val id: Int

    init {
        val vertexCode = BufferedReader(FileReader(Shader::class.java.getResource(vertexPath).path)).run {
            readText()
        }
        val fragmentCode = BufferedReader(FileReader(Shader::class.java.getResource(fragmentPath).path)).run {
            readText()
        }
        println(vertexCode)
        println(fragmentCode)
        with(gl) {
            vertexShader = glCreateShader(GL2.GL_VERTEX_SHADER)
            glShaderSource(vertexShader, 1, arrayOf(vertexCode), null)
            glCompileShader(vertexShader)
            checkCompileErrors(vertexShader, "SHADER")

            fragmentShader = glCreateShader(GL2.GL_FRAGMENT_SHADER)
            glShaderSource(fragmentShader, 1, arrayOf(fragmentCode), null)
            glCompileShader(fragmentShader)
            checkCompileErrors(fragmentShader, "SHADER")

            id = glCreateProgram()
            glAttachShader(id, vertexShader)
            glAttachShader(id, fragmentShader)
            glLinkProgram(id)
            checkCompileErrors(id, "PROGRAM")

            glDeleteShader(vertexShader)
            glDeleteShader(fragmentShader)
        }
    }

    fun use() {
        gl.glUseProgram(id)
    }

    fun unbind() {
        gl.glUseProgram(0)
    }

    fun setBool(name: String, value: Boolean) {
        val intValue = if (value) 1 else 0
        gl.glUniform1i(gl.glGetUniformLocation(id, name), intValue)
    }

    fun setInt(name: String, value: Int) {
        gl.glUniform1i(gl.glGetUniformLocation(id, name), value)
    }

    fun setFloat(name: String, value: Float) {
        gl.glUniform1f(gl.glGetUniformLocation(id, name), value)
    }

    private fun checkCompileErrors(shader: Int, type: String) {
        with(gl) {
            val status = IntArray(1)
            when(type){
                "SHADER" -> {
                    glGetShaderiv(shader, GL2.GL_COMPILE_STATUS, status, 0)
                    if (status[0] == 0){
                        val buffer = ByteBuffer.allocate(1024)
                        glGetShaderInfoLog(shader, 1024, null, buffer)

                        println("Shader compilation error: ${String(buffer.array())}")
                    }
                }
                "PROGRAM" -> {
                    glGetProgramiv(shader, GL2.GL_LINK_STATUS, status, 0)
                    if (status[0] == 0){
                        val buffer = ByteBuffer.allocate(1024)
                        glGetProgramInfoLog(shader, 1024, null, buffer)
                        println("Program linking error: ${String(buffer.array())}")
                    }
                }
            }

        }
    }
}