package plt.dataset.preprocessing;

import plt.dataset.DataSet;

/**
 *
 * @author luca
 */
public abstract  class PreprocessingOperation {
    
    private DataSet dataSet;
    private int feature;
    
    public PreprocessingOperation(DataSet d,int feature) {
        this.dataSet = d;
        this.feature = feature;
    }
    
    public DataSet getDataSet() {
        return this.dataSet;
    }
    
    public int getFeature() {
        return this.feature;
    }
    
    abstract public int numberOfOutput();
    
    abstract public double value(int object, int output);
    
    abstract public String getOperationName();
}
