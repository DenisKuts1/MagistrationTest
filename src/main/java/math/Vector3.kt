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

    operator fun compareTo(other: Vector3): Int {
        val difference = length() - other.length()
        return when {
            difference > 0 -> difference.toInt() + 1
            difference < 0 -> difference.toInt() - 1
            else -> 0
        }
    }

    operator fun compareTo(other: Float): Int {
        val difference = length() - other
        return when {
            difference > 0 -> difference.toInt() + 1
            difference < 0 -> difference.toInt() - 1
            else -> 0
        }
    }

    operator fun times(value: Float) = Vector3(x * value, y * value, z * value)

    fun normalize(): Vector3 {
        val length = length()
        return Vector3(
                x / length,
                y / length,
                z / length
        )
    }

    fun length() = Math.sqrt(x * x + y * y + z * z.toDouble()).toFloat()

    companion object {
        @JvmStatic
        val zeroVector = Vector3(0f, 0f, 0f)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector3) return false
        return x == other.x && y == other.y && z == other.z
    }

    override fun toString(): String {
        return "[x = $x, y = $y, z = $z]"
    }
}
