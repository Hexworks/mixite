# Hexameter

Hexameter is a hexagonal grid library. The motivation behind it is to have
an optimized, simple and usable library for drawing hexagonal grids without
being tied to any GUI framework.
It is **100% unit tested**.

[![Build Status](https://api.travis-ci.org/Hexworks/hexameter.svg)](https://travis-ci.org/Hexworks/hexameter)

# Getting started

This library uses [Amit's guide to hexagonal grids](http://www.redblobgames.com/grids/hexagons/). The coordinate system
by this library is the Axial coordinate system. Please check [here](http://www.redblobgames.com/grids/hexagons/#coordinates) for
further details.

Hexagonal grids come in **flat topped** and **pointy topped** shapes. The grid can have several layouts:
 - Hexagonal: the width and height of a this layout has to be equal and both have to be an odd number.
 - Triangular: the width and height of a this layout has to be equal.
 - Rectangular: no special rules
 - Trapezoid: no special rules
 - Empty: no special rules

Apart from the *Empty* grid layout all layouts have *with* and *height* values of at least **1**. You can consult [HexagonalGridLayout](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGridLayout.java) if you need further details.

This library is not tied to any GUI implementation. All operations provided by the [API](https://github.com/adam-arold/hexameter/tree/master/hexameter-core/src/main/java/org/codetome/hexameter/api) are working using the most abstract concept possible.

## Basic usage

### Creating a grid

You can use the [HexagonalGridBuilder](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGridBuilder.java) from the API package to create a [HexagonalGrid](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGrid.java):

    // ...
    private static final int GRID_HEIGHT = 9;
    private static final int GRID_WIDTH = 9;
    private static final HexagonalGridLayout GRID_LAYOUT = RECTANGULAR;
    private static final HexagonOrientation ORIENTATION = FLAT_TOP;
    private static final double RADIUS = 30;

    // ...
    HexagonalGriBuilder builder = new HexagonalGridBuilder()
        .setGridHeight(GRID_HEIGHT)
        .setGridWidth(GRID_WIDTH)
        .setGridLayout(GRID_LAYOUT)
        .setOrientation(ORIENTATION)
        .setRadius(RADIUS);

    HexagonalGrid grid = builder.build();

You can also use the [HexagonalGridBuilder](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGridBuilder.java) to create a [HexagonalGridCalculator](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGridCalculator.java) for you which supports advanced operations
on [HexagonalGrid](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGrid.java)s:

    HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

### Drawing a grid

First you want to fetch all the `Hexagon`s from your grid:

    Collection<Hexagon> hexagons = grid.getHexagons();

After that you can iterate over all the `Point`s of your `Hexagon`s:

    hexagons.forEach(hexagon -> {
		hexagon.getPoints().forEach(point -> {
			// your draw logic here
		});
	});

Note that each `Point` represents a coordinate in 2D space. You can use them for drawing.

### Manipulating your grid

There are basically only three operations for manipulating the data on your grid:
- The `addHexagon(AxialCoordinate coordinate)` operation which adds a `Hexagon` at an arbitrary coordinate.
- The `removeHexagon(AxialCoordinate coordinate)` operation which removes a `Hexagon` at an arbitrary coordinate.
- The `setSatelliteData(T data)` operation which you can use to add arbitrary data to any `Hexagon`

There is also a `clearSatelliteData()` method for clearing all satellite data from your grid.

Note that adding or removing a `Hexagon` does not interfere with other operations on the grid. If for example you
get a range of `Hexagon`s using the `getHexagonsByAxialRange` method it will simply ignore any missing `Hexagon`s.

If you don't want to use the provided layouts you can use the `EMPTY` layout and fill your grid by using the `addHexagon` method.

### GUI example:

You can find a simple GUI example in the `hexameter-swt-example` submodule. Run it by doing the following steps.

1. Clone the project: `git clone git@github.com:adam-arold/hexameter.git`
2. cd to the newly created `hexameter` folder: `cd hexameter/`
3. build the project: `mvn clean install`
4. run the created uberjar: `java -jar ./hexameter-swt-example/target/hexameter-swt-example-1.0.0.jar`


### Supported operations
 - Getting a hexagon by its grid coordinate
 - Getting a hexagon by its pixel coordinate
 - Getting the neighbors of a hexagon
 - Calculating the distance between two hexagons
 - Calculating the movement range from a hexagon
 - Adding/removing a Hexagon from the grid
 - Checking whether a Hexagon is on a grid or not
 - Supplying a custom storage object
 - Adding custom data to a Hexagon
 - Clearing all custom data from the HexagonalGrid
 - Getting a subset of Hexagons (using axial or offset cooridnate range) from the grid

Check these interfaces for more details:

- [HexagonalGridBuilder](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGridBuilder.java)
- [HexagonalGrid](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGrid.java)
- [HexagonalGridCalculator](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/HexagonalGridCalculator.java)
- [Hexagon](https://github.com/adam-arold/hexameter/blob/master/hexameter-core/src/main/java/org/codetome/hexameter/api/Hexagon.java)

## Roadmap:
 - Field of view calculation with obstacles
 - Pathfinding with obstacles
 - Movement range with obstacles

## License
Hexameter is made available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Credits
Hexameter is created and maintained by Adam Arold

*I'm open to suggestions, feel free to comment or to send me a message.
Pull reqeusts are also welcome!*
