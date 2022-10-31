package bomberman.entity.staticobject;

import bomberman.Renderer;
import bomberman.animations.BombAnimations;
import bomberman.animations.Sprite;
import bomberman.entity.Entity;
import bomberman.entity.KillableEntity;
import bomberman.entity.StaticEntity;
import bomberman.entity.boundedbox.RectBoundedBox;
import bomberman.entity.enermy.Robot;
import bomberman.entity.enermy.Zombie;
import bomberman.entity.player.Player;
import bomberman.gamecontroller.GameVariables;
import bomberman.view.Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import static bomberman.constants.GlobalConstants.CELL_SIZE;

public class BlackBomb implements StaticEntity {

  public double positionX = 0;

  public double positionY = 0;
  RectBoundedBox entityBoundary;
  Sprite currentSprite;
  BombAnimations bomb_animations;
  Date addedDate;
  int timerDurationInMillis = 2000;
  STATE bombState;

  enum STATE {
    INACTIVE, // INACTIVE when bomb's timer hasnt yet started
    ACTIVE, // Active when bomb's timer has started and it will explode soon
    EXPLODING, // when bomb is exploding
    DEAD // when the bomb has already exploded
  }

  int layer;
  double scale = 1;
  int sizeOfExploding = GameVariables.sizeOfExploding;
  int l = sizeOfExploding;
  int r = sizeOfExploding;
  int u = sizeOfExploding;
  int d = sizeOfExploding;
  private int height;
  private int width;
  private boolean friendly;

  public BlackBomb(double x, double y) {
    init(x, y);
  }

  private void init(double x, double y) {
    positionX = Math.round((x + 5) / 64) * 64;
    positionY = Math.round((y + 6) / 64) * 64;
    width = 64;
    height = 64;
    layer = 1;
    friendly = true;
    entityBoundary = new RectBoundedBox(positionX, positionY, width, height);
    bomb_animations = new BombAnimations(this);
    currentSprite = bomb_animations.getBlackBomb();
    addedDate = new Date();
    bombState = STATE.ACTIVE;
  }

  private boolean checkCollisions(double x, double y) {
    Vector<Entity> e = Game.getEntities();
    entityBoundary.setPosition(x, y, 0);
    for (int j = e.size() - 1; j >= 0; j--) {
      if (e.get(j) instanceof BlackBomb) {
        if (isExploding() && isColliding(e.get(j))) {
          ((BlackBomb) e.get(j)).setBombState(STATE.EXPLODING);
        }
      }
      if ((e.get(j) instanceof Player || e.get(j) instanceof Zombie || e.get(j) instanceof Robot)
          && isColliding(e.get(j))) {
        ((KillableEntity) e.get(j)).die();
      }
      if ((e.get(j) instanceof Wall || e.get(j) instanceof Brick) && isColliding(e.get(j))) {
        entityBoundary.setPosition(positionX, positionY, 0);
        /*System.out.println(
        "bomb x="
            + getPositionX()
            + " y="
            + getPositionY()
            + " colliding with x="
            + e.getPositionX()
            + " y="
            + e.getPositionY());*/
        return false;
      }
    }
    entityBoundary.setPosition(positionX, positionY, 0);
    return true;
  }

  public void explode() {
    try {
      Game.playSound(3);
    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
      throw new RuntimeException(e);
    }
    expLeft();
    expRight();
    expUp();
    expDown();
  }

  private boolean isExploding() {
    STATE s = checkBombState();
    return s == STATE.ACTIVE && new Date().getTime() > addedDate.getTime() + 1500;
  }

  public void expLeft() {

    Vector<Brick> bricks = Game.getBricks();
    Vector<Entity> entities = Game.getEntities();
    boolean canRemove;
    int b;
    if (this.isExploding()) {
      for (int j = bricks.size() - 1; j >= 0; j--) {
        for (b = sizeOfExploding; b > 0; b--) {
          if (bricks.get(j).getPositionX() == positionX - CELL_SIZE * b
              && bricks.get(j).getPositionY() == positionY) {
            canRemove = true;
            for (Entity e : entities) {
              for (int k = b - 1; k > 0; k--) {
                if ((e instanceof Wall || e instanceof Brick)
                    && e.getPositionX() == positionX - CELL_SIZE * k
                    && e.getPositionY() == positionY) {
                  canRemove = false;
                  break;
                }
              }
            }
            if (canRemove) {
              l = (int) (positionX - bricks.get(j).getPositionX()) / 64 - 1;
              bricks.get(j).die();
            }
          }
        }
      }
    }
  }

  public void expRight() {

    Vector<Brick> bricks = Game.getBricks();
    Vector<Entity> entities = Game.getEntities();
    boolean canRemove;
    int b;
    if (this.isExploding() || this.bombState == STATE.EXPLODING) {
      for (int j = bricks.size() - 1; j >= 0; j--) {
        for (b = sizeOfExploding; b > 0; b--) {
          if (bricks.get(j).getPositionX() == positionX + CELL_SIZE * b
              && bricks.get(j).getPositionY() == positionY) {
            canRemove = true;
            for (Entity e : entities) {
              for (int k = b - 1; k > 0; k--) {
                if ((e instanceof Wall || e instanceof Brick)
                    && e.getPositionX() == positionX + CELL_SIZE * k
                    && e.getPositionY() == positionY) {
                  canRemove = false;
                  break;
                }
              }
            }
            if (canRemove) {
              r = (int) Math.abs(positionX - bricks.get(j).getPositionX()) / 64 - 1;
              bricks.get(j).die();
            }
          }
        }
      }
    }
  }

