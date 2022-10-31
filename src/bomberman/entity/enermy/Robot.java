package bomberman.entity.enermy;

import bomberman.Renderer;
import bomberman.animations.RobotAnimations;
import bomberman.animations.Sprite;
import bomberman.constants.Direction;
import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import bomberman.entity.KillableEntity;
import bomberman.entity.MovingEntity;
import bomberman.entity.boundedbox.RectBoundedBox;
import bomberman.entity.player.Player;
import bomberman.gamecontroller.GameVariables;
import bomberman.view.Game;

import java.util.Random;

import static bomberman.constants.Direction.*;
import static bomberman.view.Game.getPlayer;
import static java.lang.Math.round;

public class Robot implements MovingEntity, KillableEntity {

  private Direction currentDirection;

  private double positionX;

  private double positionY;

  private RectBoundedBox robotBoundary;
  private Sprite currentSprite;

  private RobotAnimations robotAnimations;

  private int health;

  private boolean isAlive;

  private boolean onPath = true;

  private double speed;

  public int timeDelayR = 0;

  int layer;

  String name;

  double scale = 1;
  double reduceBoundarySizePercent = 0;

  public Robot(double posX, double posY) {
    init(posX, posY);
    layer = 2;
  }

  private void init(double posX, double posY) {
    name = "Robot";
    robotAnimations = new RobotAnimations(this);
    positionX = posX;
    positionY = posY;
    robotAnimations = new RobotAnimations(this);
    robotBoundary =
        new RectBoundedBox(
            positionX + (int) (GlobalConstants.ZOMBIE_WIDTH * getReduceBoundarySizePercent()),
            positionY + (int) (GlobalConstants.ZOMBIE_HEIGHT * getReduceBoundarySizePercent()),
            (int) (GlobalConstants.ZOMBIE_WIDTH * getScale())
                - 2 * +(int) (GlobalConstants.ZOMBIE_WIDTH * getReduceBoundarySizePercent()),
            (int) (GlobalConstants.ZOMBIE_HEIGHT * getScale())
                - 2 * +(int) (GlobalConstants.ZOMBIE_HEIGHT * getReduceBoundarySizePercent()));
    isAlive = true;
    speed = 1;
    currentSprite = robotAnimations.getIdle();
    currentDirection = getRandomDirection();
  }

  @Override
  public void update() {
    if (!isAlive) {
      timeDelayR++;
    } else autoMove();
    if (timeDelayR == 40) {
      removeFromScene();
      int sum = Game.getZombies().size() + Game.getRobots().size();
      GameVariables.score += 50;
      Game.getLabelList().get(3).setText("Enemy: " + sum);
      Game.getLabelList().get(2).setText("Score: " + GameVariables.score);
    }
  }

  @Override
  public boolean isColliding(Entity b) {
    RectBoundedBox otherEntityBoundary = b.getBoundingBox();
    return robotBoundary.checkCollision(otherEntityBoundary);
  }

  private boolean checkCollisions(double x, double y) {
    robotBoundary.setPosition(x, y, getReduceBoundarySizePercent());

    for (Entity e : Game.getEntities()) {
      if (e != this && isColliding(e) && !e.isPlayerCollisionFriendly()) {
        robotBoundary.setPosition(positionX, positionY, getReduceBoundarySizePercent());
        if (e instanceof Player) {
          onPath = false;
          ((Player) e).die();
        }

        return false;
      }
    }
    robotBoundary.setPosition(positionX, positionY, getReduceBoundarySizePercent());
    return true;
  }

  private void setCurrentSprite(Sprite s) {
    if (s != null) {
      currentSprite = s;
    } else {
      System.out.println("Sprite missing!");
    }
  }

  private void autoMove() {
    int x = (int) round(getPlayer().positionX + 5) / 64;
    int y = (int) round(getPlayer().positionY + 6) / 64;
    if (onPath) {
      searchPath(x, y);
    }
    move(speed, currentDirection);
  }

