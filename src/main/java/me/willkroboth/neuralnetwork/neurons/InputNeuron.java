package me.willkroboth.neuralnetwork.neurons;

public class InputNeuron extends Neuron {
    private double input;
    public void setActivation(double activation) {
        if(input != activation) {
            input = activation;
            markActivationDirty();
        }
    }

    @Override
    protected double recalculateActivation() {
        return input;
    }

    @Override
    protected double recalculateDCdN() {
        // No input neurons to pass to
        return 0;
    }

    @Override
    protected void calculateGradients() {
        // No internal gradients to calculate
    }

    @Override
    protected void applyGradients(int examplesProcessed) {
        // No internal gradients to apply
    }
}
