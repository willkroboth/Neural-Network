package me.willkroboth.neuralnetwork;

import me.willkroboth.neuralnetwork.layers.FullyConnectedLayer;
import me.willkroboth.neuralnetwork.layers.InputLayer;
import me.willkroboth.neuralnetwork.layers.Layer;

public class Network {
    private final InputLayer inputLayer;
    private final Layer<?> outputLayer;

    public Network(int... layerSizes) {
        inputLayer = new InputLayer(layerSizes[0]);
        Layer<?> previousLayer = inputLayer;
        for (int i = 1; i < layerSizes.length; i++) {
            previousLayer = new FullyConnectedLayer(layerSizes[i], previousLayer);
        }
        outputLayer = previousLayer;
    }

    public double[] predict(double[] input) {
        inputLayer.setActivations(input);
        return outputLayer.getActivations();
    }
}
