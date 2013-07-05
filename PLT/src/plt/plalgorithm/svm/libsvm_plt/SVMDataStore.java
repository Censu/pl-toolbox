/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.svm.libsvm_plt;

/**
 *
 * @author Owner
 */
public class SVMDataStore
{
    public double[][] svData_rankPairs;
    public double[][] svData_objData;
    public double[] alphas;
    public int[] objID_actuals;
    
    public SVMDataStore(double[][] para_SVRPairs,
                        double[][] para_SVObjData,
                        double[] para_Alphas,
                        int[] para_objIDActuals)
    {
        svData_rankPairs = para_SVRPairs;
        svData_objData = para_SVObjData;
        alphas = para_Alphas;
        objID_actuals = para_objIDActuals;
    }
}
