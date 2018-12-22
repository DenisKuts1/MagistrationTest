import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.util.FPSAnimator
import javax.swing.JFrame
import javax.swing.JPanel

class Window: JFrame("") {
    init {
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
        add(panel)
        setSize(500, 500)
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val animator = FPSAnimator(canvas, 60, true)
        animator.start()
    }
}