package org.codetome.hexameter.core.testutils;

public class TestUtils {

    /**
     * This method exists only for the reason that coverage tools don't report 100% coverage
     * on enum types if we don't call values and valueOf on enums.
     * You can only say "my framework has 100% test coverage" if the user runs a coverage
     * test on it and sees the results.
     * I'm not a fan of guard rail programming but this is a rather good (and cheap)
     * marketing tool.
     */
    public static void superficialEnumCodeCoverage(Class<? extends Enum<?>> enumClass) {
        try {
            for (Object o : (Object[]) enumClass.getMethod("values").invoke(null)) {
                enumClass.getMethod("valueOf", String.class).invoke(null, o.toString());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
