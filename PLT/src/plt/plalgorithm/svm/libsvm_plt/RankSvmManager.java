/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.svm.libsvm_plt;


import java.util.HashMap;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.plalgorithm.svm.libsvm.svm;
import plt.plalgorithm.svm.libsvm.svm_model;
import plt.plalgorithm.svm.libsvm.svm_node;
import plt.plalgorithm.svm.libsvm.svm_parameter;
import plt.utils.Preference;

/**
 *
 * @author Owner
 */
public class RankSvmManager implements IRankSvm
{
    PL_Object[] curr_objects;
    PL_Ranking[] curr_rankings;
    int curr_numFeatures;
    
    svm_parameter curr_algParams;      // Algorithm parameters used during training.
    svm_problem_pl curr_dataset;       // Dataset used to train the model.
    svm_model curr_model;              // Current model.
    
    public boolean performSetup(TrainableDataSet para_trainingDataSet,
                                SelectedFeature para_fSelected,
                                HashMap<String,Object> para_userConfig)
    {
        // Convert PLT representation of objects.
        Object[] retData = createPLObjects(para_trainingDataSet, para_fSelected);
        PL_Object[] objects = (PL_Object[]) retData[0];
        int numFeatures = (int) retData[1];
        
        // Convert PLT representation of rankings.
        PL_Ranking[] rankings = createPLRankings(para_trainingDataSet, para_fSelected);
        
        
        // Safety Check.
        if((objects == null)||(rankings == null)) return false;
        
        
        svm_parameter algParams = createRankSVMAlgParams(para_userConfig);        
        svm_problem_pl dSet = createDataset(objects, rankings, numFeatures);
        
        
        
        curr_objects = objects;
        curr_rankings = rankings;
        curr_numFeatures = numFeatures;
        
        curr_algParams = algParams;
        curr_dataset = dSet;
        curr_model = null;
        
        return true;
    }
    
    @Override
    public boolean runRankSVM()
    {
        // Safety Check.
        if(curr_dataset == null) return false;
                
        
        System.out.println("Running PLT_SVM...");
              
        // This extension of LIBSVM provides Rank SVM for PLT.
        svm_model trainedModel = null;
        if(curr_algParams.svm_type == svm_parameter.RANK)
        {
            // Construct model via training.
            trainedModel = svm.svm_train(curr_dataset, curr_algParams);
        }        
        curr_model = trainedModel;
        
        System.out.println("PLT_SVM Done.");
        
        return true;
    }
    
    @Override
    public double calculateUtility(double[] para_instanceFeatureData)
    {
        if(curr_model == null)
        {
            // Training has not yet occurred.
            return 0;
        }
        else
        {
            svm_node[] convertedInstance = new svm_node[curr_numFeatures];
            for(int i=0; i<curr_numFeatures; i++)
            {
                convertedInstance[i] = new svm_node();
                convertedInstance[i].index = i+1;      // To abide with 1-based indexing of svm_nodes.
                convertedInstance[i].value = para_instanceFeatureData[i];
            }

            return svm.svm_pl_calculateUtility(curr_model, curr_algParams, curr_dataset, convertedInstance);
        }
    }
    
    /*public double calculateAccuracy()
    {
        return calculateAccuracy(curr_model,curr_algParams,curr_dataset,curr_dataset);
    }
    
    public double calculateAccuracy(svm_model para_trainedModel,
                                    svm_parameter para_algParams,
                                    svm_problem_pl para_trainingDataset,
                                    svm_problem_pl para_testingDataset)
    {
        return svm.svm_pl_accuracyChecks(para_trainedModel, para_algParams, para_trainingDataset, para_testingDataset);
    }*/
    
    
    
    
    private Object[] createPLObjects(TrainableDataSet para_tDataSet, SelectedFeature para_fSelected)
    {
        int numObjects = para_tDataSet.getNumberOfObjects();
        
        PL_Object[] plObjArr = new PL_Object[numObjects];
        for(int i=0; i<numObjects; i++)
        {
            plObjArr[i] = new PL_Object(i, para_fSelected.select(para_tDataSet.getFeatures(i)));
        }
        
        Object[] retData = new Object[2];
        retData[0] = plObjArr;
        retData[1] = plObjArr[0].getNumFeatures();
        return retData;
    }
    
