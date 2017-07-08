package org.codetome.hexameter.core.internal.impl;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;

/**
 * Default implementation of the {@link Hexagon} interface.
 */
public class HexagonImpl<T extends SatelliteData> implements Hexagon<T> {
    private final CubeCoordinate coordinate;
    private final transient Point[] points;
    private final transient float[] vertices;
    private final transient List<Point> pointList;
    private final transient GridData sharedData;
    private final transient HexagonDataStorage<T> hexagonDataStorage;
    private final transient Rectangle extBound, intBound;

    /**
     * Creates a new {@link Hexagon} object from shared data and a coordinate.
     *
     * @param gridData grid data
     * @param coordinate coordinate
     * @param hexagonDataStorage data map
     **/
    public HexagonImpl(final GridData gridData, final CubeCoordinate coordinate, final HexagonDataStorage<T> hexagonDataStorage) {
        this.sharedData = gridData;
        this.coordinate = coordinate;
        this.hexagonDataStorage = hexagonDataStorage;
        
        this.pointList = calcPoints();
        this.points = getPoints().toArray(new Point[0]);
        int		x1 = (int) points[3].getCoordinateX(),
				y1 = (int) points[2].getCoordinateY(),
				x2 = (int) points[0].getCoordinateX(),
				y2 = (int) points[5].getCoordinateY();
		
		extBound = new Rectangle(x1, y1, x2-x1, y2-y1);
		intBound = new Rectangle((int)(getCenterX()-(1.25*sharedData.getRadius() / 2)), (int)(getCenterY()-(1.25*sharedData.getRadius() / 2)), (int)(1.25f*sharedData.getRadius()), (int)(1.25f*sharedData.getRadius()));
		
		this.vertices = new float[] {
			(float) points[0].getCoordinateX(), (float) points[0].getCoordinateY(),
			(float) points[1].getCoordinateX(), (float) points[1].getCoordinateY(),
			(float) points[2].getCoordinateX(), (float) points[2].getCoordinateY(),
			(float) points[3].getCoordinateX(), (float) points[3].getCoordinateY(),
			(float) points[4].getCoordinateX(), (float) points[4].getCoordinateY(),
			(float) points[5].getCoordinateX(), (float) points[5].getCoordinateY(),
		};
    }
    
    private List<Point> calcPoints() {
    	final List<Point> points = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            final double angle = 2 * Math.PI / 6 * (i + sharedData.getOrientation().getCoordinateOffset());
            final double x = getCenterX() + sharedData.getRadius() * cos(angle);
            final double y = getCenterY() + sharedData.getRadius() * sin(angle);
            points.add(Point.fromPosition(x, y));
        }
        return points;
    }

    @Override
    public final String getId() {
        return coordinate.toAxialKey();
    }

    @Override
    public final List<Point> getPoints() {
    	return pointList;
    }
    
    @Override
    public ArrayList<Point> getPointList() {
    	return (ArrayList<Point>)getPoints();
    }
    
    @Override
    public Point[] getPointArray() {
    	return points;
    }

    @Override
    public final CubeCoordinate getCubeCoordinate() {
        return coordinate;
    }

    @Override
    public final int getGridX() {
        return coordinate.getGridX();
    }

    @Override
    public final int getGridY() {
        return coordinate.getGridY();
    }

    @Override
    public final int getGridZ() {
        return coordinate.getGridZ();
    }

    @Override
    public final double getCenterX() {
        if (HexagonOrientation.FLAT_TOP.equals(sharedData.getOrientation())) {
            return coordinate.getGridX() * sharedData.getHexagonWidth() + sharedData.getRadius();
        } else {
            return coordinate.getGridX() * sharedData.getHexagonWidth() + coordinate.getGridZ()
                    * sharedData.getHexagonWidth() / 2 + sharedData.getHexagonWidth() / 2;
        }
    }

    @Override
    public final double getCenterY() {
        if (HexagonOrientation.FLAT_TOP.equals(sharedData.getOrientation())) {
            return coordinate.getGridZ() * sharedData.getHexagonHeight() + coordinate.getGridX()
                    * sharedData.getHexagonHeight() / 2 + sharedData.getHexagonHeight() / 2;
        } else {
            return coordinate.getGridZ() * sharedData.getHexagonHeight() + sharedData.getRadius();
        }
    }

    @Override
    //@SuppressWarnings("unchecked")
    public final Optional<T> getSatelliteData() {
        return hexagonDataStorage.getSatelliteDataBy(getCubeCoordinate());
    }

    @Override
    public final void setSatelliteData(final T satelliteData) {
        this.hexagonDataStorage.addCoordinate(getCubeCoordinate(), satelliteData);
    }

    @Override
    public final void clearSatelliteData() {
        this.hexagonDataStorage.clearDataFor(getCubeCoordinate());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(coordinate);
    }

    @Override
    public final boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final HexagonImpl hexagon = (HexagonImpl) object;
        return Objects.equals(coordinate, hexagon.coordinate);
    }

	@Override
	public Rectangle getExternalBoundingBox() {
		return extBound;
	}

	@Override
	public Rectangle getInternalBoundingBox() {
		return intBound;
	}

	@Override
	public float[] getVertices() {
		return vertices;
	}
}
