package math

import com.sun.prism.Mesh
import engine.Model
import engine.Simulator
import kotlin.math.abs

var ID = 0
class Sphere(name: String,var center: Vector3, val radius: Float)/* : Dot(name, center) */{
    val name: String = name + ID
    var color = Vector3(1f,0f, 0f)
    val model = Model(Simulator.mesh, center)
    init {
        ID++
    }
    fun intersects(other: Sphere) = center - other.center <= radius + other.radius
    fun remake(){
        model.setPosition(center)
        model.remake()
    }
}