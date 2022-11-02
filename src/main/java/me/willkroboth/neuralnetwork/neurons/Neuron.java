package me.willkroboth.neuralnetwork.neurons;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron {
    private double activation;
    public final double getActivation() {
        if (isDirty) {
            activation = recalculateActivation();
            isDirty = false;
        }

        return activation;
    }

    protected abstract double recalculateActivation();

    private final List<Neuron> dependents = new ArrayList<>();
    protected void addDependentNeuron(Neuron neuron) {
        dependents.add(neuron);
    }

    private boolean isDirty = true;
    protected void markDirty() {
        if(!isDirty) {
            isDirty = true;
            for (Neuron neuron : dependents) {
                neuron.markDirty();
            }
        }
    }
}
