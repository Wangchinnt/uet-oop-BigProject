package bomberman.entity.staticobject.item;

import bomberman.Renderer;
import bomberman.animations.Sprite;
import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import bomberman.entity.StaticEntity;
import bomberman.entity.boundedbox.RectBoundedBox;
import bomberman.entity.player.Player;
import bomberman.gamecontroller.GameVariables;
import bomberman.view.Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class BombItem implements StaticEntity {
    public double positionX;

    public double positionY;

    int height;

    int width;
    RectBoundedBox entityBoundary;
    int layer;
    double scale = 1;
    private final Sprite sprite;

    public BombItem(double x, double y) {
        positionX = x;
        positionY = y;
        height = 64;
        width = 64;
        layer = 0;
        sprite = new Sprite(this, GlobalConstants.itemImage, 64, 0.1, 0, 0, 4, width, height, scale, true);
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
            GameVariables.bombCount +=1;
            Game.getLabelList().get(4).setText(Integer.toString(GameVariables.bombCount));
            try {
                Game.playSound(2);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
            p.setBombCount(GameVariables.bombCount);
            this.removeFromScene();
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
        Game.getBombItems().remove(this);
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
}
