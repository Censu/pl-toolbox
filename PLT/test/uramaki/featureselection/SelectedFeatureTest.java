package plt.featureselection;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author luca
 */
public class SelectedFeatureTest {
    public SelectedFeatureTest() {
    }

    private SelectedFeature instance;
    
    @Before
    public void setUp() {
        this.instance = new SelectedFeature();
    }

    /**
     * Test of setSelected method, of class SelectedFeature.
     */
    @Test
    public void testSetSelected_int() {
        System.out.println("setSelected");
        instance.setSelected(10);
        assert(instance.isSelected(10));
    }

    /**
     * Test of setSelected method, of class SelectedFeature.
     */
    @Test
    public void testSetSelected_int_int() {
        System.out.println("setSelected");
        instance.setSelected(10,50);
        assert(instance.isSelected(10));
        assert(instance.isSelected(20));
        assert(instance.isSelected(50));

    }

    /**
     * Test of setUnselected method, of class SelectedFeature.
     */
    @Test
    public void testSetUnselected() {
        System.out.println("setUnselected");
        instance.setSelected(10);
        assert(instance.isSelected(10));
        instance.setUnselected(10);
        assert(!instance.isSelected(10));
    }

    /**
     * Test of isSelected method, of class SelectedFeature.
     */
    @Test
    public void testIsSelected() {
        System.out.println("isSelected");
        instance.setSelected(1);
        assert(instance.isSelected(1));
        instance.setUnselected(1);
        assert(!instance.isSelected(1));
    }

    /**
     * Test of equals method, of class SelectedFeature.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        instance.setSelected(0);
        instance.setSelected(1);
        instance.setSelected(2);
        instance.setSelected(3);
        instance.setUnselected(0);
        instance.setUnselected(3);
        
        SelectedFeature expected = new SelectedFeature();
        expected.setSelected(1,2);
        
        assertEquals(expected, this.instance);
        
    }

    /**
     * Test of getSize method, of class SelectedFeature.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        instance.setSelected(0);
        instance.setSelected(1);
        instance.setSelected(2);
        instance.setSelected(3);
        instance.setUnselected(2);
        instance.setUnselected(4);
        
        assertEquals(3, this.instance.getSize());
    }

    /**
     * Test of select method, of class SelectedFeature.
     */
    @Test
    public void testSelect() {
        System.out.println("select");
        double[] features = new double[] {0,0.1,0.2,0.3,0.4};
        instance.setSelected(1);
        instance.setSelected(3);
        
        double[] expResult = new double[] {0.1,0.3};
        double[] result = instance.select(features);
        assertArrayEquals(expResult, result,0.001);
    }
}
