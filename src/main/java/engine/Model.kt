package engine

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL3
import com.jogamp.opengl.GL4
import com.jogamp.opengl.util.texture.Texture
import com.jogamp.opengl.util.texture.TextureIO
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import math.Vector3

class Model(mesh: Mesh, pos: Vector3) {

    val meshes = ArrayList<Mesh>()
    var position: Vec3
    var rotation: Vec3
    var scale: Vec3
    var modelMatrix = Mat4(1f)

    init {
        position = Vec3(pos.x, pos.y, pos.z)
        rotation = Vec3(0f, 0f, 0f)
        scale = Vec3(0.1f, 0.1f, 0.1f)
        modelMatrix = glm.scale(modelMatrix, scale)
        modelMatrix = glm.translate(modelMatrix, position)
        //modelMatrix = glm.rotate(modelMatrix,0f,  rotation)


        meshes += mesh
    }

    fun remake(){
        modelMatrix = Mat4(1f).scale(scale).translate(position)
    }

    fun draw(gl: GL4, shader: Shader){
        meshes.forEach { mesh ->
            mesh.draw(gl, shader)
        }
    }

    fun init(gl: GL4, shader: Shader){
        meshes.forEach { mesh ->
            mesh.init(gl, shader)
        }
    }

    fun setPosition(pos: Vector3){
        position = Vec3(pos.x, pos.y, pos.z)
        modelMatrix = Mat4(1f).scale(scale).translate(position)
    }

}