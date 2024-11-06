package Input;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class KeyBoardManager {
        
    private final InputMap inputMap;
    private final ActionMap actionMap;
    private final KeyBindsManager keyBinds;

    public KeyBoardManager(JComponent component, KeyBindsManager keyBinds) {
        this.inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        this.actionMap = component.getActionMap();
        this.keyBinds = keyBinds;
    }

    // Sets up actions for key press and key release
    public void addNewAction(String actionName) {
        AbstractAction keyPressed =
            new AbstractAction() {
                String action = actionName;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (keyBinds.keyActions.get(action) == 0) {
                        keyBinds.keyActions.replace(action, 1);
                    }
                }
            };
        AbstractAction keyReleased =
            new AbstractAction() {
                String action = actionName;

                @Override
                public void actionPerformed(ActionEvent e) {
                    keyBinds.keyActions.replace(action, 2);
                }
            };
        keyBinds.keyFrames.put(actionName, 0);
        actionMap.put(actionName, keyPressed);
        actionMap.put(actionName + "Released", keyReleased);
    }

    public void addKeyBinding(String keyPressed, String action) {
        inputMap.put(KeyStroke.getKeyStroke(keyPressed), action);
        inputMap.put(KeyStroke.getKeyStroke("released " + keyPressed), action + "Released");
    }

    public void removeKeyBinding(String keyPressed) {
        inputMap.remove(KeyStroke.getKeyStroke(keyPressed));
    }
}
