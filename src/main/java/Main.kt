import assimp.Importer
import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.util.FPSAnimator
import javafx.beans.value.ObservableValue
import javafx.embed.swing.SwingNode
import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import math.Dot
import math.Hierarchy
import math.Sphere
import math.Vector3

import tornadofx.*
import javax.swing.JPanel
import javax.swing.SwingUtilities

fun main(args: Array<String>) {

    /*println(Main::class.java.getResource("objects/sphere.obj").path)
    val scene = Importer().readFile("E:/sphere.obj")
    scene?.meshes?.forEach {mesh ->
        println(mesh.name)
    }*/
    //launch<Main>(args)
    Window()
}

class Main : App(Scene::class)

var treeView: TreeView<Dot> by singleAssign()

class Scene : View() {
    val menu: Menu by inject()
    val instrumentPanel: InstrumentPanel by inject()
    override val root = borderpane {
        prefWidth = 1280.0
        prefHeight = 720.0
        border = Border(BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))
        top = vbox {
            add(menu)
            add(instrumentPanel)
        }
        center =vbox {
            add(SwingNode().apply {

                prefWidth = 500.0
                prefHeight = 500.0
                SwingUtilities.invokeLater {
                    val profile = GLProfile.get(GLProfile.GL4)
                    val capabilities = GLCapabilities(profile)
                    val canvas = GLCanvas(capabilities).apply {
                        addGLEventListener(Engine)
                        setSize(500, 500)
                        addKeyListener(Engine)
                    }
                    val panel = JPanel().apply {
                        layout = null
                        setSize(500, 500)
                        add(canvas)
                    }
                    canvas.requestFocusInWindow()
                    val animator = FPSAnimator(canvas, 60, true)
                    animator.start()
                    content = panel
                }
            })

            button()
        }

        left = vbox {
            treeView = treeview(Hierarchy.tree) {
                selectionModel.selectedItemProperty().addListener { _, _, newValue ->
                    if(newValue!= null){
                        val entity = newValue.value
                        PropertiesPanel.run {
                            enable()
                            positionX.text = entity.point.x.toString()
                            positionY.text = entity.point.y.toString()
                            positionZ.text = entity.point.z.toString()
                            rotationX.text = entity.rotation.x.toString()
                            rotationY.text = entity.rotation.y.toString()
                            rotationZ.text = entity.rotation.z.toString()
                            scalePropertyX.text = entity.scale.x.toString()
                            scalePropertyY.text = entity.scale.y.toString()
                            scalePropertyZ.text = entity.scale.z.toString()
                        }
                    } else {
                        PropertiesPanel.disable()
                    }
                }
            }

        }
        right {
            vbox {
                add(PropertiesPanel)
            }
        }
    }
}

object PropertiesPanel: View(){
    var positionX: TextField by singleAssign()
    var positionY: TextField by singleAssign()
    var positionZ: TextField by singleAssign()
    var rotationX: TextField by singleAssign()
    var rotationY: TextField by singleAssign()
    var rotationZ: TextField by singleAssign()
    var scalePropertyX: TextField by singleAssign()
    var scalePropertyY: TextField by singleAssign()
    var scalePropertyZ: TextField by singleAssign()
    override val root = vbox {

        label("Properties") {  }
        label("Position") { }
        hbox {
            prefWidth = 200.0
            label("X")
            positionX = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
            label("Y")
            positionY = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
            label("Z")
            positionZ = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
        }
        label("Rotation") { }
        hbox {
            prefWidth = 200.0
            label("X")
            rotationX = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
            label("Y")
            rotationY = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
            label("Z")
            rotationZ = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
        }
        label("Scale") { }
        hbox {
            prefWidth = 200.0
            label("X")
            scalePropertyX = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
            label("Y")
            scalePropertyY = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
            label("Z")
            scalePropertyZ = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(propertiesFocusListener)
            }
        }
    }

    fun disable(){
        positionX.isDisable = true
        positionY.isDisable = true
        positionZ.isDisable = true
        rotationX.isDisable = true
        rotationY.isDisable = true
        rotationZ.isDisable = true
        scalePropertyX.isDisable = true
        scalePropertyY.isDisable = true
        scalePropertyZ.isDisable = true
    }
    fun enable(){
        positionX.isDisable = false
        positionY.isDisable = false
        positionZ.isDisable = false
        rotationX.isDisable = false
        rotationY.isDisable = false
        rotationZ.isDisable = false
        scalePropertyX.isDisable = false
        scalePropertyY.isDisable = false
        scalePropertyZ.isDisable = false
    }
}

class Menu : View() {
    override val root = menubar {
        menu("File") {

        }
        menu("Edit") {
            menu("Add sphere").action {
                Hierarchy
            }
        }
    }
}

class InstrumentPanel : View() {
    override val root = vbox {

        hbox {
            button("Add Sphere").action {
                val selectedItem = treeView.selectionModel.selectedItem
                if (selectedItem == null) {
                    treeView.getTreeItem(0).children.add(TreeItem(Sphere("Sphere1", Vector3.zeroVector, 1f)))
                } else {
                    selectedItem.children.add(TreeItem(Sphere("Sphere1", Vector3.zeroVector, 1f)))
                }
            }
        }
    }
}

val propertiesFocusListener = {_: ObservableValue<out Boolean>, _: Boolean, newValue: Boolean ->
    if(!newValue){
        PropertiesPanel.run {
            treeView.selectedValue?.point = Vector3(
                    positionX.text.toFloat(),
                    positionY.text.toFloat(),
                    positionZ.text.toFloat()
            )
            treeView.selectedValue?.rotation = Vector3(
                    rotationX.text.toFloat(),
                    rotationY.text.toFloat(),
                    rotationZ.text.toFloat()
            )
            treeView.selectedValue?.scale = Vector3(
                    scalePropertyX.text.toFloat(),
                    scalePropertyY.text.toFloat(),
                    scalePropertyZ.text.toFloat()
            )
        }
    }
}