# Mixite

Mixite is a hexagonal grid library. The motivation behind it is to have an optimized, simple and usable library for drawing
hexagonal grids without being tied to any GUI framework.

This means that you can use Mixite on Android, your backend or your desktop app.

There is a REST-based web example which you can tinker with [here][herokurestlink]. *(not recommended, this is under rewrite)*

You can also check out the mixite.example.swt project [here][exampleprojectslink].

Mixite currently supports a maximum grid size of 1000 * 1000 (1.000.000 cells) with the default implementation but
you can provide your own storage implementation to alleviate this limitation.

> **Disclaimer for Java users**:
> There is no need to worry, Mixite works in exactly the same way as Hexameter worked before.
> Java interop is seamless, you only have to change the imports / project dependency.
>
> As always with Maven Central artifacts: previous versions of Hexameter also work, they
> are not affected.
---

Need info? [Ask us on Discord][discord]
 | or [Create an issue](issues/new)
 | Support us on [Patreon](https://www.patreon.com/hexworks)

[![][circleci img]][circleci]
[![][maven img]][maven]
[![](https://jitpack.io/v/Hexworks/mixite.svg)](https://jitpack.io/#Hexworks/mixite)
[![][license img]][license]

[discord]:https://discord.gg/vSNgvBh

[circleci]:https://circleci.com/gh/Hexworks/mixite
[circleci img]:https://circleci.com/gh/Hexworks/mixite/tree/master.svg?style=shield

[license]:LICENSE
[license img]:https://img.shields.io/badge/License-Apache2.0-blue.svg

[maven]:https://search.maven.org/search?q=g:org.hexworks.mixite
[maven img]:https://maven-badges.herokuapp.com/maven-central/org.hexworks.mixite/mixite.core/badge.svg

---

## Getting started

This library uses [Amit's guide to hexagonal grids][amitlink]. The coordinate system used by this library is the Cubic
coordinate system. Please check [here][cubecoords] for further details.

Hexagonal grids come in **flat topped** and **pointy topped** shapes. The grid can have several layouts:
 - Hexagonal: the width and height of a this layout has to be equal and both have to be an odd number.
 - Triangular: the width and height of a this layout has to be equal.
 - Rectangular: no special rules
 - Trapezoid: no special rules

All layouts have *width* and *height* values of at least **1**.
You can consult [HexagonalGridLayout][hexgridlayout] if you need further details.

This library is not tied to any GUI implementation. All operations provided by the [API][api] work using the most
abstract concept possible.

## Basic usage

### Importing Mixite
Let's start by adding Mixite as a dependency.

#### Maven

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url> <!-- Mixite uses Jitpack repository -->
  </repository>
</repositories>
...
<dependency>
  <groupId>com.github.Hexworks.mixite</groupId>
  <artifactId>mixite.core-jvm</artifactId>
  <version>2018.2.0-RELEASE</version>
</dependency>
```

#### Gradle

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' } // Mixite uses Jitpack repository
  }
}
...
dependencies {
  implementation 'com.github.Hexworks.mixite:mixite.core-jvm:2018.2.0-RELEASE'
}
```
   
> Note that if you are using Javascript you need `mixite.core-web`:
> `'org.hexworks.mixite:mixite.core-web:2018.2.0-RELEASE'`
  
You can also use the latest preview versions, more info [here](https://jitpack.io/#Hexworks/Mixite).

### Creating a grid

You can use the [HexagonalGridBuilder][hexgridbuilder] to create a [HexagonalGrid][hexgrid]:

```java
HexagonalGridBuilder builder = new HexagonalGridBuilder()
  .setGridHeight(9)
  .setGridWidth(9)
  .setGridLayout(HexagonalGridLayout.RECTANGULAR)
  .setOrientation(HexagonOrientation.FLAT_TOP)
  .setRadius(30.0);
HexagonalGrid grid = builder.build();
```

You can also use it to create a [HexagonalGridCalculator][hexgridcalc] for you which
supports advanced operations on [HexagonalGrid][hexgrid]s:

```java
HexagonalGridCalculator calc = builder.buildCalculatorFor(grid);
calc.calculateDistanceBetween(sourceHex, targetHex)
```

### Drawing a grid

Method `Grid.getHexagons` returns an iterable collection of hexagons. Each `Point` represents a coordinate in 2D space that can
be transformed for rendering.
```java
for (Hexagon hexagon : grid.getHexagons()) {
  for(Point p : hexagon.getPoints()) {
    // Do you stuff with point.coordinateX, point.coordinateY
  }
}
```


### Manipulating your grid

There are basically only one operation for manipulating your data on the grid:
The `Hexagon#setSatelliteData(T data)` operation with which you can add your own arbitrary
data to a `Hexagon` object. This means that once created a `HexagonalGrid` is immutable apart from the
satellite data you add.

There is also a `HexagonalGrid#clearSatelliteData()` method for clearing all satellite data from your grid.

The implementation of the `HexagonalGrid` is lazy. This means that it only stores data which is absolutely necessary
to keep in memory (the coordinates and your satellite data). Everything else is generated on the fly. The only limiting
factor of a grid at the moment is the coordinates (which consume memory) and the satellite data.

### GUI example:

You can find a simple GUI example in the `mixite.example.swt` project. Run it by doing the following steps.

1. Clone the project: `git clone git@github.com:Hexworks/mixite.git`
2. cd to the newly created `mixite` folder: `cd mixite/`
3. build the project: `./gradlew clean build` (or `gradlew clean build` on Windows)
4. run the created uberjar: `java -jar mixite.example.swt/build/libs/mixite.example.swt.jar`


### Supported operations
 - Querying the characteristics of the `HexagonGrid`
 - Fetching all the `Hexagon` objects from the grid
 - Getting a subset of Hexagons (using cube or offset coordinate range) from the grid
 - Checking whether a Hexagon is on a grid or not
 - Getting a `Hexagon` by its grid coordinate (cube)
 - Getting a `Hexagon` by its pixel coordinate
 - Getting the neighbors of a hexagon (also by index)

### Advanced operations
 - Calculating the distance between two `Hexagon`s
 - Calculating the movement range from a `Hexagon` to an other
 - Rotating a `Hexagon`
 - Calculating a ring from a `Hexagon`
 - Draw a line from a `Hexagon` to an other
 - Checking visibility of a `Hexagon` from an other
 - Adding custom data to a Hexagon
 - Clearing all custom data from the HexagonalGrid

Check these interfaces for more details:

- [HexagonalGridBuilder][hexgridbuilder]
- [HexagonalGrid][hexgrid]
- [HexagonalGridCalculator][hexgridcalc]
- [Hexagon][hex]

### Usage tips
 - You can add satellite data (any arbitrary data you have) to a `Hexagon`. By implementing the [`SatelliteData`][satdatlink]
   interface you gain operations like visibility checking
 - Mixite comes with a sensible default implementation of [`SatelliteData`][satdatlink] so if you don't want to add extra data
   you can use [`DefaultSatelliteData`][defsatdatlink].
 - You can use your own implementation of [`HexagonDataStorage`][hexdatstorlink] for storing your `Hexagon`s
 - Mixite comes with a sensible [`DefaultHexagonDataStorage`][defhexdatstorlink] implementation which stores all data in memory
 - You don't have to fetch all `Hexagon` objects by using the `getHexagons` method. You can query `Hexagon`s by a range using
   offset or cube coordinates

## Road map
 - Path finding with obstacles  (blocking movement)
 - Movement range with obstacles and movement cost calculation
 - Android example

## License
Mixite is made available under the [Apache2 License](https://opensource.org/licenses/Apache-2.0).

## Credits
Mixite is created and maintained by Adam Arold

*I'm open to suggestions, feel free to comment or to send me a message.
Pull requests are also welcome!*


[amitlink]:http://www.redblobgames.com/grids/hexagons/
[cubecoords]:http://www.redblobgames.com/grids/hexagons/#coordinates
[herokurestlink]:http://hexameter-rest-example.herokuapp.com/
[exampleprojectslink]:https://github.com/Hexworks/mixite.example/tree/master/mixite.example.swt

[hexgridlayout]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/HexagonalGridLayout.kt
[hexgridbuilder]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/HexagonalGridBuilder.kt
[api]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api
[hexgrid]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/HexagonalGrid.kt
[hexgridcalc]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/HexagonalGridCalculator.kt
[hex]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/Hexagon.kt
[satdatlink]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/contract/SatelliteData.kt
[defsatdatlink]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/defaults/DefaultSatelliteData.kt
[hexdatstorlink]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/contract/HexagonDataStorage.kt
[defhexdatstorlink]:mixite.core/src/commonMain/kotlin/org/hexworks/mixite/core/api/defaults/DefaultHexagonDataStorage.kt
