
package plt.plalgorithm.svm.libsvm_plt;

import plt.dataset.TrainableDataSet;

/**
 *
 * @author Owner
 */
public interface IRankSvm
{
    boolean runRankSVM();
    double calculateUtility(double[] para_instanceFeatureData);
    SVMDataStore getDataForSVsAndAlphas(TrainableDataSet para_dSet);
}
