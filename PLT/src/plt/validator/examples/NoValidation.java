package plt.validator.examples;

import plt.dataset.TrainableDataSet;
import plt.model.Model;
import plt.plalgorithm.PLAlgorithm;
import plt.report.Report;
import plt.utils.Preference;
import plt.validator.Validator;

/**
 *
 * @author luca
 */
public class NoValidation extends Validator {

    @Override
    public Report runWithValidation(PLAlgorithm algorithm) {
        Report report = new Report();
        
        //System.out.println("dataset: " + algorithm.getDataset());
        
        Model before = algorithm.prepareToRun();
        TrainableDataSet validationDataSet = algorithm.getDataset();

        double beforeCorrectness = 0;
        for (int z = 0; z < validationDataSet.getNumberOfPreferences(); z++) {
            Preference instance = validationDataSet.getPreference(z);
            if (before.preference(instance.getPreferred(), instance.getOther())) {
                beforeCorrectness++;
            }
        }
        beforeCorrectness /= validationDataSet.getNumberOfPreferences();
        
        
        Model model = algorithm.createModel();
        double correctness = 0;
        for (int z = 0; z < validationDataSet.getNumberOfPreferences(); z++) {
            Preference instance = validationDataSet.getPreference(z);
            if (model.preference(instance.getPreferred(), instance.getOther())) {
                correctness++;
            }
        }
        
        correctness /= validationDataSet.getNumberOfPreferences();
        
        report.addExperimentResult(model, correctness,beforeCorrectness);

        return report;
    }

    @Override
    public String toString() {
        return "NoValidation";
    }
}
