
package plt.plalgorithm.neruoevolution.NE;

/**
 *
 * @author luca
 */
public class HyperbolicTangent implements ActivationFunction {
    @Override
    public double evalue(double input) {
        return Math.tanh(input);
    }
    
    @Override
    public String toString() {
        return "{HyperbolicTangent}";
    }

    @Override
    public double evalueDerivative(double input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
