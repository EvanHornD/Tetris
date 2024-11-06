
import java.awt.*;
import javax.swing.JFrame;

public final class TetrisWindow {
        
    private JFrame frame;
    TetrisPanel panel;

    public TetrisWindow() {
        initialize();
    }

    public static Dimension getScreenDimensions(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (screenSize);
    }

    public void initialize(){
        frame = new JFrame();
        this.frame.setTitle("Game");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension screenDimensions = getScreenDimensions();
        int width = (int)screenDimensions.getWidth();
        int height = (int)screenDimensions.getHeight();

        this.frame.setSize(width, height);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setVisible(true);

        panel = new TetrisPanel(screenDimensions);
        this.frame.add(panel);
    }
}
