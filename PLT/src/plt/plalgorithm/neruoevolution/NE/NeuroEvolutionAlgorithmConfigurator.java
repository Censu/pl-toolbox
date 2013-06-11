package plt.plalgorithm.neruoevolution.NE;

import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;

/**
 *
 * @author luca
 */

public abstract class NeuroEvolutionAlgorithmConfigurator  {
    private GeneticAlgorithmConfigurator geneticAlgorithmConfigurator;
    
    public NeuroEvolutionAlgorithmConfigurator(GeneticAlgorithmConfigurator geneticAlgorithmConfigurator) {
        this.geneticAlgorithmConfigurator = geneticAlgorithmConfigurator;
    }
    
    public abstract int[] getTopology();

    public GeneticAlgorithmConfigurator getGeneticAlgorithmConfigurator() {
        return this.geneticAlgorithmConfigurator;
    }
   
    public abstract ActivationFunction[] getActivationsFunctions();
}
