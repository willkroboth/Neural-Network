package me.willkroboth.neuralnetwork;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class Util {
    public static <T> T[] newArrayFromSupplier(int size, IntFunction<T[]> arrayCreator, Supplier<T> objectCreator) {
        if(size <= 0) throw new IllegalArgumentException("Array size must be positive, given " + size);
        T[] array = arrayCreator.apply(size);
        for (int i = 0; i < size; i++) {
            array[i] = objectCreator.get();
        }
        return array;
    }
}