  public void move(double steps, Direction direction) {
    if (steps == 0) {
      setCurrentSprite(robotAnimations.getIdle());
    } else {
      switch (direction) {
        case UP:
          if (checkCollisions(positionX, positionY - speed)) {
            positionY -= steps;
            setCurrentSprite(robotAnimations.getMoveUp());
            currentDirection = UP;
          } else currentDirection = getRandomDirection();
          break;
        case DOWN:
          if (checkCollisions(positionX, positionY + speed)) {
            positionY += steps;
            setCurrentSprite(robotAnimations.getMoveDown());
            currentDirection = DOWN;
          } else currentDirection = getRandomDirection();
          break;
        case LEFT:
          if (checkCollisions(positionX - speed, positionY)) {
            positionX -= steps;
            setCurrentSprite(robotAnimations.getMoveLeft());
            currentDirection = LEFT;
          } else currentDirection = getRandomDirection();
          break;
        case RIGHT:
          if (checkCollisions(positionX + speed, positionY)) {
            positionX += steps;
            setCurrentSprite(robotAnimations.getMoveRight());
            currentDirection = RIGHT;
          } else currentDirection = getRandomDirection();
          break;
        default:
          setCurrentSprite(robotAnimations.getIdle());
      }
    }
  }

  @Override
  public boolean isPlayerCollisionFriendly() {
    return false;
  }

  @Override
  public void draw() {
    if (currentSprite != null) {
      Renderer.playAnimation(currentSprite);
    }
  }

  @Override
  public void removeFromScene() {
    Game.getEntities().remove(this);
    Game.getRobots().remove(this);
  }

  @Override
  public void die() {
    currentSprite = robotAnimations.getDie();
    timeDelayR = 0;
    isAlive = false;
    speed = 0;
  }

  public void searchPath(int goalCol, int goalRow) {

    int startCol = (int) round(positionX) / 64;
    int startRow = (int) round(positionY) / 64;

    Game.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

    if (Game.pFinder.search()) {
      int nextX = Game.pFinder.pathList.get(0).col * 64;
      int nextY = Game.pFinder.pathList.get(0).row * 64;

      if (positionX < nextX && checkCollisions(positionX + speed, positionY)) {
        currentDirection = RIGHT;
      } else if (positionX > nextX && checkCollisions(positionX - speed, positionY)) {
        currentDirection = LEFT;
      } else if (positionY < nextY && checkCollisions(positionX, positionY + speed)) {
        currentDirection = DOWN;
      } else if (positionY > nextY && checkCollisions(positionX, positionY - speed)) {
        currentDirection = UP;
      }
      int nextCol = Game.pFinder.pathList.get(0).col;
      int nextRow = Game.pFinder.pathList.get(0).row;

      if (nextCol == goalCol && nextRow == goalRow) {
        onPath = false;
      }
    }
  }

  @Override
  public double getPositionX() {
    return positionX;
  }

  @Override
  public double getPositionY() {
    return positionY;
  }

  @Override
  public RectBoundedBox getBoundingBox() {
    return robotBoundary;
  }

  @Override
  public int getLayer() {
    return layer;
  }

  @Override
  public double getScale() {
    return scale;
  }

  @Override
  public void reduceHealth(int damage) {}

  @Override
  public int getHealth() {
    return 0;
  }

  public Direction getRandomDirection() {
    Random random = new Random();
    int i = random.nextInt(4) + 1;
    Direction randomDirection = null;
    if (i == 1) {
      randomDirection = DOWN;
    }
    if (i == 2) {
      randomDirection = UP;
    }
    if (i == 3) {
      randomDirection = LEFT;
    }
    if (i == 4) {
      randomDirection = RIGHT;
    }
    return randomDirection;
  }

  public double getReduceBoundarySizePercent() {
    return reduceBoundarySizePercent;
  }
}
