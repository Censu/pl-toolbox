package plt.utils;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luca
 */
public class Preference {
    private int preferred;
    private int other;
    
    /**
     * Given a list of preference for example (A,B,C) where the first is the most preferred, it returns 
     * a list of pair wise preference (for example A>B, C>B, A>C) as vector of pair (example [[B,A],[B,C],[C,A]])
     * where the second element of each pair is the most preferred (example [C,A] means that A is preferred over C)
     */
    public static List<Preference> listToPairWisePreference(int[] list) {
        List<Preference> output = new LinkedList<>();
        for (int i = 0; i < list.length; i++) {
                    for (int j = 0; j < i; j++) {
                        output.add(new Preference(list[j],list[i]));                        
                    }
                }
        
        return output;
    }
    
    public Preference(int preferred,int other) {
        this.preferred = preferred;
        this.other = other;
    }

    /**
     * @return the preferred
     */
    public int getPreferred() {
        return preferred;
    }

    /**
     * @return the other
     */
    public int getOther() {
        return other;
    }

    @Override
    public boolean equals(Object o) {
        Preference input = (Preference)o;
        return (input.other == this.other && input.preferred == this.preferred);
    }

    @Override
    public String toString() {
        return this.preferred+" ≻ "+this.other;
    }
    
    
    
    
}
