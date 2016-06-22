package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.round;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a hexagonal
 * shape.
 */
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public final class HexagonalGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Observable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        Observable<CubeCoordinate> result = Observable.create(new OnSubscribe<CubeCoordinate>() {
            @Override
            public void call(Subscriber<? super CubeCoordinate> subscriber) {
                final double gridSize = builder.getGridHeight();
                int startX = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? (int) floor(gridSize / 2d) : (int) round(gridSize / 4d);
                final int hexRadius = (int) floor(gridSize / 2d);
                final int minX = startX - hexRadius;
                for (int y = 0; y < gridSize; y++) {
                    final int distanceFromMid = abs(hexRadius - y);
                    for (int x = max(startX, minX); x <= max(startX, minX) + hexRadius + hexRadius - distanceFromMid; x++) {
                        final int gridX = x;
                        final int gridZ = HexagonOrientation.FLAT_TOP.equals(builder.getOrientation()) ? y - (int) floor(gridSize / 4d) : y;
                        subscriber.onNext(CubeCoordinate.fromCoordinates(gridX, gridZ));
                    }
                    startX--;
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        final boolean superResult = super.checkParameters(gridHeight, gridWidth);
        final boolean result = gridHeight == gridWidth && abs(gridHeight % 2) == 1;
        return result && superResult;
    }

}
