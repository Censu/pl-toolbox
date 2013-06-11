package plt.dataset.preprocessing;

import java.util.LinkedList;
import java.util.List;
import plt.dataset.DataSet;

/**
 *
 * @author luca
 */
public class Nominal extends PreprocessingOperation {
    private List<String> possibleValues;

    public Nominal(DataSet d, int featre) {
        super(d, featre);
    }

    @Override
    public int numberOfOutput() {
        return 1;
    }

    @Override
    public double value(int object, int output) {
       if (possibleValues == null) preparePossibleValues();

        String value = this.getDataSet().getFeature(object, this.getFeature());
        return this.possibleValues.indexOf(value);        
    }
    
    private void preparePossibleValues() {
        possibleValues = new LinkedList<>();
        for (int i=0; i< this.getDataSet().getNumberOfObjects(); i++) {
            String value = this.getDataSet().getFeature(i, this.getFeature());
            if (!possibleValues.contains(value))
                possibleValues.add(value);
        }   
    }
    
    @Override
    public String toString() {
        if (possibleValues == null) preparePossibleValues();
        return "{Nominal - numberOfOutput: "+ this.numberOfOutput() +"}";

    }

    @Override
    public String getOperationName() {
        return "Nominal";
    }
    
}
