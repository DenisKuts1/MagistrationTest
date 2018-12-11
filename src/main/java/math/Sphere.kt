package math

import kotlin.math.abs


class Sphere(name: String, center: Vector3, val radius: Vector3) : Dot(name, center) {

    fun intersects(other: Sphere) = radius + other.radius <= point - other.point

}