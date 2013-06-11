package plt.plalgorithm.neruoevolution.NE;

/**
 *
 * @author luca
 */
public class Sigmond implements ActivationFunction {


    
    @Override
    public double evalue(double input) {
        return plt.utils.Math.sigmoid(input, 1);


    }
    
    
    public String toString() {
        return "{Sigmond}";
    }

    @Override
    public double evalueDerivative(double input) {
        return input*(1 -input);
    }
}
