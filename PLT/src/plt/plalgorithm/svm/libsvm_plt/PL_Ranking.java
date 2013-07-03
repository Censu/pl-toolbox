
package plt.plalgorithm.svm.libsvm_plt;

class PL_Ranking
{
    int objID_pref;
    int objID_nonPref;

    public PL_Ranking(int para_objIDPref, int para_objIDNonPref)
    {
        objID_pref = para_objIDPref;
        objID_nonPref = para_objIDNonPref;
    }

    public int getPref() { return objID_pref; }
    public int getNonPref() { return objID_nonPref; }
}
