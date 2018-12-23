package engine

import app.Window
import math.ID
import math.Sphere
import math.Vector3
import java.util.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode

object Simulator {
    val spheres = ArrayList<Sphere>()
    val mesh = ObjImporter.import("/objects/sphere.obj")

    fun addSphere() {
        val random = Random()
        var intersects: Boolean
        do {
            intersects = false
            val newSphere = Sphere("Sphere", Vector3(
                    random.nextFloat() * 8 - 4,
                    random.nextFloat() * 8 - 4,
                    random.nextFloat() * 8 - 4), 1f)
            spheres.forEach { sphere ->
                if (sphere.intersects(newSphere)) intersects = true
            }
            if (!intersects) {
                spheres.add(newSphere)
                Window.scene.add(DefaultMutableTreeNode(newSphere.name))
                Window.treeView.updateUI()
                Window.tree.updateUI()
                println(newSphere.name)
            } else {
                ID--
            }
        } while (intersects)
    }

    fun clearSpheres() {
        spheres.clear()
    }

    fun findSphereByName(name: String) = spheres.find { it.name == name }!!

    val step = 0.1f
    var running = false

    fun moveRandomCircle() {
        if(spheres.size < 2) return
        val random = Random()
        val index1 = random.nextInt(spheres.size)
        var index2: Int
        do {
            index2 = random.nextInt(spheres.size)
        } while (index1 == index2)
        val movingSphere = spheres[index1]
        val targetSphere = spheres[index2]
        val backupCoordinates = movingSphere.center
        movingSphere.center = moveTowards(movingSphere.center, targetSphere.center, step)
        for (sphere in spheres) {
            if (sphere === movingSphere) continue
            if (movingSphere.intersects(sphere)) {
                movingSphere.center = backupCoordinates
                return
            }
        }
        movingSphere.remake()
    }

    fun moveTowards(start: Vector3, end: Vector3, step: Float): Vector3 {
        val direction = (end - start).normalize()
        return start + direction * step

    }
}