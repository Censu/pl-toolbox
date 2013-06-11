package plt.dataset.sushireader;

import java.io.File;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import plt.utils.Preference;

/**
 *
 * @author luca
 */
public class SushiFormatDataSetSimpleTest {
    private SushiFormatDataSet instance;

    @Before
    public void setUp()  {
        
        File idata = new File("test-dataset/data.idata");
        File order = new File("test-dataset/order.order");
        instance = new SushiFormatDataSet(idata, "\t", order, " ", 0, 0);
        
        try {
            instance.parse();
        } catch (ParseException ex) {
            Logger.getLogger(SushiPreprocessedDataSetTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Test of getNumberOfObjects method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetNumberOfObjects() {
        System.out.println("getNumberOfObjects");
        int expResult = 10;
        int result = instance.getNumberOfObjects();
        assertEquals(expResult, result);
    }

        /**
     * Test of getNumberOfIstances method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetNumberOfIstances() {
        System.out.println("getNumberOfIstances");
        int expResult = (1+2+3+4+5+6+7+8+9)*1;
        int result = instance.getNumberOfPreferences();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumberOfFeatures method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetNumberOfFeatures() {
        System.out.println("getNumberOfFeatures");
        int expResult = 2;
        int result = instance.getNumberOfFeatures();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFeatureName method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetFeatureName() {
        System.out.println("getFeatureName");
        int n = 0;
        String expResult = "feature1";
        String result = instance.getFeatureName(n);
        assertEquals(expResult, result);
        
        n = 1;
        expResult = "feature2";
        result = instance.getFeatureName(n);
        assertEquals(expResult, result);

    }

    /**
     * Test of getFeatures method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetFeatures() {
        System.out.println("getFeatures");
        int n = 4;
        String[] expResult = new String [] {"4","12"};
        String[] result = instance.getFeatures(n);
        assertArrayEquals(expResult, result);

    }
    
    /**
     * Test of getFeature method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetFeature() {
        System.out.println("getFeature");
        int n = 0;

        int f = 0;
        String expResult = "0";
        String result = instance.getFeature(n, f);
        assertEquals(expResult, result);

        f = 1;
        expResult = "0";
        result = instance.getFeature(n, f);
        assertEquals(expResult, result);        
    }
    
    /**
     * Test of getInstance method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        int n = 0;
        assertEquals(instance.getPreference(n), new Preference(0,1));

    }
    
     /**
     * Test of isNumeric method, of class SushiFormatDataSet.
     */
    @Test
    public void testIsNumeric() {
        System.out.println("isNumeric");
        int f = 0;
        boolean expResult = true;
        boolean result = instance.isNumeric(f);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of atomicGroup method, of class SushiFormatDataSet.
     */
    @Test
    public void testAtomicGroup() {
        System.out.println("atomicGroup");
        int n = 0;
        int expResult = 0;
        int result = instance.atomicGroup(n);
        assertEquals(expResult, result);
    }
}
