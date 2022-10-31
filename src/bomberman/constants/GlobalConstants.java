/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman.constants;

import bomberman.utils.ImageUtils;
import javafx.scene.image.Image;

import java.io.File;

/**
 * @author Ashish
 */
public class GlobalConstants {

    public static int SCENE_WIDTH = 896;
    public static int SCENE_HEIGHT = 640;
    public static int CELL_SIZE = 64; // Cells are square
    public static int CANVAS_WIDTH = 896;
    public static int CANVAS_HEIGHT = 640;
    public static String GAME_NAME = "BomberMan";
    public static int PLAYER_WIDTH = 64;
    public static int PLAYER_HEIGHT = 64;

    public static int ZOMBIE_WIDTH = 64;

    public static int ZOMBIE_HEIGHT = 64;

    public static double PLAYER_SCALE = 1;

    public static int BOMB_WIDTH = 64;

    public static int BOMB_HEIGHT = 64;

    public static Image mapImage = ImageUtils.loadImage("resource/map/map_sheet.png");

    public static Image itemImage = ImageUtils.loadImage("resource/items/item_sheet.png");
    public static Image femaleImage = ImageUtils.loadImage("resource/characters/female_Sheet.png");
    public static Image maleImage = ImageUtils.loadImage("resource/characters/male_Sheet.png");

    public static Image femaleIdle = ImageUtils.loadImage("resource/characters/female_Idle.png");

    public static Image maleIdle = ImageUtils.loadImage("resource/characters/male_Idle.png");
    public static Image bombImage = ImageUtils.loadImage("resource/characters/boom_Sheet.png");

    public static Image zombieImage = ImageUtils.loadImage("resource/characters/zombie_Sheet.png");

    public static Image robotImage = ImageUtils.loadImage("resource/characters/robot_Sheet.png");

    public static Image bombItem = ImageUtils.loadImage("resource/items/bomb_item.png");

    public static Image flameItem = ImageUtils.loadImage("resource/items/flame_item.png");

    public static Image speedItem = ImageUtils.loadImage("resource/items/speed_item.png");

    public static Image pauseButton = ImageUtils.loadImage("resource/pause_button.png");

    public static Image helpButton = ImageUtils.loadImage("resource/help_button.png");

    public static Image gameOverImage = ImageUtils.loadImage("resource/game_over.jpg");

    public static Image yesImage = ImageUtils.loadImage("resource/yes.png");

    public static Image noImage = ImageUtils.loadImage("resource/no.png");
    public static File mapFile = new File("resource/map/map01.txt");

    public static File mapFile2 = new File("resource/map/map02.txt");

    public static File scoresFile = new File("config/scores.txt");

    public enum GameStatus {
        Running,
        Paused,
        GameOver
    }
}
