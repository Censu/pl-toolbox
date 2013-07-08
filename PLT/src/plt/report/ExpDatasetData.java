/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.report;

/**
 *
 * @author Owner
 */
public class ExpDatasetData
{
    String object_file;
    String rank_file;
    double accuracy_forSpecificModel;
    double accuracy_averageOverFolds;
    
    public ExpDatasetData(String para_objFile,
                          String para_rankFile,
                          double para_specificAccuracy,
                          double para_averageAccuracy)
    {
        object_file = para_objFile;
        rank_file = para_rankFile;
        accuracy_forSpecificModel = ((Math.round(para_specificAccuracy * 100) * 1000) / 1000);
        accuracy_averageOverFolds = ((Math.round(para_averageAccuracy * 100) * 1000) / 1000);
    }
}
