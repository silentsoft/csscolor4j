# CSSColor4J

[![Maven Central](https://img.shields.io/maven-central/v/org.silentsoft/csscolor4j)](https://search.maven.org/artifact/org.silentsoft/csscolor4j)
[![Build Status](https://app.travis-ci.com/silentsoft/csscolor4j.svg?branch=main)](https://app.travis-ci.com/silentsoft/csscolor4j)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=silentsoft_csscolor4j&metric=alert_status)](https://sonarcloud.io/dashboard?id=silentsoft_csscolor4j)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=silentsoft_csscolor4j&metric=coverage)](https://sonarcloud.io/dashboard?id=silentsoft_csscolor4j)
[![Hits](https://hits.sh/github.com/silentsoft/csscolor4j.svg)](https://hits.sh/github.com/silentsoft/csscolor4j/)

`CSSColor4J` is a simple Java library for parsing CSS color string representations.

## Supported Formats
- rgb[a](red, green, blue[, opacity])
- cmyk[a](cyan, magenta, yellow, black[, opacity])
- hsl[a](hue, saturation, lightness[, opacity])
- named color
- hexadecimal numbers

## Examples
```java
Color.valueOf("rgb(255, 0, 153)");
Color.valueOf("rgb(100%, 0%, 60%)");
Color.valueOf("rgb(255 0 153)");
Color.valueOf("rgb(255, 0, 153, 1)");
Color.valueOf("rgb(255, 0, 153, 100%)");
Color.valueOf("rgb(255 0 153 / 1)");
Color.valueOf("rgb(255 0 153 / 100%)");
Color.valueOf("rgb(1e2, .5e1, .5e0, +.25e2%)");

Color.valueOf("rgba(51, 170, 51, .5)");

Color.valueOf("cmyk(1, 0, 0, 0)");

Color.valueOf("hsl(270, 60%, 70%)");
Color.valueOf("hsl(270deg, 60%, 70%)");
Color.valueOf("hsl(4.71239rad, 60%, 70%)");
Color.valueOf("hsl(300grad, 60%, 70%)");
Color.valueOf("hsl(.75turn, 60%, 70%)");

Color.valueOf("black");
Color.valueOf("rebeccapurple");

Color.valueOf("#f09");
Color.valueOf("#ff0099");
Color.valueOf("#ff0099ff");
```

## Maven Central
```xml
<dependency>
    <groupId>org.silentsoft</groupId>
    <artifactId>csscolor4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please note we have a [CODE_OF_CONDUCT](https://github.com/silentsoft/csscolor4j/blob/main/CODE_OF_CONDUCT.md), please follow it in all your interactions with the project.

## License
Please refer to [LICENSE](https://github.com/silentsoft/csscolor4j/blob/main/LICENSE.txt).
