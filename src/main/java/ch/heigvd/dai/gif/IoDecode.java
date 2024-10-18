package ch.heigvd.dai.gif;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Paths;
import java.util.*;
import javax.imageio.*;

public class IoDecode {

    /**
     * Read a Gif file and convert it to a byteArray.
     * @param fileName the name of the file to read.
     * @return a byte array corresponding to the file's content.
     */
    public static byte[] readGif(String fileName) {
        byte[] data = null;
        try (FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis))
        {
            data = bis.readAllBytes();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return data;
    }

    /**
     * Create and write BufferedImage in output files in a chosen directory.
     * @param directoryName the name of the directory to save the files in.
     * @param images a List of BufferedImage to save as jpg files.
     */
    public static void writeImages(String directoryName, List<BufferedImage> images) {
        for (int i = 1; i <= images.size(); i++) {
            try(FileOutputStream fos = new FileOutputStream(Paths.get(directoryName, "image_" + i + ".png").toString());
                BufferedOutputStream bos = new BufferedOutputStream(fos))
            {
                ImageIO.write(images.get(i - 1), "png", bos);
                bos.flush();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
