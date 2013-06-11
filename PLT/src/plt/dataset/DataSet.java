package plt.dataset;

import plt.utils.Preference;

/**
 * A DataSet is composed by a set of objects, a set of functions for the objects 
 * called features, and a set of pairwise preference called instances.
 * A dataSet can't be used as input for the preference learning algorithms: a TrainableDataSet
 * must be constructed and then used.
 * @author luca
 */
public interface DataSet {
    
    
    /**
     * Returns the number of objects in the dataset
     * An object is defined by its features.
     * 
     * @return the number of objects in the dataset
     */
    public int getNumberOfObjects();

    /**
    *  Returns the number of the preferences in the dataset
    * 
    *  @return the number of preferences in the dataset
    */
    public int getNumberOfPreferences();
    
    
    /**
     * Returns the number of the feature for the objects in the dataset
     * A feature is a property of an object and in the dataset all the 
     * objects have the same number and types of features
     * 
     * @return the number of the features for the object in the dataset
     */
    public int getNumberOfFeatures();

    
     /**
     * Given a number in [0..this.getNumberOfFeatures-1] returns the name of the nth feature
     * 
     * @param n a number that identify the feature
     * @return the name of the nth feature
     */
    public String getFeatureName(int n);
    
    /**
     * Given a number in [0..this.getNumberOfObjects()-1] returns all the feature of the nth object
     * @param n a number that identify the object
     * @return an array with all the features of the nth object
     */
    public String[] getFeatures(int n);
    
 
     /**
     * Given a number n in [0..this.getNumberOfObjects()-1] and a number f in [0..this.getNumberOfFeatures()-1]
     * returns the fth feature of the nth object
     * @param n a number that identify the object
     * @param f a number that identify the feature
     * @return the fth feature of the nth object
     */
    public String getFeature(int n, int f);


    /**
     * Given a number in [0..this.getNumberOfIstances()-1] returns the nth preference.
     * A preference is defined between to objects.
     * @param n a number that identify the instance.
     * @return a preference between two objects. 
     */
    public Preference getPreference(int n); 
    
        
    /**
     * Given an instance n, it return its own "atomic group".
     * An atomic group is a set of instance that can not be divided in subset 
     * @param n a number that identify the instance.
     * @return a number that identify the atomic group.
     */
    public int atomicGroup(int n);
    
     /**
     * Given a number in [0..this.getNumberOfFeatures-1] returns true if all of the values for the feature can be parsed as numberic value otherwise false.
     * 
     * @param n a number that identify the feature
     * @return true if all of the values for the feature can be parsed as numeric value otherwise false.
     */
    public boolean isNumeric(int n);
    
}
