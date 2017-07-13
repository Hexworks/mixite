package org.codetome.hexameter.core.api.defaults;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.backport.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class DefaultHexagonDataStorageTest {

    private static final CubeCoordinate TEST_CUBE_COORDINATE = CubeCoordinate.fromCoordinates(1, 1);
    private static final CubeCoordinate ANOTHER_TEST_CUBE_COORDINATE = CubeCoordinate.fromCoordinates(2, 1);

    private DefaultHexagonDataStorage target;

    @Mock
    private DefaultSatelliteData defaultSatelliteDataMock;

    @Before
    public void setUp() {
        initMocks(this);
        target = new DefaultHexagonDataStorage();
    }

    @Test
    public void shouldAddCoordinateWhenAddCoordinateIsCalledWithCubeCoordinate() {
        target.addCoordinate(TEST_CUBE_COORDINATE);
        assertThat(target.containsCoordinate(TEST_CUBE_COORDINATE)).isTrue();
    }

    @Test
    public void shouldAddCoordinateAndDataWhenCalledWithBoth() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        assertThat(target.containsCoordinate(TEST_CUBE_COORDINATE)).isTrue();
        assertThat(target.hasDataFor(TEST_CUBE_COORDINATE));
        assertThat(target.getSatelliteDataBy(TEST_CUBE_COORDINATE).get()).isEqualTo(defaultSatelliteDataMock);
    }

    @Test
    public void shouldProperlySignalModificationWhenReplacesPreviousValue() {
        target.addCoordinate(TEST_CUBE_COORDINATE);
        final boolean result = target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        assertThat(result).isTrue();
    }

    @Test
    public void shouldProperlyReturnEmptyOptionalWhenTryingToGetNonPresentData() {
        target.addCoordinate(TEST_CUBE_COORDINATE);
        final Optional<DefaultSatelliteData> result = target.getSatelliteDataBy(TEST_CUBE_COORDINATE);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldProperlyReturnEmptyOptionalWhenTryingToGetDataWithNonPresentKey() {
        final Optional<DefaultSatelliteData> result = target.getSatelliteDataBy(TEST_CUBE_COORDINATE);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldProperlyReturnFalseWhenContainsCoordinateIsCalledWithNonPresentCoordinate() {
        assertThat(target.containsCoordinate(TEST_CUBE_COORDINATE)).isFalse();
    }

    @Test
    public void shouldProperlyReturnTrueWhenContainsCoordinateIsCalledWithPresentCoordinate() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        assertThat(target.containsCoordinate(TEST_CUBE_COORDINATE)).isTrue();
    }

    @Test
    public void shouldProperlyReturnFalseWhenHasDataForIsCalledAndThereIsNoData() {
        assertThat(target.hasDataFor(TEST_CUBE_COORDINATE)).isFalse();
    }

    @Test
    public void shouldProperlyReturnTrueWhenHasDataForIsCalledAndThereIsData() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        assertThat(target.hasDataFor(TEST_CUBE_COORDINATE)).isTrue();
    }

    @Test
    public void shouldProperlyReturnCoordinatesWhenThereAreNonePresent() {
        assertThat(target.getCoordinates().count().toBlocking().single()).isEqualTo(0);
    }

    @Test
    public void shouldProperlyReturnCoordinatesWhenThereAreSeveralPresent() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        target.addCoordinate(ANOTHER_TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        assertThat(target.getCoordinates().count().toBlocking().single()).isEqualTo(2);
    }

    @Test
    public void shouldProperlyClearDataForWhenCalledAndDataIsPresent() {
        target.addCoordinate(TEST_CUBE_COORDINATE, defaultSatelliteDataMock);
        assertThat(target.hasDataFor(TEST_CUBE_COORDINATE)).isTrue();
        target.clearDataFor(TEST_CUBE_COORDINATE);
        assertThat(target.hasDataFor(TEST_CUBE_COORDINATE)).isFalse();
    }

}
