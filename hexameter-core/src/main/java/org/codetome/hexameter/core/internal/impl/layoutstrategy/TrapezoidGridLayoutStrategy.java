package org.codetome.hexameter.core.internal.impl.layoutstrategy;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import rx.Observable;
import rx.Subscriber;

@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public final class TrapezoidGridLayoutStrategy extends GridLayoutStrategy {

    @Override
    public Observable<CubeCoordinate> fetchGridCoordinates(final HexagonalGridBuilder builder) {
        Observable<CubeCoordinate> result = Observable.create(new Observable.OnSubscribe<CubeCoordinate>() {
            @Override
            public void call(final Subscriber<? super CubeCoordinate> subscriber) {
                for (int gridZ = 0; gridZ < builder.getGridHeight(); gridZ++) {
                    for (int gridX = 0; gridX < builder.getGridWidth(); gridX++) {
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
