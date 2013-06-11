
package plt.plalgorithm.neruoevolution.NE;

import plt.plalgorithm.neruoevolution.NE.SimpleNeuralNetwork;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author luca
 */
public class SimpleNeuralNetworkTest {
    private SimpleNeuralNetwork nn;
    
    public SimpleNeuralNetworkTest() {}
    
    @Before
    public void setUp() {
        this.nn = new SimpleNeuralNetwork(new int[] {2,3,1}, new ActivationFunction[]{new Sigmond(), new Sigmond()});
    }


    /**
     * Test of getNumberOfNerons method, of class SimpleNeuralNetwork.
     */
    @Test
    public void testGetNumberOfNerons() {
        System.out.println("getNumberOfNerons");
        SimpleNeuralNetwork instance = this.nn;
        int expResult = 4;
        int result = instance.getNumberOfNerons();
        assertEquals(expResult, result);
    }
    
        /**
     * Test of getNumberOfNerons method, of class SimpleNeuralNetwork.
     */
    @Test
    public void testGetNumberOfWeights() {
        System.out.println("GetNumberOfWeights");
        SimpleNeuralNetwork instance = this.nn;
        int expResult = 13;
        int result = instance.getNumberOfWeights();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetOutputs() {
        System.out.println("getOutputs");
        SimpleNeuralNetwork instance = this.nn;
        instance.setInputs(new double[]{0.5,0.8});
        instance.setWeights(new double[] {0.4,0.2,0.1, //layer 1, neuron 0
                                          0.3,0.4,0.5, //layer 1, neuron 1
                                          0.6,0.7,0.8, //layer 1, neuron 2
                                          0.9,0.1,0.8,0.5 //layer 2, neuron 0
                                         });
        
        
        int weightPointer = instance.weightPointer(2, 0);
        assertEquals(0.9, instance.weights[weightPointer++], 0.001);
        assertEquals(0.1, instance.weights[weightPointer++], 0.001);
        assertEquals(0.8, instance.weights[weightPointer++], 0.001);
        assertEquals(0.5, instance.weights[weightPointer++], 0.001);
                   
        assertEquals(-0.22, instance.getSValueOf(1, 0),0.001);
        assertEquals(0.3, instance.getSValueOf(1, 1),0.001);
        assertEquals(0.39, instance.getSValueOf(1, 2),0.001);
        assertEquals(-0.09778257, instance.getSValueOf(2, 0),0.00000001);

        assertEquals(0.44522076, instance.getValueOf(1, 0),0.00000001);
        assertEquals(0.57444251, instance.getValueOf(1, 1),0.00000001);
        assertEquals(0.59628269, instance.getValueOf(1, 2),0.00000001);
        assertEquals(0.47557381, instance.getValueOf(2, 0),0.00000001);
        
        assertEquals(1,instance.getOutputs().length);
        assertEquals(instance.getOutputs()[0], instance.getValueOf(2, 0),0.00001);

    }
}
