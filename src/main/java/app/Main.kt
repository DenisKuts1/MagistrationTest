package app

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

    /*println(app.Main::class.java.getResource("objects/sphere.obj").path)
    val scene = Importer().readFile("E:/sphere.obj")
    scene?.meshes?.forEach {mesh ->
        println(mesh.name)
    }*/
    //launch<app.Main>(args)
    Window
}
/*
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
                        addGLEventListener()
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
            app.treeView = treeview(Hierarchy.tree) {
                selectionModel.selectedItemProperty().addListener { _, _, newValue ->
                    if(newValue!= null){
                        val entity = newValue.value
                        app.PropertiesPanel.run {
                            app.PropertiesPanel.enable()
                            app.PropertiesPanel.positionX.text = entity.point.x.toString()
                            app.PropertiesPanel.positionY.text = entity.point.y.toString()
                            app.PropertiesPanel.positionZ.text = entity.point.z.toString()
                            app.PropertiesPanel.rotationX.text = entity.rotation.x.toString()
                            app.PropertiesPanel.rotationY.text = entity.rotation.y.toString()
                            app.PropertiesPanel.rotationZ.text = entity.rotation.z.toString()
                            app.PropertiesPanel.scalePropertyX.text = entity.scale.x.toString()
                            app.PropertiesPanel.scalePropertyY.text = entity.scale.y.toString()
                            app.PropertiesPanel.scalePropertyZ.text = entity.scale.z.toString()
                        }
                    } else {
                        app.PropertiesPanel.disable()
                    }
                }
            }

        }
        right {
            vbox {
                add(app.PropertiesPanel)
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
            app.PropertiesPanel.positionX = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
            label("Y")
            app.PropertiesPanel.positionY = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
            label("Z")
            app.PropertiesPanel.positionZ = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
        }
        label("Rotation") { }
        hbox {
            prefWidth = 200.0
            label("X")
            app.PropertiesPanel.rotationX = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
            label("Y")
            app.PropertiesPanel.rotationY = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
            label("Z")
            app.PropertiesPanel.rotationZ = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
        }
        label("Scale") { }
        hbox {
            prefWidth = 200.0
            label("X")
            app.PropertiesPanel.scalePropertyX = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
            label("Y")
            app.PropertiesPanel.scalePropertyY = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
            label("Z")
            app.PropertiesPanel.scalePropertyZ = textfield {
                prefWidth = 40.0
                focusedProperty().addListener(app.propertiesFocusListener)
            }
        }
    }

    fun disable(){
        app.PropertiesPanel.positionX.isDisable = true
        app.PropertiesPanel.positionY.isDisable = true
        app.PropertiesPanel.positionZ.isDisable = true
        app.PropertiesPanel.rotationX.isDisable = true
        app.PropertiesPanel.rotationY.isDisable = true
        app.PropertiesPanel.rotationZ.isDisable = true
        app.PropertiesPanel.scalePropertyX.isDisable = true
        app.PropertiesPanel.scalePropertyY.isDisable = true
        app.PropertiesPanel.scalePropertyZ.isDisable = true
    }
    fun enable(){
        app.PropertiesPanel.positionX.isDisable = false
        app.PropertiesPanel.positionY.isDisable = false
        app.PropertiesPanel.positionZ.isDisable = false
        app.PropertiesPanel.rotationX.isDisable = false
        app.PropertiesPanel.rotationY.isDisable = false
        app.PropertiesPanel.rotationZ.isDisable = false
        app.PropertiesPanel.scalePropertyX.isDisable = false
        app.PropertiesPanel.scalePropertyY.isDisable = false
        app.PropertiesPanel.scalePropertyZ.isDisable = false
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
                val selectedItem = app.treeView.selectionModel.selectedItem
                if (selectedItem == null) {
                    app.treeView.getTreeItem(0).children.add(TreeItem(Sphere("Sphere1", Vector3.zeroVector, 1f)))
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
            app.treeView.selectedValue?.point = Vector3(
                    app.PropertiesPanel.positionX.text.toFloat(),
                    app.PropertiesPanel.positionY.text.toFloat(),
                    app.PropertiesPanel.positionZ.text.toFloat()
            )
            app.treeView.selectedValue?.rotation = Vector3(
                    app.PropertiesPanel.rotationX.text.toFloat(),
                    app.PropertiesPanel.rotationY.text.toFloat(),
                    app.PropertiesPanel.rotationZ.text.toFloat()
            )
            app.treeView.selectedValue?.scale = Vector3(
                    app.PropertiesPanel.scalePropertyX.text.toFloat(),
                    app.PropertiesPanel.scalePropertyY.text.toFloat(),
                    app.PropertiesPanel.scalePropertyZ.text.toFloat()
            )
        }
    }
}
*/