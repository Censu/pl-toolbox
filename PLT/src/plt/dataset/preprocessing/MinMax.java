package plt.dataset.preprocessing;

import plt.dataset.DataSet;
import plt.dataset.sushireader.SushiFormatDataSet;

/**
 *
 * @author luca
 */
public class MinMax extends PreprocessingOperation{

    private double min;
    private double max;
    
    public MinMax(DataSet d, int featre, double min, double max) {
        super(d, featre);
        this.min = min;
        this.max = max;
    }
    
    @Override
    public int numberOfOutput() {
        return 1;
    }

    @Override
    public double value(int object, int output) {
        Double value_actual = Double.parseDouble(this.getDataSet().getFeature(object, this.getFeature()));
        
        SushiFormatDataSet castDataSet = (SushiFormatDataSet) this.getDataSet();
        double minValForFeature = (double) castDataSet.getMinValForFeature(this.getFeature());
        double maxValForFeature = (double) castDataSet.getMaxValForFeature(this.getFeature());
        
        double nwMin = min;
        double nwMax = max;
        
        double computedVal = (nwMin + (((value_actual - minValForFeature) / (maxValForFeature - minValForFeature)) * (nwMax-nwMin)));
        double roundedVal = (double)Math.round(computedVal * 1000) / 1000;
        return roundedVal;
    }
    
    @Override
    public String toString() {
        return "{MinMax - numberOfOutput: "+ this.numberOfOutput() +" min: "+this.min+
                " max: "+ this.max +"}";

    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }

    @Override
    public String getOperationName() {
        return "Min Max";
    }

}
