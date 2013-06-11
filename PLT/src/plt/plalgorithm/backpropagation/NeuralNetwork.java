/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.backpropagation;

import java.util.Arrays;
import java.util.Random;
import plt.plalgorithm.neruoevolution.NE.ActivationFunction;
import plt.plalgorithm.neruoevolution.NE.SimpleNeuralNetwork;

/**
 *
 * @author luca
 */
public class NeuralNetwork extends SimpleNeuralNetwork {

   
    public double[] deltas;
    public double[] gradients;

    public double learningRate;

    public NeuralNetwork(int[] topology, ActivationFunction[] activationFunctions, double learningRate) {
        super(topology, activationFunctions);
        this.deltas = new double[this.weights.length];
        this.gradients = new double[this.weights.length];

        this.learningRate = learningRate;
        
        Random r = new Random();
        for (int i=0; i< this.weights.length; i++) {
           this.weights[i] = (r.nextDouble()*2)-1;
           this.deltas[i] = 0;
           this.gradients[i] = 0;
        }
    }

    public double backpropagate(boolean preferred, double otherValue) {

 
       this.activate();
       
       for (int layer = this.topology.length - 1; layer > 0; layer--) {
            for (int neuron = 0; neuron < this.topology[layer]; neuron++) {

                if (layer == this.topology.length - 1) {
                    this.calculateDeltaForOutputNueron(layer, neuron, preferred, otherValue);
                } else {
                    this.calculateDeltaForNueron(layer, neuron);
                }
                
            }
        }
                

        for (int i=0; i< this.weights.length; i++) {
            this.deltas[i] += this.gradients[i];
            this.gradients[i] = 0;
        }
        
        double fP = preferred ? this.outputs[0] : otherValue;
        double fN = preferred ? otherValue : this.outputs[0];
        
        
        return Math.max(1- (fP-fN),0);
    }
    
    public void applyDeltas() {
        for (int i=0; i< this.weights.length; i++) {
            this.weights[i] -= (this.learningRate*this.deltas[i]);
            this.deltas[i] = 0;
        }
    }

    private void calculateDeltaForOutputNueron(int layer, int neuron, boolean preferred, double otherValue) {
        
        if (layer != this.topology.length - 1) {
            throw new IllegalArgumentException();
        }
        
        int weightPointer = weightPointer(layer, neuron);
        double output = this.getValueOf(layer, neuron);
        double dOutput = this.activationFunctions[layer-1].evalueDerivative(output);
        
        double de = 0;
        double fP = preferred ? this.outputs[0] : otherValue;
        double fN = preferred ? otherValue : this.outputs[0];

        if (fP - fN < 1) {
            de = preferred ? -1 : 1;
        }

        for (int i = 0; i <= topology[layer - 1]; i++) {
            double input =  (i==0) ? -1 : this.getValueOf(layer - 1, i-1);
            this.gradients[weightPointer++] = de * dOutput * input;
        }
        
    }

    private void calculateDeltaForNueron(int layer, int neuron) {
        
        if (layer > this.topology.length - 2 || layer < 1) {
            throw new IllegalArgumentException();
        }
        
        double output = this.getValueOf(layer, neuron);
        double dOutput = this.activationFunctions[layer-1].evalueDerivative(output);

        double d = 0;
        for (int k =0; k < topology[layer+1]; k++) {
            int w = this.weightPointer(layer+1, k) + neuron+1;
            d +=  (this.gradients[w]/output)* this.weights[w];
        }
        
        int weightPointer = weightPointer(layer, neuron);
        for (int i = 0; i <= topology[layer - 1]; i++) {
            double input =  (i==0) ? -1 : this.getValueOf(layer - 1, i-1);
            this.gradients[weightPointer++] = d * dOutput * input;
        }
    }
}
