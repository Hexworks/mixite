package org.codetome.hexameter.core.backport;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalTest {

    @Test
    public void testEmpty() throws Exception {
        Optional.empty();
    }

    @Test
    public void testOf() throws Exception {
        Optional.of("value");
    }

    @Test
    public void testOfNullable() throws Exception {
        Optional.ofNullable("value");
    }

    @Test
    public void testGet() throws Exception {
        Optional.of("value").get();
    }

    @Test
    public void testIsPresent() throws Exception {
        Optional.empty().isPresent();
    }

    @Test
    public void testIfPresent() throws Exception {
        Optional.of("value").isPresent();
    }

    @Test
    public void testFilter() throws Exception {
        Optional.of("value").filter(new Predicate<String>() {
            @Override
            public boolean test(String String) {
                return false;
            }
        });
    }

    @Test
    public void testMap() throws Exception {
        Optional.of("value").map(new Function<String, Object>() {
            @Override
            public Object apply(String String) {
                return null;
            }
        });
    }

    @Test
    public void testFlatMap() throws Exception {
        Optional.of("value").flatMap(new Function<String, Optional<String>>() {
            @Override
            public Optional<String> apply(String s) {
                return Optional.of("");
            }
        });
    }

    @Test
    public void testOrElse() throws Exception {
        Optional.of("value").orElse("someother");
    }

    @Test
    public void testOrElseGet() throws Exception {
        Optional.of("value").orElseGet(new Supplier<String>() {
            @Override
            public String get() {
                return null;
            }
        });
    }

    @Test
    public void testOrElseThrow() throws Throwable {
        Optional.of("value").orElseThrow(new Supplier<Throwable>() {
            @Override
            public Throwable get() {
                return null;
            }
        });
    }

    @Test
    public void testMisc() {
        Optional.of("").equals(null);
        Optional.of("").toString();
        Optional.of("").hashCode();
    }
}
