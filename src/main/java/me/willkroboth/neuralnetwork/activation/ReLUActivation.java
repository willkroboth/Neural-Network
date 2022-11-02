package me.willkroboth.neuralnetwork.activation;

public class ReLUActivation implements ActivationFunction {
    private static final ReLUActivation instance = new ReLUActivation();

    private ReLUActivation(){}

    public static ReLUActivation getInstance() {
        return instance;
    }
    @Override
    public double of(double input) {
        return Math.max(0, input);
    }

    @Override
    public double derivative(double input) {
        return input <= 0 ? 0 : 1;
    }
}
