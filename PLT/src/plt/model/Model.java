package plt.model;

import java.io.File;
import java.io.IOException;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;


/**
 *
 * @author luca
 */
public abstract class Model {
    private TrainableDataSet dataSet;
    private SelectedFeature selectedFeature;

    public Model(TrainableDataSet n, SelectedFeature c) {
        this.dataSet = n;
        this.selectedFeature = selectedFeature;
    }
    
    public TrainableDataSet getDataSet() {
        return dataSet;
    }

    public SelectedFeature selectedFeature() {
        return selectedFeature;
    }
 
    public boolean preference(int x,int y) {
        
        if (x >= dataSet.getNumberOfObjects() 
            || y >= dataSet.getNumberOfObjects()) {
            throw new IllegalArgumentException();
        }
        
        return preferenceValue(x) > preferenceValue(y)  ;
    }

    public double preferenceValue(int x) {
        SelectedFeature s = this.selectedFeature;
        if (s == null) {
            s = new SelectedFeature();
            s.setSelected(0, this.dataSet.getNumberOfFeatures() - 1);
        }
        
        double[] featuresX = s.select(this.getDataSet().getFeatures(x));
        return calculatePreference(featuresX);

    }
    
    protected abstract double calculatePreference(double[] features);

    //abstract public void save(File file) throws IOException;
    abstract public void save(File file, Experiment experiment) throws IOException;
}