    private PL_Ranking[] createPLRankings(TrainableDataSet para_tDataSet, SelectedFeature para_fSelected)
    {
        int numPreferences = para_tDataSet.getNumberOfPreferences();
        
        PL_Ranking[] plRankArr = new PL_Ranking[numPreferences];
        for(int i=0; i<numPreferences; i++)
        {
            Preference p = para_tDataSet.getPreference(i);
            plRankArr[i] = new PL_Ranking(p.getPreferred(), p.getOther());
        }
        
        return plRankArr;       
    }
    
    
        
    private svm_problem_pl createDataset(PL_Object[] para_objects,
                                         PL_Ranking[] para_rankings,
                                         int para_numOfFeatures)
    {
        // Prepare the SVM Dataset. Note: Use svm_problem_plt instead of svm_problem.
        svm_problem_pl prob = new svm_problem_pl();
        prob.l = para_rankings.length;
        prob.y = new double[prob.l];


        
        prob.x = new svm_node [prob.l][2];
        for(int i=0;i<prob.l;i++)
        {
            // X stores rank pairs.
            PL_Ranking rankDataSample = para_rankings[i];
            prob.x[i][0] = new svm_node();
            prob.x[i][0].index = 1;
            prob.x[i][0].value = rankDataSample.objID_pref;
            prob.x[i][1] = new svm_node();
            prob.x[i][1].index = 2;
            prob.x[i][1].value = rankDataSample.objID_nonPref;

            // Y (desired vals) is set to all 1s for PLT.
            prob.y[i] = 1;
        }


        // Fill in pl_objs. PLT SVM will require feature data.
        prob.pl_objs = new svm_node[para_objects.length][para_numOfFeatures];
        for(int i=0; i<para_objects.length; i++)
        {
            PL_Object objectDataSample = para_objects[i];
            for(int f=0; f<para_numOfFeatures; f++)
            {
                prob.pl_objs[i][f] = new svm_node();
                prob.pl_objs[i][f].index = f+1;      // To abide with 1-based indexing of svm_nodes.
                prob.pl_objs[i][f].value = objectDataSample.features[f];
            }
        }


        // Keep references to object and ranking , data objects.
        prob.pl_objArr_orig = para_objects;
        prob.pl_rankingArr_orig = para_rankings;
        
        return prob;
    }
    
    
    private svm_parameter createRankSVMAlgParams(HashMap<String,Object> para_userConfig)
    {
        // Set Default Rank SVM Algorithm Parameters.
        svm_parameter param = new svm_parameter();        
        param.svm_type = svm_parameter.RANK;            // For Preference Learning / Rank SVM.
        param.kernel_type = svm_parameter.LINEAR;
        param.degree = 3;
        param.gamma = 0;
        param.coef0 = 0;
        param.nu = 0.5;
        param.cache_size = 40;
        param.C = 1;
        param.eps = 1e-3;
        param.p = 0.1;
        param.shrinking = 1;
        param.probability = 0;
        param.nr_weight = 0;
        param.weight_label = new int[0];
        param.weight = new double[0];
        
        if(param.gamma == 0) param.gamma = 0.5;
        
        
        
        // Override with User Specified Parameters.
        
        String kernelType = (String) para_userConfig.get("kernel");
        if(kernelType.equals("Linear")) { param.kernel_type = svm_parameter.LINEAR; }
        else if(kernelType.equals("Poly")) { param.kernel_type = svm_parameter.POLY; }
        else if(kernelType.equals("RBF")) { param.kernel_type = svm_parameter.RBF; }
        
        // Guaranteed to be != 0 due to pre-experiment validation check.
        param.gamma = (double) para_userConfig.get("gamma");
        param.degree = (int) ((double) para_userConfig.get("degree"));
        
        return param;
    }
}
