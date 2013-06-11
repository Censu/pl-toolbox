package plt.plalgorithm.neruoevolution.GA.genticaloperators;

import java.util.Random;
import plt.plalgorithm.neruoevolution.GA.DNA;
import plt.plalgorithm.neruoevolution.GA.GenticalOperator;

/**
 *
 * @author luca
 */
public class Invertion implements GenticalOperator {
    protected static Random random = new Random();
    private double probability;
    
    public Invertion(double probability) {
        this.probability = probability;
    } 

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public void perform(DNA dna) {
        this.perform(dna, 
                Invertion.random.nextInt(dna.vector.length),
                Invertion.random.nextInt(dna.vector.length));
    }
        
    
    private void perform(DNA dna, int point1, int point2) {
        for (int i = 0; i < dna.vector.length; i++)
            if (i >= point1  && i <= point2)
                dna.vector[i] = 1-dna.vector[i];
    }
    
    @Override
    public void perform(DNA dna1, DNA dna2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
