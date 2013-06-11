package plt.plalgorithm.neruoevolution.NE;

import java.util.Arrays;

/**
 *
 * @author luca
 */
public class SimpleNeuralNetwork implements Cloneable {

    public double[] weights;
    public int[] topology;
    public double[] outputs;
    public double[] inputs;
    public ActivationFunction[] activationFunctions;

    public SimpleNeuralNetwork(int[] topology, ActivationFunction[] activationFunctions) {

        if (topology.length < 2) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < topology.length; i++) {
            if (topology[i] <= 0) {
                throw new IllegalArgumentException();
            }
        }

        if (topology.length != activationFunctions.length + 1) {
            throw new IllegalArgumentException();
        }

        this.topology = topology;
        this.weights = new double[this.getNumberOfWeights()];
        this.activationFunctions = activationFunctions;


    }

    public int getNumberOfNerons() {
        int result = 0;
        for (int i = 1; i < topology.length; i++) {
            result += topology[i];
        }
        return result;
    }

    public int getNumberOfWeights() {

        int result = 0;
        for (int l = 1; l < topology.length; l++) {
            result += topology[l] * (1 + topology[l - 1]);
        }

        return result;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
        this.outputs = null;
    }

    public void setWeights(double[] weights) {
        if (weights.length != getNumberOfWeights()) {
            throw new RuntimeException();
        }

        this.weights = weights;
        this.outputs = null;
    }

    public double[] getOutputs() {
        if (this.outputs == null) {
            activate();
        }
        return this.outputs;
    }

    protected void activate() {
        outputs = new double[topology[topology.length - 1]];
        for (int i = 0; i < outputs.length; i++) {
            outputs[i] = getValueOf(topology.length - 1, i);
        }
    }
    
    protected double getValueOf(int layer, int neuron) {
        if (layer == 0) {
            return inputs[neuron];
        }
        return getAValueOf(layer, neuron);
    }

    protected double getSValueOf(int layer, int neuron) {
        if (layer == 0) {
            throw new IllegalArgumentException();
        }

        int weightPointer = weightPointer(layer, neuron);
        double result = -weights[weightPointer++];
        for (int i = 0; i < topology[layer - 1]; i++) {
            result += getValueOf(layer - 1, i) * weights[weightPointer++];
        }
        
        return result;
        
    }
    protected double getAValueOf(int layer, int neuron) {

        return activationFunctions[layer - 1].evalue(getSValueOf(layer, neuron));
    }

    protected int weightPointer(int layer, int neuron) {
        int result = 0;
        for (int l = 1; l < layer; l++) {
            result += topology[l] * (1 + topology[l - 1]);
        }
        result += neuron * (1 + topology[layer - 1]);

        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SimpleNeuralNetwork n = new SimpleNeuralNetwork(this.topology, this.activationFunctions);
        n.weights = this.weights.clone();

        if (this.inputs != null) {
            n.inputs = this.inputs.clone();
        }

        return n;
    }
}
