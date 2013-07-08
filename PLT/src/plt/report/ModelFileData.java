/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import plt.dataset.preprocessing.MinMax;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.dataset.preprocessing.ZScore;
import plt.dataset.sushireader.SushiFormatDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;

/**
 *
 * @author Owner
 */
public abstract class ModelFileData implements Serializable
{
    
    
    public Object[] createSFInfo(Experiment para_exp,
                                 SelectedFeature para_selF)
    {
        int[] featureIDs = null;
        String[] featureNames = null;
        
        if(para_selF == null)
        {
            // If no feature selection was performed.
            
            ArrayList<Integer> tmpFIDList = new ArrayList<>();
            ArrayList<String> tmpFNameList = new ArrayList<>();
            
            boolean[] igArr = para_exp.ignoredFeaturesProperty().get();
            for(int i=0; i<igArr.length; i++)
            {
                if(! igArr[i])
                {
                    tmpFIDList.add(i);
                    tmpFNameList.add(para_exp.dataSetProperty().get().getFeatureName(i));
                }
            }
            
            featureIDs = new int[tmpFIDList.size()];
            Integer[] tmpIntArr = tmpFIDList.toArray(new Integer[tmpFIDList.size()]);
            for(int i=0; i<tmpIntArr.length; i++) { featureIDs[i] = tmpIntArr[i]; }
            featureNames = tmpFNameList.toArray(new String[tmpFNameList.size()]);
            
        }
        else
        {
            
            int[] selF_ids = para_selF.getSelectedFeatures();
            featureIDs = selF_ids;
            featureNames = new String[selF_ids.length];
            for(int i=0; i<selF_ids.length; i++)
            {
                featureNames[i] = para_exp.dataSetProperty().get().getFeatureName(selF_ids[i]);
            }
          
        }
        
        Object[] retData = new Object[2];
        retData[0] = featureIDs;
        retData[1] = featureNames;
        return retData;
    }
        
    public PreprocessingInfo[] createPreproInfo(Experiment para_exp,
                                                int[] para_selFeatureIds,
                                                String[] para_selFeatureNames)
    {
        // Feature Preprocessing Data
        
        int[] selF_ids = para_selFeatureIds;
        String[] inputs = para_selFeatureNames;
        
        PreprocessingInfo[] preprocessingInfo = new PreprocessingInfo[selF_ids.length];
        for(int i=0; i<selF_ids.length; i++)
        {
            PreprocessingOperation preProOp = para_exp.preprocessingOperationsProperty().get()[selF_ids[i]];
            
            PreprocessingInfo nwPreProInfo = new PreprocessingInfo();
            nwPreProInfo.featureName = inputs[i];
            nwPreProInfo.preprocessingTypeName = preProOp.getOperationName();
            
            if(preProOp instanceof MinMax)
            {
                MinMax castMinMax = (MinMax) preProOp;
                double userGivenMin = castMinMax.getMin();
                double userGivenMax = castMinMax.getMax();
                
                SushiFormatDataSet castDataSet = (SushiFormatDataSet) castMinMax.getDataSet();
                double featureMin = (double) castDataSet.getMinValForFeature(selF_ids[i]);
                double featureMax = (double) castDataSet.getMaxValForFeature(selF_ids[i]);
                
                nwPreProInfo.addNwEntry("User Min", userGivenMin);
                nwPreProInfo.addNwEntry("User Max", userGivenMax);
                nwPreProInfo.addNwEntry("Feature Min", featureMin);
                nwPreProInfo.addNwEntry("Feature Max", featureMax);               
            }
            else if(preProOp instanceof ZScore)
            {
                ZScore castZScore = (ZScore) preProOp;
                double average = castZScore.getAverage();
                double stdev = castZScore.getStdev();
                
                nwPreProInfo.addNwEntry("Average",average);
                nwPreProInfo.addNwEntry("Stdev", stdev);
            }
            
            preprocessingInfo[i] = nwPreProInfo;
        }
        
        return preprocessingInfo;
    }

}
