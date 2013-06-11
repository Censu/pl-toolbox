/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.utils;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author luca
 */
public class PreferenceTest {

    @Before
    public void setUp() {
    }
    


    /**
     * Test of listToPairWisePreference method, of class Preference.
     */
    @Test
    public void testListToPairWisePreference() {
        System.out.println("listToPairWisePreference");
        String[] list = "0 1 2 3 4 5 6 7 8 9".split(" ");
        int[] intList = new int[list.length];
        for (int i=0;i<list.length;i++) {
            intList[i] = Integer.parseInt(list[i]);
        }
        
        List<Preference> result = Preference.listToPairWisePreference(intList);
        
        assertEquals(1+2+3+4+5+6+7+8+9, result.size());

        assertEquals(new Preference(0, 1), result.get(0));
        assertEquals(new Preference(0, 2), result.get(1));
        assertEquals(new Preference(1, 2), result.get(2));
        assertEquals(new Preference(0, 3), result.get(3));
        assertEquals(new Preference(6, 9), result.get(42));
        assertEquals(new Preference(7, 9), result.get(43));
        assertEquals(new Preference(8, 9), result.get(44));
        
        
    }

}