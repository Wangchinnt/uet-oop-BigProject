package bomberman.map;

import bomberman.entity.enermy.Robot;
import bomberman.entity.enermy.Zombie;
import bomberman.entity.player.Player;
import bomberman.entity.staticobject.Brick;
import bomberman.entity.staticobject.Grass;
import bomberman.entity.staticobject.Portal;
import bomberman.entity.staticobject.Wall;
import bomberman.entity.staticobject.item.BombItem;
import bomberman.entity.staticobject.item.FlameItem;
import bomberman.entity.staticobject.item.SpeedItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static bomberman.constants.GlobalConstants.CELL_SIZE;
import static bomberman.view.Game.*;

public class Map {

  private int level;
  public File mapFile;
  private int col;

  private int row;


  public Map(File file) {
    mapFile = file;
  }

  public int getLevel() {
    return level;
  }

  public void LoadMap() throws IOException {
    Vector<Wall> walls = new Vector<>();
    Vector<Grass> grasses = new Vector<>();
    Vector<Brick> bricks = new Vector<>();
    Vector<Zombie> zombies = new Vector<>();
    Vector<Robot> robots = new Vector<>();
    Vector<Portal> portals = new Vector<>();
    Vector<SpeedItem> speedItems = new Vector<>();
    Vector<FlameItem> flameItems = new Vector<>();
    Vector<BombItem> bombItems = new Vector<>();
    boolean playerSet = false;

    try (BufferedReader inputStream = new BufferedReader(new FileReader(mapFile))) {
      String line;
      String firstLine = inputStream.readLine();
      String str = "";
      List<String> tempList = new ArrayList();
      for (int j = 0; j < firstLine.length(); j++) {
        if (firstLine.charAt(j) != ' ') {
          str += firstLine.charAt(j);
        } else {
          tempList.add(str);
          str = "";
        }
        if (j == firstLine.length() - 1) {
          tempList.add(str);
        }
      }
      level = Integer.parseInt(tempList.get(0));
      row = Integer.parseInt(tempList.get(1));
      col = Integer.parseInt(tempList.get(2));
      for (int y = 1; y <= row; y++) {
        line = inputStream.readLine();
        for (int x = 0; x < col; x++) {
          grasses.add(new Grass(x * CELL_SIZE, y * CELL_SIZE));
          switch (line.charAt(x)) {
            case '#':
              walls.add(new Wall(x * CELL_SIZE, y * CELL_SIZE));
              break;
            case '*':
              bricks.add(new Brick(x * CELL_SIZE, y * CELL_SIZE));
              break;
            case 'p':
              setPlayer(new Player(x * CELL_SIZE, y * CELL_SIZE));
              playerSet = true;
              break;
            case '1':
              zombies.add(new Zombie(x * CELL_SIZE, y * CELL_SIZE));
              break;
            case '2':
              robots.add(new Robot(x * CELL_SIZE, y * CELL_SIZE));
              break;
            case 'x':
              portals.add(new Portal(x * CELL_SIZE, y * CELL_SIZE));
              bricks.add(new Brick(x * CELL_SIZE, y * CELL_SIZE));
              break;
            case 's':
              speedItems.add(new SpeedItem(x * CELL_SIZE, y * CELL_SIZE));
              bricks.add(new Brick(x * CELL_SIZE, y * CELL_SIZE));
              break;
            case 'f':
              flameItems.add(new FlameItem(x * CELL_SIZE, y * CELL_SIZE));
              bricks.add(new Brick(x * CELL_SIZE, y * CELL_SIZE));
              break;
            case 'b':
              bombItems.add(new BombItem(x * CELL_SIZE, y * CELL_SIZE));
              bricks.add(new Brick(x * CELL_SIZE, y * CELL_SIZE));
              break;
          }
        }
      }
    }

    if (!playerSet) {
      System.err.println("No player location is set on map.");
      System.exit(1);
    }

    for (Wall wall : walls) {
      addWallToGame(wall);
    }
    for (Grass grass : grasses) {
      addGrassToGame(grass);
    }
    for (Brick brick : bricks) {
      addBrickToGame(brick);
    }
    for (Zombie zombie : zombies) {
      addZombieToGame(zombie);
    }
    for (Robot robot :robots) {
      addRobotToGame(robot);
    }
    for (Portal portal : portals) {
      addPortalToGame(portal);
    }
    for (SpeedItem speedItem : speedItems) {
      addSpeedItemToGame(speedItem);
    }
    for (FlameItem flameItem : flameItems) {
      addFlameItemToGame(flameItem);
    }
    for (BombItem bombItem : bombItems) {
      addBombItemToGame(bombItem);
    }
  }
  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }




}
