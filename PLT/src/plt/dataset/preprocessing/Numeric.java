package plt.dataset.preprocessing;

import plt.dataset.DataSet;

/**
 *
 * @author luca
 */
public class Numeric extends PreprocessingOperation {

    public Numeric(DataSet d, int feature) {
        super(d, feature);
        if (!d.isNumeric(feature))
            throw  new IllegalArgumentException();
        
    }
   
    @Override
    public int numberOfOutput() {
        return 1;
    }

    @Override
    public double value(int object, int output) {
        return Double.parseDouble(this.getDataSet().getFeature(object, this.getFeature()));
    }
    
    @Override
    public String toString() {
        return "{Numeric - numberOfOutput: "+ this.numberOfOutput() +"}";

    }

    @Override
    public String getOperationName() {
        return "Default";
    }
    
}
