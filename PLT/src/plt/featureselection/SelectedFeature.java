package plt.featureselection;

import java.util.*;
import plt.plalgorithm.neruoevolution.GA.DNA;

/**
 * This class represents a set of feature of a dataset selected to be used for a 
 * preference learning algorithm
 * @author luca
 */
public class SelectedFeature implements Cloneable {
    List<Integer> features;
    
    public SelectedFeature() {
        this.features = new ArrayList<>();
    }
    
    /**
     * set feature x as selected
     * @param f a number that identify a feature
     */
    public void setSelected(int f) {
        if (f <0)
            throw new IllegalArgumentException();
        
        if (!isSelected(f)) 
            this.features.add(f);
    }
    
     /**
     * set feature x in [from,to] as selected
     */
    public void setSelected(int from, int to) {
        if (from <0 || from >= to)
            throw new IllegalArgumentException();
        
        for (int f= from; f<= to; f++)
            if (!isSelected(f)) 
                this.features.add(f);
    }
    
    
    /**
     * set feature x as unselected
     * @param f a number that identify a feature
     */
    public void setUnselected(int f) {
        if (f <0)
            throw new IllegalArgumentException();
        
        if (isSelected(f)) 
            this.features.remove(this.features.indexOf(f));
    }
    
   
    /**
     * 
     * @param f a number that identify a feature
     * @return true if feature f is selected false otherwise
     */
    public boolean isSelected(int f) {
        return this.features.contains(f);
    }

    @Override
    public boolean equals(Object o) {
        
        SelectedFeature other = (SelectedFeature)o;
        
        return this.features.equals(other.features);
    }
    
    public int getSize() {
        return this.features.size();
    }
        
    public double[] select(double[] features) {
        
        double[] result = new double[features.length];
        int size =0;
        
        for (int i=0; i<features.length; i++)
            if (this.isSelected(i)) result[size++] = features[i];
        
        return Arrays.copyOf(result, size);
    }
    
    public int[] getSelectedFeatures()
    {
        int[] retArr = new int[features.size()];
        
        for(int i=0; i<features.size(); i++)
        {
            retArr[0] = features.get(i);
        }
        
        return retArr;
    }
    
    @Override
    public String toString() {
        return "{SelectedFeature:"+ this.features +"}";
        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        SelectedFeature s = new SelectedFeature();
        s.features = new ArrayList<>(this.features);
        return s;
    }

}
