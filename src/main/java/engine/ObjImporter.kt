package engine

import math.Vector2
import math.Vector3
import java.io.BufferedReader
import java.io.FileReader

object ObjImporter {
    fun import(path: String): Mesh {
        BufferedReader(FileReader(ObjImporter::class.java.getResource(path).path)).run {
            val coordinates = ArrayList<Vector3>()
            val normals = ArrayList<Vector3>()
            val textureCoordinates = ArrayList<Vector2>()
            val vertices = ArrayList<Vertex>()
            val indices = ArrayList<Int>()
            while (ready()) {
                val line = readLine()
                if (line.isEmpty()) continue
                val components = line.split(" ")
                when (components[0]) {
                    "v" -> coordinates += Vector3(
                            components[1].toFloat() / 100,
                            components[2].toFloat() / 100,
                            components[3].toFloat() / 100)
                    "vn" -> normals += Vector3(
                            components[1].toFloat(),
                            components[2].toFloat(),
                            components[3].toFloat())
                    "vt" -> textureCoordinates += Vector2(
                            components[1].toFloat(),
                            components[2].toFloat())
                    "f" -> {
                        components.takeLast(3).forEach { vertexIds ->
                            val ids = vertexIds.split("/")
                            val coordinateId = ids[0].toInt() - 1
                            val textureId = ids[1].toInt() - 1
                            val normalId = ids[2].toInt() - 1
                            val vertex = Vertex(
                                    coordinates[coordinateId],
                                    textureCoordinates[textureId],
                                    normals[normalId]
                            )
                            if (vertices.contains(vertex)) {
                                indices += vertices.indexOf(vertex)
                            } else {
                                vertices += vertex
                                indices += vertices.size - 1
                            }
                        }
                    }
                }
            }

            val objName = path.substringAfterLast("/").substringBefore(".")
            val textures = HashMap<String, String>()
            textures["diffuseTexture"] = "/objects/${objName}_normal.png"
            textures["normalTexture"] = "/objects/${objName}_normal.png"

            return Mesh(vertices, indices, textures)
        }
    }
}