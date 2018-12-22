package math

data class Vertex(val position: Vector3, val textureCoordinate: Vector2, val normal: Vector3) {
    override fun equals(other: Any?): Boolean {
        if(other !is Vertex) return false
        return position == other.position && normal == other.normal && textureCoordinate == other.textureCoordinate
    }

    operator fun get(index : Int) = when(index){
        0 -> position.x
        1 -> position.y
        2 -> position.z
        3 -> textureCoordinate.u
        4 -> textureCoordinate.v
        5 -> normal.x
        6 -> normal.y
        7 -> normal.z
        else -> -1f
    }
}