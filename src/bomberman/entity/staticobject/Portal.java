package bomberman.entity.staticobject;

import bomberman.Renderer;
import bomberman.animations.Sprite;
import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import bomberman.entity.StaticEntity;
import bomberman.entity.boundedbox.RectBoundedBox;
import bomberman.entity.player.Player;
import bomberman.view.Game;

public class Portal implements StaticEntity {
    public double positionX;

    public double positionY;

    int height;

    int width;
    RectBoundedBox entityBoundary;
    int layer;
    double scale = 1;

    private final Sprite sprite;

    public Portal(double x, double y) {
        positionX = x;
        positionY = y;
        height = 64;
        width = 64;
        layer = 0;
        sprite = new Sprite(this, GlobalConstants.mapImage, 64, 0.1, 0, 0, 1, width, height, 1, false);
        entityBoundary = new RectBoundedBox(positionX, positionY, (int) (width * getScale()), (int) (height * getScale()));
    }

    @Override
    public boolean isColliding(Entity b) {
        RectBoundedBox otherEntityBoundary = b.getBoundingBox();
        return entityBoundary.checkCollision(otherEntityBoundary);
    }

    @Override
    public void update() {
        Player p = Game.getPlayer();
        if (isColliding(p)) {
            Game.setLevel(Game.getLevel() + 1);
            Game.reset();
            Game.createNewGame(Game.getGameStage());
        }
    }

    @Override
    public boolean isPlayerCollisionFriendly() {
        return true;
    }

    @Override
    public void draw() {
        Renderer.playAnimation(sprite);
    }

    @Override
    public void removeFromScene() {

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
}
