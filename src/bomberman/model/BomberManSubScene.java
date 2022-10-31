package bomberman.model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BomberManSubScene extends SubScene {

  private static final String FONT_PATH = "bomberman/model/resources/Pixel.tff";
  private static final String BACKGROUND_IMAGE = "bomberman/model/resources/brown_panel.png";

  private boolean isHidden;

  private final AnchorPane root2 = (AnchorPane) this.getRoot();

  public BomberManSubScene() {
    super(new AnchorPane(), 640, 640);
    prefWidth(640);
    prefHeight(640);

    BackgroundImage image =
        new BackgroundImage(
            new Image(BACKGROUND_IMAGE, 640, 640, false, true),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            null);
    root2.setBackground(new Background(image));

    isHidden = true;
    setLayoutX(1000);

  }

  public void moveSubScene() {
    TranslateTransition transition = new TranslateTransition();
    transition.setDuration(Duration.seconds(0.3));
    transition.setNode(this);

    if (isHidden) {
      transition.setToX(-760);
      isHidden = false;
    } else {
      transition.setToX(0);
      isHidden = true;
    }

    transition.play();
  }
  public void setTextSubScene(String str) {
    Text text = new Text(str);
    Font font = new Font("pixel", 40);
    text.setFont(font);
    text.setLayoutX(60);
    text.setLayoutY(175);
    root2.getChildren().add(text);
  }

  public AnchorPane getPane() {
    return (AnchorPane)this.getRoot();
  }



}
