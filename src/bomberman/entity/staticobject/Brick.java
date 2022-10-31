package bomberman.entity.staticobject;

import bomberman.Renderer;
import bomberman.animations.Sprite;
import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import bomberman.entity.KillableEntity;
import bomberman.entity.boundedbox.RectBoundedBox;
import bomberman.gamecontroller.GameVariables;
import bomberman.view.Game;

public class Brick implements KillableEntity {

    private final int width;
    private final int height;

    private final Sprite sprite;

    private Sprite currentSprite;
    private final Sprite brokenSprite;

    public double positionX = 0;
    public double positionY = 0;
    RectBoundedBox entityBoundary;

    int layer;

    double scale = 1;

    private boolean isAlive;

    int timeDelay = 0;


    public Brick(int x, int y) {
        positionX = x;
        positionY = y;
        width = 64;
        height = 64;
        layer = 3;
        isAlive = true;
        sprite = new Sprite(this, GlobalConstants.mapImage, 64, 0.1, 0, 64, 1, width, height, 1, false);
        brokenSprite = new Sprite(this, GlobalConstants.mapImage, 64, 0.3, 64, 64, 4, width, height, 1, true);

        entityBoundary =
                new RectBoundedBox(
                        (int) positionX, positionY, (int) (width * getScale()), (int) (height * getScale()));
        currentSprite = sprite;
    }

    @Override
    public boolean isColliding(Entity b) {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        if (!isAlive) {
            currentSprite = brokenSprite;
            timeDelay++;
        }
        if (timeDelay == 30) {
            GameVariables.score += 10;
            Game.getLabelList().get(2).setText("Score: " + GameVariables.score);
            removeFromScene();
        }
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return false; // To change body of
    }

    @Override
    public void draw() {
        if (currentSprite != null) {
            Renderer.playAnimation(currentSprite);
        }
    }

    @Override
    public void removeFromScene() {
        Game.getBricks().remove(this);
        Game.getEntities().remove(this);
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
    public int getLayer() {
        return layer;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public void die() {
        timeDelay = 0;
        isAlive = false;
    }

    @Override
    public void reduceHealth(int damage) {
    }

    @Override
    public int getHealth() {
        return 0;
    }

}
