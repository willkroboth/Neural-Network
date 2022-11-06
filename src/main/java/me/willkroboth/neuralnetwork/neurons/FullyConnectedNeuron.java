package me.willkroboth.neuralnetwork.neurons;

import me.willkroboth.neuralnetwork.Util;
import me.willkroboth.neuralnetwork.activation.ActivationFunction;
import me.willkroboth.neuralnetwork.layers.Layer;

import java.util.function.Supplier;

public class FullyConnectedNeuron extends Neuron {
    private final ActivationFunction activationFunction;
    private double weightedSum;
    private double bias;
    private double accumulatedDCdb = 0;

    public FullyConnectedNeuron(Layer<?> previousLayer, Supplier<Double> parameterSupplier, ActivationFunction activationFunction) {
        previousLayer.forEach((neuron -> linkInputNeuron(neuron, parameterSupplier.get())));
        bias = parameterSupplier.get();
        this.activationFunction = activationFunction;
    }

    @Override
    protected double recalculateActivation() {
        weightedSum = bias;
        for (Axon axon : inputAxons) {
            weightedSum += axon.getInputNeuron().getActivation() * axon.getWeight();
        }
        return activationFunction.of(weightedSum);
    }

    @Override
    protected double recalculateDCdN() {
        double dCdN = 0;
        for(Axon axon : outputAxons) {
            dCdN += axon.getWeight() * axon.getOutputNeuron().dCdN();
        }
        return dCdN;
    }

    @Override
    public void calculateGradients() {
        double dNds = activationFunction.derivative(weightedSum);
        double dCds = dCdN() * dNds;

        double dsdb = 1;
        double dCdb = dCds * dsdb;
        accumulatedDCdb += dCdb;

        for(Axon axon : inputAxons) {
            axon.calculateGradients(dCds);
        }
    }

    @Override
    public void applyGradients(int examplesProcessed) {
        bias = Util.updateValue(accumulatedDCdb/examplesProcessed, bias);
        accumulatedDCdb = 0;

        for (Axon axon : inputAxons) {
            axon.applyGradients(examplesProcessed);
        }
    }

    public void printInformation() {
        System.out.printf("Bias: %s ", bias);
        int weightIndex = 0;
        for(Axon axon: inputAxons) {
            System.out.printf("Weight %s: %s ", weightIndex, axon.getWeight());
            weightIndex++;
        }
        System.out.println();
    }
}
