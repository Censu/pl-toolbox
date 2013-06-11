/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.neruoevolution.NE;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.GA.ParentSelection;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOver;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOverType;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.GaussianMutation;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.Invertion;
import plt.plalgorithm.neruoevolution.GA.parentselections.RouletteWheelSelection;

/**
 *
 * @author luca
 */
public class NeuroEvolutionAlgorithmTest {

    NeuroEvolutionAlgorithm instance;

    private static double fitness(SimpleNeuralNetwork n) {
        double fitness = 0;
        
        for (double j = 0; j < 10; j++) {
             double input = j/10;
            n.setInputs(new double[]{input});
            double output = n.getOutputs()[0];
            fitness += 1-Math.abs((1-input)-output);

        }
        return fitness;
    }

    @Before
    public void setUp() {
        GeneticAlgorithmConfigurator gac = new GeneticAlgorithmConfigurator() {
            @Override
            public int getPopulationSize() {
                return 5000;
            }

            @Override
            public int getNumberOfParents() {
                return 200;
            }

            @Override
            public int getElitSize() {
                return 20;
            }

            @Override
            public ParentSelection getParentSelection() {
                return new RouletteWheelSelection();
            }

            @Override
            public CrossOver getCrossOver() {
                return new CrossOver(0.2, CrossOverType.UNIFORM);
            }

            @Override
            public GaussianMutation getMutation() {
                return new GaussianMutation(0.9);
            }

            @Override
            public Invertion getInvertion() {
                return new Invertion(0.8);
            }
        };

        NeuroEvolutionAlgorithmConfigurator nec = new NeuroEvolutionAlgorithmConfigurator(gac) {
            @Override
            public int[] getTopology() {
                return new int[]{1, 6,6, 1};
            }

            @Override
            public ActivationFunction[] getActivationsFunctions() {
                ActivationFunction[] result = new ActivationFunction[this.getTopology().length-1];
                for (int i=0; i<result.length;i++) {
                    result[i] = new Sigmond();
                }
                
                return result;
            }
        };

        this.instance = new NeuroEvolutionAlgorithm(nec, new NetworkEvalutaor() {
            @Override
            public double evaluate(SimpleNeuralNetwork network) {

                return fitness(network);
            }
        });
    }

    /**
     * Test of runFor method, of class NeuroEvolutionAlgorithm.
     */
    @Test
    public void run() {
        System.out.println("run");
        
        SimpleNeuralNetwork network = null;
          this.instance.runFor(500);
         
         for (double j = 0; j < 10; j++) {
             double input = j/10;
            network.setInputs(new double[]{input});
            double output = network.getOutputs()[0];
            System.out.println(input+" ->"+output);

            }

            

         


    }
}
