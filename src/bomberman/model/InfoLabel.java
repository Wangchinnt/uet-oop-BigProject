package bomberman.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class InfoLabel extends Label {
    public static final String BACKGROUND_IMAGE = "bomberman/model/resources/red_small_panel.png";

    public InfoLabel(String text) {
        this.setPrefWidth(300.0);
        this.setPrefHeight(49.0);
        this.setText(text);
        this.setWrapText(true);
        this.setFont(Font.font("Arial", 20));
        this.setAlignment(Pos.CENTER);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 300.0, 49.0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, (BackgroundSize)null);
        this.setBackground(new Background(backgroundImage));
    }


}
