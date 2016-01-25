package org.codetome.hexameter.core.api.exception;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HexagonNotFoundExceptionTest {

    private static final String TEST_MESSAGE = "TEST_MESSAGE";
    private static final Throwable TEST_CAUSE = new Throwable();

    @Test
    public void shouldReturnProperMessageAndCauseWhenHexagonNotFoundExceptionIsThrown() {
        try {
            throw new HexagonNotFoundException(TEST_MESSAGE, TEST_CAUSE);
        } catch (final HexagonNotFoundException e) {
            assertEquals(TEST_MESSAGE, e.getMessage());
            assertEquals(TEST_CAUSE, e.getCause());
        }

    }

    @Test
    public void shouldReturnProperMessageWhenHexagonNotFoundExceptionIsThrown() {
        try {
            throw new HexagonNotFoundException(TEST_MESSAGE);
        } catch (final HexagonNotFoundException e) {
            assertEquals(TEST_MESSAGE, e.getMessage());
        }
    }

}
