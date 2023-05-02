package dev.slys.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
	ArrayList<Integer> ints = new ArrayList<>();
	System.out.println("capacity: " + ArrayListHelper.getArrayListCapacity(ints));
	for (int i = 0; i < 99; ++i) {
	  ints.add(i);
	  System.out.println("capacity: " + ArrayListHelper.getArrayListCapacity(ints));
	}
    }
}
