package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.codetome.hexameter.core.api.AxialCoordinate;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import rx.Observable;
import rx.Subscriber;

import static org.codetome.hexameter.core.api.AxialCoordinate.fromCoordinates;

/**
 * This strategy is responsible for generating a {@link HexagonalGrid} which has a triangular
 * shape.
 */
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public final class TriangularGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Observable<AxialCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        Observable<AxialCoordinate> result = Observable.create(new Observable.OnSubscribe<AxialCoordinate>() {
            @Override
            public void call(Subscriber<? super AxialCoordinate> subscriber) {
                final int gridSize = builder.getGridHeight();
                for (int gridZ = 0; gridZ < gridSize; gridZ++) {
                    final int endX = gridSize - gridZ;
                    for (int gridX = 0; gridX < endX; gridX++) {
                        subscriber.onNext(fromCoordinates(gridX, gridZ));
                    }
                }
                subscriber.onCompleted();
            }
        });
        return result;
    }

    @Override
    public boolean checkParameters(final int gridHeight, final int gridWidth) {
        final boolean superResult = super.checkParameters(gridHeight, gridWidth);
        final boolean result = gridHeight == gridWidth;
        return superResult && result;
    }
}
