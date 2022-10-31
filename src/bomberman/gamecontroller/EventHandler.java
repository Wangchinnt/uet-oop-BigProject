package bomberman.gamecontroller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class EventHandler {
    public static char lastKeyPress;
    public static char lastKeyReleased;

    public static ArrayList<KeyCode> inputList = new ArrayList<>();

    public static void attachEventHandlers(Scene s) {
        keyReleaseHanlder krh = new keyReleaseHanlder();
        keyPressedHandler kph = new keyPressedHandler();
        s.setOnKeyReleased(krh);
        s.setOnKeyPressed(kph);
    }

    public static List getInputList() {
        return inputList;
    }

    public boolean isKeyDown(KeyCode k) {
        return inputList.contains(k);
    }

    public abstract void handle(MouseEvent event);
}

class keyReleaseHanlder implements javafx.event.EventHandler<KeyEvent> {
    public keyReleaseHanlder() {
    }

    @Override
    public void handle(KeyEvent evt) {
        //System.out.println(
        //"The key released is : " + evt.getText() + " with keycode " + evt.getCode().getName());

        KeyCode code = evt.getCode();

        EventHandler.inputList.remove(code);
    }
}

class keyPressedHandler implements javafx.event.EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent evt) {
        //System.out.println(
        //"The key pressed is : " + evt.getText() + " with keycode " + evt.getCode().getName());
        KeyCode code = evt.getCode();

        // only add once... prevent duplicates
        if (!EventHandler.inputList.contains(code)) EventHandler.inputList.add(code);
    }
}
