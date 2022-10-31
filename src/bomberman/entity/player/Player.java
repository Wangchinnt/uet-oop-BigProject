package bomberman.entity.player;

import bomberman.Renderer;
import bomberman.animations.PlayerAnimations;
import bomberman.animations.Sprite;
import bomberman.constants.Direction;
import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import bomberman.entity.KillableEntity;
import bomberman.entity.MovingEntity;
import bomberman.entity.boundedbox.RectBoundedBox;
import bomberman.gamecontroller.GameVariables;
import bomberman.view.Game;

import static bomberman.constants.Direction.DOWN;
import static bomberman.constants.Direction.RIGHT;

public class Player implements MovingEntity, KillableEntity {

  public double positionX = 0;

  public double positionY = 0;
  public int bombCount = GameVariables.bombCount;
  public double steps = GameVariables.steps;
  public int timeToDropBoom = 0;
  RectBoundedBox playerBoundary;
  Sprite currentSprite;
  PlayerAnimations playerAnimations;
  Direction currentDirection;
  int layer;
  String name;
  double scale = 1;
  double reduceBoundarySizePercent = 0.1;
  private int health;
  private boolean isAlive;

  public int timeDelayP = 0;

  public Player(int posX, int posY) {
    init(posX, posY);
    health = 100;
    isAlive = true;
    layer = 2;
  }

  private void init(int x, int y) {
    name = "Player";
    playerAnimations = new PlayerAnimations(this, 1);
    positionX = x;
    positionY = y;

    playerBoundary =
        new RectBoundedBox(
            positionX + (int) (GlobalConstants.PLAYER_WIDTH * getReduceBoundarySizePercent()),
            positionY + (int) (GlobalConstants.PLAYER_HEIGHT * getReduceBoundarySizePercent()),
            (int) (GlobalConstants.PLAYER_WIDTH * getScale())
                - 2 * +(int) (GlobalConstants.PLAYER_WIDTH * getReduceBoundarySizePercent()),
            (int) (GlobalConstants.PLAYER_HEIGHT * getScale())
                - 2 * +(int) (GlobalConstants.PLAYER_HEIGHT * getReduceBoundarySizePercent()));

    currentSprite = playerAnimations.getPlayerIdleSprite();
  }

  private void setCurrentSprite(Sprite s) {
    if (s != null) {
      currentSprite = s;
    } else {
      System.out.println("Sprite missing!");
    }
  }

  public int getHealth() {
    return health;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public String toString() {
    return name;
  }

  @Override
  public boolean isColliding(Entity b) {
    // playerBoundary.setPosition(positionX, positionY);
    RectBoundedBox otherEntityBoundary = b.getBoundingBox();
    return playerBoundary.checkCollision(otherEntityBoundary);
  }

  @Override
  public void draw() {
    Renderer.playAnimation(currentSprite);
  }

  @Override
  public void die() {
    currentSprite = playerAnimations.getDieSprite();
    timeDelayP = 0;
    isAlive = false;
  }

  @Override
  public void update() {
    if (!isAlive) {
      timeDelayP++;
    }
    if (timeDelayP == 55) {
      Renderer.createGameOver();
      Game.gameStatus = GlobalConstants.GameStatus.GameOver;
      removeFromScene();
    }
    this.setTimeToDropBoom();
    this.decrementTimeToDropBoom();
  }

  private boolean checkCollisions(double x, double y) {
    playerBoundary.setPosition(x, y, getReduceBoundarySizePercent());

    for (Entity e : Game.getEntities()) {
      if (e != this && isColliding(e) && !e.isPlayerCollisionFriendly()) {
        playerBoundary.setPosition(positionX, positionY, getReduceBoundarySizePercent());
        /*System.out.println("Player x="+getPositionX()+" y="
        +getPositionY()+" colliding with x="+e.getPositionX()
        +" y="+e.getPositionY());*/

        return false;
      }
    }
    playerBoundary.setPosition(positionX, positionY, getReduceBoundarySizePercent());
    return true;
  }

  @Override
  public void move(double steps, Direction direction) {
    if (steps == 0) {
      setCurrentSprite(playerAnimations.getPlayerIdleSprite());
    } else {
      switch (direction) {
        case UP:
          if (checkCollisions(positionX, positionY - steps)) {
            positionY -= steps;
            setCurrentSprite(playerAnimations.getMoveUpSprite());
            currentDirection = Direction.UP;
          }
          break;
        case DOWN:
          if (checkCollisions(positionX, positionY + steps)) {
            positionY += steps;
            setCurrentSprite(playerAnimations.getMoveDownSprite());
            currentDirection = DOWN;
          }
          break;
        case LEFT:
          if (checkCollisions(positionX - steps, positionY)) {
            positionX -= steps;
            setCurrentSprite(playerAnimations.getMoveLeftSprite());
            currentDirection = Direction.LEFT;
          }
          break;
        case RIGHT:
          if (checkCollisions(positionX + steps, positionY)) {
            positionX += steps;
            setCurrentSprite(playerAnimations.getMoveRightSprite());
            currentDirection = RIGHT;
          }
          break;
        default:
          setCurrentSprite(playerAnimations.getPlayerIdleSprite());
          break;
      }
    }
  }

  @Override
  public void reduceHealth(int damage) {
    if (health - damage <= 0) {
      die();
    } else {
      health -= damage;
    }
  }

  @Override
  public void removeFromScene() {
    Game.getEntities().remove(this);
    Game.getPlayers().remove(this);
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
    playerBoundary.setPosition(positionX, positionY, getReduceBoundarySizePercent());
    return playerBoundary;
  }

  @Override
  public boolean isPlayerCollisionFriendly() {
    return false;
  }

  @Override
  public int getLayer() {
    return layer;
  }

  @Override
  public double getScale() {
    return scale;
  }

  public void setScale(double scale) {
    this.scale = scale;
  }

  public double getReduceBoundarySizePercent() {
    return reduceBoundarySizePercent;
  }

  public void setReduceBoundarySizePercent(double reduceBoundarySizePercent) {
    this.reduceBoundarySizePercent = reduceBoundarySizePercent;
  }

  public boolean hasMoreBombs() {
    return bombCount > 0;
  }

  public void incrementBombCount() {
    ++bombCount;
  }

  public void decrementBombCount() {
    --bombCount;
  }

  public int getTimeToDropBoom() {
    return timeToDropBoom;
  }

  public void setTimeToDropBoom() {
    if (timeToDropBoom < -60) {
      timeToDropBoom = 0;
    }
  }

  public void decrementTimeToDropBoom() {
    --timeToDropBoom;
  }

  public double getSteps() {
    return steps;
  }

  public void setSteps(double steps) {
    this.steps = steps;
  }
  public int getBombCount() {
    return bombCount;
  }

  public void setBombCount(int bombCount) {
    this.bombCount = bombCount;
  }
}
