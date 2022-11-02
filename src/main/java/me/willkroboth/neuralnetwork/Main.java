package me.willkroboth.neuralnetwork;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Network model = new Network(2, 2, 2);
        System.out.println(Arrays.toString(model.predict(new double[]{1, 1})));
        System.out.println(Arrays.toString(model.predict(new double[]{1, 1})));
        System.out.println(Arrays.toString(model.predict(new double[]{2, 2})));
    }
}
