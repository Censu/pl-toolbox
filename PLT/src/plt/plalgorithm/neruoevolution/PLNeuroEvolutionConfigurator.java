package plt.plalgorithm.neruoevolution;

import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.NE.ActivationFunction;

/**
 *
 * @author luca
 */
public interface PLNeuroEvolutionConfigurator   {
    
    public GeneticAlgorithmConfigurator getGeneticAlgorithmConfigurator();

    public int iterations();
    
    public int[] getTopology(int inputSize);

    public ActivationFunction[] getActivationsFunctions();

}