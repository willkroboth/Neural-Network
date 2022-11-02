package me.willkroboth.neuralnetwork.activation;

public class SigmoidActivation implements ActivationFunction {
    private static final SigmoidActivation instance = new SigmoidActivation();

    private SigmoidActivation(){}

    public static SigmoidActivation getInstance() {
        return instance;
    }

    private double eTo(double x) {
        return Math.pow(Math.E, x);
    }
    @Override
    public double of(double x) {
        return 1/(1+eTo(x));
    }

    @Override
    public double derivative(double x) {
        return eTo(x)/Math.pow(eTo(x) + 1, 2);
    }
}
