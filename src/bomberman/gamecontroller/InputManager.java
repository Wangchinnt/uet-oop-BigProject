package bomberman.gamecontroller;

import bomberman.constants.Direction;
import bomberman.constants.GlobalConstants;
import bomberman.entity.player.Player;
import bomberman.entity.staticobject.BlackBomb;
import bomberman.view.Game;
import javafx.scene.input.KeyCode;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

public class InputManager {
    public static void handle() {

        List keyboardInputs = EventHandler.getInputList();
        Player player = Game.getPlayer();
        double steps = player.getSteps();
        // System.err.println("" + keyboardInputs);
        if (player.isAlive()) {
            if (keyboardInputs.contains(KeyCode.UP) || keyboardInputs.contains(KeyCode.W)) {
                player.move(steps, Direction.UP);
            } else if (keyboardInputs.contains(KeyCode.DOWN) || keyboardInputs.contains(KeyCode.S)) {
                player.move(steps, Direction.DOWN);
            } else if (keyboardInputs.contains(KeyCode.LEFT) || keyboardInputs.contains(KeyCode.A)) {
                player.move(steps, Direction.LEFT);
            } else if (keyboardInputs.contains(KeyCode.RIGHT) || keyboardInputs.contains(KeyCode.D)) {
                player.move(steps, Direction.RIGHT);
            } else if (!keyboardInputs.contains(KeyCode.LEFT)
                    && !keyboardInputs.contains(KeyCode.RIGHT)
                    && !keyboardInputs.contains(KeyCode.UP)
                    && !keyboardInputs.contains(KeyCode.DOWN)
                    && !keyboardInputs.contains(KeyCode.W)
                    && !keyboardInputs.contains(KeyCode.A)
                    && !keyboardInputs.contains(KeyCode.S)
                    && !keyboardInputs.contains(KeyCode.D)) {
                player.move(0, Direction.DOWN);
            }
            if (keyboardInputs.contains(KeyCode.J)) {
                if (player.hasMoreBombs() && player.getTimeToDropBoom() < 0) {
                    BlackBomb bomb = new BlackBomb(player.getPositionX(), player.getPositionY());
                    Game.addBlackBombsToGame(bomb);
                    try {
                        Game.playSound(1);
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    player.timeToDropBoom = 15;
                    player.decrementBombCount();
                }
            }
            if (keyboardInputs.contains(KeyCode.P)) {
                    Game.gameStatus = GlobalConstants.GameStatus.Paused;
                }
        }
    }
}
