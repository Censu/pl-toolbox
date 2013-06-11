/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.neruoevolution.GA;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luca
 */
public class IndividualTest {
    


    /**
     * Test of getDna method, of class Individual.
     */
    @Test
    public void test() {
        GeneticEncoder g = new GeneticEncoder() {

            @Override
            public Object decode(DNA dna) {
                
                String result = "";
                for (int i=0; i<dna.vector.length; i++) {
                    result += dna.vector[i] > 0.5 ? "1" : "0";
                }
                return result;
            }

            @Override
            public double evaluationFunction(DNA dna) {
                double fintess = 0;
                for (int i=0; i<dna.vector.length; i++) {
                    fintess += dna.vector[i] > 0.5 ? 1 : 0;
                }
                return fintess;
            }

            @Override
            public int dnaSize() {
                return 5;
            }
        };
        
        Individual i = new Individual(g);
        Individual j = new Individual(g);

        assertEquals(5,i.getDna().vector.length);
        assert(!i.getDna().equals(j.getDna()));
        
        Individual a = new Individual(g, new DNA(new double[] {0,0.9,0,0,0}));
        assertEquals(1, a.getFitness(),0.1);
        
        
    }


}
