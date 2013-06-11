/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.dataset.dump;

import plt.functions.MathematicalFunction;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import plt.dataset.DataSet;
import plt.utils.Preference;

/**
 *
 * @author luca
 */
public class DumpDataSet implements DataSet {
    private String[][] features;
    private List<Preference> preferences;
    
    public DumpDataSet(int numberOfObjects, int numberOfFeatures, MathematicalFunction function) {
        super();
        this.features = new String[numberOfObjects][numberOfFeatures];
        
        
        
        Random rand = new Random();
        
        for (int i=0; i<numberOfObjects; i++) {
            for (int j=0; j<numberOfFeatures; j++) {
                this.features[i][j] = ""+rand.nextDouble();
            }
        }
        
        preferences = new LinkedList<>();
        for (int i = 0; i < numberOfObjects; i++) {
            for (int j = 0; j < i; j++) {

                double iValue =  function.evaluate(features[i]);
                double jValue =  function.evaluate(features[j]);
                
                if (jValue > iValue) {
                    preferences.add(new Preference(j, i));
                } else {
                    preferences.add(new Preference(i, j));
                }
                
            }
       }
        
    }

    @Override
    public int getNumberOfObjects() {
        return features.length;
    }

    @Override
    public int getNumberOfPreferences() {
        return preferences.size();
     }

    @Override
    public int getNumberOfFeatures() {
        return features[0].length;
    }

    @Override
    public String getFeatureName(int n) {
        return "feature "+n;
    }

    @Override
    public String[] getFeatures(int n) {
        return this.features[n];
    }

    @Override
    public String getFeature(int n, int f) {
        return this.features[n][f];
    }

    @Override
    public Preference getPreference(int n) {
        return this.preferences.get(n);
    }

    @Override
    public int atomicGroup(int n) {
        return n;
    }

    @Override
    public boolean isNumeric(int n) {
        return true;
    }
        
    
}
