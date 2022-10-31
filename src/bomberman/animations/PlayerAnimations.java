package bomberman.animations;

import bomberman.entity.Entity;
import bomberman.view.Game;
import javafx.scene.image.Image;

public class PlayerAnimations {
    private final Sprite idle;
    private final Sprite moveUp;
    private final Sprite moveLeft;
    private final Sprite moveDown;
    private final Sprite moveRight;

    private final Sprite die;
    private double playSpeed;

    Image playerImage = Game.getPlayerImage();

    public PlayerAnimations(Entity e, int scale) {
        playSpeed = 0.1;
        idle = new Sprite(e, playerImage, 64, playSpeed, 0, 0, 1, 64, 64, scale, true);
        moveRight = new Sprite(e, playerImage, 64, playSpeed, 0, 0, 9, 64, 64, scale, true);
        moveLeft = new Sprite(e, playerImage, 64, playSpeed, 0, 64, 9, 64, 64, scale, true);
        moveDown = new Sprite(e, playerImage, 64, playSpeed, 0, 128, 5, 64, 64, scale, true);
        moveUp = new Sprite(e, playerImage, 64, playSpeed, 0, 192, 5, 64, 64, scale, true);
        die = new Sprite(e,playerImage, 64, 0.5, 0, 256, 4, 64, 64, scale, true);
    }

    public Sprite getPlayerIdleSprite() {
        return idle;
    }


    public Sprite getMoveUpSprite() {
        return moveUp;
    }

    public Sprite getMoveLeftSprite() {
        return moveLeft;
    }

    public Sprite getMoveDownSprite() {
        return moveDown;
    }

    public Sprite getMoveRightSprite() {
        return moveRight;
    }

    public Sprite getDieSprite() {
        return die;
    }
}
