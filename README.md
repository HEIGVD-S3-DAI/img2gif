# IMG2GIF

- [About](#about)
- [Installation](#installation)
- [Usage](#usage)
- [References](#references)
- [Authors](#authors)

## About

Convert images into gifs.

![Cat GIF](https://media.giphy.com/media/vFKqnCdLPNOKc/giphy.gif)

## Installation

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

### Images to GIF

```bash
img2gif encode <inputFolder>
```

### GIF to Images

```bash
img2gif decode <inputGif>
```

## References

For the implementation of the GIF Codec, we used ChatGPT to create a minimal
API that would allow us to encode and decode GIF files. See
[Encoder](./src/main/java/ch/heigvd/dai/gif/Encoder.java)
and [Decoder](./src/main/java/ch/heigvd/dai/gif/Decoder.java)

## Authors

- Leonard Cseres [@leoanrdcser](https://github.com/leonardcser)
- Aude Laydu [@eau2](https://github.com/eau2)
