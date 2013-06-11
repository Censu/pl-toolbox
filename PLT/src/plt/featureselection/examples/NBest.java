/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.featureselection.examples;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import plt.dataset.TrainableDataSet;
import plt.featureselection.FeatureSelection;
import plt.featureselection.SelectedFeature;
import plt.plalgorithm.PLAlgorithm;
import plt.report.Report;
import plt.validator.Validator;

/**
 *
 * @author luca
 */
public class NBest extends FeatureSelection {
    private NBestConfigurator configurator;
    private SelectedFeature result;
    
    public NBest(NBestConfigurator configurator) {
        this.configurator = configurator;
    }
    
    public int getN()
    {
        return configurator.getN();
    }

    @Override
    public void run(Validator v, PLAlgorithm algorithm) {
        
        Logger.getLogger("plt.logger").log(Level.INFO, "running nBEST");

        TrainableDataSet t = algorithm.getDataset();
        double[] results = new double[t.getNumberOfFeatures()];
        for (int i=0; i<results.length; i++) {
            SelectedFeature selection = new SelectedFeature();
            selection.setSelected(i);
            algorithm.setSelectedFeature(selection);
            Report report = algorithm.createModelWithValidation(v);
            results[i] = report.getAVGAccuracy();
        }
        
        Logger.getLogger("plt.logger").log(Level.INFO, "selecting the "+this.configurator.getN() + " best\n between "+ Arrays.toString(results));

        this.result = new SelectedFeature();
        for (int i=0; i<this.configurator.getN(); i++) {
            int index = -1;
            for (int j=0; j<results.length; j++) {
                if (index == -1 || results[j] > results[index])
                    index = j;
            }
            
            this.result.setSelected(i);
        }
    }

    @Override
    public SelectedFeature getResult() {
        return this.result;
    }

    @Override
    public String getFSelName() {
        return "N-Best";
    }
    
}
