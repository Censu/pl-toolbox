package plt.dataset.sushireader;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import plt.utils.Preference;

/**
 *
 * @author luca
 */
public class SushiFormatDataSetTest {
    private SushiFormatDataSet instance;

    @Before
    public void setUp()  {
        
        File idata = new File("sushi3/sushi3.idata");
        File order = new File("sushi3/sushi3b.5000.10.order");
        instance = new SushiFormatDataSet(idata, "\t", order, " ", 1, 2);
        
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
        int expResult = 100;
        int result = instance.getNumberOfObjects();
        assertEquals(expResult, result);
    }

        /**
     * Test of getNumberOfIstances method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetNumberOfIstances() {
        System.out.println("getNumberOfIstances");
        int expResult = (1+2+3+4+5+6+7+8+9)*5000;
        int result = instance.getNumberOfPreferences();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumberOfFeatures method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetNumberOfFeatures() {
        System.out.println("getNumberOfFeatures");
        int expResult = 8;
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
        String expResult = "name";
        String result = instance.getFeatureName(n);
        assertEquals(expResult, result);
        
        n = 4;
        expResult = "heaviness";
        result = instance.getFeatureName(n);
        assertEquals(expResult, result);
        
        n = 7;
        expResult = "frequence-sold";
        result = instance.getFeatureName(n);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFeatures method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetFeatures() {
        System.out.println("getFeatures");
        int n = 0;
        String[] expResult = new String [] {"1","0","6","2.72897800776197","2.13842173350582","1.83841991341991","0.84"};
        String[] result = instance.getFeatures(n);
        assertArrayEquals(expResult, Arrays.copyOfRange(result, 1, result.length));
        
        n = 99;
        expResult = new String [] {"1","0","4","1.77922077922078","0.727272727272727","1.25","0.04"};
        result = instance.getFeatures(n);
        assertArrayEquals(expResult, Arrays.copyOfRange(result, 1, result.length));
    }
    
    /**
     * Test of getFeature method, of class SushiFormatDataSet.
     */
    @Test
    public void testGetFeature() {
        System.out.println("getFeature");
        int n = 98;

        int f = 1;
        String expResult = "1";
        String result = instance.getFeature(n, f);
        assertEquals(expResult, result);

        f = 7;
        expResult = "0.04";
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
        int[] expResult = new int[] {4,58};
        assertEquals(instance.getPreference(n), new Preference(58, 4));
        
        n = 1;
        expResult = new int[] {3,58};
        assertEquals(instance.getPreference(n), new Preference(58, 3));
        
        n = 2;
        expResult = new int[] {3,4};
        assertEquals(instance.getPreference(n), new Preference(4, 3));
    }
    
     /**
     * Test of isNumeric method, of class SushiFormatDataSet.
     */
    @Test
    public void testIsNumeric() {
        System.out.println("isNumeric");
        int f = 0;
        boolean expResult = false;
        boolean result = instance.isNumeric(f);
        assertEquals(expResult, result);
        
        f = 7;
        expResult = true;
        result = instance.isNumeric(f);
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
        
        n = 44;
        expResult = 0;
        result = instance.atomicGroup(n);
        assertEquals(expResult, result);
        
        n = 45;
        expResult = 1;
        result = instance.atomicGroup(n);
        assertEquals(expResult, result);
    }
}