  public void expUp() {

    Vector<Brick> bricks = Game.getBricks();
    Vector<Entity> entities = Game.getEntities();
    boolean canRemove;
    int b;
    if (this.isExploding()) {
      for (int j = bricks.size() - 1; j >= 0; j--) {
        for (b = sizeOfExploding; b > 0; b--) {
          if (bricks.get(j).getPositionX() == positionX
              && bricks.get(j).getPositionY() == positionY - CELL_SIZE * b) {
            canRemove = true;
            for (Entity e : entities) {
              for (int k = b - 1; k > 0; k--) {
                if ((e instanceof Wall || e instanceof Brick)
                    && e.getPositionX() == positionX
                    && e.getPositionY() == positionY - CELL_SIZE * k) {
                  canRemove = false;
                  break;
                }
              }
            }
            if (canRemove) {
              u = (int) Math.abs(positionY - bricks.get(j).getPositionY()) / 64 - 1;
              bricks.get(j).die();
            }
          }
        }
      }
    }
  }

  public void expDown() {

    Vector<Brick> bricks = Game.getBricks();
    Vector<Entity> entities = Game.getEntities();
    boolean canRemove;
    int b;
    if (this.isExploding()) {
      for (int j = bricks.size() - 1; j >= 0; j--) {
        for (b = sizeOfExploding; b > 0; b--) {
          if (bricks.get(j).getPositionX() == positionX
              && bricks.get(j).getPositionY() == positionY + CELL_SIZE * b) {
            canRemove = true;
            for (Entity e : entities) {
              for (int k = b - 1; k > 0; k--) {
                if ((e instanceof Wall || e instanceof Brick)
                    && e.getPositionX() == positionX
                    && e.getPositionY() == positionY + CELL_SIZE * k) {
                  canRemove = false;
                  break;
                }
              }
            }
            if (canRemove) {
              d = (int) Math.abs(positionY - bricks.get(j).getPositionY()) / 64 - 1;
              bricks.get(j).die();
            }
          }
        }
      }
    }
  }

  public boolean isAlive() {
    STATE s = checkBombState();
    if (s == STATE.DEAD) {
      return false;
    } else {
      if (s == STATE.ACTIVE || s == STATE.INACTIVE) {
        return true;
      }
      return true;
    }
  }

  public void setBombState(STATE bombState) {
    this.bombState = bombState;
  }

  public STATE checkBombState() {
    Date current = new Date();
    if (current.getTime() > timerDurationInMillis + addedDate.getTime()) {
      return STATE.DEAD;
    } else {
      return STATE.ACTIVE;
    }
  }

  @Override
  public void draw() {
    Renderer.playAnimation(currentSprite);
    if (bombState == STATE.EXPLODING || isExploding()) {
      explode();
      checkCollisions(positionX, positionY);
      Renderer.playAnimation(
          bomb_animations.getPopCenter().spriteImages,
          bomb_animations.getPlaySpeed(),
          positionX,
          positionY,
          width,
          height);
      if (l > 0) {
        for (int i = 1; i <= l; i++) {
          if (checkCollisions(positionX - CELL_SIZE * i, positionY)) {
            Renderer.playAnimation(
                bomb_animations.getPopLeft().spriteImages,
                bomb_animations.getPlaySpeed(),
                positionX - CELL_SIZE * i,
                positionY,
                width,
                height);
          } else break;
        }
      }
      if (r > 0) {
        for (int i = 1; i <= r; i++) {
          if (checkCollisions(positionX + CELL_SIZE * i, positionY)) {
            Renderer.playAnimation(
                bomb_animations.getPopRight().spriteImages,
                bomb_animations.getPlaySpeed(),
                positionX + CELL_SIZE * i,
                positionY,
                width,
                height);
          } else break;
        }
      }
      if (u > 0) {
        for (int i = 1; i <= u; i++) {
          if (checkCollisions(positionX, positionY - CELL_SIZE * i)) {
            Renderer.playAnimation(
                bomb_animations.getPopUp().spriteImages,
                bomb_animations.getPlaySpeed(),
                positionX,
                positionY - CELL_SIZE * i,
                width,
                height);
          } else break;
        }
      }
      if (d > 0) {
        for (int i = 1; i <= d; i++) {
          if (checkCollisions(positionX, positionY + CELL_SIZE * i)) {
            Renderer.playAnimation(
                bomb_animations.getPopDown().spriteImages,
                bomb_animations.getPlaySpeed(),
                positionX,
                positionY + CELL_SIZE * i,
                width,
                height);
          } else break;
        }
      }
      // System.out.println(l + "-" + r + "-" + u + "-" + d);
    }
  }

  @Override
  public void removeFromScene() {
    Game.getEntities().remove(this);
    Game.getBlackBombs().remove(this);
    Game.getPlayer().incrementBombCount();
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
    return entityBoundary;
  }

  @Override
  public boolean isColliding(Entity b) {
    RectBoundedBox otherEntityBoundary = b.getBoundingBox();
    return entityBoundary.checkCollision(otherEntityBoundary);
  }

  @Override
  public void update() {
    for (int j = Game.getBlackBombs().size() - 1; j >= 0; j--) {
      if (Game.getBlackBombs().get(j) == this && !isColliding(Game.getPlayer())) {
        Game.getBlackBombs().get(j).setFriendly(false);
      }
      if (Game.getBlackBombs().get(j) == this && !Game.getBlackBombs().get(j).isAlive()) {
        Game.getBlackBombs().get(j).removeFromScene();
      }
    }
  }

  @Override
  public boolean isPlayerCollisionFriendly() {
    return friendly;
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

  public void setFriendly(boolean friendly) {
    this.friendly = friendly;
  }
}
