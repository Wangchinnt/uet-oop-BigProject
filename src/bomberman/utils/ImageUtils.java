package bomberman.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.File;

public class ImageUtils {
  public static Image loadImage(String path) {
    File file = new File(path);
    System.out.println("Loading Sprite sheet " + file.exists());
    String imagePath = file.getAbsolutePath();
    System.out.println("Before ImagePath " + imagePath);
    if (File.separatorChar == '\\') {
      // From Windows to Linux/Mac
      imagePath = imagePath.replace('/', File.separatorChar);
      imagePath = imagePath.replace("\\", "\\\\");
    } else {
      // From Linux/Mac to Windows
      imagePath = imagePath.replace('\\', File.separatorChar);
    }
    imagePath = "file:" + imagePath;
    System.out.println("After ImagePath " + imagePath);

    return new Image(imagePath);
  }

  public static Image crop(Image img, int x, int y, int w, int h) {
    PixelReader reader = img.getPixelReader();
    return new WritableImage(reader, x, y, w, h);
  }
}
