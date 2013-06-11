package plt.plalgorithm.neruoevolution.GA;

/**
 *
 * @author luca
 */
public interface GeneticEncoder {
    
    public Object decode(DNA dna);
    public double evaluationFunction(DNA dna);
    public int dnaSize();
}
