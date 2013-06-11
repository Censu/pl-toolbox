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
public class QuadraticFuncion extends MathematicalFunction {
    int[] x;
    
    public QuadraticFuncion(int... x) {
        super();
        this.x = x;   
    }

    @Override
    public double evaluate(double[] feature) {
        double result = 0;
        
        for (int i=0; i<feature.length; i++) {
            result += feature[i]* this.x[i%feature.length] ;
        }
        
        return Math.pow(result, 2);
    }
    
    @Override
    public String toString() {
        return "Quadratic (" + Arrays.toString(x) +")";
    }
    
}
