package plt.plalgorithm;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.model.Model;
import plt.report.Report;
import plt.validator.Validator;

/**
 *
 * @author luca
 */
public abstract class PLAlgorithm {

    private SelectedFeature featureSelection;
    private TrainableDataSet dataSet;
    private Model result;
    private Model untrainedModel;


    public PLAlgorithm(TrainableDataSet dataSet) {
        this.result = null;
        this.dataSet = dataSet;
    }

    public SelectedFeature getFeatureSelection() {
        return featureSelection;
    }

    public void setSelectedFeature(SelectedFeature f) {
        this.featureSelection = f;
        this.result = null;
    }

    public TrainableDataSet getDataset() {
        return dataSet;
    }

    public void setDataSet(TrainableDataSet n) {
        this.result = null;
        this.dataSet = n;
    }
    


    public Report createModelWithValidation(Validator v) {
        Logger.getLogger("plt.logger").log(Level.INFO, "create new model(s) using: " + v);
        prepareToRun();

        return v.runWithValidation(this);
    }

    public Model createModel() {
        Logger.getLogger("plt.logger").log(Level.INFO, "create a new model");

        if (this.untrainedModel == null || this.result != null) {
           throw new IllegalStateException("preapte to run not called");
        }

        if (this.result == null) {
            this.result = run();
        }

        return this.result;

    }

    public Model prepareToRun() {
        if (this.dataSet == null) {
            throw new IllegalStateException("missing dataSet");
        }

        if (this.featureSelection == null) {
            this.featureSelection = new SelectedFeature();
            this.featureSelection.setSelected(0, this.dataSet.getNumberOfFeatures() - 1);
        }
        
        this.result = null;
        this.untrainedModel = this.beforeRun();
        
        return this.untrainedModel;
    }

    protected abstract Model run();
    protected abstract Model beforeRun();
    
    public abstract ArrayList<Object[]> getProperties();

}
