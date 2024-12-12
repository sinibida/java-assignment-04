package views;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MeImage extends JLabel {
  public MeImage() {
    Image imageMeSrc;
    try {
      imageMeSrc = ImageIO.read(new File("me.jpg"))
          .getScaledInstance(800, 100, Image.SCALE_AREA_AVERAGING);
      setIcon(new ImageIcon(imageMeSrc));
      // setSize(100, 100);
    } catch (IOException e) {
      setText("me.jpg Not Found.");
    }
  }
}
