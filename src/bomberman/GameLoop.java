package bomberman;

import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import bomberman.entity.enermy.Robot;
import bomberman.entity.enermy.Zombie;
import bomberman.entity.player.Player;
import bomberman.entity.staticobject.*;
import bomberman.entity.staticobject.item.BombItem;
import bomberman.entity.staticobject.item.FlameItem;
import bomberman.entity.staticobject.item.SpeedItem;
import bomberman.gamecontroller.GameVariables;
import bomberman.gamecontroller.InputManager;
import bomberman.view.Game;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.Vector;

public class GameLoop {
  static final long startNanoTime = System.nanoTime();
  static double currentGameTime;
  static double oldGameTime;
  static double deltaTime;
  static int times = 0;

  static int count = 0;

  public static double getCurrentGameTime() {
    return currentGameTime;
  }

  public static void start(GraphicsContext gc) {
    new AnimationTimer() {
      public void handle(long currentNanoTime) {
        oldGameTime = currentGameTime;
        currentGameTime = (currentNanoTime - startNanoTime) / 1000000000.0;
        deltaTime = currentGameTime - oldGameTime;
        gc.clearRect(0, 0, GlobalConstants.CANVAS_WIDTH, GlobalConstants.CANVAS_WIDTH);
        if (Game.gameStatus == GlobalConstants.GameStatus.Running) {
          updateGame();
          count = 0;
        }
        if (Game.gameStatus == GlobalConstants.GameStatus.Paused) {
          count++;
          if (count == 1) {
            Renderer.renderImage(
                GlobalConstants.pauseButton,
                GlobalConstants.SCENE_WIDTH / 2 - 130,
                GlobalConstants.SCENE_HEIGHT / 2 - 50,
                true);
          }
        }
        if (Game.gameStatus == GlobalConstants.GameStatus.GameOver) {
          stop();
        }
        renderGame();
        Game.getGamePane()
            .getScene()
            .getWindow()
            .setOnCloseRequest(
                event -> {
                  event.consume();
                  Game.createWindowExit((Stage) Game.getGamePane().getScene().getWindow());
                });
        try {
          Thread.sleep((long) ((1000.0 / 120d) - deltaTime));
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }.start();
  }

  public static void updateGame() {
    times++;
    InputManager.handle();
    Vector<Entity> entities = Game.getEntities();
    for (int i = entities.size() - 1; i >= 0; i--) {
      entities.get(i).update();
    }
    if (times == 60) {
      GameVariables.time--;
      Game.getLabelList().get(1).setText("Time: " + GameVariables.time);
      times = 0;
    }
  }

  public static void renderGame() {
    for (Grass grass : Game.getGrasses()) {
      grass.draw();
    }
    for (Portal portal : Game.getPortals()) {
      portal.draw();
    }
    for (SpeedItem speedItem : Game.getSpeedItems()) {
      speedItem.draw();
    }
    for (FlameItem flameItem : Game.getFlameItems()) {
      flameItem.draw();
    }
    for (BombItem bombItem : Game.getBombItems()) {
      bombItem.draw();
    }
    for (BlackBomb blackBomb : Game.getBlackBombs()) {
      blackBomb.draw();
    }
    for (Player player : Game.getPlayers()) {
      player.draw();
    }
    for (Zombie zombie : Game.getZombies()) {
      zombie.draw();
    }
    for (Robot robot : Game.getRobots()) {
      robot.draw();
    }
    for (Wall wall : Game.getWalls()) {
      wall.draw();
    }
    for (Brick brick : Game.getBricks()) {
      brick.draw();
    }
  }
}
