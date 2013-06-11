/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.neruoevolution;

import org.junit.Before;
import org.junit.Test;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.dataset.dump.DumpDataSet;
import plt.functions.MathematicalFunction;
import plt.dataset.preprocessing.Numeric;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.GA.ParentSelection;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOver;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOverType;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.GaussianMutation;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.Invertion;
import plt.plalgorithm.neruoevolution.GA.parentselections.RouletteWheelSelection;
import plt.plalgorithm.neruoevolution.NE.ActivationFunction;
import plt.plalgorithm.neruoevolution.NE.Sigmond;
import plt.report.Report;
import plt.validator.Validator;
import plt.validator.examples.NoValidation;

/**
 *
 * @author luca
 */
public class PLNeuroEvolutionTest {
    private PLNeuroEvolution instance;
    private TrainableDataSet trainableDataSet;
    private DumpDataSet dataSet;
    
    public PLNeuroEvolutionTest() {
    }
    
    
@Before
    public void setUp() {
        
        this.dataSet = new DumpDataSet(10,2, new MathematicalFunction() {

            @Override
            public double evaluate(double[] feature) {
                double x = feature[0];
                double y = feature[1];
                
                return ((2*x) - (3*y));
            
            }
        });
        this.trainableDataSet = new PreprocessedDataSet(dataSet, new PreprocessingOperation[]{ new Numeric(dataSet, 0), new Numeric(dataSet, 1) });
        

        this.instance = new PLNeuroEvolution(this.trainableDataSet, new PLNeuroEvolutionConfigurator() {

            @Override
            public GeneticAlgorithmConfigurator getGeneticAlgorithmConfigurator() {
                return new GeneticAlgorithmConfigurator() {

                    @Override
                    public int getPopulationSize() {
                        return 200;
                    }

                    @Override
                    public int getNumberOfParents() {
                        return 20;
                    }

                    @Override
                    public int getElitSize() {
                        return 10;
                    }

                    @Override
                    public ParentSelection getParentSelection() {
                        return new RouletteWheelSelection();
                    }

                    @Override
                    public CrossOver getCrossOver() {
                        return new CrossOver(0.8, CrossOverType.UNIFORM);
                    }

                    @Override
                    public GaussianMutation getMutation() {
                        return new GaussianMutation(0.5);
                    }

                    @Override
                    public Invertion getInvertion() {
                        return new Invertion(0.1);
                    }
                };
            }

            @Override
            public int iterations() {
                return 300;
            }

            @Override
            public int[] getTopology(int inputSize) {
                return new int[] {inputSize, 6,6, 1};
            }

            @Override
            public ActivationFunction[] getActivationsFunctions() {
                return new ActivationFunction[] {new Sigmond(), new Sigmond(),  new Sigmond()};
            }
        });

}


    /**
     * Test of run method, of class PLNeuroEvolution.
     */
    @Test
    public void testRun() {

        for (int i =0; i<100; i++) {
            this.setUp();
            Validator v = new NoValidation();
            Report r  = this.instance.createModelWithValidation(v);
            System.out.println("accuracy"+ r);
        }
       

        
    }
}
