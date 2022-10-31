package bomberman.animations;

import bomberman.entity.Entity;
import bomberman.utils.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Sprite {
    public double playSpeed;
    public int spriteLocationOnSheetX;
    public int spriteLocationOnSheetY;
    public int numberOfFrames;
    public double width;
    public double height;
    public int actualSize;
    public boolean reversePlay;
    public Image spriteImage;
    public Image[] spriteImages;
    public boolean hasValidSpriteImages;
    public Entity entityReference;
    private double scale;

    public Sprite(Entity e, Image imageSheet, int actualSize, double playSpeed, int spriteLocationOnSheetX, int spriteLocationOnSheetY, int numberOfFrames, double width, double height, double scale, boolean leftToRight) {
        super();
        spriteImage = imageSheet;
        this.actualSize = actualSize;
        this.playSpeed = playSpeed;
        this.spriteLocationOnSheetX = spriteLocationOnSheetX;
        this.spriteLocationOnSheetY = spriteLocationOnSheetY;
        this.numberOfFrames = numberOfFrames;
        this.width = width;
        this.height = height;
        this.scale = scale;
        reversePlay = leftToRight;
        this.entityReference = e;
    }

    public Sprite(Entity e, int actualSize, double playSpeed, Image spriteSheet, List<Rectangle> specifications, double width, double height, double scale, boolean leftToRight) {
        super();
        this.actualSize = actualSize;
        this.playSpeed = playSpeed;
        this.numberOfFrames = specifications.size();
        this.width = width;
        this.height = height;
        this.scale = scale;
        reversePlay = leftToRight;
        this.entityReference = e;
        hasValidSpriteImages = true;
        spriteImages = new Image[specifications.size()];
        for (int i = 0; i < specifications.size(); i++) {
            Rectangle specification = specifications.get(i);
            int x = (int) specification.getX();
            int y = (int) specification.getY();
            int w = (int) specification.getWidth();
            int h = (int) specification.getHeight();

            //To DO Check dimensions provided are not going out of sprite sheet dimensions\
            spriteImages[i] = ImageUtils.crop(spriteSheet, x, y, w, h);
        }
    }


    public double getXPosition() {
        return entityReference.getPositionX();
    }

    public double getYPosition() {
        return entityReference.getPositionY();
    }

    public Entity getEntityReference() {
        return entityReference;
    }

    public void setEntityReference(Entity entityReference) {
        this.entityReference = entityReference;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

}

