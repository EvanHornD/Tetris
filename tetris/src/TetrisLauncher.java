import javax.swing.SwingUtilities;

public class TetrisLauncher {
            public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
            TetrisWindow menu = new TetrisWindow();
            menu.panel.startGameTimer();
        });
    }

}
