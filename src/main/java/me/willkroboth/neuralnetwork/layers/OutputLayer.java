package me.willkroboth.neuralnetwork.layers;

import me.willkroboth.neuralnetwork.neurons.Neuron;
import me.willkroboth.neuralnetwork.neurons.OutputNeuron;

public class OutputLayer extends Layer<OutputNeuron> {
    public OutputLayer(Layer<?> previousLayer) {
        super(previousLayer.size(), OutputNeuron[]::new, (i) -> new OutputNeuron(previousLayer.getNeuron(i)));
    }

    public void setExpectedOutput(double[] results) {
        if(results.length != neurons.length)
            throw new IllegalArgumentException(String.format("Expected same number of results (%s) as neurons (%s)", results.length, neurons.length));

        for (int i = 0; i < results.length; i++) {
            neurons[i].setExpectedValue(results[i]);
        }
    }

    public void calculateGradients() {
        for (OutputNeuron neuron : neurons) {
            neuron.calculateGradients();
        }
    }

    public void applyGradients(int examplesProcessed) {
        for(OutputNeuron neuron : neurons) {
            neuron.applyGradients(examplesProcessed);
        }
    }
}
