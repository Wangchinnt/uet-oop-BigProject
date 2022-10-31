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

import static bomberman.view.Game.getPlayer;

public class SpeedItem implements StaticEntity {

    public double positionX;

    public double positionY;

    int height;

    int width;
    RectBoundedBox entityBoundary;
    int layer;
    double scale = 1;
    private final Sprite sprite;

    public SpeedItem(double x, double y) {
        positionX = x;
        positionY = y;
        height = 64;
        width = 64;
        layer = 0;
        sprite =
                new Sprite(
                        this, GlobalConstants.itemImage, 64, 0.1, 0, 128, 4, width, height, scale, true);
        entityBoundary =
                new RectBoundedBox(
                        positionX, positionY, (int) (width * getScale()), (int) (height * getScale()));
    }

    @Override
    public boolean isColliding(Entity b) {
        RectBoundedBox otherEntityBoundary = b.getBoundingBox();
        return entityBoundary.checkCollision(otherEntityBoundary);
    }

    @Override
    public void update() {
        Player p = getPlayer();
        if (isColliding(getPlayer())) {
            GameVariables.steps += 0.5;
            p.setSteps(GameVariables.steps);
            Game.getLabelList().get(6).setText(Double.toString(GameVariables.steps));
            try {
                Game.playSound(2);
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
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
        Game.getSpeedItems().remove(this);
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
