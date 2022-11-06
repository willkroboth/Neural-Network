package me.willkroboth.neuralnetwork;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        seedsData();
    }

    private static String readFile(String directory, String fileName) throws IOException {
        try {
            return Files.readString(Path.of(directory + fileName), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            System.out.println("File input for \"" + directory + fileName + "\" failed:");
            throw e;
        }
    }

    private static double[][][] testTrainSplit(double[][] inputs, double[][] outputs, double percentTrain) {
        Integer[] indexes = new Integer[inputs.length];
        Arrays.setAll(indexes, (i) -> i);
        // Shuffle array
        for (int i = indexes.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = indexes[j];
            indexes[j] = indexes[i];
            indexes[i] = temp;
        }
        System.out.println(Arrays.toString(indexes));

        int countTrain = (int) (percentTrain * inputs.length);
        double[][] trainInput = new double[countTrain][inputs[0].length];
        double[][] trainOutput = new double[countTrain][outputs[0].length];
        int i;
        for (i = 0; i < countTrain; i++) {
            trainInput[i] = inputs[indexes[i]];
            trainOutput[i] = outputs[indexes[i]];
        }

        int countTest = inputs.length - countTrain;
        double[][] testInput = new double[countTest][inputs[0].length];
        double[][] testOutput = new double[countTest][outputs[0].length];
        for (; i < inputs.length; i++) {
            testInput[i - countTrain] = inputs[indexes[i]];
            testOutput[i - countTrain] = outputs[indexes[i]];
        }

        System.out.println("Train Input");
        System.out.println(Arrays.deepToString(trainInput));
        System.out.println("Train Output");
        System.out.println(Arrays.deepToString(trainOutput));
        System.out.println("Test Input");
        System.out.println(Arrays.deepToString(testInput));
        System.out.println("Test Output");
        System.out.println(Arrays.deepToString(testOutput));

        return new double[][][]{trainInput, trainOutput, testInput, testOutput};
    }

    public static void seedsData() {
        double[][][] data = readSeedData();
        double[][] inputs = data[0];
        double[][] outputs = data[1];

        double[][][] split = testTrainSplit(inputs, outputs, 0.9);
        double[][] trainInput = split[0];
        double[][] trainOutput = split[1];
        double[][] testInput = split[2];
        double[][] testOutput = split[3];

        Network model = new Network(inputs[0].length, 10, 5, outputs[0].length);

        model.train(trainInput, trainOutput, 100000);

        model.printParameters();

        model.test(testInput, testOutput, true);

        model.test(inputs, outputs, false);
    }

    private static double[][][] readSeedData() {
        String rawInput;
        try {
            rawInput = readFile("/Users/wk48343/Desktop/Java Programs/Neural-Network/", "seeds_dataset.txt");
        } catch (IOException e) {
            throw new TerminateProgram(e);
        }
        System.out.println("Raw data:");
        System.out.println(rawInput);
        String[] entries = rawInput.split("\n");
        double[][] inputs = new double[entries.length][7];
        double[][] outputs = new double[entries.length][3];
        for (int i = 0; i < entries.length; i++) {
            String[] data = entries[i].split("\t");
            for (int j = 0; j < 7; j++) {
                inputs[i][j] = Double.parseDouble(data[j]);
            }

            int choice = Integer.parseInt(data[7]);
            outputs[i][choice - 1] = 1;
        }
        System.out.println("Processed input");
        System.out.println(Arrays.deepToString(inputs));
        System.out.println("Processed outputs");
        System.out.println(Arrays.deepToString(outputs));
        return new double[][][]{inputs, outputs};
    }

    public static void testPetalData() {
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

        model.test(inputs, outputs, true);
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
