package plt.dataset;

import plt.dataset.preprocessing.PreprocessingOperation;
import plt.utils.Preference;

 /**
 * a PreprocessedDataSet is a TrainableDataSet, created from a DataSet and list of operation to be
 * applied to the object's features. Any operation define a transformation that will be applied to a feature.
 */
public class PreprocessedDataSet extends TrainableDataSet {

    private DataSet dataSet;
    private PreprocessingOperation[] preprocessingOperations;
    private int numberOfFeature;
    private int[] operationForFeature;
    private int[] cacheStatus;
    private double[][] cache;


    /**
     * Constructor for the PreprocessedDataSet
     * @param dataSet used as source for the PreprocessedDataSet
     * @param preprocessingOperations set of operation to be applied to the dataSet
     */
    public PreprocessedDataSet(DataSet dataSet, PreprocessingOperation[] preprocessingOperations) {
        super(dataSet);
        this.dataSet = dataSet;
        this.preprocessingOperations = preprocessingOperations;

        if (this.preprocessingOperations.length != dataSet.getNumberOfFeatures()) {
            throw new IllegalArgumentException();
        }

        this.numberOfFeature = 0;
        for (int i = 0; i < dataSet.getNumberOfFeatures(); i++) {
            if (preprocessingOperations[i].numberOfOutput() < 0) {
                throw new IllegalArgumentException();
            }
            
            if (this.dataSet != preprocessingOperations[i].getDataSet()) {
                throw  new IllegalArgumentException();
            }
            
            this.numberOfFeature += preprocessingOperations[i].numberOfOutput();
        }

        this.operationForFeature = new int[this.numberOfFeature];
        int counter =0;
        for (int i = 0; i < dataSet.getNumberOfFeatures(); i++) {
            for (int j = 0; j < preprocessingOperations[i].numberOfOutput(); j++) {
                this.operationForFeature[counter++] = i;
            }
        }

    }

    /**
     * Given a number in [0..this.getNumberOfIstances()-1] returns the nth preference.
     * A preference is defined between to objects.
     * @param n a number that identify the instance.
     * @return a preference between two objects. 
     */
    @Override
    public Preference getPreference(int n) {
        return this.dataSet.getPreference(n);
    }

    /**
     * Returns the number of the feature for the objects in the dataset
     * A feature is a property of an object and in the dataset all the 
     * objects have the same number and types of features
     * 
     * @return the number of the features for the object in the dataset
     */
    @Override
    public int getNumberOfFeatures() {
        return this.numberOfFeature;
    }

     /**
     * Given a number n in [0..this.getNumberOfObjects()-1] and a number f in [0..this.getNumberOfFeatures()-1]
     * returns the fth feature of the nth object
     * @param n a number that identify the object
     * @param f a number that identify the feature
     * @return the fth feature of the nth object
     */
    @Override
    public double getFeature(int n, int f) {

        if (n < 0 || n > this.dataSet.getNumberOfObjects() - 1) {
            throw new IllegalArgumentException();
        }

        if (f < 0 || f > this.getNumberOfFeatures() - 1) {
            throw new IllegalArgumentException();
        }

        initCache();

        Double cacheValue = this.cache[n][f];

        if (cacheValue.isNaN()) {

            int value = 0;
            int i = f-1;

            while (i > 0 && this.operationForFeature[f] == this.operationForFeature[i--]) {
                value++;
            }

            
            cache[n][f] = this.preprocessingOperations[this.operationForFeature[f]].value( n, value);
            cacheStatus[n] += 1;
        }

        return cache[n][f];
    }

     /**
     * Given a number in [0..this.getNumberOfObjects()-1] returns all the feature of the nth object
     * @param n a number that identify the object
     * @return an array with all the features of the nth object
     */
    @Override
    public double[] getFeatures(int n) {

        if (n < 0 || n > this.dataSet.getNumberOfObjects() - 1) {
            throw new IllegalArgumentException();
        }

        initCache();

        if (cacheStatus[n] < this.getNumberOfFeatures()) {
            for (int i = 0; i < this.getNumberOfFeatures(); i++) {
                getFeature(n, i);
            }
        }

        return cache[n];
    }

   /**
    *  Returns the number of the instances in the dataset
    *  An instance define a preference between 2 objects
    * 
    *  @return the number of instances in the dataset
    */
    @Override
    public int getNumberOfPreferences() {
        return this.dataSet.getNumberOfPreferences();
    }

    private void initCache() {
        if (this.cache != null) {
            return;
        }

        this.cache = new double[this.dataSet.getNumberOfObjects()][this.getNumberOfFeatures()];
        for (int i = 0; i < cache.length; i++) {
            this.cache[i] = new double[this.getNumberOfFeatures()];
            for (int j = 0; j < this.cache[i].length; j++) {
                this.cache[i][j] = Double.NaN;
            }
        }

        this.cacheStatus = new int[this.dataSet.getNumberOfObjects()];
        for (int i = 0; i < cacheStatus.length; i++) {
            cacheStatus[i] = 0;
        }
    }

    /**
     * Given an instance n, it return is own "atomic group".
     * An atomic group is a set of instance that can not be divided in subset 
     * @param n a number that identify the instance.
     * @return a number that identify the atomic group.
    */
    @Override
    public int atomicGroup(int n) {
        return this.dataSet.atomicGroup(n);
    }

    @Override
    public String toString() {
        return "{PreprocessedDataSet: "+ super.toString() +"}";
    }
   
}