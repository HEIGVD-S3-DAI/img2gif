package ch.heigvd.dai.commands;

import ch.heigvd.dai.gif.GifDecoder;
import ch.heigvd.dai.gif.IoUtils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
    name = "decode",
    description = "Create png files of the different images composing a gif file.")
public class Decode implements Callable<Integer> {

  @CommandLine.ParentCommand private Root parent;

  @CommandLine.Parameters(index = "0", description = "The name of input file.", arity = "1")
  protected String inputFile;

  @CommandLine.Option(
      names = {"-o", "--output"},
      description = "Path of the output directory to save the images in",
      defaultValue = "")
  private String outputPath;

  @Override
  public Integer call() {
    try {
      byte[] gifData = IoUtils.readGif(inputFile);
      List<BufferedImage> images = GifDecoder.decode(gifData);
      IoUtils.writeImages(outputPath, images);
    } catch (IOException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
    return 0;
  }
}
