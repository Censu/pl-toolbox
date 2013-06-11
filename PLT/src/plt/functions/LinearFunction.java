/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.functions;

import java.util.Arrays;

/**
 *
 * @author luca
 */
public class LinearFunction extends MathematicalFunction {
    int[] x;
    
    public LinearFunction(int... x) {
        super();
        this.x = x;   
    }

    @Override
    public double evaluate(double[] feature) {
        double result = 0;
        
        for (int i=0; i<x.length; i++) {
            result += feature[i]* this.x[i] ;
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        return "Linear (" + Arrays.toString(x) +")";
    }
    
}
