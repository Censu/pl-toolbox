package plt.plalgorithm.neruoevolution.GA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOver;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.GaussianMutation;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.Invertion;

/**
 *
 * @author luca
 */
public class Population {

    protected static Random random;
    private List<Individual> elements;
    private int populationSize;
    private double maxFitness;
    private Individual bestGene;
    private GeneticEncoder geneticEncoder;
    private boolean evaluated;

    protected Population(int size, GeneticEncoder geneticEncoder) {

        if (Population.random == null) {
            long seed = System.currentTimeMillis();
            Population.random = new Random(seed);
        }

        this.elements = new ArrayList<>();
        this.populationSize = size;
        this.geneticEncoder = geneticEncoder;

        for (int i = 0; i < size; i++) {
            Individual individual = new Individual(geneticEncoder);
            this.elements.add(individual);
        }

        this.evaluated = false;

    }

    protected void evaluate() {

        this.maxFitness = Double.NEGATIVE_INFINITY;


        for (int i = 0; i < this.getSize(); i++) {
            double currentFitness = this.getIndividual(i).getFitness();
            if (currentFitness > this.maxFitness) {
                this.maxFitness = currentFitness;
                this.bestGene = this.getIndividual(i);
            }
        }

        this.evaluated = true;

    }

    protected int getSize() {
        return elements.size();
    }

    private Individual getIndividual(int index) {
        return elements.get(index);
    }

    protected void produceNextGeneration(ParentSelection parentSelection,
            int numberOfParents, int eliteSize,
            CrossOver crossOver, Invertion invertion, GaussianMutation mutation) {

        if (!this.evaluated) {
            this.evaluate();
        }

        if (numberOfParents > this.elements.size()) {
            numberOfParents = this.elements.size();
        }

        if (eliteSize > this.elements.size()) {
            eliteSize = this.elements.size();
        }

        Collections.sort(this.elements);

        List<Individual> newGeneration = new ArrayList<>(this.populationSize);
        newGeneration.addAll(this.elements.subList(0, eliteSize));

        List<Individual> pickFrom = new ArrayList<>(numberOfParents);
        pickFrom.addAll(this.elements.subList(0, numberOfParents));
        parentSelection.setSource(pickFrom);

        while (newGeneration.size() < this.populationSize) {

            DNA a = parentSelection.select().getDna();
            DNA b = parentSelection.select().getDna();

            crossOver.perform(a, b);
            mutation.perform(a);
            mutation.perform(b);
            invertion.perform(a);
            invertion.perform(b);

            newGeneration.add(new Individual(geneticEncoder, a));
            newGeneration.add(new Individual(geneticEncoder, b));

        }

        this.elements = newGeneration;
        this.evaluated = false;

    }

    protected Object getBestPhenotype() {
        if (!this.evaluated) {
            this.evaluate();
        }
        return bestGene.getPhenotype();
    }
    
   protected Object getBestDNA() {
        if (!this.evaluated) {
            this.evaluate();
        }
        return bestGene.getDna();
    }

    protected double getMaxFitness() {

        if (!this.evaluated) {
            this.evaluate();
        }
        return maxFitness;
    }

    protected Individual get(int i) {
        return this.elements.get(i);
    }

    protected List<Individual> get() {
        return this.elements;
    }
}
