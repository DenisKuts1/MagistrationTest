package math

class Vector2(val u: Float, val v: Float) {
    override fun equals(other: Any?): Boolean {
        if(other !is Vector2) return false
        return u == other.u && v == other.v
    }
}