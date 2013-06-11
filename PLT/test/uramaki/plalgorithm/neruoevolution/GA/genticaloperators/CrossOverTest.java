package plt.plalgorithm.neruoevolution.GA.genticaloperators;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.spy;
import plt.plalgorithm.neruoevolution.GA.DNA;

/**
 *
 * @author luca
 */
public class CrossOverTest {
    private Random random;
    
    public CrossOverTest() {
    }

    
    @Before
    public void setUp() {
        this.random = spy(new Random());
        CrossOver.random = this.random;
    }


    /**
     * Test of isBinary method, of class OnePointCrossOver.
     */
    @Test
    public void testIsBinary() {
        System.out.println("isBinary");
        CrossOver instance = new CrossOver(0, CrossOverType.ONEPOINT);
        boolean expResult = true;
        boolean result = instance.isBinary();
        assertEquals(expResult, result);
    }

    /**
     * Test of perform method, of class OnePointCrossOver.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testPerform_DNA() {
        System.out.println("perform");
        int size = 3;
        DNA dna = new DNA(new double[] {0.2,0.3,0.4});
        CrossOver instance = new CrossOver(0, CrossOverType.ONEPOINT);
        instance.perform(dna);
    }
/**
     * Test of perform method, of class OnePointCrossOver.
     */
    
    @Test
    public void testPerform_DNA_DNA_Uniform() {
        System.out.println("perform");
        CrossOver instance = new CrossOver(0.5, CrossOverType.ONEPOINT.UNIFORM);
        DNA dna1 = new DNA(4);
        DNA dna2 = new DNA(4);
        DNA exp1 = new DNA(4);
        DNA exp2 = new DNA(4);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp2.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        boolean where[] = new boolean[] {true, true, true, true};

        
        instance.perform(dna1, dna2, where);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        exp2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        where = new boolean[] {false, false, false, false};
        
        instance.perform(dna1, dna2, where);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.2, 0.1, 0.9, 0.7};
        exp2.vector = new double[] {0.8 ,0.5 ,0.3, 0.4};
        where = new boolean[] {false, true, true, false};
        
        instance.perform(dna1, dna2, where);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
    }

    /**
     * Test of perform method, of class OnePointCrossOver.
     */
    @Test
    public void testPerform_DNA_DNA_OnePointCrossOver() {
        System.out.println("perform");
        CrossOver instance = new CrossOver(0.5, CrossOverType.ONEPOINT);
        DNA dna1 = new DNA(4);
        DNA dna2 = new DNA(4);
        DNA exp1 = new DNA(4);
        DNA exp2 = new DNA(4);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp2.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        int point = 0;
        
        instance.perform(dna1, dna2, point);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        exp2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        point = 4;
        
        instance.perform(dna1, dna2, point);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.2 ,0.5 ,0.9, 0.4};
        exp2.vector = new double[] {0.8, 0.1, 0.3, 0.7};
        point = 2;
        
        instance.perform(dna1, dna2, point);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
    }
    
    /**
     * Test of perform method, of class TwoPointsCrossOver.
     */
    @Test
    public void testPerform_DNA_DNA_TwoPointsCrossOver() {
        System.out.println("perform");
        CrossOver instance = new CrossOver(0.5, CrossOverType.TWOPOINT);
        DNA dna1 = new DNA(4);
        DNA dna2 = new DNA(4);
        DNA exp1 = new DNA(4);
        DNA exp2 = new DNA(4);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.8, 0.1, 0.3, 0.7};
        exp2.vector = new double[] {0.2 ,0.5 ,0.9, 0.4};
        int point1 = 0;
        int point2 = 1;

        
        instance.perform(dna1, dna2, point1, point2);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.2 ,0.5 ,0.9, 0.4};
        exp2.vector = new double[] {0.8, 0.1, 0.3, 0.7};
        point1 = 2;
        point2 = 3;
        
        instance.perform(dna1, dna2, point1, point2);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
        
        dna1.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        dna2.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp1.vector = new double[] {0.8, 0.1, 0.9, 0.4};
        exp2.vector = new double[] {0.2 ,0.5 ,0.3, 0.7};
        point1 = 0;
        point2 = 3;
        
        instance.perform(dna1, dna2, point1, point2);
        assertEquals(exp1, dna1);
        assertEquals(exp2, dna2);
    }
}
