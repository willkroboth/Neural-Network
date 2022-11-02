package me.willkroboth.neuralnetwork.activation;

public interface ActivationFunction {
    double of(double input);

    double derivative(double input);
}
