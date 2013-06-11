package plt.plalgorithm.neruoevolution.GA;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

class IsListOfTwoElements extends ArgumentMatcher {

    @Override
    public boolean matches(Object list) {
        return ((List) list).size() == 2;
    }
}

/**
 *
 * @author luca
 */
public class PopulationTest {

    private Population population;
    private GeneticEncoder geneticEncoder;

    public PopulationTest() {
    }

    @Before
    public void setUp() {
        this.geneticEncoder = mock(GeneticEncoder.class);
        stub(this.geneticEncoder.dnaSize()).toReturn(10);

        this.population = new Population(20, this.geneticEncoder);
    }

    /**
     * Test of getSize method, of class Population.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        Population instance = this.population;
        int expResult = 20;
        int result = instance.getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxFitness method, of class Population.
     */
    @Test
    public void testGetMaxFitness() {
        System.out.println("getMaxFitness");
        Population instance = this.population;
        double expResult = 10.0;

        final int[] i = new int[]{0};
        stub(this.geneticEncoder.evaluationFunction(any(DNA.class))).toAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (i[0]++ == 0) {
                    return 10;
                } else {
                    return 5;
                }
            }
        });

        double result = instance.getMaxFitness();
        assertEquals(expResult, result, 0.001);
    }

    /**
     * Test of getBestPhenotype method, of class Population.
     */
    @Test
    public void testGetBestPhenotype() {


        System.out.println("getBestPhenotype");
        Population instance = this.population;

        final int[] i = new int[]{0};
        final Object[] o = new Object[]{null};

        stub(this.geneticEncoder.evaluationFunction(any(DNA.class))).toAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (i[0]++ == 0) {
                    o[0] = invocation.getArguments()[0];
                    return 10;
                } else {
                    return 5;
                }
            }
        });

        stub(this.geneticEncoder.decode(any(DNA.class))).toAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if (o[0].equals(invocation.getArguments()[0])) {
                    return "BEST";
                } else {
                    return "INDIVIDUAL";
                }
            }
        });


        Object expResult = "BEST";
        Object result = instance.getBestPhenotype();
        assertEquals(expResult, result);
    }
}
