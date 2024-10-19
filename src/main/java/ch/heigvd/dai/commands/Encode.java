package ch.heigvd.dai.commands;

import ch.heigvd.dai.gif.GifEncoder;
import ch.heigvd.dai.gif.IoUtils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
    name = "encode",
    description = "Create a gif file based on a directory containing images.")
public class Encode implements Callable<Integer> {

  @CommandLine.ParentCommand private Root parent;

  @CommandLine.Parameters(index = "0", description = "The name of input folder.", arity = "1")
  protected String inputFolder;

  @CommandLine.Option(
      names = {"-l", "--loopcount"},
      description =
          "Number of loops of the gif, set to 0 for infinite loop, (default: ${DEFAULT-VALUE}).",
      defaultValue = "0")
  private int loopCount;

  @CommandLine.Option(
      names = {"-d", "--delay"},
      description = "Delay between frames of the gif, (default: ${DEFAULT-VALUE}).",
      defaultValue = "0")
  private int delay;

  @CommandLine.Option(
      names = {"-o", "--output"},
      description = "Output path, (default: ${DEFAULT-VALUE}).",
      defaultValue = "output.gif")
  private String outputPath;

  @Override
  public Integer call() {
    try {
      List<BufferedImage> images = IoUtils.readImages(inputFolder);
      if (images.isEmpty()) return 1;
      byte[] dataGif = GifEncoder.encode(images, loopCount, delay);
      IoUtils.writeGif(this.outputPath, dataGif);
    } catch (IOException e) {
      System.err.println("ERROR:" + e.getMessage());
    }
    return 0;
  }
}
