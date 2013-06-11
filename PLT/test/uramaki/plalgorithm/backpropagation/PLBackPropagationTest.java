/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.backpropagation;

import org.junit.Before;
import org.junit.Test;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.dataset.dump.DumpDataSet;
import plt.functions.MathematicalFunction;
import plt.dataset.preprocessing.Numeric;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.plalgorithm.neruoevolution.NE.ActivationFunction;
import plt.plalgorithm.neruoevolution.NE.Linear;
import plt.plalgorithm.neruoevolution.NE.Sigmond;
import plt.report.Report;
import plt.validator.Validator;
import plt.validator.examples.NoValidation;

/**
 *
 * @author luca
 */
public class PLBackPropagationTest {

    private PLBackPropagation algorithm;
    private TrainableDataSet trainableDataSet;
    private DumpDataSet dataSet;

    @Before
    public void setUp() {
        
        this.dataSet = new DumpDataSet(10,2, new MathematicalFunction() {

            @Override
            public double evaluate(double[] feature) {
                double x = feature[0];
                double y = feature[1];
                
                //return Math.pow((2*feature[0])-(3*feature[1]),2);
                return (2*x)-(3*y);
            }
        });
        
        
        this.trainableDataSet = new PreprocessedDataSet(dataSet, new PreprocessingOperation[]{ new Numeric(dataSet, 0), new Numeric(dataSet, 1) });
        
        this.algorithm = new PLBackPropagation(this.trainableDataSet, new PLBackPropagationConfigurator() {
            @Override
            public int[] getTopology(int inputSize) {
             return new int[]{inputSize, 10, 10};
              //return new int[]{inputSize, 1};
            }

            @Override
            public ActivationFunction[] getActivationsFunctions() {
                return new ActivationFunction[]{ new Sigmond(), new Linear()};
//                return new ActivationFunction[]{ new Sigmond()};

            }

            @Override
            public double getLearningRate() {
                return 0.01;
            }

            @Override
            public double getErrorThreeshold() {
                return 0.01;
            }

            @Override
            public int getMaxNumberOfIterations() {
                return 5000;
            }
        });
    }

    /**
     * Test of run method, of class PLBackPropagation.
     */
    @Test
    public void test() {

        for (int i =0; i<100; i++) {
            this.setUp();
            Validator v = new NoValidation();
            Report r  = this.algorithm.createModelWithValidation(v);
            System.out.println( r.getAVGAccuracy());
        }
       

        
    }
}
