package plt.plalgorithm.neruoevolution.GA;

import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOver;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.GaussianMutation;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.Invertion;

/**
 *
 * @author luca
 */
public interface GeneticAlgorithmConfigurator {

    public int getIterations();
    public int getPopulationSize();
    public int getNumberOfParents();
    public int getElitSize();
    public ParentSelection getParentSelection();  
    public CrossOver getCrossOver();
    public GaussianMutation getMutation();
    public Invertion getInvertion();
    
}
