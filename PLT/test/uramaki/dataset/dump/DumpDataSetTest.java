/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.dataset.dump;

import plt.functions.MathematicalFunction;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.dataset.preprocessing.Numeric;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.utils.Preference;

/**
 *
 * @author luca
 */
public class DumpDataSetTest {
    DumpDataSet d;
    MathematicalFunction f;

    public DumpDataSetTest() {
        f = new MathematicalFunction() {

            @Override
            public double evaluate(double[] feature) {
                double x = feature[0];
                double y = feature[1];
                
                //return Math.pow((2*x)-(3*y),2)
                return (2*x)-(3*y);
            }
        };
        d = new DumpDataSet(10, 2, f);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of getNumberOfObjects method, of class DumpDataSet.
     */
    @Test
    public void testGetNumberOfObjects() {
        System.out.println("getNumberOfObjects");
        int expResult = 10;
        int result = d.getNumberOfObjects();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumberOfPreferences method, of class DumpDataSet.
     */
    @Test
    public void testGetNumberOfPreferences() {
        System.out.println("getNumberOfPreferences");
        int expResult = 1+2+3+4+5+6+7+8+9;
        int result = d.getNumberOfPreferences();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumberOfFeatures method, of class DumpDataSet.
     */
    @Test
    public void testGetNumberOfFeatures() {
        System.out.println("getNumberOfFeatures");
        int expResult = 2;
        int result = d.getNumberOfFeatures();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetPreference() {
        System.out.println("getPreference");
        
        for (int i=0; i< d.getNumberOfPreferences(); i++) {
            Preference p = d.getPreference(i);

            int a = p.getPreferred();
            int b = p.getOther();
            double aV = this.f.evaluate(this.d.getFeatures(a));
            double bV = this.f.evaluate(this.d.getFeatures(b));

            
            assert (aV > bV);
            
        }
    }

    /**
     * Test of atomicGroup method, of class DumpDataSet.
     */
    @Test
    public void testAtomicGroup() {
        System.out.println("atomicGroup");
        int n = 1;
        int expResult = n;
        int result = d.atomicGroup(n);
    }

    /**
     * Test of isNumeric method, of class DumpDataSet.
     */
    @Test
    public void testIsNumeric() {
        System.out.println("isNumeric");
        int n = 0;
        boolean expResult = true;
        boolean result = d.isNumeric(n);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTrainableDataSet() {
        System.out.println("testTrainableDataSet");

        TrainableDataSet t = new PreprocessedDataSet(this.d, new PreprocessingOperation[]{ new Numeric(this.d, 0), new Numeric(this.d, 1) });
        for (int i=0; i< t.getNumberOfPreferences(); i++) {
            Preference p = t.getPreference(i);

            int a = p.getPreferred();
            int b = p.getOther();
            double aV = this.f.evaluate(t.getFeatures(a));
            double bV = this.f.evaluate(t.getFeatures(b));


            assert (aV > bV);
            
        }
    
    }

    /**
     * Test of getFeatureName method, of class DumpDataSet.
     */
    @Test
    public void testGetFeatureName() {
        System.out.println("getFeatureName");
        int n = 0;
        DumpDataSet instance = null;
        String expResult = "";
        String result = instance.getFeatureName(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFeatures method, of class DumpDataSet.
     */
    @Test
    public void testGetFeatures() {
        System.out.println("getFeatures");
        int n = 0;
        DumpDataSet instance = null;
        String[] expResult = null;
        String[] result = instance.getFeatures(n);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFeature method, of class DumpDataSet.
     */
    @Test
    public void testGetFeature() {
        System.out.println("getFeature");
        int n = 0;
        int f = 0;
        DumpDataSet instance = null;
        String expResult = "";
        String result = instance.getFeature(n, f);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
