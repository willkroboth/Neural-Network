package me.willkroboth.neuralnetwork;

import me.willkroboth.neuralnetwork.layers.FullyConnectedLayer;
import me.willkroboth.neuralnetwork.layers.InputLayer;
import me.willkroboth.neuralnetwork.layers.Layer;
import me.willkroboth.neuralnetwork.layers.OutputLayer;
import me.willkroboth.neuralnetwork.neurons.FullyConnectedNeuron;

import java.util.List;

public class Network {
    private final InputLayer inputLayer;
    private final OutputLayer outputLayer;
    private final FullyConnectedLayer[] hiddenLayers;

    public Network(int... layerSizes) {
        hiddenLayers = new FullyConnectedLayer[layerSizes.length - 1];

        inputLayer = new InputLayer(layerSizes[0]);
        Layer<?> previousLayer = inputLayer;
        for (int i = 1; i < layerSizes.length; i++) {
            FullyConnectedLayer newLayer = new FullyConnectedLayer(layerSizes[i], previousLayer);
            hiddenLayers[i-1] = newLayer;
            previousLayer = newLayer;
        }
        outputLayer = new OutputLayer(previousLayer);
    }

    public double[] predict(double[] input) {
        inputLayer.setActivations(input);
        return outputLayer.getActivations();
    }

    public void applyTrainingExample(double[] input, double[] output) {
        inputLayer.setActivations(input);
        outputLayer.setExpectedOutput(output);
        outputLayer.calculateGradients();
    }

    public void train(double[][] inputs, double[][] outputs, int epochs, int batchSize) {
        if(inputs.length != outputs.length)
            throw new IllegalArgumentException("Expected inputs (%s) to correspond to outputs (%s)".formatted(inputs.length, outputs.length));

        for(int j = 0; j < epochs; j++) {
            double MSE = 0;
            for (int c = 0; c < batchSize; c++) {
                int i = (int) (Math.random() * inputs.length);

                double[] predicition = predict(inputs[i]);
                for(int k = 0; k < outputs[i].length; k++) {
                    MSE += Math.pow(predicition[k] - outputs[i][k], 2);
                }

                applyTrainingExample(inputs[i], outputs[i]);
            }
            MSE /= batchSize;
            System.out.printf("Epoch: %s, Cost: %s%n", j, MSE);

            outputLayer.applyGradients(batchSize);
        }
        double MSE = 0;
        for (int i = 0; i < inputs.length; i++) {
            double[] predicition = predict(inputs[i]);
            for(int k = 0; k < outputs[i].length; k++) {
                MSE += Math.pow(predicition[k] - outputs[i][k], 2);
            }

            applyTrainingExample(inputs[i], outputs[i]);
        }
        MSE /= inputs.length;
        System.out.printf("Final Cost: %s%n", MSE);
    }

    public void test(double[][] inputs, double[][] outputs) {
        int correct = 0;
        for (int i = 0; i < inputs.length; i++) {
            double[] result = predict(inputs[i]);

            int correctChoice = Util.argMax(outputs[i]);
            int prediction = Util.argMax(result);
            System.out.printf("Correct: %s, Predicted: %s%n", correctChoice, prediction);
            if(correctChoice == prediction) correct++;
        }
        System.out.printf("%s/%s examples classified correctly%n", correct, inputs.length);
    }

    public void printParameters() {
        int layerIndex = 0;
        for(FullyConnectedLayer layer : hiddenLayers) {
            int neuronIndex = 0;
            for (FullyConnectedNeuron neuron : layer) {
                System.out.printf("Layer %s, Neuron %s: ", layerIndex, neuronIndex);
                neuron.printInformation();
                neuronIndex++;
            }
            layerIndex ++;
        }
    }
}
