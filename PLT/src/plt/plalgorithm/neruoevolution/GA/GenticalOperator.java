package plt.plalgorithm.neruoevolution.GA;

/**
 *
 * @author luca
 */
public interface GenticalOperator {
    
    public boolean isBinary();
    
    public void perform(DNA dna);
    public void perform(DNA dna1, DNA dna2);
}
