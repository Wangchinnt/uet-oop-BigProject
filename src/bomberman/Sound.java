package bomberman;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    File soundFile;

    String[] filesPaths = {"resource/sounds/music.wav",
            "resource/sounds/DropBomb.wav",
            "resource/sounds/powerUp.wav",
            "resource/sounds/burning.wav",
            "resource/sounds/gameOver.wav"};

    Clip clip;

    public void setAudio(int i) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        soundFile = new File(filesPaths[i]);
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(inputStream);
    }
    public void playAudio() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void pauseAudio() {
        clip.stop();
    }
    public void playSound() {
        clip.start();
    }
}
