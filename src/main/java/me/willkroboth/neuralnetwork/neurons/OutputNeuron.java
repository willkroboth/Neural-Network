package me.willkroboth.neuralnetwork.neurons;

public class OutputNeuron extends Neuron {
    private final Neuron constituent;

    public OutputNeuron(Neuron constituent) {
        this.constituent = constituent;
        linkInputNeuron(constituent, 1);
    }

    @Override
    protected double recalculateActivation() {
        return constituent.getActivation();
    }

    private double expectedValue;
    public void setExpectedValue(double result) {
        if(expectedValue != result) {
            expectedValue = result;
            markGradientDirty();
        }
    }

    @Override
    protected double recalculateDCdN() {
        return 2*(getActivation()-expectedValue);
    }

    @Override
    public void calculateGradients() {
        // No internal gradients to calculate
        for(Axon axon : inputAxons) {
            axon.calculateGradients(false, 0);
        }
    }

    @Override
    public void applyGradients(int examplesProcessed) {
        // No internal gradients to update
        for (Axon axon : inputAxons) {
            axon.applyGradients(false, examplesProcessed);
        }
    }
}
