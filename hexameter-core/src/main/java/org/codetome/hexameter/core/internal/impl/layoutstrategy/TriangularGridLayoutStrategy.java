package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import rx.Observable;
import rx.Subscriber;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public final class TriangularGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Observable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        Observable<CubeCoordinate> result = Observable.create(new Observable.OnSubscribe<CubeCoordinate>() {
            @Override
            public void call(final Subscriber<? super CubeCoordinate> subscriber) {
                final int gridSize = builder.getGridHeight();
                for (int gridZ = 0; gridZ < gridSize; gridZ++) {
                    final int endX = gridSize - gridZ;
                    for (int gridX = 0; gridX < endX; gridX++) {
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
        final boolean superResult = checkCommonCase(gridHeight, gridWidth);
        final boolean result = gridHeight == gridWidth;
        return superResult && result;
    }
}
