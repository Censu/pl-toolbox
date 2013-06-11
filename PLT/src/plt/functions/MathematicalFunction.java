/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.functions;

/**
 *
 * @author luca
 */
public abstract class MathematicalFunction {
    
    public double evaluate(String[] features) {
        
        double[] f = new double[features.length];
        for (int i=0; i<features.length; i++) {
            f[i] = Double.parseDouble(features[i]);
        }
        return this.evaluate(f);
    }
    
    public abstract double evaluate (double[] feature);
}
