package bomberman.animations;

import bomberman.constants.GlobalConstants;
import bomberman.entity.Entity;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class BombAnimations {

    private Sprite blackBomb;

    private final Sprite popCenter;

    private final Sprite popLeft;

    private final Sprite popRight;

    private final Sprite popUp;

    private final Sprite popDown;


    private double playSpeed;


    public BombAnimations(Entity e) {
        playSpeed = 0.2;

        List<Rectangle> specs = new ArrayList<>();
        List<Rectangle> specs1 = new ArrayList<>();
        List<Rectangle> specs2 = new ArrayList<>();
        List<Rectangle> specs3 = new ArrayList<>();
        List<Rectangle> specs4 = new ArrayList<>();

        //specs.add(new Rectangle(0, 128, 64, 64));
        //specs.add(new Rectangle(64, 128, 64, 64));
        specs.add(new Rectangle(192, 128, 64, 64));

        specs1.add(new Rectangle(0, 64, 64, 64));
        specs2.add(new Rectangle(64, 64, 64, 64));
        specs3.add(new Rectangle(128, 64, 64, 64));
        specs4.add(new Rectangle(192, 64, 64, 64));


        blackBomb = new Sprite(e, GlobalConstants.bombImage, 64, playSpeed, 0, 0, 3, 64, 64, 1, true);

        popCenter = new Sprite(e, 64, 1, GlobalConstants.bombImage, specs, 64, 64, 1, false);

        popLeft = new Sprite(e, 64, playSpeed, GlobalConstants.bombImage, specs1, 64, 64, 1, false);

        popRight = new Sprite(e, 64, playSpeed, GlobalConstants.bombImage, specs2, 64, 64, 1, false);

        popUp = new Sprite(e, 64, playSpeed, GlobalConstants.bombImage, specs3, 64, 64, 1, false);

        popDown = new Sprite(e, 64, playSpeed, GlobalConstants.bombImage, specs4, 64, 64, 1, false);

    }

    public Sprite getBlackBomb() {
        return blackBomb;
    }

    public void setBlackBomb(Sprite blackBomb) {
        this.blackBomb = blackBomb;
    }

    public Sprite getPopCenter() {
        return popCenter;
    }

    public Sprite getPopLeft() {
        return popLeft;
    }

    public Sprite getPopRight() {
        return popRight;
    }

    public Sprite getPopUp() {
        return popUp;
    }

    public Sprite getPopDown() {
        return popDown;
    }

    public double getPlaySpeed() {
        return playSpeed;
    }
}

