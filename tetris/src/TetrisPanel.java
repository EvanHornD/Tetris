

import GameFiles.*;
import Input.KeyBindsManager;
import Rendering.*;
import java.awt.*;
import javax.swing.*;

public final class TetrisPanel extends JPanel {

    //#region   Class attributes
    gameTimer gameTimer;
    static KeyBindsManager keyBinds;
    static GraphicsRenderer renderer;
    static int panelWidth;
    static int panelHeight;
    static double screenRatio;
    ShapeEntity backGround = new ShapeEntity(new Color(24,24,24));
    //#endregion

    TetrisPanel(Dimension dimensions){
        this.setPreferredSize(dimensions);
        this.setFocusable(true);
        this.requestFocus();
        keyBinds = new KeyBindsManager(this);
        renderer = new GraphicsRenderer(this);
        gameTimer = new gameTimer();
        panelWidth = (int)dimensions.getWidth();
        panelHeight = (int)dimensions.getHeight();
        screenRatio = panelWidth/(1920.);
        startGameTimer();
    }

    // -------------
    // Start Game
    // -------------

    public void startGameTimer() {
        gameTimer.start(deltaTime -> {
            // Update game state
            runGameLoop(deltaTime);
            keyBinds.updateFrameInformation();

            // Trigger the repaint
            renderer.triggerRepaint();
        });
    }

    // -------------
    // Run Game Logic
    // -------------
    public static void runGameLoop(double dt){
    }

    public void render(Graphics2D g2d) {
        backGround.render(g2d);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        renderer.draw(g2d);
    }
}

