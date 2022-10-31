package bomberman;

import bomberman.constants.GlobalConstants;
import bomberman.view.Game;

public class Camera {

    private int camX;
    private int camY;

    public Camera() {

        int offsetMaxX = Game.getMap().getCol() * GlobalConstants.CELL_SIZE - GlobalConstants.SCENE_WIDTH;
        int offsetMaxY = Game.getMap().getRow() * GlobalConstants.CELL_SIZE - GlobalConstants.SCENE_HEIGHT;
        int offsetMinX = 0;
        int offsetMinY = 0;

        camX = (int) Game.getPlayer().getPositionX() - GlobalConstants.SCENE_WIDTH / 2;
        camY = (int) Game.getPlayer().getPositionY() - GlobalConstants.SCENE_HEIGHT / 2;

        if (camX > offsetMaxX) {
            camX = offsetMaxX;
        } else if (camX < offsetMinX) {
            camX = offsetMinX;
        }
        if (camY > offsetMaxY) {
            camY = offsetMaxY;
        } else if (camY < offsetMinY) {
            camY = offsetMinY;
        }
    }

    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }
}
