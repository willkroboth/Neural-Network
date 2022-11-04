package me.willkroboth.neuralnetwork.layers;

import me.willkroboth.neuralnetwork.activation.SigmoidActivation;
import me.willkroboth.neuralnetwork.neurons.FullyConnectedNeuron;

public class FullyConnectedLayer extends Layer<FullyConnectedNeuron> {
    public FullyConnectedLayer(int size, Layer<?> previousLayer) {
        super(size, FullyConnectedNeuron[]::new, () -> new FullyConnectedNeuron(previousLayer, () -> Math.random() * 2 - 1, SigmoidActivation.getInstance()));
    }
}
