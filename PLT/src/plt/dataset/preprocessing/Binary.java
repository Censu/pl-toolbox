package plt.dataset.preprocessing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import plt.dataset.DataSet;

/**
 *
 * @author luca
 */
public class Binary extends PreprocessingOperation {

    private List<String> possibleValues;
    private List<String> ignore;

    public Binary(DataSet d, int feature, List<String> ignore) {
        super(d, feature);
        this.ignore = ignore;
    }

    @Override
    public int numberOfOutput() {
        if (possibleValues == null) {
            preparePossibleValues();
        }

        return possibleValues.size();
    }

    @Override
    public double value(int object, int output) {
        if (output > this.numberOfOutput()) {
            throw new IllegalArgumentException();
        }

        if (possibleValues == null) {
            preparePossibleValues();
        }

        String value = this.getDataSet().getFeature(object, this.getFeature());

        if (possibleValues.get(output).equals(value)) {
            return 1;
        } else {
            return 0;
        }

    }

    private void preparePossibleValues() {
        possibleValues = new LinkedList<>();
        for (int i = 0; i < this.getDataSet().getNumberOfObjects(); i++) {
            String value = this.getDataSet().getFeature(i, this.getFeature());
            if (!possibleValues.contains(value) && (ignore == null || !ignore.contains(value))) {
                possibleValues.add(value);
            }
        }


    }

    @Override
    public String toString() {
        if (possibleValues == null) {
            preparePossibleValues();
        }
        return "{Binary - numberOfOutput: " + this.numberOfOutput() + "}";
    }

    @Override
    public String getOperationName() {
        return "Binary";
    }

    
}
