package plt.plalgorithm.neruoevolution.GA.genticaloperators;

import java.util.Random;
import plt.plalgorithm.neruoevolution.GA.DNA;
import plt.plalgorithm.neruoevolution.GA.GenticalOperator;

/**
 *
 * @author luca
 */
public class CrossOver implements GenticalOperator {

    private double probability;
    private CrossOverType type;
    protected static Random random;

    public CrossOver(double probablity, CrossOverType type) {
        this.probability = probablity;
        this.type = type;

        if (this.probability < 0 || this.probability > 1) {
            throw new IllegalArgumentException();
        }

        if (CrossOver.random == null) {
            CrossOver.random = new Random(System.currentTimeMillis());
        }
    }
    
    public double getProbability()
    {
        return probability;
    }
    
    public CrossOverType getCrossOverType()
    {
        return type;
    }

    @Override
    public boolean isBinary() {
        return true;
    }

    @Override
    public void perform(DNA dna) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void perform(DNA dna1, DNA dna2) {

        if (CrossOver.random.nextDouble() > this.probability) {
            return;
        }

        if (type == CrossOverType.ONEPOINT) {
            this.perform(dna1, dna2, CrossOver.random.nextInt(dna1.vector.length));
        } else if (type == CrossOverType.TWOPOINT) {
            this.perform(dna1, dna2,
                    CrossOver.random.nextInt(dna1.vector.length),
                    CrossOver.random.nextInt(dna1.vector.length));
        } else {
            boolean where[] = new boolean[dna1.vector.length];
            for (int i = 0; i < dna1.vector.length; i++) {
                where[i] = CrossOver.random.nextDouble() < 0.5;
            }

            this.perform(dna1, dna2, where);
        }

    }

    protected void perform(DNA dna1, DNA dna2, int point) {

        for (int i = 0; i < dna1.vector.length; i++) {
            double value1 = dna1.vector[i];
            double value2 = dna2.vector[i];

            dna1.vector[i] = i >= point ? value2 : value1;
            dna2.vector[i] = i >= point ? value1 : value2;
        }
    }

    protected void perform(DNA dna1, DNA dna2, int point1, int point2) {
        for (int i = 0; i < dna1.vector.length; i++) {
            double value1 = dna1.vector[i];
            double value2 = dna2.vector[i];

            dna1.vector[i] = i >= point1 && i <= point2 ? value2 : value1;
            dna2.vector[i] = i >= point1 && i <= point2 ? value1 : value2;
        }
    }

    protected void perform(DNA dna1, DNA dna2, boolean[] where) {
        for (int i = 0; i < dna1.vector.length; i++) {
            double value1 = dna1.vector[i];
            double value2 = dna2.vector[i];

            dna1.vector[i] = where[i] ? value2 : value1;
            dna2.vector[i] = where[i] ? value1 : value2;
        }
    }
}
