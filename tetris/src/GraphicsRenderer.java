
import java.awt.Graphics2D;
import javax.swing.*;

public class GraphicsRenderer {
 
    private TetrisPanel panel;
 
    public GraphicsRenderer(TetrisPanel panel) {
        this.panel = panel;
    }
 
    public void draw(Graphics2D g2d) {
        panel.render(g2d);
    }
 
    public void triggerRepaint() {
        SwingUtilities.invokeLater(panel::repaint);
    }
}
