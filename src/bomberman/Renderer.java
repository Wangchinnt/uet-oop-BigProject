package bomberman;

import bomberman.animations.Sprite;
import bomberman.constants.GlobalConstants;
import bomberman.view.Game;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Renderer {

  public static void init() {
    renderImage(GlobalConstants.bombItem, 600, -5, false);
    renderImage(GlobalConstants.flameItem, 660, -5, false);
    renderImage(GlobalConstants.speedItem, 720, -5, false);

  }

  public static void playAnimation(Sprite sprite) {
    Camera camera = new Camera();
    double time = GameLoop.getCurrentGameTime();
    GraphicsContext gc = Game.getGraphicsContext();
    if (sprite.hasValidSpriteImages) {
      playAnimation(
          sprite.spriteImages,
          sprite.playSpeed,
          sprite.getXPosition(),
          sprite.getYPosition(),
          sprite.width,
          sprite.height);
    }
    playAnimation(
        gc,
        time,
        sprite.actualSize,
        sprite.spriteImage,
        sprite.spriteLocationOnSheetX,
        sprite.spriteLocationOnSheetY,
        sprite.numberOfFrames,
        (int) sprite.getXPosition(),
        (int) sprite.getYPosition(),
        sprite.width,
        sprite.height,
        sprite.getScale(),
        sprite.reversePlay,
        sprite.playSpeed);

    /*double minx = sprite.getEntityReference().getBoundingBox().getBoundary().getMinX();
    double miny = sprite.getEntityReference().getBoundingBox().getBoundary().getMinY();
    double maxx = sprite.getEntityReference().getBoundingBox().getBoundary().getMaxX();
    double maxy = sprite.getEntityReference().getBoundingBox().getBoundary().getMaxY();
    gc.strokeRect(minx - camera.getCamX(), miny, maxx - minx, maxy - miny);*/
  }

  public static void playAnimation(
      GraphicsContext gc,
      double time,
      int actualSize,
      Image imageSheet,
      int startingPointX,
      int startingPointY,
      int numberOfFrames,
      int x,
      int y,
      double width,
      double height,
      double scale,
      boolean reversePlay,
      double playSpeed) {
    double speed = playSpeed >= 0 ? playSpeed : 0.3;
    Camera camera = new Camera();
    // index reporesents the index of image to be drawn from the set of images representing frames
    // of animation
    int index = findCurrentFrame(time, numberOfFrames, speed);

    // newX represents the X coardinate of image in the spritesheet image to be drawn on screen
    int newSpriteSheetX = reversePlay ? startingPointX + index * actualSize : startingPointX;
    // newY represents the X coardinate of image in the spritesheet image to be drawn on screen
    int newSpriteSheetY = reversePlay ? startingPointY : startingPointY + index * actualSize;
    // System.out.println("Time, Total Frames" + time + ", " + numberOfFrames);
    // img,             sx,            sy,         w,     h,      dx,dy,      dw,
    // dh
    gc.drawImage(
        imageSheet,
        newSpriteSheetX,
        newSpriteSheetY,
        width,
        height,
        x - camera.getCamX(),
        y,
        width * scale,
        height * scale);
  }

  public static void playAnimation(
      Image[] imgs, double speed, double x, double y, double w, double h) {
    double time = GameLoop.getCurrentGameTime();
    Camera camera = new Camera();
    GraphicsContext gc = Game.getGraphicsContext();
    int numberOfFrames = imgs.length;
    int index = findCurrentFrame(time, numberOfFrames, speed);
    // System.out.println("index= "+index+" x="+x+" y="+y+" w="+w+" h="+h+" no of
    // frames="+imgs.length+" speed="+speed+" time="+time);
    gc.drawImage(imgs[index], x - camera.getCamX(), y, w, h);
  }

  public static void renderText(String mes, int x, int y) {
    Text text = new Text(x, y, mes);
    text.setFill(Color.BLACK);
    text.setFont(Font.font("Pixel", 23));
    Game.getGamePane().getChildren().add(text);
  }

  public static void renderImage(Image fileImage, int x, int y, boolean isPauseButton) {
    ImageView img = new ImageView(fileImage);
    img.setLayoutX(x);
    img.setLayoutY(y);
    Glow glow = new Glow();
    glow.setLevel(9.0);
    img.setEffect(glow);
    Game.getGamePane().getChildren().add(img);
    if (isPauseButton) {
      img.setPickOnBounds(true);
      img.setOnMouseEntered(
              event -> img.setEffect(new DropShadow()));

      img.setOnMouseClicked(
              new EventHandler<>() {
                @Override
                public void handle(MouseEvent event) {
                  Game.getGamePane().getChildren().remove(img);
                  Game.gameStatus = GlobalConstants.GameStatus.Running;
                }
              });
      img.setOnMouseExited(
              new EventHandler<>() {
                @Override
                public void handle(MouseEvent event) {
                  img.setEffect(null);
                }
              });
    }

  }
  public static void createGameOver() {
    try {
      Game.playSound(4);
    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
      throw new RuntimeException(e);
    }
    ImageView gameOverImage = new ImageView(GlobalConstants.gameOverImage);
    gameOverImage.setLayoutX(0);
    gameOverImage.setLayoutY(0);

    ImageView yes = new ImageView(GlobalConstants.yesImage);
    yes.setPickOnBounds(true);
    yes.setLayoutX(371);
    yes.setLayoutY(433);
    ImageView no = new ImageView(GlobalConstants.noImage);
    no.setPickOnBounds(true);
    no.setLayoutX(492);
    no.setLayoutY(433);
    Game.getGamePane().getChildren().add(gameOverImage);
    Game.getGamePane().getChildren().add(yes);
    Game.getGamePane().getChildren().add(no);

    yes.setOnMouseClicked(
            new EventHandler<>() {
              @Override
              public void handle(MouseEvent event) {
                Game.reset();
                Game.createNewGame(Game.getGameStage());
              }
            });
    no.setOnMouseClicked(
            new EventHandler<>() {
              @Override
              public void handle(MouseEvent event) {
               Game.getGameStage().close();
              }
            });
  }


  private static int findCurrentFrame(double time, int totalFrames, double speed) {
    return (int) (time % (totalFrames * speed) / speed);
  }
}
