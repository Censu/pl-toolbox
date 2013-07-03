
package plt.plalgorithm.svm.libsvm_plt;

class PL_Object
{
    int objID;
    double[] features;

    public PL_Object(int para_objID, double[] para_features)
    {
        objID = para_objID;
        features = para_features;
    }

    public int getObjID() { return objID; }
    public int getNumFeatures() { return features.length; }
    public double[] getFeatures() { return features; }
    public double getFeatureData(int para_reqFIndex) { return features[para_reqFIndex]; }
}
