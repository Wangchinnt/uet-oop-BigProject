package bomberman.gamecontroller;

public class GameVariables {


    public static int time = 300;
    public static int score = 0;



    public static int sizeOfExploding = 1;
    public static int bombCount = 1;
    public static double steps = 3;

    public static void reset() {
        time = 300;
        score = 0;
        sizeOfExploding = 1;
        int bombCount = 1;
        double steps = 3;
    }
    public int getTime() {
        return time;
    }
}
