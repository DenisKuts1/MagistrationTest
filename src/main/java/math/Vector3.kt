package math

class Vector3(val x: Float, val y: Float, val z: Float) {

    operator fun plus(other: Vector3) = Vector3(
            x + other.x,
            y + other.y,
            z + other.z)

    operator fun minus(other: Vector3) = Vector3(
            x - other.x,
            y - other.y,
            z - other.z)

    operator fun compareTo(other: Vector3): Int{
        val difference = length() - other.length()
        return when{
            difference > 0 -> difference.toInt() + 1
            difference < 0 -> difference.toInt() - 1
            else -> 0
        }
    }



    fun normalize(): Vector3 {
        val length = length()
        return Vector3(
                x / length,
                y / length,
                z / length
        )
    }

    fun length() = Math.sqrt(x * x + y * y + z * z.toDouble()).toFloat()
}