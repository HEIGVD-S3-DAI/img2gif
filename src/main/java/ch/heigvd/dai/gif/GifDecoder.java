package ch.heigvd.dai.gif;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class GifDecoder {

  /**
   * Decodes a GIF byte array into a list of BufferedImage objects.
   *
   * @param gifData Byte array representing the GIF data.
   * @return List of BufferedImage objects extracted from the GIF.
   * @throws IOException If an I/O error occurs during decoding.
   */
  public static List<BufferedImage> decode(byte[] gifData) throws IOException {
    List<BufferedImage> images = new ArrayList<>();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(gifData);
    ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
    Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");

    if (readers.hasNext()) {
      ImageReader reader = readers.next();
      reader.setInput(imageInputStream);

      int numFrames = reader.getNumImages(true);
      for (int i = 0; i < numFrames; i++) {
        BufferedImage image = reader.read(i);
        images.add(image);
      }
    }

    return images;
  }
}
