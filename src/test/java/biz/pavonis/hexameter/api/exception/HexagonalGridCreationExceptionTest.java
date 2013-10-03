package biz.pavonis.hexameter.api.exception;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class HexagonalGridCreationExceptionTest {

	private static final String TEST_MESSAGE = "TEST_MESSAGE";

	@Test
	public void testHexagonalGridCreationException() {
		try {
			throw new HexagonalGridCreationException(TEST_MESSAGE);
		} catch (HexagonalGridCreationException e) {
			assertEquals(TEST_MESSAGE, e.getMessage());
		}
	}
}
