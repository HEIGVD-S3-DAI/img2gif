package ch.heigvd.dai.commands;

import static ch.heigvd.dai.gif.Decoder.*;
import static ch.heigvd.dai.gif.IoDecode.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.*;
import picocli.CommandLine;

@CommandLine.Command(
    name = "decode",
    description = "Create png files of the different images composing a gif file.")
public class Decode implements Callable<Integer> {

  @CommandLine.ParentCommand protected Root parent;

  @CommandLine.Option(
      names = {"-o", "--output"},
      description = "Path of the output directory to save the images in",
      defaultValue = "")
  protected String outputPath;

  @Override
  public Integer call() {
    try {
      byte[] gifData = readGif(parent.getInputFolder());
      List<BufferedImage> images = decodeGif(gifData);
      writeImages(outputPath, images);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return 0;
  }
}
