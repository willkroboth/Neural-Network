package me.willkroboth.neuralnetwork.layers;

import me.willkroboth.neuralnetwork.Util;
import me.willkroboth.neuralnetwork.neurons.Neuron;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public abstract class Layer<NeuronType extends Neuron> implements Iterable<NeuronType> {
    protected final NeuronType[] neurons;

    protected Layer(NeuronType[] neurons) {
        this.neurons = neurons;
    }

    protected Layer(int size, IntFunction<NeuronType[]> arrayCreator, Supplier<NeuronType> neuronCreator) {
        neurons = Util.newArrayFromSupplier(size, arrayCreator, neuronCreator);
    }

    public int size() {
        return neurons.length;
    }

    public NeuronType getNeuron(int i) {
        return neurons[i];
    }

    public double[] getActivations() {
        double[] activations = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            activations[i] = neurons[i].getActivation();
        }
        return activations;
    }

    @Override
    public Iterator<NeuronType> iterator() {
        return Arrays.stream(neurons).iterator();
    }
}
