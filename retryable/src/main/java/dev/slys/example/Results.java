package dev.slys.example;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Results implements Iterable<String> {

    private static final List<String> STRINGS = Arrays.asList("FOO", "BAR", "BAZ");

    @Override
    public Iterator<String> iterator() {
        return STRINGS.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Results that = (Results) obj;
        return Objects.equals(STRINGS, that.STRINGS);
    }
}
