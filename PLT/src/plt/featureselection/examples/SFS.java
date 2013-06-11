/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.featureselection.examples;

import java.util.HashSet;
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
public class SFS extends FeatureSelection {
    private NBestConfigurator configurator;
    private SelectedFeature result;
    

    public SFS() {
    }


    @Override
    public void run(Validator v, PLAlgorithm algorithm) {
        
        Logger.getLogger("plt.logger").log(Level.INFO, "running SFS");

        TrainableDataSet t = algorithm.getDataset();
        SelectedFeature selected = new SelectedFeature();
        HashSet<Integer> candidate = new HashSet();
        for (int i=0; i<algorithm.getDataset().getNumberOfFeatures(); i++)
            candidate.add(i);
        
        boolean finished = false;
        double goodness = Double.MIN_VALUE;

        while (!finished && candidate.size() > 0) {   
            int toAdd = -1;

            for (Integer i: candidate) {
                SelectedFeature test = null;

                try { test = (SelectedFeature) selected.clone();} 
                catch (CloneNotSupportedException ex) {Logger.getLogger(SFS.class.getName()).log(Level.SEVERE, null, ex);}
                test.setSelected(i);

                Logger.getLogger("plt.logger").log(Level.INFO, "testing features:\n" + test);

                algorithm.setSelectedFeature(test);
                Report report = algorithm.createModelWithValidation(v);
                double result = report.getAVGAccuracy();
                
                if (result > goodness) {
                    goodness = result;
                    toAdd = i;
                }
            }
            
            
            if (toAdd != -1) {
                selected.setSelected(toAdd);
                candidate.remove(toAdd);
            } else
                finished = true;
        }
        
        this.result = selected;
        
    }

    @Override
    public SelectedFeature getResult() {
        return this.result;
    }

    @Override
    public String getFSelName() {
        return "SFS";
    }
   
}