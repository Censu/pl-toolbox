/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui;

import java.util.ArrayList;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author user
 */
public class ExecutionProgress 
{
    // Min = 0, Max = 1.
    static SimpleFloatProperty totProgress = new SimpleFloatProperty(0);
    static StringProperty currTaskTextIndicator = new SimpleStringProperty("test");
    
    
    static ArrayList<Float> progressStack;
    static float currTaskProgress;
    
    static String taskHeader;
    static String taskSubHeader;
            
    public ExecutionProgress()
    {
        totProgress = new SimpleFloatProperty(0);
        currTaskTextIndicator = new SimpleStringProperty("test");
        progressStack = new ArrayList<>();
        currTaskProgress = 0;
        
        taskHeader = "";
        taskSubHeader = "";
    }
    
    /*public static void updateTotalProgress(float para_nwProgVal)
    {
        totProgress.set(para_nwProgVal);
    }*/
    
    public static void reset()
    {
        totProgress.setValue(0);
        if(progressStack == null) { progressStack = new ArrayList<>(); } else { progressStack.clear(); }
        currTaskProgress = 0;
        
        taskHeader = "";
        taskSubHeader = "";
    }
    
    public static float getCurrTaskMaxProgVal()
    {
        if(progressStack == null) { progressStack = new ArrayList<>(); }
        
        if(progressStack.isEmpty())
        {
            return 0;
        }
        else
        {
            return progressStack.get(progressStack.size()-1);
        }
    }
    
    // para_portionOfRemainingProgPerc = value between 0 and 1.
    public static void signalBeginTask(String para_taskMainHeader, float para_portionOfRemainingProgPerc)
    {
        if(progressStack == null) { progressStack = new ArrayList<>(); }
        progressStack.add((1-totProgress.get()) * para_portionOfRemainingProgPerc);
        currTaskProgress = 0;
        
        taskHeader = para_taskMainHeader;
        if(currTaskTextIndicator == null) { currTaskTextIndicator = new SimpleStringProperty("test"); }
        currTaskTextIndicator.setValue(taskHeader + " - " + taskSubHeader);
        //currTaskTextIndicator.set("3");
    }
    
    public static void signalTaskComplete()
    {
        if(progressStack == null) { progressStack = new ArrayList<>(); }
        
        if(! progressStack.isEmpty())
        {
            progressStack.remove(progressStack.size()-1);
        }
        
        currTaskProgress = 0;
    }
    
    // para_percIncrement = value between 0 and 1.
    public static void incrementTaskProgByPerc(float para_percIncrement)
    {   
        float actualInc = (getCurrTaskMaxProgVal() * para_percIncrement);
        totProgress.set(totProgress.get() + actualInc);
        
        currTaskProgress += para_percIncrement;
        
       
        if(currTaskProgress >= 1)
        {
            signalTaskComplete();
        } 
    }
    
    public static void setTaskSubHeader(String para_taskSubHeader)
    {
        taskSubHeader = para_taskSubHeader;
        currTaskTextIndicator.setValue(taskHeader + " - " + taskSubHeader);
        //currTaskTextIndicator.setValue("4");
    }
}
