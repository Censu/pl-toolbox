package plt.featureselection;

import plt.plalgorithm.PLAlgorithm;
import plt.validator.Validator;

/**
 * A FeatureSelection algorithm it's meant to select a number of features of a dataset
 * to be used in a preference learning algorithm
 * @author luca
 */
public abstract class FeatureSelection {

    /**
     * run the algorithm
     */
    public abstract void run(Validator v, PLAlgorithm a);
    
    /**
     * 
     * @return the name of the feature selection type.
     */
    public abstract String getFSelName();
        
    /**
     * @return the result as a SelectedFeature object. 
     */
    public abstract SelectedFeature getResult();

}
