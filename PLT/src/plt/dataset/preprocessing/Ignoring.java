package plt.dataset.preprocessing;

import plt.dataset.DataSet;

/**
 *
 * @author luca
 */
public class Ignoring extends PreprocessingOperation {

    public Ignoring(DataSet d, int feature) {
        super(d, feature);
    }

    @Override
    public int numberOfOutput() {
        return 0;
    }

    @Override
    public double value(int object, int output) {
        return 0;
    }
    
    @Override
    public String toString() {
        return "{Ignoring - numberOfOutput: "+ this.numberOfOutput() +"}";
    }

    @Override
    public String getOperationName() {
        return "Ignoring";
    }
    
}
