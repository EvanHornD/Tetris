package ProjectFiles.Input;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;

public final class KeyBindsManager { 

    private KeyBoardManager keyBoard;
    private MouseInputManager mouse;

    public Map<String, String> defaultKeyBoardBindings = new HashMap<>(Map.of(
        "UP", "Rotate",
        "W", "Rotate",
        "DOWN", "Down",
        "S", "Down",
        "LEFT", "Left",
        "A", "Left",
        "RIGHT", "Right",
        "D", "Right",
        "SPACE", "QuickDrop",
        "Q", "Store"
    ));

    public Map<String, String> defaultMouseBindings = new HashMap<>(Map.of(
        "BUTTON1", "Input"
    ));

    public Map<String, Integer> keyFrames = new HashMap<>(Map.of(
        "Rotate", 0,
        "Store",0,
        "QuickDrop",0,
        "Down", 0,
        "Left", 0,
        "Right", 0,
        "Input", 0
    ));

    public int[] mouseCoords = {0,0};

    public KeyBindsManager(JComponent component) {
        this.keyBoard = new KeyBoardManager(component,this);
        this.mouse = new MouseInputManager(component,this);
        setDefaultKeyBindings();
    }

    public void setDefaultKeyBindings() {
        for (Map.Entry<String, Integer> entry : keyFrames.entrySet()) {
            keyBoard.addNewAction(entry.getKey());
            mouse.addNewAction(entry.getKey());
        }
        for (Map.Entry<String, String> entry : defaultKeyBoardBindings.entrySet()) {
            keyBoard.addKeyBinding(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : defaultMouseBindings.entrySet()) {
            mouse.addMouseBinding(entry.getKey(),entry.getValue());
        }
    }


    public Object[][] getInformation(){
        Object[][] ActionSet = new Object[keyFrames.size()][];
        int actionNum = 0;
        for (Map.Entry<String,Integer> action : keyFrames.entrySet()) {
            String actionName = action.getKey();
            ActionSet[actionNum] = new Object[]{actionName,action.getValue()};
            actionNum++;
        }
        return ActionSet;
    }

    public Map<String, Integer> getKeyFrames(){
        return this.keyFrames;
    }

    public void updateFrameInformation(){
        for (Map.Entry<String,Integer> action : keyFrames.entrySet()) {
            String actionName = action.getKey();
            Integer actionState = action.getValue();
            if(actionState>0){
                actionState++;
                keyFrames.replace(actionName,actionState);
            } else if(actionState==-1){
                keyFrames.replace(actionName,0);
            }
        }
    }
}
