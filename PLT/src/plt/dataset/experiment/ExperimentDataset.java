/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.dataset.experiment;

import java.util.Random;
import plt.dataset.DataSet;
import plt.functions.MathematicalFunction;
import plt.utils.Preference;

/**
 *
 * @author luca
 */
public class ExperimentDataset implements DataSet {
    private final int numberOfReports;
    private final int numberOfPrefernecesForReport;
    private final String[][] features;
    private final MathematicalFunction function;
    private final int numberOfObjectsForReport;

    public ExperimentDataset(int numberOfReports, int numberOfObjectsForReport, 
        int numberOfFeaturesForObject, MathematicalFunction function) {
        this.numberOfReports = numberOfReports;
        
        int n = 0;
        for (int i=0; i<numberOfObjectsForReport; i++) {
            n += i;
        }
        
        this.numberOfPrefernecesForReport = n;
        this.features = new String[numberOfObjectsForReport*numberOfReports][numberOfFeaturesForObject];
        this.function = function;
        this.numberOfObjectsForReport= numberOfObjectsForReport;
        
        Random r = new Random();
        for (int i=0; i< numberOfObjectsForReport*numberOfReports; i++ )
            for (int j=0; j< numberOfFeaturesForObject; j++)
                features[i][j] = ""+r.nextDouble();
        
        
    }
    
    @Override
    public int getNumberOfObjects() {
        return features.length;
    }

    @Override
    public int getNumberOfPreferences() {
        return numberOfPrefernecesForReport*numberOfReports;
    }

    @Override
    public int getNumberOfFeatures() {
        return features[0].length;
    }

    @Override
    public String getFeatureName(int n) {
        return "Feature "+n;
    }

    @Override
    public String getFeature(int n, int f) {
        return this.getFeatures(n)[f];
    }

    @Override
    public boolean isNumeric(int n) {
        return true;
    }
    
    @Override
    public int atomicGroup(int n) {
        return n;
    }
    
    
    @Override
    public String[] getFeatures(int n) {
        return this.features[n];
    }
    
    @Override
    public Preference getPreference(int n) {
        
        int offset = n/this.numberOfPrefernecesForReport;
        int m = n%this.numberOfPrefernecesForReport ;
        
        int x = 0;
        int a = 0;
        int b = 0;
        
        for (int i = 0; i < this.numberOfObjectsForReport; i++) {
            for (int j = 0; j < i; j++) {
                if (x == m) {
                    a = i+(offset*numberOfObjectsForReport);
                    b = j+(offset*numberOfObjectsForReport);
                }                
                x++;
            }
        }
        


        
        double aValue =  function.evaluate(features[a]);
        double bValue =  function.evaluate(features[b]);
        
        if (aValue > bValue) {
            return new Preference(a, b);
        } else {
            return new Preference(b, a);
      }
                
        
        
    }

    
}
