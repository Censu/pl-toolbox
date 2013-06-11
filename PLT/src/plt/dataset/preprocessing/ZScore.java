package plt.dataset.preprocessing;

import plt.dataset.DataSet;

/**
 *
 * @author luca
 */
public class ZScore extends PreprocessingOperation {

    private double avg;
    private double sigma;
    private double stdev;
    private boolean ready;
    
    
    public ZScore(DataSet d, int feature) {
        super(d, feature);
        ready = false;
    }

    @Override
    public int numberOfOutput() {
        return 1;
    }

    @Override
    public double value(int object, int output) {
        if (output > this.numberOfOutput())
            throw new IllegalArgumentException();
        
        if (!ready) prepareValues();

        double value = Double.parseDouble(this.getDataSet().getFeature(object, this.getFeature()));
        
        return (value-this.avg)/this.sigma;
        
    }
    
    private void prepareValues() {
        
        double n = this.getDataSet().getNumberOfObjects();
        double sum =0;
        for (int i=0; i< n; i++) {
            double d = Double.parseDouble(this.getDataSet().getFeature(i, this.getFeature()));
            sum += d;
        }         
        
        this.avg = sum/this.getDataSet().getNumberOfObjects();
        
        double x =0;
        for (int i=0; i< n; i++) {
            double d = Double.parseDouble(this.getDataSet().getFeature(i, this.getFeature()));
            x += Math.pow(d-this.avg , 2);
        }  
        
        this.sigma = Math.sqrt(x/n);
        
        
        // Calculate Stdev
        double[] squareDiffsArr = new double[(int) n];
        double meanOfDifferences = 0;
        for(int i=0; i<n; i++)
        {
            double d = Double.parseDouble(this.getDataSet().getFeature(i, this.getFeature()));
            double diff = d - avg;
            double squareDiff = diff * diff;
            squareDiffsArr[i] = squareDiff;
            
            meanOfDifferences += squareDiff;
        }
        meanOfDifferences = meanOfDifferences / ((squareDiffsArr.length) * 1.0f);
        
        stdev = Math.sqrt(meanOfDifferences);
        
    }
    

    @Override
    public String toString() {
        return "{ZScore - numberOfOutput: "+ this.numberOfOutput() +"}";
    }

    @Override
    public String getOperationName() {
        return "Z-Score";
    }
    
    public double getAverage()
    {
        return avg;
    }
    
    public double getStdev()
    {
        return stdev;
    }
}