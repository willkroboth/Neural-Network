package me.willkroboth.neuralnetwork.layers;

import me.willkroboth.neuralnetwork.neurons.InputNeuron;

public class InputLayer extends Layer<InputNeuron> {
    public InputLayer(int size) {
        super(size, InputNeuron[]::new, InputNeuron::new);
    }

    public void setActivations(double[] activations) {
        if(activations.length != neurons.length)
            throw new IllegalArgumentException(String.format("Expected same number of activations (%s) as neurons (%s)", activations.length, neurons.length));

        for (int i = 0; i < activations.length; i++) {
            neurons[i].setActivation(activations[i]);
        }
    }
}
