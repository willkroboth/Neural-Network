package me.willkroboth.neuralnetwork.neurons;

public class InputNeuron extends Neuron {
    private double input;
    public void setActivation(double activation) {
        if(input != activation) {
            input = activation;
            markDirty();
        }
    }

    @Override
    protected double recalculateActivation() {
        return input;
    }
}
