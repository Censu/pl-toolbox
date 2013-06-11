package plt.plalgorithm.neruoevolution.GA;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.*;
import plt.plalgorithm.neruoevolution.GA.parentselections.RouletteWheelSelection;

/**
 *
 * @author luca
 */
public class GeneticAlgorithmTest {

    private GeneticAlgorithm instance;

    @Before
    public void setUp() {
        GeneticAlgorithmConfigurator configurator = new GeneticAlgorithmConfigurator() {
            @Override
            public int getPopulationSize() {
                return 10;
            }

            @Override
            public int getNumberOfParents() {
                return 5;
            }

            @Override
            public int getElitSize() {
                return 2;
            }

            @Override
            public ParentSelection getParentSelection() {
                return new RouletteWheelSelection();
            }

            @Override
            public GaussianMutation getMutation() {
                return new GaussianMutation(0.4);
            }

            @Override
            public Invertion getInvertion() {
                return new Invertion(0);
            }

            @Override
            public CrossOver getCrossOver() {
                return new CrossOver(0.5, CrossOverType.ONEPOINT);
            }
        };

        GeneticEncoder encoder = new GeneticEncoder() {
            @Override
            public Object decode(DNA dna) {
                String result = "";
                for (int i = 0; i < 50; i++) {
                    if (dna.vector[i] > 0.5) {
                        result += "A";
                    } else {
                        result += "a";
                    }
                }

                return result;
            }

            @Override
            public double evaluationFunction(DNA dna) {
                double result = 0;
                for (int i = 0; i < 50; i++) {
                    if (dna.vector[i] > 0.5) {
                        result += 1;
                    }
                }

                return result;
            }

            @Override
            public int dnaSize() {
                return 50;
            }
        };


        this.instance = new GeneticAlgorithm(configurator, encoder);
    }

    /**
     * Test of runUntillReach method, of class GeneticAlgorithm.
     */
    @Test
    public void testRunUntillReach() {
        System.out.println("runUntillReach");
        double threshold = 50;
        Object expResult = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        instance.runUntillReach(threshold);
        Object result = instance.getResult();
        assertEquals(expResult, result);
    }
}
