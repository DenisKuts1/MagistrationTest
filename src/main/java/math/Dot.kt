package math

open class Dot(override val name: String, var point: Vector3): Entity{
    var rotation = Vector3.zeroVector
    var scale = Vector3.zeroVector
    override fun toString() = name
}