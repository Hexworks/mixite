package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.CoordinateConverter;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import rx.Observable;
import rx.Subscriber;


/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a rectangular
 * shape.
 */
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public final class RectangularGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Observable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        Observable<CubeCoordinate> result = Observable.create(new Observable.OnSubscribe<CubeCoordinate>() {
            @Override
            public void call(final Subscriber<? super CubeCoordinate> subscriber) {
                for (int y = 0; y < builder.getGridHeight(); y++) {
                    for (int x = 0; x < builder.getGridWidth(); x++) {
                        final int gridX = CoordinateConverter.convertOffsetCoordinatesToCubeX(x, y, builder.getOrientation());
                        final int gridZ = CoordinateConverter.convertOffsetCoordinatesToCubeZ(x, y, builder.getOrientation());
                        subscriber.onNext(CubeCoordinate.fromCoordinates(gridX, gridZ));
                    }
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        return checkCommonCase(gridHeight, gridWidth);
    }
}
