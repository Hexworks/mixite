package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import rx.Observable;
import rx.Subscriber;

import static org.codetome.hexameter.core.api.CubeCoordinate.fromCoordinates;

@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public final class TrapezoidGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Observable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        Observable<CubeCoordinate> result = Observable.create(new Observable.OnSubscribe<CubeCoordinate>() {
            @Override
            public void call(Subscriber<? super CubeCoordinate> subscriber) {
                for (int gridZ = 0; gridZ < builder.getGridHeight(); gridZ++) {
                    for (int gridX = 0; gridX < builder.getGridWidth(); gridX++) {
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
        return super.checkParameters(gridHeight, gridWidth);
    }
}
