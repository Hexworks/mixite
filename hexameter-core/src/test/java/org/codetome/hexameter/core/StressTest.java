package org.codetome.hexameter.core;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.junit.Before;
import org.junit.Test;
import rx.functions.Action1;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * This should be later refactored to a stress test directory.
 */
public class StressTest {

    private static final int BIG_GRID_HEIGHT = 1000;
    private static final int BIG_GRID_WIDTH = 1000;
    private static final double BIG_GRID_RADIUS = 50;
    private static final long EXPECTED_MAXIMUM_GENERATION_TIME = 2000;
    private static final long EXPECTED_MAXIMUM_FETCH_TIME = 2000;

    private HexagonalGridBuilder hexagonalGridBuilder;

    @Before
    public void setUp() {
        hexagonalGridBuilder = new HexagonalGridBuilder()
                .setGridHeight(BIG_GRID_HEIGHT)
                .setGridWidth(BIG_GRID_WIDTH)
                .setRadius(BIG_GRID_RADIUS);
    }

    @Test
    public void shouldBeAbleToCreateBigGrid() {
        final long start = System.nanoTime();
        hexagonalGridBuilder.build();
        final long end = System.nanoTime();
        assertThat(nanoToMs(end - start)).isLessThan(EXPECTED_MAXIMUM_GENERATION_TIME);
    }

    @Test
    public void shouldBeAbleToFetchHexesFromBigGrids() {
        HexagonalGrid grid = hexagonalGridBuilder.build();

        final long start = System.nanoTime();
        final AtomicInteger ai = new AtomicInteger();
        grid.getHexagons().forEach(new Action1<Hexagon>() {
            @Override
            public void call(Hexagon hexagon) {
                ai.incrementAndGet();
            }
        });
        final long end = System.nanoTime();
        System.out.println(format("Number of hexes: %s, generated in: %dms.", ai.get(), nanoToMs(end - start)));
        assertThat(nanoToMs(end - start)).isLessThan(EXPECTED_MAXIMUM_FETCH_TIME);
    }

    private long nanoToMs(long nanoTime) {
        return nanoTime / 1000 / 1000;
    }
}
