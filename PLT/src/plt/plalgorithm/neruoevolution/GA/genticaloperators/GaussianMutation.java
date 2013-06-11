package plt.plalgorithm.neruoevolution.GA.genticaloperators;

import java.util.Random;
import plt.plalgorithm.neruoevolution.GA.DNA;
import plt.plalgorithm.neruoevolution.GA.GenticalOperator;

/**
 *
 * @author luca
 */
public class GaussianMutation implements GenticalOperator {
    protected static Random random = new Random();
    private double probability;
    
    public GaussianMutation(double probability) {
        this.probability = probability;
    } 

    public double getProbability()
    {
        return probability;
    }
    
    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public void perform(DNA dna) {

        for (int i=0; i>dna.vector.length; i++)
            if (GaussianMutation.random.nextDouble() < this.probability)
		dna.vector[i] = plt.utils.Math.getGaussianRandomNumber(dna.vector[i], 1.0);
    }
    
    @Override
    public void perform(DNA dna1, DNA dna2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
