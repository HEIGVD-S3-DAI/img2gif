package ch.heigvd.dai.gif;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import javax.imageio.*;

public class IoUtils {

  /**
   * Read a Gif file and convert it to a byteArray.
   *
   * @param fileName the name of the file to read.
   * @return a byte array corresponding to the file's content.
   * @throws IOException
   */
  public static byte[] readGif(String fileName) throws IOException {
    byte[] data = null;
    try (FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis)) {
      data = bis.readAllBytes();
    } catch (IOException e) {
      throw e;
    }
    return data;
  }

  /**
   * Create a file and write the content of a byteArray in it. Used to save the output.
   *
   * @param outputPath path of the directory where the output file will be created.
   * @param byteArray content that will be written in the output binary file.
   * @throws IOException
   */
  public static void writeGif(String outputPath, byte[] byteArray) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(outputPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos)) {
      bos.write(byteArray);
      bos.flush();
    } catch (IOException e) {
      throw e;
    }
  }

  /**
   * Read all binary files that are images in a directory and convert them to BufferedImage.
   *
   * @param directoryName the name of the directory containing the images.
   * @return a List of BufferedImage corresponding to the image files in the directory.
   * @throws IOException
   */
  public static List<BufferedImage> readImages(String directoryName) throws IOException {
    List<String> fileNames = getImagesFilesNames(directoryName);
    List<BufferedImage> images = new ArrayList<>();
    for (String fileName : fileNames) {
      images.add(readImage(fileName));
    }
    return images;
  }

  /**
   * Create and write BufferedImage in output files in a chosen directory.
   *
   * @param directoryName the name of the directory to save the files in.
   * @param images a List of BufferedImage to save as jpg files.
   * @throws IOException
   */
  public static void writeImages(String directoryName, List<BufferedImage> images)
      throws IOException {
    for (int i = 1; i <= images.size(); i++) {
      try (FileOutputStream fos =
              new FileOutputStream(Paths.get(directoryName, "image_" + i + ".png").toString());
          BufferedOutputStream bos = new BufferedOutputStream(fos)) {
        ImageIO.write(images.get(i - 1), "png", bos);
        bos.flush();
      } catch (IOException e) {
        throw e;
      }
    }
  }

  /**
   * Check if a file is an image based on its extension.
   *
   * @param fileName the name of the file we want to test.
   * @return true if the file is an image, false if it isn't.
   */
  private static boolean isImageFile(String fileName) {
    // Check for common image file extensions
    return fileName.endsWith(".jpg")
        || fileName.endsWith(".jpeg")
        || fileName.endsWith(".png")
        || fileName.endsWith(".bmp")
        || fileName.endsWith(".tiff");
  }

  /**
   * Select only the files that are images inside a directory.
   *
   * @param directoryName the name of the directory.
   * @return A List of String containing names of the files that are images in the directory.
   * @throws IOException
   */
  private static List<String> getImagesFilesNames(String directoryName) throws IOException {
    List<String> fileNames = new ArrayList<>();
    File directory = new File(directoryName);

    // Check if the directory exists and is indeed a directory
    if (!directory.exists()) {
      throw new IOException("Directory does not exist: " + directoryName);
    }

    if (!directory.isDirectory()) {
      throw new IOException(directoryName + " is not a directory.");
    }

    // Get all the files in the directory
    File[] files = directory.listFiles();

    if (files != null) {
      for (File file : files) {
        // Add only file names (excluding directories)
        if (file.isFile() && isImageFile(file.getName())) {
          fileNames.add(Paths.get(directoryName, file.getName()).toString());
        }
      }
    } else {
      throw new IOException("An error occurred while reading the directory.");
    }

    return fileNames;
  }

  /**
   * Read a binary file and convert it to a BufferedImage.
   *
   * @param fileName the name of the file.
   * @return a BufferedImage corresponding to the file read.
   * @throws IOException
   */
  private static BufferedImage readImage(String fileName) throws IOException {
    try (InputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis); ) {
      return ImageIO.read(bis);

    } catch (IOException e) {
      throw e;
    }
  }
}
