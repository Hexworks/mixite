package biz.pavonis.hexameter.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import biz.pavonis.hexameter.api.AxialCoordinateTest;
import biz.pavonis.hexameter.api.CoordinateConverterTest;
import biz.pavonis.hexameter.api.HexagonOrientationTest;
import biz.pavonis.hexameter.api.HexagonalGridBuilderTest;
import biz.pavonis.hexameter.api.HexagonalGridLayoutTest;
import biz.pavonis.hexameter.api.PointTest;
import biz.pavonis.hexameter.api.exception.HexagonNotFoundExceptionTest;
import biz.pavonis.hexameter.api.exception.HexagonalGridCreationExceptionTest;
import biz.pavonis.hexameter.internal.SharedHexagonDataTest;
import biz.pavonis.hexameter.internal.impl.HexagonImplTest;
import biz.pavonis.hexameter.internal.impl.HexagonalGridCalculatorImplTest;
import biz.pavonis.hexameter.internal.impl.HexagonalGridImplTest;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.AbstractGridLayoutStrategyTest;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.CustomGridLayoutStrategyTest;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.HexagonalGridLayoutStrategyTest;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.RectangularGridLayoutStrategyTest;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.TrapezoidGridLayoutStrategyTest;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.TriangularGridLayoutStrategyTest;

@RunWith(Suite.class)
@SuiteClasses({ HexagonNotFoundExceptionTest.class, HexagonalGridCreationExceptionTest.class, AxialCoordinateTest.class, CoordinateConverterTest.class,
		HexagonalGridBuilderTest.class, HexagonOrientationTest.class, PointTest.class, SharedHexagonDataTest.class, HexagonalGridCalculatorImplTest.class,
		HexagonalGridImplTest.class, HexagonImplTest.class, CustomGridLayoutStrategyTest.class, HexagonalGridLayoutStrategyTest.class, RectangularGridLayoutStrategyTest.class,
		TrapezoidGridLayoutStrategyTest.class, TriangularGridLayoutStrategyTest.class, HexagonalGridLayoutTest.class, AbstractGridLayoutStrategyTest.class })
public class AllTests {

}
