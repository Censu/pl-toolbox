package plt.validator.examples;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
public class SplitValidation extends Validator {
    
    private int ratio;
    
    public SplitValidation(int validationRatio) {
        if (validationRatio < 0 || validationRatio > 100)
            throw  new IllegalArgumentException();
        
        this.ratio = validationRatio;
    }

    @Override
    public Report runWithValidation(PLAlgorithm algorithm) {
        Report report = new Report();
        
        Set<Integer> trainingPreferences = new HashSet<>();
        Set<Integer> validationPreferences = new HashSet<>();
        
        TrainableDataSet originalDataSet = algorithm.getDataset();
        
        
        int splitPoint = (originalDataSet.getNumberOfPreferences()*20)/100;
        
        for (int i=0;i < originalDataSet.getNumberOfPreferences(); i++) {
            if (i < splitPoint)
                validationPreferences.add(i);
            else
                trainingPreferences.add(i);
        }


        
        TrainableDataSet validationDataSet = originalDataSet.subSet(trainingPreferences);
        TrainableDataSet trainingDataSet = originalDataSet.subSet(validationPreferences);

        
        Model before = algorithm.prepareToRun();

        double beforeCorrectness = 0;
        for (int z = 0; z < validationDataSet.getNumberOfPreferences(); z++) {
            Preference instance = validationDataSet.getPreference(z);
            if (before.preference(instance.getPreferred(), instance.getOther())) {
                beforeCorrectness++;
            }
        }
        beforeCorrectness /= validationDataSet.getNumberOfPreferences();
        
        algorithm.setDataSet(trainingDataSet);
        Model model = algorithm.createModel();
        if(model == null) { return null; }
        
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
