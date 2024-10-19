package ch.heigvd.dai.commands;

import ch.heigvd.dai.gif.Encoder;
import ch.heigvd.dai.gif.IoEncode;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
    name = "encode",
    description = "Create a gif file based on a directory containing images.")
public class Encode implements Callable<Integer> {

  @CommandLine.ParentCommand protected Root parent;

  @CommandLine.Option(
      names = {"-l", "--loopcount"},
      description =
          "Number of loops of the gif, set to 0 for infinite loop, (default: ${DEFAULT-VALUE}).",
      defaultValue = "0")
  protected int loopCount;

  @CommandLine.Option(
      names = {"-d", "--delay"},
      description = "Delay between frames of the gif, (default: ${DEFAULT-VALUE}).",
      defaultValue = "0")
  protected int delay;

  @CommandLine.Option(
      names = {"-o", "--output"},
      description = "Output path, (default: ${DEFAULT-VALUE}).",
      defaultValue = "output.gif")
  protected String outputPath;

  @Override
  public Integer call() {
    try {
      List<BufferedImage> images = IoEncode.readImages(parent.getInputFolder());
      byte[] dataGif = Encoder.encodeGif(images, loopCount, delay);
      IoEncode.writeGif(this.outputPath, dataGif);
    } catch (IOException e) {
      System.err.println("Error : " + e.getMessage());
    }
    return 0;
  }
}
