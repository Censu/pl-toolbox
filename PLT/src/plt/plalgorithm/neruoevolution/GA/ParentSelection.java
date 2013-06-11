package plt.plalgorithm.neruoevolution.GA;

import java.util.List;

/**
 *
 * @author luca
 */
public interface ParentSelection {

    public void setSource(List<Individual> elements);
    public Individual select();
    public String getSelectionName();

    
}
