package org.codetome.hexameter.core.testutils;

import org.junit.Test;

public abstract class EnumTest {

    private static final String TEST_POSTFIX = "Test";

    @SuppressWarnings("unchecked")
    @Test
    public void testEnum() throws ClassNotFoundException {
        String testClassName = getClass().getSimpleName();
        if (!testClassName.endsWith(TEST_POSTFIX)) {
            throw new IllegalArgumentException("The test class' name (" + testClassName + ") does not end with 'Test'!");
        }
        Class<?> enumClass = Class.forName(testClassName.replace(TEST_POSTFIX, ""));
        if (Enum.class.isAssignableFrom(enumClass)) {
            TestUtils.superficialEnumCodeCoverage((Class<? extends Enum<?>>) enumClass);
        } else {
            throw new IllegalArgumentException("You are trying to test a(n) " + enumClass.getSimpleName() + " which is not an enum!");
        }
    }
}
