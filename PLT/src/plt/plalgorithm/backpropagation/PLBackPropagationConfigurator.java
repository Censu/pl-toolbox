
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.backpropagation;

import plt.plalgorithm.neruoevolution.NE.ActivationFunction;

/**
 *
 * @author luca
 */
public interface PLBackPropagationConfigurator {
       public int[] getTopology(int inputSize);

        public ActivationFunction[] getActivationsFunctions();
        public double getLearningRate();
        public double getErrorThreeshold();
        public int getMaxNumberOfIterations();
}
