public class Neuron {
    ActivationFunction activationFunction;

    double activation;
    double weightedSum;
    double bias;
    double accumulatedDCdb;

    List<Axon> inputAxons;
    List<Axon> outputAxons;

    recalculateActivation() {
        weightedSum = bias;
        for (Axon axon : inputAxons) {
            weightedSum += axon.getInputNeuron().getActivation() * axon.getWeight();
        }
        activation = activationFunction.of(weightedSum);
    }

    calculateGradients() {
        double dCdN;
        if(outputAxons.size() == 0) {
            dCdN = 2*(activation-expectedValue)
        } else {
            dCdN = 0;
            for (Axon axon : outputAxons) {
                dCdN += axon.getWeight() * axon.getOutputNeuron().dCdN;
            }
        }

        double dNds = activationFunction.derivative(weightedSum);
        double dCds = dCdN * dNds;

        double dsdb = 1;
        double dCdb = dCds * dsdb;
        accumulatedDCdb += dCdb;

        for(Axon axon : inputAxons) {
            axon.calculateGradients(dCds);
        }
    }

    applyGradients(int examplesProcessed) {
        bias = Util.updateValue(accumulatedDCdb/examplesProcessed, bias);
        accumulatedDCdb = 0;

        for (Axon axon : inputAxons) {
            axon.applyGradients(true, examplesProcessed);
        }
    }
}

public class Axon {
    Neuron input;
    Neuron output;
    double weight;
    double accumulatedDCdW;

    calculateGradients(double dCds) {
        double dsdw = input.getActivation();
        double dCdw = dCds * dsdw;

        accumulatedDCdW += dCdw;

        input.calculateGradients();
    }

    applyGradients(boolean updateWeight, int examplesProcessed) {
        if (updateWeight) {
            weight = Util.updateValue(accumulatedDCdW/examplesProcessed, weight);
            accumulatedDCdW = 0;
        }
        input.applyGradients(examplesProcessed);
    }
}

public interface ActivationFunction {
    double of(double input);

    double derivative(double input);
}

public class SigmoidActivation implements ActivationFunction {
    private double eTo(double x) {
        return Math.pow(Math.E, x);
    }

    public double of(double x) {
        return 1/(1+eTo(-x));
    }

    public double derivative(double x) {
        double eToNegX = eTo(-x);
        return eToNegX/Math.pow(eToNegX + 1, 2);
    }
}