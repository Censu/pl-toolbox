/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.validator.examples;

import java.util.Arrays;
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
public class KFoldCVTest {
    private TrainableDataSet dataset;
    private PLAlgorithm algorithm;

    @Before
    public void setUp() {
        
        final int numberOfObject = 30;
        final int groupSize = 1+2+3+4+5+6+7+8+9;
        DataSet d = new DataSet() {

            @Override
            public int getNumberOfObjects() {
                return numberOfObject;
            }

            @Override
            public int getNumberOfPreferences() {
                return groupSize*3;
            }

            @Override
            public int getNumberOfFeatures() {
                return 2;
            }

            @Override
            public String getFeatureName(int n) {
                return "Feature-"+n;
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
                int group = n/groupSize;
                
                int [] intList = new int[10];
                for (int i=0;i<10;i++) {
                    intList[i] = i+(group*10);
                }

                List<Preference> result = Preference.listToPairWisePreference(intList);
                return result.get(n%groupSize);
            }

            

            @Override
            public int atomicGroup(int n) {
                return n/groupSize;
            }

            @Override
            public boolean isNumeric(int n) {
                return true;
            }
        };
        
        this.dataset = new PreprocessedDataSet(d, new PreprocessingOperation[]{ new MinMax(d, 0, 0, numberOfObject*2),new MinMax(d, 1, 0, numberOfObject*2) });
        this.algorithm = new PLAlgorithm(dataset) {

            @Override
            protected Model run() {
                return new Model(dataset, null) {

                    @Override
                    protected double calculatePreference(double[] features) {
                        if (features[0]*numberOfObject*2 < 20) { 
                            return -features[0];
                        }
                        else {
                            return features[0];
                        }
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
        Report createModelWithValidation = algorithm.createModelWithValidation(new KFoldCV(3));
        assertEquals(createModelWithValidation.resultAccurancy(0), 1.0, 0.001);
        assertEquals(createModelWithValidation.resultAccurancy(1), 1.0, 0.001);
        assertEquals(createModelWithValidation.resultAccurancy(2), 0.0, 0.001);
        assertEquals(createModelWithValidation.getAccuracy(), 0.666, 0.001);

    }


}
