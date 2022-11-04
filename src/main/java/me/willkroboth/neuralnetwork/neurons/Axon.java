package me.willkroboth.neuralnetwork.neurons;

import me.willkroboth.neuralnetwork.Util;

import java.util.List;

public class Axon {
    private final Neuron input;
    private final Neuron output;
    private double weight;
    private double accumulatedDCdW = 0;

    public Axon(Neuron input, Neuron output, double initialWeight) {
        this.input = input;
        this.output = output;
        this.weight = initialWeight;
    }

    public double getWeight() {
        return weight;
    }

    public Neuron getInputNeuron() {
        return input;
    }

    public Neuron getOutputNeuron() {
        return output;
    }

    public void calculateGradients(boolean updateWeight, double dCds) {
        if (updateWeight) {
            double dsdw = input.getActivation();
            double dCdw = dCds * dsdw;
            accumulatedDCdW += dCdw;
        }
        input.calculateGradients();
    }

    public void applyGradients(boolean updateWeight, int examplesProcessed) {
        if (updateWeight) {
            weight = Util.updateValue(accumulatedDCdW/examplesProcessed, weight);
            accumulatedDCdW = 0;
        }
        input.applyGradients(examplesProcessed);
    }
}
