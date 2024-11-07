import javax.swing.SwingUtilities;
import ProjectFiles.*;

public class TetrisLauncher {
            public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
            TetrisWindow menu = new TetrisWindow();
            menu.panel.startGameTimer();
        });
    }
}
