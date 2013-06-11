package plt.report;

import java.util.LinkedList;
import java.util.List;
import plt.model.Model;

/**
 *
 * @author luca
 */
public class Report {

    private List<Double> after;
    private List<Double> before;
    private List<Model> models;

    public Report() {
        this.models = new LinkedList<>();
        this.after = new LinkedList<>();
        this.before = new LinkedList<>();
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.after.size(); i++) {
            
            result += "|" + this.before.get(i) + "->" + this.after.get(i) + "|";
        }

        return result;

    }

    public void addExperimentResult(Model model, double after) {
        this.addExperimentResult(model, after, 0);
    }

    public int numberOfResults() {
        return this.after.size();
    }

    public double resultAccurancy(int i) {
        if (i >= this.numberOfResults()) {
            throw new IllegalArgumentException();
        }

        return this.after.get(i);
    }

    public double getAVGAccuracy() {
        double result = 0;
        for (double d : after) {
            result += d;
        }

        return result / after.size();
    }

    public Model getBestModel() {

        Model best = null;
        double maxAccuracies = Double.MIN_VALUE;

        for (int i = 0; i < this.after.size(); i++) {
            if (maxAccuracies < this.after.get(i)) {
                maxAccuracies = this.after.get(i);
                best = this.models.get(i);
            }

        }

        return best;
    }
    
    public Model getModel(int n) {
        return models.get(n);
    }

    public void addExperimentResult(Model model, double after, double before) {
        this.models.add(model);
        this.after.add(after);
        this.before.add(before);
    }
}
