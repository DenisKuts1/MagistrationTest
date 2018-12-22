package math

import kotlin.math.abs


class Sphere(name: String, center: Vector3, val radius: Float) : Dot(name, center) {

    fun intersects(other: Sphere) = point - other.point <= radius + other.radius

}