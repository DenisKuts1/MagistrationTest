import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.awt.GLCanvas
import javafx.scene.Parent
import javafx.scene.layout.BorderPane
import javafx.embed.swing.SwingNode
import tornadofx.*
import javax.swing.JButton
import javax.swing.JPanel

fun main(args: Array<String>) {
    launch<Main>(args)
}

class Main : App(Scene::class)

class Scene : View() {
    override val root = borderpane {
        center = SwingNode().apply {
            val profile = GLProfile.get(GLProfile.GL4)
            val capabilities = GLCapabilities(profile)
            val canvas = GLCanvas(capabilities).apply {
                setSize(500, 500)
                addGLEventListener(Engine)
                addKeyListener(Engine)
            }
            val panel = JPanel().apply {
                add(canvas)
            }
            content = panel
        }

        left = vbox {
            treeview<String> {

            }
        }
    }
}