package plt.plalgorithm.neruoevolution.NE;

/**
 *
 * @author luca
 */
public class Linear implements ActivationFunction {
    @Override
    public double evalue(double input) {
        return input;
    }

    @Override
    public String toString() {
        return "{Linear}";
    }

    @Override
    public double evalueDerivative(double input) {
        return 1;
    }
}
