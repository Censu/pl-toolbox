/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.validator.examples;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import plt.dataset.DataSet;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.dataset.preprocessing.MinMax;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.model.Model;
import plt.plalgorithm.PLAlgorithm;
import plt.report.Report;
import plt.utils.Preference;


/**
 *
 * @author luca
 */
public class NoValidationTest {
    private TrainableDataSet dataset;
    private PLAlgorithm algorithm;

    @Before
    public void setUp() {
        
        DataSet d = new DataSet() {

            @Override
            public int getNumberOfObjects() {
                return 10;
            }

            @Override
            public int getNumberOfPreferences() {
                return 1+2+3+4+5+6+7+8+9;
            }

            @Override
            public int getNumberOfFeatures() {
                return 2;
            }

            @Override
            public String getFeatureName(int n) {
                return "Feature";
            }

            @Override
            public String[] getFeatures(int n) {
                return new String[] {this.getFeature(n, 0), this.getFeature(n, 1)};
            }

            @Override
            public String getFeature(int n, int f) {
                return n*(f+1)+"";
            }

            @Override
            public Preference getPreference(int n) {
                String[] list = "0 1 2 3 4 5 6 7 8 9".split(" ");
                int[] intList = new int[list.length];
                for (int i=0;i<list.length;i++) {
                    intList[i] = Integer.parseInt(list[i]);
                }

                List<Preference> result = Preference.listToPairWisePreference(intList);
                return result.get(n);
            }

            

            @Override
            public int atomicGroup(int n) {
                return 0;
            }

            @Override
            public boolean isNumeric(int n) {
                return true;
            }
        };
        
        this.dataset = new PreprocessedDataSet(d, new PreprocessingOperation[]{ new MinMax(d, 0, 0, 18),new MinMax(d, 1, 0, 18) });
        this.algorithm = new PLAlgorithm(dataset) {

            @Override
            protected Model run() {
                return new Model(dataset, null) {

                    @Override
                    protected double calculatePreference(double[] features) {
                        return -features[0];
                    }
                };
            }
        };
        
        
    }

    /**
     * Test of runWithValidation method, of class NoValidation.
     */
    @Test
    public void run() {
        Report createModelWithValidation = algorithm.createModelWithValidation(new NoValidation());
        assertEquals(createModelWithValidation.getAccuracy(), 1.0, 0.001);

    }


}
