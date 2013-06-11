
package plt.help;

import java.util.HashMap;

/**
 *
 * @author user
 */
public interface IHelpExtractor 
{
    // Extract all tool tip data for a particular gui tab.
    HashMap<String,String> extractToolTipFile(String para_filePath);
    
    // Extract relevant extensive help. Select items from the file and do not load all of the contents.
    HashMap<String,String> extractExtensiveHelpFile(String para_filePath, String[] para_reqHelpItemIDs);
}
