package me.willkroboth.neuralnetwork.neurons;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron {
    // Handle getting and calculating activation
    private double activation;
    public final double getActivation() {
        if (!activationUpdated) {
            activation = recalculateActivation();
            activationUpdated = true;
        }

        return activation;
    }

    protected abstract double recalculateActivation();

    // Update and apply gradients
    private double dCdN;
    public final double dCdN() {
        if(!gradientUpdated) {
            dCdN = recalculateDCdN();
            gradientUpdated = true;
        }

        return dCdN;
    }

    protected abstract double recalculateDCdN();

    protected abstract void calculateGradients();

    protected abstract void applyGradients(int examplesProcessed);

    // Keep track of inputs and outputs
    protected final List<Axon> inputAxons = new ArrayList<>();
    protected final List<Axon> outputAxons = new ArrayList<>();
    protected void linkInputNeuron(Neuron input, double initialWeight) {
        Axon axon = new Axon(input, this, initialWeight);
        this.inputAxons.add(axon);
        input.outputAxons.add(axon);
    }

    private boolean activationUpdated = false;
    private boolean gradientUpdated = false;

    protected void markActivationDirty() {
        if(activationUpdated) {
            activationUpdated = false;
            for (Axon axon : outputAxons) {
                axon.getOutputNeuron().markActivationDirty();
            }
        }
        markGradientDirty();
    }

    protected void markGradientDirty() {
        if(gradientUpdated) {
            gradientUpdated = false;
            for(Axon axon: inputAxons) {
                axon.getInputNeuron().markGradientDirty();
            }
        }
    }
}
