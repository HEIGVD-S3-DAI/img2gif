package ch.heigvd.dai.gif;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

public class Encoder {

  /**
   * Encodes a list of BufferedImage objects into a GIF byte array.
   *
   * @param images List of BufferedImage objects to be encoded.
   * @param loopCount Number of times the GIF should loop. 0 means infinite loop.
   * @param delay Delay time between frames in milliseconds.
   * @return Byte array representing the encoded GIF.
   * @throws IOException If an I/O error occurs during encoding.
   */
  public static byte[] encodeGif(List<BufferedImage> images, int loopCount, int delay)
      throws IOException {
    if (images == null || images.isEmpty()) {
      throw new IllegalArgumentException("Image list cannot be null or empty");
    }

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("gif");

    if (writers.hasNext()) {
      ImageWriter writer = writers.next();
      writer.setOutput(imageOutputStream);
      writer.prepareWriteSequence(null);

      IIOMetadata metadata = createMetadata(writer, images.get(0), loopCount, delay);

      for (BufferedImage image : images) {
        writer.writeToSequence(new javax.imageio.IIOImage(image, null, metadata), null);
      }

      writer.endWriteSequence();
      imageOutputStream.close();
    }

    return outputStream.toByteArray();
  }

  /**
   * Creates and configures the metadata for the GIF.
   *
   * @param writer ImageWriter used to write the GIF.
   * @param firstImage The first image in the list to get the image type specifier.
   * @param loopCount Number of times the GIF should loop.
   * @param delay Delay time between frames in milliseconds.
   * @return Configured IIOMetadata object.
   * @throws IOException If an I/O error occurs during metadata creation.
   */
  private static IIOMetadata createMetadata(
      ImageWriter writer, BufferedImage firstImage, int loopCount, int delay) throws IOException {
    IIOMetadata metadata =
        writer.getDefaultImageMetadata(new javax.imageio.ImageTypeSpecifier(firstImage), null);
    String metaFormatName = metadata.getNativeMetadataFormatName();
    IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

    configureGraphicsControlExtension(root, delay);
    configureApplicationExtensions(root, loopCount);

    metadata.setFromTree(metaFormatName, root);
    return metadata;
  }

  /**
   * Configures the Graphics Control Extension node with the delay time.
   *
   * @param root Root metadata node.
   * @param delay Delay time between frames in milliseconds.
   */
  private static void configureGraphicsControlExtension(IIOMetadataNode root, int delay) {
    IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
    graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10));
    graphicsControlExtensionNode.setAttribute("disposalMethod", "restoreToBackgroundColor");
  }

  /**
   * Configures the Application Extensions node with the loop count.
   *
   * @param root Root metadata node.
   * @param loopCount Number of times the GIF should loop.
   */
  private static void configureApplicationExtensions(IIOMetadataNode root, int loopCount) {
    IIOMetadataNode applicationExtensionsNode = getNode(root, "ApplicationExtensions");
    IIOMetadataNode applicationExtensionNode = new IIOMetadataNode("ApplicationExtension");
    applicationExtensionNode.setAttribute("applicationID", "NETSCAPE");
    applicationExtensionNode.setAttribute("authenticationCode", "2.0");

    byte[] loopBytes = new byte[] {1, (byte) (loopCount & 0xFF), (byte) ((loopCount >> 8) & 0xFF)};
    applicationExtensionNode.setUserObject(loopBytes);
    applicationExtensionsNode.appendChild(applicationExtensionNode);
  }

  /**
   * Retrieves or creates a metadata node with the specified name.
   *
   * @param rootNode Root metadata node.
   * @param nodeName Name of the node to retrieve or create.
   * @return The retrieved or newly created IIOMetadataNode.
   */
  private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
    int nNodes = rootNode.getLength();
    for (int i = 0; i < nNodes; i++) {
      if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
        return (IIOMetadataNode) rootNode.item(i);
      }
    }
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    rootNode.appendChild(node);
    return node;
  }
}
