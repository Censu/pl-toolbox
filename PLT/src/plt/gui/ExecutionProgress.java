/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui;

import javafx.beans.property.SimpleFloatProperty;

/**
 *
 * @author user
 */
public class ExecutionProgress 
{
    // Min = 0, Max = 1.
    static SimpleFloatProperty totProgress = new SimpleFloatProperty(0);
    
    public ExecutionProgress()
    {
        totProgress = new SimpleFloatProperty(0);
    }
    
    public static void updateProgress(float para_nwProgVal)
    {
        totProgress.set(para_nwProgVal);
    }
}
