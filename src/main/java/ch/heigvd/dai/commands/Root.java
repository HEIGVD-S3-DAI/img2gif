package ch.heigvd.dai.commands;

import picocli.CommandLine;

@CommandLine.Command(
    description = "A small CLI to convert images to GIFs.",
    version = "1.0.0",
    subcommands = {
      Encode.class,
      Decode.class,
    },
    scope = CommandLine.ScopeType.INHERIT,
    mixinStandardHelpOptions = true)
public class Root {

  @CommandLine.Parameters(index = "0", description = "The name of input folder.", arity = "1")
  protected String inputFolder;

  public String getInputFolder() {
    return inputFolder;
  }
}
