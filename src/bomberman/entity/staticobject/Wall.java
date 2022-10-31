package bomberman.entity.staticobject;

import bomberman.Renderer;
import bomberman.animations.Sprite;
import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import bomberman.entity.StaticEntity;
import bomberman.entity.boundedbox.RectBoundedBox;

public class Wall implements StaticEntity {
    private final Sprite sprite;

    public double positionX;

    public double positionY;

    int height = 64;
    int width = 64;
    RectBoundedBox entityBoundary;

    int layer;
    private double scale = 1;


    public Wall(double x, double y) {
        positionX = x;
        positionY = y;
        layer = 4;
        sprite = new Sprite(this, GlobalConstants.mapImage, 64, 0.1, 64, 0, 1, width, height, 1, false);
        entityBoundary = new RectBoundedBox(positionX, positionY, (int) (width * getScale()), (int) (height * getScale()));
    }

    public boolean isColliding(Entity b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        Renderer.playAnimation(sprite);
    }

    @Override
    public void removeFromScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean isPlayerCollisionFriendly() {
        return false;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }


}

