package me.willkroboth.neuralnetwork.neurons;

import me.willkroboth.neuralnetwork.Util;
import me.willkroboth.neuralnetwork.activation.ActivationFunction;
import me.willkroboth.neuralnetwork.layers.Layer;

import java.util.function.Supplier;

public class FullyConnectedNeuron extends Neuron {
    private final Layer<?> previousLayer;
    private final ActivationFunction activationFunction;
    private Double[] weights;
    private double bias;

    public FullyConnectedNeuron(Layer<?> previousLayer, Supplier<Double> parameterSupplier, ActivationFunction activationFunction) {
        this.previousLayer = previousLayer;
        for (Neuron neuron : previousLayer) {
            neuron.addDependentNeuron(this);
        }
        weights = Util.newArrayFromSupplier(previousLayer.size(), Double[]::new, parameterSupplier);
        bias = parameterSupplier.get();
        this.activationFunction = activationFunction;
    }

    @Override
    protected double recalculateActivation() {
        double activation = bias;
        for (int i = 0; i < weights.length; i++) {
            activation += previousLayer.getNeuron(i).getActivation() * weights[i];
        }
        return activationFunction.of(activation);
    }
}
