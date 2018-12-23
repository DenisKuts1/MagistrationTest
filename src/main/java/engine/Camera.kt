package engine

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import math.Vector3

class Camera {

    var position: Vec3
    var front: Vec3
    val up: Vec3
    var viewMatrix: Mat4
    val forwardStep = 0.5f
    var angle = 0.0f
    val rotationStep = 0.05f

    constructor() {
        position = Vec3(0f, 0f, -1f)
        front = Vec3(0f, 0f, 1f)
        up = Vec3(0f, 1f, 0f)
        viewMatrix = glm.lookAt(position, position + front, up)
    }

    constructor(position: Vec3, front: Vec3, up: Vec3) {
        this.position = position
        this.front = front
        this.up = up
        viewMatrix = glm.lookAt(position, position + front, up)
    }

    fun move(delta: Float, forward: Int, right: Int){
        if(forward != 0){
            val amount = forwardStep * forward * delta
            val newPosition = position + Vec3(0f, 0f, amount)
            if(newPosition.length() > 1){
                position = newPosition
                front = -newPosition

            }
        }
        if(right != 0){
            angle += rotationStep * right
        }
        val rotatedPosition = glm.rotateY(Vec3(), position, angle)
        val rotatedFront = -rotatedPosition
        viewMatrix = glm.lookAt(rotatedPosition, rotatedPosition + rotatedFront, up)
    }
}

fun Vec3.print(){
    println("$x $y $z")
}