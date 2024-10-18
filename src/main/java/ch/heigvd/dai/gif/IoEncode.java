package ch.heigvd.dai.gif;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.nio.file.Paths;

public class IoEncode {

    /**
     *
     * Check if a file is an image based on its extension.
     * @param fileName the name of the file we want to test.
     * @return true if the file is an image, false if it isn't.
     */
    public static boolean isImageFile(String fileName) {
        // Check for common image file extensions
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                fileName.endsWith(".png") || fileName.endsWith(".bmp") ||
                fileName.endsWith(".tiff");
    }

    /**
     *
     * Select only the files that are images inside a directory.
     * @param directoryName the name of the directory.
     * @return A List of String containing names of the files that are images in the directory.
     */
    public static List<String> getImagesFilesNames(String directoryName) {
        List<String> fileNames = new ArrayList<>();
        File directory = new File(directoryName);

        // Check if the directory exists and is indeed a directory
        if (!directory.exists()) {
            System.err.println("Directory does not exist: " + directoryName);
            return fileNames;  // Return an empty list
        }

        if (!directory.isDirectory()) {
            System.err.println(directoryName + " is not a directory.");
            return fileNames;  // Return an empty list
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
            System.err.println("An error occurred while reading the directory.");
        }

        return fileNames;
    }

    /**
     *
     * Read a binary file and convert it to a BufferedImage.
     * @param fileName the name of the file.
     * @return a BufferedImage corresponding to the file read.
     */
    public static BufferedImage readImage(String fileName) {
        try (InputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             )
        {
            return ImageIO.read(bis);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     *
     * Read all binary files that are images in a directory and convert them to BufferedImage.
     * @param directoryName the name of the directory containing the images.
     * @return a List of BufferedImage corresponding to the image files in the directory.
     */
    public static List<BufferedImage> readImages(String directoryName) {
        List<String> fileNames = getImagesFilesNames(directoryName);
        List<BufferedImage> images = new ArrayList<>();
        for (String fileName : fileNames) {
            images.add(readImage(fileName));
        }
        return images;
    }

    /**
     *
     * Create a file and write the content of a byteArray in it. Used to save the output.
     * @param outputPath path of the directory where the output file will be created.
     * @param byteArray content that will be written in the output binary file.
     */
    public static void writeGif(String outputPath, byte[] byteArray) {
        try (FileOutputStream fos = new FileOutputStream(outputPath);
            BufferedOutputStream bos = new BufferedOutputStream(fos))
        {
            bos.write(byteArray);
            bos.flush();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
