package bomberman.entity.enermy;

import bomberman.Renderer;
import bomberman.animations.Sprite;
import bomberman.animations.ZombieAnimations;
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

public class Zombie implements MovingEntity, KillableEntity {

    public Direction currentDirection;
    public double positionX = 0;
    public double positionY = 0;
    RectBoundedBox zombieBoundary;
    Sprite currentSprite;
    ZombieAnimations zombieAnimations;
    int layer;
    String name;
    double scale = 1;
    double reduceBoundarySizePercent = 0;
    private int health;
    private boolean isAlive;
    private double speed;

    public int timeDelayZ = 0;

    public Zombie(double posX, double posY) {
        init(posX, posY);
        layer = 2;
    }

    private void init(double posX, double posY) {
        name = "Zombie";
        zombieAnimations = new ZombieAnimations(this);
        positionX = posX;
        positionY = posY;
        zombieBoundary =
                new RectBoundedBox(
                        positionX + (int) (GlobalConstants.ZOMBIE_WIDTH * getReduceBoundarySizePercent()),
                        positionY + (int) (GlobalConstants.ZOMBIE_HEIGHT * getReduceBoundarySizePercent()),
                        (int) (GlobalConstants.ZOMBIE_WIDTH * getScale())
                                - 2 * +(int) (GlobalConstants.ZOMBIE_WIDTH * getReduceBoundarySizePercent()),
                        (int) (GlobalConstants.ZOMBIE_HEIGHT * getScale())
                                - 2 * +(int) (GlobalConstants.ZOMBIE_HEIGHT * getReduceBoundarySizePercent()));
        isAlive = true;
        speed = 0.5;
        currentSprite = zombieAnimations.getIdle();
        currentDirection = getRandomDirection();
    }

    private void setCurrentSprite(Sprite s) {
        if (s != null) {
            currentSprite = s;
        } else {
            System.out.println("Sprite missing!");
        }
    }

    private boolean checkCollisions(double x, double y) {
        zombieBoundary.setPosition(x, y, getReduceBoundarySizePercent());

        for (Entity e : Game.getEntities()) {
            if (e != this && isColliding(e) && !e.isPlayerCollisionFriendly()) {
                zombieBoundary.setPosition(positionX, positionY, getReduceBoundarySizePercent());
                if (e instanceof Player) {
                    ((Player) e).die();
                }
        /*System.out.println(
            "Zombie x="
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
        zombieBoundary.setPosition(positionX, positionY, getReduceBoundarySizePercent());
        return true;
    }

    @Override
    public boolean isColliding(Entity b) {
        RectBoundedBox otherEntityBoundary = b.getBoundingBox();
        return zombieBoundary.checkCollision(otherEntityBoundary);
    }

    @Override
    public void update() {
        if (!isAlive) {
            timeDelayZ++;
        }
        else autoMove();
        if (timeDelayZ == 55) {
            removeFromScene();
            int sum = Game.getZombies().size() + Game.getRobots().size();
            GameVariables.score += 25;
            Game.getLabelList().get(3).setText("Enemy: " + sum);
            Game.getLabelList().get(2).setText("Score: " + GameVariables.score);
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
        Game.getZombies().remove(this);
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
        return zombieBoundary;
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
    public void die() {
        currentSprite = zombieAnimations.getDie();
        timeDelayZ = 0;
        isAlive = false;
        speed = 0;
    }

    @Override
    public void reduceHealth(int damage) {
    }

    @Override
    public int getHealth() {
        return 0;
    }

    public Direction getRandomDirection() {
        Random random = new Random();
        int i = random.nextInt(4) + 1;
        Direction randomDirection = null;
        if (i == 1) {
            randomDirection = UP;
        }
        if (i == 2) {
            randomDirection = DOWN;
        }
        if (i == 3) {
            randomDirection = LEFT;
        }
        if (i == 4) {
            randomDirection = RIGHT;
        }
        return randomDirection;
    }


    public void autoMove() {
        move(speed, currentDirection);
    }

    public void move(double steps, Direction direction) {

        if (steps == 0) {
            setCurrentSprite(zombieAnimations.getIdle());
        } else {
            switch (direction) {
                case UP:
                    if (checkCollisions(positionX, positionY - steps)) {
                        positionY -= steps;
                        setCurrentSprite(zombieAnimations.getMoveUp());
                        currentDirection = UP;
                    } else currentDirection = getRandomDirection();
                    break;
                case DOWN:
                    if (checkCollisions(positionX, positionY + steps)) {
                        positionY += steps;
                        setCurrentSprite(zombieAnimations.getMoveDown());
                        currentDirection = DOWN;
                    } else currentDirection = getRandomDirection();
                    break;
                case LEFT:
                    if (checkCollisions(positionX - steps, positionY)) {
                        positionX -= steps;
                        setCurrentSprite(zombieAnimations.getMoveLeft());
                        currentDirection = LEFT;
                    } else currentDirection = getRandomDirection();
                    break;
                case RIGHT:
                    if (checkCollisions(positionX + steps, positionY)) {
                        positionX += steps;
                        setCurrentSprite(zombieAnimations.getMoveRight());
                        currentDirection = RIGHT;
                    } else currentDirection = getRandomDirection();
                    break;
                default:
                    setCurrentSprite(zombieAnimations.getIdle());
            }
        }
    }


    public double getReduceBoundarySizePercent() {
        return reduceBoundarySizePercent;
    }
}
