package plt.dataset;

import java.util.*;
import plt.utils.Preference;

/**
 * A TrainableDataset is a desired input for a preference learning algorithm.
 * The difference between a DataSet and a TrainableDataset, is that the first must 
 * be considered an input as it is imported from a stream or a database, the second instead is wrapper of the first that can be
 * used as inputs for the preference learning algorithm of this toolbox. PreprocessedDataSet for example is a TrainableDataset created
 * by a DataSet and a list of preprocessing operation to apply to it.
 * An important difference is that the features of a DataSet are String of a TrainableDataset are Double.
 * @author luca
 */
public abstract class TrainableDataSet {
    
    private DataSet dataSet;
    
    public TrainableDataSet(DataSet d) {
        this.dataSet = d;
    }    
    
     /**
     * Given a number n in [0..this.getNumberOfObjects()-1] and a number f in [0..this.getNumberOfFeatures()-1]
     * returns the fth feature of the nth object
     * @param n a number that identify the object
     * @param f a number that identify the feature
     * @return the fth feature of the nth object
     */
        public abstract double getFeature(int n, int f);

    
    /**
     * Given a number in [0..this.getNumberOfObjects()-1] returns all the feature of the nth object
     * @param n a number that identify the object
     * @return an array with all the features of the nth object
     */
    public abstract double[] getFeatures(int n);

    /**
     * Given a number in [0..this.getNumberOfIstances()-1] returns the nth preference.
     * A preference is defined between to objects.
     * @param n a number that identify the instance.
     * @return a preference between two objects. 
     */
    public abstract Preference getPreference(int n);
    
    /**
     * Given an instance n, it return its own "atomic group".
     * An atomic group is a set of instance that can not be divided in subset 
     * @param n a number that identify the instance.
     * @return a number that identify the atomic group.
     */
    public abstract int atomicGroup(int n);

    
    /**
     * Returns the number of the feature for the objects in the dataset
     * A feature is a property of an object and in the dataset all the 
     * objects have the same number and types of features
     * 
     * @return the number of the features for the object in the dataset
     */
    public abstract int getNumberOfFeatures();

    /**
    *  Returns the number of the preferences in the dataset
    * 
    *  @return the number of instances in the dataset
    */
    public abstract int getNumberOfPreferences();

        
    /**
     * Two trainable datasets are considered equals if created from the same DataSet, with the same number
     * of objects, the same number of instances, and the same features for each object
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        TrainableDataSet other= (TrainableDataSet)o;
        
        if (other.dataSet != this.dataSet) {
            return false;
        }
        
        if (other.getNumberOfPreferences() != this.getNumberOfPreferences()) {
            return false;
        }
        if (other.getNumberOfFeatures() != this.getNumberOfFeatures()) {
            return false;
        }
        
        for (int i=0; i<this.getNumberOfPreferences(); i++) {
            if (!other.getPreference(i).equals(this.getPreference(i))) {
                return false;
            }
        }
        
        return true;
        
    }
    
    
    /**
     * create a subset of trainableDataSet given a vector of instances
     * @param subset
     * @return 
     */
    public TrainableDataSet subSet(final int[] subset) {

        if (subset.length < 1) {
            throw new IllegalArgumentException();
        }
        
        HashMap<Integer,Set<Integer>> subHash = new HashMap<>();
        
        for (int i=0; i<subset.length; i++) {
            int j = this.atomicGroup(subset[i]);
            if (subHash.get(j) == null) {
                subHash.put(j, new HashSet<Integer>());
            }
            subHash.get(j).add(subset[i]);
        }
        
        HashMap<Integer,Set<Integer>> hash = new HashMap<>();

        for (int i =0; i< this.dataSet.getNumberOfPreferences(); i++) {
            int j =this.dataSet.atomicGroup(i);
            if (hash.get(j) == null) {
                hash.put(j, new HashSet<Integer>());
            }
            hash.get(j).add(i);
        }
        
        for (Integer i : subHash.keySet()) {
            if (hash.get(i).size() != subHash.get(i).size()) {
                throw  new IllegalArgumentException();
            }
        }

        final TrainableDataSet father = this;
        return new TrainableDataSet(this.dataSet) {
            
            @Override
            public Preference getPreference(int n) {
                if (n >= subset.length || n <0) {
                    throw new IllegalArgumentException(n + " is not a valid instance");
                }
                
                return father.getPreference(subset[n]);
            }
            
            @Override
            public int atomicGroup(int n) {
                if (n >= subset.length || n <0) {
                    throw new IllegalArgumentException(n + " is not a valid instance");
                }
                
                return father.atomicGroup(subset[n]);
            }


            @Override
            public int getNumberOfPreferences() {
                return subset.length;
            }

            @Override
            public double[] getFeatures(int n) {
                return father.getFeatures(n);
            }

            @Override
            public double getFeature(int n, int f) {
                return father.getFeature(n, f);
            }

            @Override
            public int getNumberOfFeatures() {
                return father.getNumberOfFeatures();
            }
        };
    }
    
    /**
     * create a subset of trainableDataSet given a set of instances
     * @param subset
     * @return 
     */
    public TrainableDataSet subSet(Set<Integer> subset) {
        int[] output = new int[subset.size()];
        int counter=0;
        for (Integer i: subset) {
            output[counter++] = i;
        }
        
        return this.subSet(output);
    }
    
    /**
     * @return all the atomicsGropus of the trainableDataSet
     */
    public List<Set<Integer>> atomicGroups () {
        int n = this.getNumberOfPreferences();
        HashMap<Integer,Set<Integer>> hash = new HashMap<>();
        for (int i=0; i<n; i++) {
            int atomicGroup = this.dataSet.atomicGroup(i);
            Set<Integer> set = hash.get(atomicGroup);
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(i);
            hash.put(atomicGroup, set);
        }
        
        return new LinkedList<>(hash.values());
    }
 
    @Override
    public String toString() {
        return "{Trainable Dataset - number of feature(s): " + this.getNumberOfFeatures() +
                " number of istance(s): " + this.getNumberOfPreferences() + "}";
    }

    public int getNumberOfObjects() {
        return this.dataSet.getNumberOfObjects();
    }
    
}