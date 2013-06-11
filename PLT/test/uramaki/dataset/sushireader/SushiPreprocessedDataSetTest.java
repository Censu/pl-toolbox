package plt.dataset.sushireader;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.dataset.preprocessing.Ignoring;
import plt.dataset.preprocessing.MinMax;
import plt.dataset.preprocessing.NumericBinary;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.utils.Preference;

/**
 *
 * @author luca
 */
public class SushiPreprocessedDataSetTest {

    private PreprocessedDataSet instance;

    @Before
    public void setUp() {
        SushiFormatDataSet dataSet;

        File idata = new File("sushi3/sushi3.idata");
        File order = new File("sushi3/sushi3b.5000.10.order");
        dataSet = new SushiFormatDataSet(idata, "\t", order, " ", 1, 2);

        try {
            dataSet.parse();
        } catch (ParseException ex) {
            Logger.getLogger(SushiPreprocessedDataSetTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> s = Arrays.asList(new String[]{"0"});

        this.instance = new PreprocessedDataSet(dataSet, new PreprocessingOperation[]{
                    new Ignoring(dataSet, 0),
                    new NumericBinary(dataSet, 1, Arrays.asList(new Double[]{1.0})),
                    new NumericBinary(dataSet, 2, Arrays.asList(new Double[]{1.0})),
                    new NumericBinary(dataSet, 3, null),
                    new MinMax(dataSet, 4, 0.0, 4),
                    new MinMax(dataSet, 5, 0.0, 3),
                    new MinMax(dataSet, 6, 0.0, 5),
                    new MinMax(dataSet, 7, 0.0, 1)});
    }

    /**
     * Test of getNumberOfFeatures method, of class PreprocessedDataSet.
     */
    @Test
    public void testGetNumberOfFeatures() {
        System.out.println("getNumberOfFeatures");
        int expResult = 18;
        int result = instance.getNumberOfFeatures();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFeatures method, of class PreprocessedDataSet.
     */
    @Test
    public void testGetFeatures() {
        
        System.out.println("getFeatures");
        int n = 6;
        double[] expResult = new double[]{0, 1,
            0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
            0.3162, 0.712807245f, 0.539, 0.84f};
        double[] result = instance.getFeatures(n);
        System.out.println(Arrays.toString(instance.getFeatures(6)));
        
        assertArrayEquals(expResult, result, 0.1);
    }

    /**
     * Test of getFeature method, of class PreprocessedDataSet.
     */
    @Test
    public void testGetFeature() {
        System.out.println("getFeature");
        int n = 6;
        int f = 17;
        double expResult = 0.84f;
        double result = instance.getFeature(n, f);
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of getNumberOfIstances method, of class PreprocessedDataSet.
     */
    @Test
    public void testGetNumberOfIstances() {
        System.out.println("getNumberOfIstances");
        int expResult = (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9) * 5000;
        int result = instance.getNumberOfPreferences();
        assertEquals(expResult, result);
    }

    /**
     * Test of atomicGroup method, of class PreprocessedDataSet.
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

    /**
     * Test of getInstance method, of class PreprocessedDataSet.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        int n = 0;
        Preference result = instance.getPreference(n);
        assertEquals(new Preference(58, 4), result);

        n = 1;
        result = instance.getPreference(n);
        assertEquals(new Preference(58,3), result);

        n = 2;
        result = instance.getPreference(n);
        assertEquals(new Preference(4,3), result);
    }

    /**
     * Test of subSet method, of class TrainableDataSet.
     */
    @Test
    public void testSubSet() {
        System.out.println("subSet");
        int[] subset = new int[45];
        for (int i = 0; i < 45; i++) {
            subset[i] = i;
        }

        TrainableDataSet result = instance.subSet(subset);
        assert (result.getNumberOfPreferences() == 45);
        assertEquals(result.getFeatures(0), instance.getFeatures(0));
        assertEquals(result.getPreference(0), instance.getPreference(0));
        assertEquals(result.getPreference(44), instance.getPreference(44));
    }

    /**
     * Test of subSet method, of class TrainableDataSet.
     */
    @Test
    public void testSubSet2() {
        System.out.println("subSet");
        int[] subset = new int[45];
        for (int i = 0; i < 45; i++) {
            subset[i] = i + 45;
        }

        TrainableDataSet result = instance.subSet(subset);
        assert (result.getNumberOfPreferences() == 45);
        assertEquals(result.getFeatures(0), instance.getFeatures(0));
        assertEquals(result.getPreference(0), instance.getPreference(45));
        assertEquals(result.getPreference(44), instance.getPreference(44 + 45));
    }

    /**
     * Test of subSet method, of class TrainableDataSet.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSubSet3() {
        System.out.println("subSet");
        int[] subset = new int[45];
        for (int i = 0; i < 45; i++) {
            subset[i] = i > 10 ? i + 45 : i;
        }

        TrainableDataSet result = instance.subSet(subset);
        assert (result.getNumberOfPreferences() == 45);
        assertEquals(result.getFeatures(0), instance.getFeatures(0));
        assertEquals(result.getPreference(0), instance.getPreference(45));
        assertEquals(result.getPreference(44), instance.getPreference(44 + 45));
    }
}