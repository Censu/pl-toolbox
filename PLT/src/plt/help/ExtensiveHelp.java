/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.help;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class ExtensiveHelp 
{
    ArrayList<String> sectionOrderList;
    HashMap<String, String> helpBySectionMap;
    
    public ExtensiveHelp()
    {
        // For Json parser only.
    }
    
    public ExtensiveHelp(ArrayList<String> para_SectionOrderList, HashMap<String,String> para_HelpBySectionMap)
    {
        sectionOrderList = para_SectionOrderList;
        helpBySectionMap = para_HelpBySectionMap;
    }
    
    
    public String getSectionHelp(String para_SectionID)
    {
        if(helpBySectionMap.containsKey(para_SectionID))
        {
            return helpBySectionMap.get(para_SectionID);
        }
        else
        {
            return null;
        }
    }
    
    public HashMap<String,String> getHelpFromAllSections()
    {
        return helpBySectionMap;
    }
}
