package bomberman.view;

import bomberman.GameLoop;
import bomberman.Renderer;
import bomberman.Sound;
import bomberman.entity.Entity;
import bomberman.entity.enermy.Astar.PathFinder;
import bomberman.entity.enermy.Robot;
import bomberman.entity.enermy.Zombie;
import bomberman.entity.player.Player;
import bomberman.entity.staticobject.*;
import bomberman.entity.staticobject.item.BombItem;
import bomberman.entity.staticobject.item.FlameItem;
import bomberman.entity.staticobject.item.SpeedItem;
import bomberman.gamecontroller.EventHandler;
import bomberman.gamecontroller.GameVariables;
import bomberman.map.Map;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import static bomberman.constants.GlobalConstants.*;

public class Game {

  private static int level = 1;

  private static Image playerImage;

  public static void setMusicEnabled(boolean musicEnabled) {
    Game.musicEnabled = musicEnabled;
  }

  private static boolean musicEnabled;

  public static GameStatus gameStatus;

  private static List<Label> labelList = new ArrayList<Label>();

  public static Image getPlayerImage() {
    return playerImage;
  }

  public static void setPlayerImage(Image playerImage) {
    Game.playerImage = playerImage;
  }

  private static AnchorPane gamePane;
  private static Scene gameScene;
  private static Canvas gameCanvas;
  private static GraphicsContext gc;

  private static Stage gameStage;

  private static List<Map> maps;

  private static Sound sound;
  private static Player sandboxPlayer;
  private static Comparator<Entity> layerComparator =
      new Comparator<Entity>() {
        @Override
        public int compare(Entity o1, Entity o2) {
          return Integer.compare(o1.getLayer(), o2.getLayer());
        }
      };

  public static PathFinder pFinder;

  private static final Vector<Grass> grasses = new Vector<Grass>();
  private static final Vector<Portal> portals = new Vector<Portal>();
  private static final Vector<SpeedItem> speedItems = new Vector<>();

  private static final Vector<FlameItem> flameItems = new Vector<>();

  private static final Vector<BombItem> bombItems = new Vector<>();

  private static final Vector<BlackBomb> blackBombs = new Vector<BlackBomb>();
  private static final Vector<Player> players = new Vector<Player>();
  private static final Vector<Zombie> zombies = new Vector<Zombie>();

  private static final Vector<Robot> robots = new Vector<Robot>();
  private static final Vector<Wall> walls = new Vector<Wall>();
  private static final Vector<Brick> bricks = new Vector<Brick>();
  private static final Vector<Entity> entities = new Vector<Entity>();

  private static void init() {
    gamePane = new AnchorPane();
    gameScene = new Scene(gamePane, SCENE_WIDTH, SCENE_HEIGHT);
    gameScene.setFill(Color.rgb(233, 222, 186));
    gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    gamePane.getChildren().add(gameCanvas);
    gameStage = new Stage();
    gameStage.setScene(gameScene);
    gameStage.setTitle(GAME_NAME);
    gc = gameCanvas.getGraphicsContext2D();
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(2);
    gc.setFill(Color.BLUE);
    GameLoop.start(gc);
    Renderer.init();
    // load map
    maps = new ArrayList<Map>();
    maps.add(new Map(mapFile));
    maps.add(new Map(mapFile2));
    try {
      maps.get(level - 1).LoadMap();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    sound = new Sound();
    createLabel();
    pFinder = new PathFinder();
    EventHandler.attachEventHandlers(gameScene);
    Game.gameStatus = GameStatus.Running;
  }

  public static void playSound(int i)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    sound.setAudio(i);
    if (musicEnabled) sound.playSound();
    else sound.pauseAudio();
  }

  public static void createNewGame(Stage menuStage) {
    init();
    menuStage.hide();
    gameStage.show();
  }

