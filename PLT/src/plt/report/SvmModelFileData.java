/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.report;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;
import plt.gui.configurators.PLRankSvmConfigurator;
import plt.plalgorithm.svm.libsvm_plt.PLRankSvm;
import plt.plalgorithm.svm.libsvm_plt.SVMDataStore;
import plt.utils.TimeHelper;

/**
 *
 * @author Owner
 */
public class SvmModelFileData extends ModelFileData implements Serializable
{
    final String algorithm = "Rank SVM";
    
    ExpMetaData experiment_metadata;
    ExpDatasetData experiment_dataset;
    
    // Feature Config Data.
    String[] selected_features;
    PreprocessingInfo[] preprocessingInfo;
    
    
    // Rank Svm Alg Specifics.
    SvmAlgConfiguration svm_configuration;
    double[][] support_vectors;
    double[] alphas;
    //ArrayList<ObjWrapper> support_vector_objData;
    
    public SvmModelFileData(String para_fileName,
                            SVMDataStore para_svmDStore,
                            TrainableDataSet para_dataSet,
                            SelectedFeature para_selF,
                            Experiment para_exp,
                            double para_accResult_specificModel,
                            double para_accResult_averageOverFolds)
    {
        
        experiment_metadata = new ExpMetaData(para_fileName,
                                              "dd/MM/yyyy   HH:mm:ss",
                                              para_exp.expStartTimestampProperty().get(),
                                              para_exp.expCompleteTimestampProperty().get());
        
        experiment_dataset = new ExpDatasetData(para_exp.idataProperty().get().getAbsolutePath(),
                                                para_exp.orderProperty().get().getAbsolutePath(),
                                                para_accResult_specificModel,
                                                para_accResult_averageOverFolds);
        
        // Preprocessing Info.
        
        // Selected Features.
        Object[] selFeatData = this.createSFInfo(para_exp, para_selF);
        int[] selFeatIDs = (int[]) selFeatData[0];
        String[] selFeatNames = (String[]) selFeatData[1];
        selected_features = selFeatNames;
        preprocessingInfo = this.createPreproInfo(para_exp, selFeatIDs, selFeatNames);
        
        
        // SVM Specifics.        
        ConfigFactory configFac = new ConfigFactory();
        svm_configuration = configFac.createReqSvmAlgConfig(para_exp);
        
        support_vectors = para_svmDStore.svData_rankPairs;
        alphas = para_svmDStore.alphas;
        
        /*support_vector_objData = new ArrayList<>();
        for(int i=0; i<para_svmDStore.objID_actuals.length; i++)
        {
            ObjWrapper nwObjWrap = new ObjWrapper(para_svmDStore.objID_actuals[i], para_svmDStore.svData_objData[i]);
            support_vector_objData.add(nwObjWrap);
        }*/
    }
    
    class ObjWrapper implements Serializable
    {
        // Actual ID.
        int object_ID;
        double[] object_all_features;
        
        public ObjWrapper(int para_actualID, double[] para_allFeatures)
        {
            object_ID = para_actualID;
            object_all_features = para_allFeatures;
        }
    }
    
    
    class ConfigFactory
    {
        SvmAlgConfiguration createReqSvmAlgConfig(Experiment para_exp)
        {
            PLRankSvm castSvmAlg = (PLRankSvm) para_exp.algorithmProperty().get();
            PLRankSvmConfigurator svmConfig = castSvmAlg.getConfigurator();
            String kernelType = svmConfig.getKernelType();
            
            if(kernelType.equals("Linear"))
            {
                return new LinearConfiguration();
            }
            else if(kernelType.equals("Poly"))
            {
                return new PolyConfiguration(svmConfig.getGamma(), (int) svmConfig.getDegree());
            }
            else if(kernelType.equals("RBF"))
            {
                return new RBFConfiguration(svmConfig.getGamma());
            }
            else
            {
                return null;
            }
        }
    }
    
    class SvmAlgConfiguration implements Serializable
    {
        String kernel_type;
    }   
        
    class LinearConfiguration extends SvmAlgConfiguration implements Serializable
    {
        public LinearConfiguration()
        {
            kernel_type = "Linear";
        }
    }

    class PolyConfiguration extends SvmAlgConfiguration implements Serializable
    {
        double gamma;
        int degree;
        
        public PolyConfiguration(double para_gamma, int para_degree)
        {
            kernel_type = "Poly";
            gamma = para_gamma;
            degree = para_degree;
        }
    }

    class RBFConfiguration extends SvmAlgConfiguration implements Serializable
    {
        double gamma;
        
        public RBFConfiguration(double para_gamma)
        {
            kernel_type = "RBF";
            gamma = para_gamma;
        }
    }
    
}
