package app

import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.util.FPSAnimator
import engine.Simulator
import math.Sphere
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.BoxLayout
import jogamp.graph.font.typecast.ot.Mnemonic.CEILING
import math.Vector3
import java.math.RoundingMode
import java.text.DecimalFormat


object Window : JFrame("") {

    lateinit var runMenuItem: JMenuItem
    lateinit var stopMenuItem: JMenuItem
    lateinit var addSphereMenuItem: JMenuItem

    lateinit var scene: DefaultMutableTreeNode
    lateinit var tree: JTree
    lateinit var treeView: JScrollPane

    lateinit var positionX: JTextField
    lateinit var positionY: JTextField
    lateinit var positionZ: JTextField

    lateinit var colorX: JTextField
    lateinit var colorY: JTextField
    lateinit var colorZ: JTextField

    lateinit var canvas: GLCanvas
    lateinit var animator: FPSAnimator

    lateinit var selectedSphere: Sphere

    init {
        setUpMenu()
        setUpTree()
        setUpPropertiesPanel()
        setUpCanvas()
        setSize(850, 600)
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

    fun setUpMenu() {
        val menuBar = JMenuBar()
        val fileMenu = JMenu("File")
        val editMenu = JMenu("Edit")
        val runMenu = JMenu("Run")
        addSphereMenuItem = JMenuItem("Add Sphere")
        runMenuItem = JMenuItem("Run")
        stopMenuItem = JMenuItem("Stop")
        val clearMenuItem = JMenuItem("Clear")
        menuBar.add(fileMenu)
        menuBar.add(editMenu)
        menuBar.add(runMenu)
        editMenu.add(addSphereMenuItem)
        editMenu.add(clearMenuItem)
        runMenu.add(runMenuItem)
        runMenu.add(stopMenuItem)
        contentPane.add(menuBar, BorderLayout.PAGE_START)

        clearMenuItem.addActionListener {
            Simulator.clearSpheres()
        }

        addSphereMenuItem.addActionListener {
            Simulator.addSphere()
        }
        runMenuItem.addActionListener {
            Simulator.running = true
        }
        stopMenuItem.addActionListener {
            Simulator.running = false
        }
    }

    fun setUpTree() {
        scene = DefaultMutableTreeNode("Scene")
        tree = JTree(scene)
        treeView = JScrollPane(tree)
        treeView.preferredSize = Dimension(100, 500)
        contentPane.add(treeView, BorderLayout.LINE_START)
        tree.addTreeSelectionListener {
            val name = (it.path.lastPathComponent as DefaultMutableTreeNode).toString()
            if(name != "Scene") {
                val df = DecimalFormat("#.###")
                df.roundingMode = RoundingMode.CEILING
                selectedSphere = Simulator.findSphereByName(name)
                positionX.text = df.format(selectedSphere.center.x).toString()
                positionY.text = df.format(selectedSphere.center.y).toString()
                positionZ.text = df.format(selectedSphere.center.z).toString()
                colorX.text = df.format(selectedSphere.color.x).toString()
                colorY.text = df.format(selectedSphere.color.y).toString()
                colorZ.text = df.format(selectedSphere.color.z).toString()
            }
        }
    }

    fun setUpPropertiesPanel() {
        val propertiesPanel = JPanel()
        propertiesPanel.layout = BoxLayout(propertiesPanel, BoxLayout.PAGE_AXIS)
        val positionLabel = JLabel("Position")
        val positionPanel = JPanel()
        positionX = JTextField()
        positionY = JTextField()
        positionZ = JTextField()
        positionX.preferredSize = Dimension(60, 20)
        positionY.preferredSize = Dimension(60, 20)
        positionZ.preferredSize = Dimension(60, 20)
        propertiesPanel.add(positionLabel)
        propertiesPanel.add(positionPanel)

        positionPanel.add(positionX)
        positionPanel.add(positionY)
        positionPanel.add(positionZ)

        val colorLabel = JLabel("Color")
        val colorPanel = JPanel()
        colorX = JTextField()
        colorY = JTextField()
        colorZ = JTextField()
        colorX.preferredSize = Dimension(60, 20)
        colorY.preferredSize = Dimension(60, 20)
        colorZ.preferredSize = Dimension(60, 20)
        propertiesPanel.add(colorLabel)
        propertiesPanel.add(colorPanel)

        colorPanel.add(colorX)
        colorPanel.add(colorY)
        colorPanel.add(colorZ)

        val button = JButton("Edit")
        propertiesPanel.add(button)
        button.addActionListener {
            selectedSphere.center = Vector3(
                    positionX.text.replace(",",".").toFloat(),
                    positionY.text.replace(",",".").toFloat(),
                    positionZ.text.replace(",",".").toFloat())
            selectedSphere.color = Vector3(
                    colorX.text.replace(",",".").toFloat(),
                    colorY.text.replace(",",".").toFloat(),
                    colorZ.text.replace(",",".").toFloat())
            selectedSphere.remake()
        }

        contentPane.add(propertiesPanel, BorderLayout.LINE_END)
    }

    fun setUpCanvas() {
        val profile = GLProfile.get(GLProfile.GL4)
        val capabilities = GLCapabilities(profile)
        canvas = GLCanvas(capabilities).apply {
            addGLEventListener(Engine)
            setSize(500, 500)
            addKeyListener(Engine)
        }
        contentPane.add(canvas, BorderLayout.CENTER)
        canvas.requestFocusInWindow()
        animator = FPSAnimator(canvas, 60, true)
        animator.start()
    }


}