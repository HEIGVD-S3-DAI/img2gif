package ch.heigvd.dai.commands;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
    description = "A small CLI to convert images to GIFs.",
    version = "1.0.0",
    scope = CommandLine.ScopeType.INHERIT,
    mixinStandardHelpOptions = true)
public class Root implements Callable<Integer> {

  @CommandLine.Parameters(index = "0", description = "The name of inupt folder.", arity = "1")
  protected String inputFolder;

  @CommandLine.Option(
      names = {"-o", "--output"},
      description =
          "The name of the output folder. By default, it is the same as the input folder.")
  protected String outputFolder;

  public String getInputFolder() {
    return inputFolder;
  }

  @Override
  public Integer call() throws Exception {
    // TODO: Implement the logic of the command
    return 0;
  }
}
