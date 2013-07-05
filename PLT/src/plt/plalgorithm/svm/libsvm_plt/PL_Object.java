
package plt.plalgorithm.svm.libsvm_plt;

class PL_Object
{
    int objID_actual;
    int objID_order;
    double[] features;

    public PL_Object(int para_objIDActual,
                     int para_objIDOrder,
                     double[] para_features)
    {
        objID_actual = para_objIDActual;
        objID_order = para_objIDOrder;
        features = para_features;
    }

    public int getObjIDActual() { return objID_actual; }
    public int getNumFeatures() { return features.length; }
    public double[] getFeatures() { return features; }
    public double getFeatureData(int para_reqFIndex) { return features[para_reqFIndex]; }
}
