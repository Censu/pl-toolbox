package plt.plalgorithm.neruoevolution.GA;

/**
 *
 * @author luca
 */
public class Individual implements Comparable {
    private DNA dna;
    private GeneticEncoder geneticEncoder;
    private boolean evalueted;
    private double fitness;
    
    public Individual (GeneticEncoder geneticEncoder) {
        this(geneticEncoder, new DNA(geneticEncoder.dnaSize()));
    }
    
    public Individual (GeneticEncoder geneticEncoder, DNA dna)  {
        this.geneticEncoder = geneticEncoder;
        this.dna = dna;
        this.evalueted = false;
    }

    public DNA getDna() {
        DNA result = null;
        
        try {
            result = (DNA) dna.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException();
        }
        
        return result;
    }


    @Override
    public int compareTo(Object o) {
        Individual other = (Individual) o;

        if (this.getFitness() > other.getFitness()) {
            return -1;
        } else if (this.getFitness() < other.getFitness()) {
            return 1;
        } else {
            return 0;
        }
    }
    
     public double getFitness() {
        if (!evalueted) 
            this.fitness = this.geneticEncoder.evaluationFunction(this.getDna());
        
        evalueted = true;
        return fitness;
    }
     
    public Object getPhenotype() {
        try {
            return geneticEncoder.decode((DNA)this.dna.clone());
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException();
        }
    }
    
}
