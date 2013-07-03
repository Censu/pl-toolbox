
package plt.plalgorithm.svm.libsvm_plt;

/**
 *
 * @author Owner
 */
public interface IRankSvm
{
    boolean runRankSVM();
    double calculateUtility(double[] para_instanceFeatureData);    
}
