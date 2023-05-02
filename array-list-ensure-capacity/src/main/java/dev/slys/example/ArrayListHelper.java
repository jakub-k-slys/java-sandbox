package dev.slys.example;

import java.lang.reflect.*;
import java.util.*;

public final class ArrayListHelper {
   
    private ArrayListHelper() {}
    private static final Field field;
    static {
        try {
            field = ArrayList.class.getDeclaredField("elementData");
            field.setAccessible(true);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> int getArrayListCapacity(ArrayList<E> arrayList) {
        try {
            final E[] elementData = (E[]) field.get(arrayList);
            return elementData.length;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
