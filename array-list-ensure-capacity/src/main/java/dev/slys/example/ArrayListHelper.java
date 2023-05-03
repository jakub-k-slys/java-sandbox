package dev.slys.example;

import java.lang.reflect.*;
import java.util.*;

public final class ArrayListHelper {
   
    private ArrayListHelper() {}
    private static final String ELEMENT_DATA_NAME = "elementData";
    private static Field ELEMENT_DATA_FIELD;
    static {
        try {
            ELEMENT_DATA_FIELD = ArrayList.class.getDeclaredField(ELEMENT_DATA_NAME);
            ELEMENT_DATA_FIELD.setAccessible(true);
        } catch (Exception ignore) {
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> int capacity(ArrayList<E> arrayList) {
        try {
            final E[] elementData = (E[]) ELEMENT_DATA_FIELD.get(arrayList);
            return elementData.length;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
