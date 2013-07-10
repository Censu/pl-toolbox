/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    
    // Stores the state of all registered threads. 
    // Key = allocated thread id. Value = thread state.
    static HashMap<Integer,Boolean> threadActiveStatus;
    static HashSet<Integer> interruptRequestSet;
    static boolean shutdownProgram;
    //static int nxtThreadIDTicket;
    
    static boolean hasSetup = false;
    
    public ExecutionProgress()
    {
        totProgress = new SimpleFloatProperty(0);
        currTaskTextIndicator = new SimpleStringProperty("test");
        progressStack = new ArrayList<>();
        currTaskProgress = 0;
        
        taskHeader = "";
        taskSubHeader = "";
        
        threadActiveStatus = new HashMap<Integer,Boolean>();
        interruptRequestSet = new HashSet<Integer>();
        shutdownProgram = false;
        //nxtThreadIDTicket = 1;
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
        
        if(threadActiveStatus == null) { threadActiveStatus = new HashMap<Integer,Boolean>(); } else { threadActiveStatus.clear(); }
        if(interruptRequestSet == null) { interruptRequestSet = new HashSet<Integer>(); } else { interruptRequestSet.clear(); }
        shutdownProgram = false;
        //nxtThreadIDTicket = 1;
        
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
        if(currTaskTextIndicator == null) { currTaskTextIndicator = new SimpleStringProperty(""); }
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
    
    public static void registerThread(int para_threadID)
    {
        if(!hasSetup) { reset(); }
        threadActiveStatus.put(para_threadID, true);
    }
    
    public static void signalDeactivation(int para_threadID)
    {
        threadActiveStatus.put(para_threadID, false);
    }
    
    public static boolean safeToShutdown()
    {
        if(threadActiveStatus == null) { return true; }
        
        int numOfDeactivatedSubThreads = 0;
        for (Map.Entry pairs : threadActiveStatus.entrySet())
        {
            if(pairs.getValue() == false)
            {
                numOfDeactivatedSubThreads++;
            }
        }
        
        if(numOfDeactivatedSubThreads == threadActiveStatus.size())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static boolean needToShutdown()
    {
        return shutdownProgram;
    }
    
    public static boolean hasInterruptRequest(int para_threadID)
    {
        if(interruptRequestSet != null)
        {
            if(interruptRequestSet.contains(para_threadID))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public static void requestThreadInterrupt(int para_threadToInterrupt)
    {
        if(! interruptRequestSet.contains(para_threadToInterrupt))
        {
            interruptRequestSet.add(para_threadToInterrupt);
        }
    }
    
    
    
}
