# IMG2GIF

- [About](#about)
- [Installation](#installation)
- [Usage](#usage)
  - [Images to GIF](#images-to-gif)
    - [Example](#example)
  - [GIF to Images](#gif-to-images)
    - [Example](#example-1)
- [References](#references)
- [Authors](#authors)

## About

A simple CLI tool to Convert images into gifs.

| Before                                                  | After                                                             |
| ------------------------------------------------------- | ----------------------------------------------------------------- |
| ![Cat](samples/image_1.png) ![Cat](samples/image_4.png) | ![Cat GIF](https://media.giphy.com/media/vFKqnCdLPNOKc/giphy.gif) |

## Installation

Start by cloning the repository:

```bash
git clone https://github.com/HEIGVD-S3-DAI/img2gif.git
```

Make sure you make java jdk>=21 installed on your machine and follow the steps below:

```bash
# Download the dependencies and their transitive dependencies
./mvnw dependency:go-offline
```

```bash
# Package the application
./mvnw package
```

## Usage

Optionally, create an alias to the jar application with the command below:

```bash
alias img2gif="java -jar target/java-img2png-1.0-SNAPSHOT.jar"
```

To see a list of avaiable commands, run:

```bash
img2gif -h
```

```
Usage: java-img2png-1.0-SNAPSHOT.jar [-hV] [COMMAND]
A small CLI to convert images to GIFs.
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  encode  Create a gif file based on a directory containing images.
  decode  Create png files of the different images composing a gif file.
```

### Images to GIF

You can provide a folder containing images and it will encode them into a gif. The images are selected based on their alphabetical order.

```bash
img2gif encode <inputFolder>
```

| Command           | Description                                                          |
| ----------------- | -------------------------------------------------------------------- |
| `<inputFolder>`   | The folder containing the images to encode                           |
| `-o, --output`    | The output file path. Default is `output.gif`                        |
| `-d, --delay`     | Delay between frames of the gif, (default: 0)                        |
| `-h, --help`      | Show this help message and exit                                      |
| `-l, --loopcount` | Number of loops of the gif, set to 0 for infinite loop, (default: 0) |

#### Example

```bash
img2gif encode samples -o samples.gif -d 100 -l 0
```

The command above produces a gif file named `samples.gif` based on the images in the `samples` folder. The delay between frames is 100ms and the gif loops indefinitely.

### GIF to Images

You can also decode a gif file into its composing images.

```bash
img2gif decode <inputGif>
```

| Command        | Description                                                                  |
| -------------- | ---------------------------------------------------------------------------- |
| `<inputFile>`  | The GIF file to decode                                                       |
| `-o, --output` | The output directory to save the images in. Default is the current directory |
| `-h, --help`   | Show this help message and exit                                              |

#### Example

```bash
img2gif decode samples.gif -o samples_copy
```

The command above decodes the `samples.gif` file into a `samples_copy` folder.

## References

For the implementation of the GIF Codec, we used ChatGPT to create a minimal
API that would allow us to encode and decode GIF files. See
[GifEncoder](./src/main/java/ch/heigvd/dai/gif/GifEncoder.java)
and [GifDecoder](./src/main/java/ch/heigvd/dai/gif/GifDecoder.java). We implemented the rest of the CLI tool and IO operations.

## Authors

- Leonard Cseres [@leoanrdcser](https://github.com/leonardcser)
- Aude Laydu [@eau2](https://github.com/eau2)
