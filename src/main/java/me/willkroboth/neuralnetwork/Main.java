package me.willkroboth.neuralnetwork;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        double[][] inputs = new double[][]{
                {1.64, 2.01},
                {1.83, 1.5},
                {1.12, 2.09},
                {0.47, 2.31},
                {0.49, 1.83},
                {1.12, 1.47},
                {1.54, 0.88},
                {1.27, 0.41},
                {0.57, 0.91},
                {2.13, 0.94},
                {2.59, 1.29},
                {2.61, 2.60},
                {2.89, 1.85},
                {2.43, 2.04},
                {2.15, 1.74},
                {1.68, 2.33},
                {2.01, 2.62},
                {2.74, 0.54},
                {1.19, 2.55},
                {0.60, 2.69}
        };

        double[][] outputs = new double[][]{
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {1, 0},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 1},
                {0, 1}
        };

        Network model = new Network(2, 2, 2);
        model.train(inputs, outputs, 100000, 10);

        int correct = 0;
        for (int i = 0; i < inputs.length; i++) {
            double[] result = model.predict(inputs[i]);
            System.out.printf("%s, %s%n", result[0] > result[1], outputs[i][0] > outputs[i][1]);
            if (result[0] > result[1] == outputs[i][0] > outputs[i][1]) correct++;
        }
        System.out.printf("%s/%s examples classified correctly%n", correct, inputs.length);
        model.printParameters();

        while (true) {
            System.out.println("Height?");
            double height = userInput.nextDouble();
            System.out.println("Petal Length?");
            double length = userInput.nextDouble();

            System.out.println(Arrays.toString(model.predict(new double[]{height, length})));
        }
    }
}
