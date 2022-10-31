package bomberman.animations;

import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;

public class ZombieAnimations {
    private final Sprite idle;

    private final Sprite moveUp;

    private final Sprite moveDown;

    private final Sprite moveLeft;

    private final Sprite moveRight;

    private final Sprite die;

    private double playSpeed;

    public Sprite getDie() {
        return die;
    }

    public ZombieAnimations(Entity e) {
        playSpeed = 0.1;

        idle = new Sprite(e, GlobalConstants.zombieImage, 64, playSpeed, 0, 128, 1, 64, 64, 1, false);
        moveUp = new Sprite(e, GlobalConstants.zombieImage, 64, playSpeed, 0, 192, 6, 64, 64, 1, true);
        moveDown = new Sprite(e, GlobalConstants.zombieImage, 64, playSpeed, 0, 128, 6, 64, 64, 1, true);
        moveLeft = new Sprite(e, GlobalConstants.zombieImage, 64, playSpeed, 0, 64, 8, 64, 64, 1, true);
        moveRight = new Sprite(e, GlobalConstants.zombieImage, 64, playSpeed, 0, 0, 8, 64, 64, 1, true);
        die = new Sprite(e, GlobalConstants.zombieImage, 64, 0.5, 0, 256, 5, 64, 64, 1, true);

    }

    public Sprite getIdle() {
        return idle;
    }

    public Sprite getMoveUp() {
        return moveUp;
    }

    public Sprite getMoveDown() {
        return moveDown;
    }

    public Sprite getMoveLeft() {
        return moveLeft;
    }

    public Sprite getMoveRight() {
        return moveRight;
    }
}