  public static void createWindowExit(Stage stage) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exit");
    alert.setHeaderText("Na");
    alert.setContentText("Do you want to exit?");
    if (alert.showAndWait().get() == ButtonType.OK) {
      stage.close();
    }
  }

  public static void reset() {
    GameVariables.reset();
    Game.getLabelList().clear();
    Game.getGamePane().getChildren().clear();
    Game.getEntities().clear();
    Game.getPlayers().clear();
    Game.getBombItems().clear();
    Game.getFlameItems().clear();
    Game.getSpeedItems().clear();
    Game.getBricks().clear();
    Game.getZombies().clear();
    Game.getRobots().clear();
    Game.getWalls().clear();
    Game.getPortals().clear();
    Game.getGraphicsContext().restore();
    Game.getGameStage().close();
  }

  public static List<Label> getLabelList() {
    return labelList;
  }

  public static void createLabel() {
    Label labelLvl = new Label("Level " + maps.get(level - 1).getLevel()); // 0
    labelLvl.setFont(new Font("Pixel", 23));
    labelLvl.setLayoutX(20);
    labelLvl.setLayoutY(35);
    labelList.add(labelLvl);

    Label labelTime = new Label("Time: " + GameVariables.time); // 1
    labelTime.setFont(new Font("Pixel", 23));
    labelTime.setLayoutX(170);
    labelTime.setLayoutY(35);
    labelList.add(labelTime);

    Label labelScore = new Label("Score: " + GameVariables.score); // 2
    labelScore.setFont(new Font("Pixel", 23));
    labelScore.setLayoutX(320);
    labelScore.setLayoutY(35);
    labelList.add(labelScore);

    int sum = zombies.size() + robots.size();
    Label labelEnemy = new Label("Enemy: " + sum); // 3
    labelEnemy.setFont(new Font("Pixel", 23));
    labelEnemy.setLayoutX(470);
    labelEnemy.setLayoutY(35);

    labelList.add(labelEnemy);

    Label labelBombItem = new Label(Integer.toString(GameVariables.bombCount)); // 4
    labelBombItem.setFont(new Font("Pixel", 23));
    labelBombItem.setLayoutX(623);
    labelBombItem.setLayoutY(35);
    labelList.add(labelBombItem);

    Label labelFlameItem = new Label(Integer.toString(GameVariables.sizeOfExploding)); // 5
    labelFlameItem.setFont(new Font("Pixel", 23));
    labelFlameItem.setLayoutX(682);
    labelFlameItem.setLayoutY(35);
    labelList.add(labelFlameItem);

    Label labelSpeedItem = new Label(Double.toString(GameVariables.steps)); // 6
    labelSpeedItem.setFont(new Font("Pixel", 23));
    labelSpeedItem.setLayoutX(734);
    labelSpeedItem.setLayoutY(35);
    labelList.add(labelSpeedItem);

    gamePane.getChildren().addAll(labelList);
  }

  public static void addGrassToGame(Grass e) {
    if (!grasses.contains(e)) {
      grasses.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addBlackBombsToGame(BlackBomb e) {
    if (!blackBombs.contains(e)) {
      blackBombs.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addPortalToGame(Portal e) {
    if (!portals.contains(e)) {
      portals.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addSpeedItemToGame(SpeedItem e) {
    if (!speedItems.contains(e)) {
      speedItems.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addFlameItemToGame(FlameItem e) {
    if (!flameItems.contains(e)) {
      flameItems.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addBombItemToGame(BombItem e) {
    if (!bombItems.contains(e)) {
      bombItems.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addPlayerToGame(Player e) {
    if (!players.contains(e)) {
      players.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addZombieToGame(Zombie e) {
    if (!zombies.contains(e)) {
      zombies.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addRobotToGame(Robot e) {
    if (!robots.contains(e)) {
      robots.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addWallToGame(Wall e) {
    if (!walls.contains(e)) {
      walls.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static void addBrickToGame(Brick e) {
    if (!bricks.contains(e)) {
      bricks.add(e);
      entities.add(e);
      entities.sort(layerComparator);
    }
  }

  public static Scene getScene() {
    return gameScene;
  }

  public static GraphicsContext getGraphicsContext() {
    return gc;
  }

  public static Canvas getCanvas() {
    return gameCanvas;
  }

  public static Player getPlayer() {
    return sandboxPlayer;
  }

  public static void setPlayer(Player p) {
    sandboxPlayer = p;
    addPlayerToGame(p);
  }

  public static Map getMap() {
    return maps.get(level - 1);
  }

  public static void setLevel(int level) {
    Game.level = level;
  }

  public static AnchorPane getGamePane() {
    return gamePane;
  }

  public static int getLevel() {
    return level;
  }

  public static Stage getGameStage() {
    return gameStage;
  }

  public static Vector<Grass> getGrasses() {
    return grasses;
  }

  public static Vector<Entity> getEntities() {
    return entities;
  }

  public static Vector<BlackBomb> getBlackBombs() {
    return blackBombs;
  }

  public static Vector<Portal> getPortals() {
    return portals;
  }

  public static Vector<SpeedItem> getSpeedItems() {
    return speedItems;
  }

  public static Vector<FlameItem> getFlameItems() {
    return flameItems;
  }

  public static Vector<BombItem> getBombItems() {
    return bombItems;
  }

  public static Vector<Player> getPlayers() {
    return players;
  }

  public static Vector<Zombie> getZombies() {
    return zombies;
  }

  public static Vector<Robot> getRobots() {
    return robots;
  }

  public static Vector<Wall> getWalls() {
    return walls;
  }

  public static Vector<Brick> getBricks() {
    return bricks;
  }
}
