package plt.plalgorithm.neruoevolution.GA.parentselections;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import plt.plalgorithm.neruoevolution.GA.Individual;
import plt.plalgorithm.neruoevolution.GA.ParentSelection;

/**
 *
 * @author luca
 */
public class RouletteWheelSelection implements ParentSelection {
    private List<Individual> source;
    private double[] cumulativeFitnesses;
    protected static Random random = new Random();

    
    
    @Override
    public void setSource(List<Individual> elements) {
        if (elements == null || elements.size() < 1)
            throw  new IllegalArgumentException();
        
        this.source = elements;
        
        this.cumulativeFitnesses = new double[elements.size()];
        cumulativeFitnesses[0] = elements.get(0).getFitness();
        for (int i = 1; i < elements.size(); i++) {
            double fitness = elements.get(i).getFitness();
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }
    }

    @Override
    public Individual select() {
        if (this.source == null) 
            throw  new IllegalStateException();

        double randomFitness = RouletteWheelSelection.random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
        int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
        if (index < 0) index = Math.abs(index + 1);
        return this.source.get(index);
        
    }

    @Override
    public String getSelectionName()
    {
        return "Roulette Wheel";
    }

}
