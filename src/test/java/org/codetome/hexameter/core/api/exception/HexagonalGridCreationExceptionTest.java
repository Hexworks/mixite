package org.codetome.hexameter.core.api.exception;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HexagonalGridCreationExceptionTest {

    private static final String TEST_MESSAGE = "TEST_MESSAGE";

    @Test
    public void shouldReturnProperMessageWhenHexagonGridCreationExceptionIsThrown() {
        try {
            throw new HexagonalGridCreationException(TEST_MESSAGE);
        } catch (final HexagonalGridCreationException e) {
            assertEquals(TEST_MESSAGE, e.getMessage());
        }
    }
}
