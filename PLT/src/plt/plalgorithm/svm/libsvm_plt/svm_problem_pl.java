package plt.plalgorithm.svm.libsvm_plt;


import plt.plalgorithm.svm.libsvm.svm_node;
import plt.plalgorithm.svm.libsvm.svm_problem;


public class svm_problem_pl extends svm_problem
{
    // Defs:
    //
    // INHERITED    l = number of pairs.
    // INHERITED    y = all 1s.
    // INHERITED    x = ranking data. (Transformation of PL_Ranking pairs).
    // NEW          pl_objArr reference.
    // NEW          pl_rankingArr reference.
    
    public svm_node[][] pl_objs;
    
    public PL_Object[] pl_objArr_orig;
    public PL_Ranking[] pl_rankingArr_orig;
}
