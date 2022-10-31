package bomberman.view;

import bomberman.Sound;
import bomberman.constants.GlobalConstants;
import bomberman.model.BomberManButton;
import bomberman.model.BomberManSubScene;
import bomberman.model.InfoLabel;
import bomberman.model.PlayerPicker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {

  private static final int WIDTH = 640;
  private static final int HEIGHT = 640;
  private static final int MENU_BUTTONS_START_X = 50;
  private static final int MENU_BUTTONS_START_Y = 30;
  List<BomberManButton> menuButtons;
  private AnchorPane mainPane;
  private Scene mainScene;
  private Stage mainStage;
  private BomberManSubScene creditsSubScene;
  private BomberManSubScene helpSubScene;
  private BomberManSubScene scoreSubScene;
  private BomberManSubScene characterChooseScene;
  private BomberManSubScene sceneToHide;

  private PlayerPicker male;

  private final boolean[] musicEnabled = {false};



  private PlayerPicker female;

  private Sound sound;

  private Image playerImage;

  public ViewManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    menuButtons = new ArrayList<>();
    mainPane = new AnchorPane();
    mainScene = new Scene(mainPane, WIDTH, HEIGHT);
    mainStage = new Stage();
    mainStage.setTitle(GlobalConstants.GAME_NAME);
    mainStage.setScene(mainScene);
    sound = new Sound();
    sound.setAudio(0);
    createSubScene();
    createButtons();
    createBackground();
    createLogo();
  }

  private void showSubScene(BomberManSubScene subScene) {
    if (sceneToHide != null) {
      sceneToHide.moveSubScene();
    }

    subScene.moveSubScene();
    sceneToHide = subScene;
  }

  private void createSubScene() {
    createCharacterChooserSubScene();
    createScoreSubScene();
    creditsSubScene = new BomberManSubScene();
    mainPane.getChildren().add(creditsSubScene);

    helpSubScene = new BomberManSubScene();
    ImageView imageView = new ImageView(GlobalConstants.helpButton);
    imageView.setLayoutX(15);
    imageView.setLayoutY(145);
    helpSubScene.getPane().getChildren().add(imageView);
    mainPane.getChildren().add(helpSubScene);
  }

  private void createScoreSubScene() {
    scoreSubScene = new BomberManSubScene();
    mainPane.getChildren().add(scoreSubScene);
  }

  private void createCharacterChooserSubScene() {
    characterChooseScene = new BomberManSubScene();
    mainPane.getChildren().add(characterChooseScene);
    InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR PLAYER");
    chooseShipLabel.setLayoutX(45);
    chooseShipLabel.setLayoutY(100);
    characterChooseScene.getPane().getChildren().add(chooseShipLabel);
    characterChooseScene.getPane().getChildren().add(createPlayerToChoose());
    characterChooseScene.getPane().getChildren().add(createButtonToStart());
  }

  private HBox createPlayerToChoose() {
    HBox box = new HBox();
    box.setSpacing(30);
    male = new PlayerPicker();
    male.addImage(GlobalConstants.maleIdle);
    female = new PlayerPicker();
    female.addImage(GlobalConstants.femaleIdle);
    box.getChildren().add(male);
    box.getChildren().add(female);

    male.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            male.setIsCircleChoosen(true);
            female.setIsCircleChoosen(false);
          }
        });
    female.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {
            female.setIsCircleChoosen(true);
            male.setIsCircleChoosen(false);
          }
        });
    box.setLayoutX(40);
    box.setLayoutY(150);

    return box;
  }

  private BomberManButton createButtonToStart() {
    BomberManButton startButton = new BomberManButton("START");
    startButton.setLayoutX(110);
    startButton.setLayoutY(410);
    startButton.setOnAction(
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            playerImage = male.getCircleChoosen() ? GlobalConstants.maleImage : GlobalConstants.femaleImage;
            Game.setPlayerImage(playerImage);
            Game.createNewGame(mainStage);
            Game.setMusicEnabled(!musicEnabled[0]);

          }
        });
    return startButton;
  }

  public Stage getMainStage() {
    return mainStage;
  }

  private void addMenuButton(BomberManButton button) {
    button.setLayoutX(MENU_BUTTONS_START_X);
    button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 80);
    menuButtons.add(button);
    mainPane.getChildren().add(button);
  }

  private void createButtons() {
    createPlayButton();
    createScoresButton();
    createHelpButton();
    createSoundButton();
    createExitButton();
  }

  private void createPlayButton() {
    BomberManButton startButton = new BomberManButton("PLAY");
    addMenuButton(startButton);

    startButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            showSubScene(characterChooseScene);
          }
        });
  }

  private void createScoresButton() {
    BomberManButton scoresButton = new BomberManButton("SCORES");
    addMenuButton(scoresButton);

    scoresButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            showSubScene(scoreSubScene);
          }
        });
  }

  private void createHelpButton() {
    BomberManButton helpButton = new BomberManButton("HELP");
    addMenuButton(helpButton);

    helpButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            showSubScene(helpSubScene);
          }
        });
  }

  private void createSoundButton() {
    sound.playAudio();
    BomberManButton soundButton = new BomberManButton("SOUND:On");
    addMenuButton(soundButton);

    soundButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if (musicEnabled[0]) {
              soundButton.setText("SOUND:On");
              sound.playAudio();
              musicEnabled[0] = false;
            } else {
              soundButton.setText("SOUND:Off");
              sound.pauseAudio();
              musicEnabled[0] = true;
            }
          }
        });
  }

  private void createExitButton() {
    BomberManButton exitButton = new BomberManButton("EXIT");
    addMenuButton(exitButton);

    exitButton.setOnAction(
        new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent event) {
            Game.createWindowExit(mainStage);
          }
        });
  }

  private void createBackground() {
    Image backgroundImage =
        new Image("bomberman/view/resources/menu_game.png", 640, 640, false, true);
    BackgroundImage background =
        new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT,
            null);
    mainPane.setBackground(new Background(background));
  }

  private void createLogo() {
    ImageView logo = new ImageView("bomberman/view/resources/logo.png");
    logo.setX(280);
    logo.setY(10);

    logo.setOnMouseEntered(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            logo.setEffect(new DropShadow());
          }
        });

    logo.setOnMouseExited(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            logo.setEffect(null);
          }
        });
    mainPane.getChildren().add(logo);
  }



}
