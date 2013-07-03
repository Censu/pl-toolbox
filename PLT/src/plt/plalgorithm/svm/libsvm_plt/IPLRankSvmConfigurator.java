/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.svm.libsvm_plt;

/**
 *
 * @author Owner
 */
public interface IPLRankSvmConfigurator
{
    public String getKernelType();
    public double getGamma();
    public double getDegree();
}
