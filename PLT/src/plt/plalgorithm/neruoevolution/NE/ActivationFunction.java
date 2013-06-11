/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.neruoevolution.NE;

/**
 *
 * @author luca
 */
public interface ActivationFunction {
    public double evalue(double input);
    public double evalueDerivative(double input);
}
