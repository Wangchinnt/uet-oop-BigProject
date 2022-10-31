package bomberman.model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PlayerPicker extends VBox {
  ImageView circleImage;
  String circleNotChoosen = "bomberman/model/resources/grey_circle.png";
  String circleChoosen = "bomberman/model/resources/red_choosen.png";
  boolean isCircleChoosen;

  public PlayerPicker() {
    circleImage = new ImageView(circleNotChoosen);

    isCircleChoosen = false;
    this.setAlignment(Pos.CENTER);
    this.setSpacing(5);
    this.getChildren().add(circleImage);

  }

  public boolean getCircleChoosen() {
    return this.isCircleChoosen;
  }

  public void setIsCircleChoosen(boolean isCircleChoosen) {
    this.isCircleChoosen = isCircleChoosen;
    String imageToSet = this.isCircleChoosen ? this.circleChoosen : this.circleNotChoosen;
    this.circleImage.setImage(new Image(imageToSet));
  }
  public void addImage(Image image) {
    this.getChildren().add(new ImageView(image));
  }
}
