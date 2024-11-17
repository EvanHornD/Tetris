package ProjectFiles.Input;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MouseInputManager {

    private HashMap<Integer,String> mouseInputMap = new HashMap<Integer,String>();
    private HashMap<String,AbstractAction> mouseActionMap = new HashMap<String,AbstractAction>();
    private KeyBindsManager keyBinds;

    public MouseInputManager(JComponent component, KeyBindsManager keyBinds){
        this.keyBinds = keyBinds;
        setMouseListeners(component);
    }

    private void setMouseListeners(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Map.Entry<Integer,String> entry : mouseInputMap.entrySet()) {
                    if(e.getButton()==entry.getKey()){
                        AbstractAction action = mouseActionMap.get(entry.getValue());
                        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for (Map.Entry<Integer,String> entry : mouseInputMap.entrySet()) {
                    if(e.getButton()+5==entry.getKey()){
                        AbstractAction action = mouseActionMap.get(entry.getValue());
                        action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                    }
                }
            }
        });
        component.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent mouse) {
                keyBinds.mouseCoords[0] = mouse.getX();
                keyBinds.mouseCoords[1] = mouse.getY();
            }
        });
    }

    public void addNewAction(String actionName) {
        AbstractAction mousePressed =
            new AbstractAction() {
                String action = actionName;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (keyBinds.keyFrames.get(action) == 0) {
                        keyBinds.keyFrames.replace(action, 1);
                    }
                }
            };
        AbstractAction mouseReleased =
            new AbstractAction() {
                String action = actionName;

                @Override
                public void actionPerformed(ActionEvent e) {
                    keyBinds.keyFrames.replace(action, 2);
                }
            };
        keyBinds.keyFrames.put(actionName, 0);
        mouseActionMap.put(actionName, mousePressed);
        mouseActionMap.put(actionName + "Released", mouseReleased);
    }

    public void addMouseBinding(String mousePressed, String action) {
        mouseInputMap.put(getMouseInput(mousePressed), action);
        mouseInputMap.put(getMouseInput(mousePressed)+5, action + "Released");
    }

    public void removeKeyBinding(String mousePressed) {
        mouseInputMap.remove(getMouseInput(mousePressed));
    }

    private final HashMap<String,Integer> MouseInput = new HashMap<>(Map.of(
        "BUTTON1", 1,
        "BUTTON2", 2,
        "BUTTON3", 3,
        "BUTTON4", 4,
        "BUTTON5", 5
    ));
    public Integer getMouseInput(String mouseInput){
        return MouseInput.get(mouseInput);
    }
}
