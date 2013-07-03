package plt.plalgorithm.svm.libsvm_plt;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import plt.plalgorithm.svm.libsvm.svm;
import plt.plalgorithm.svm.libsvm.svm_model;
import plt.plalgorithm.svm.libsvm.svm_node;
import plt.plalgorithm.svm.libsvm.svm_parameter;


public class svm_pl_tester
{
    public static void main(String[] args)
    {
        svm_pl_tester prog = new svm_pl_tester();
        prog.go();
    }
    
    public void go()
    {
        System.out.println("Running PLT_SVM...");
        
        String parentDir = "C:\\Users\\Owner\\Desktop\\";
        String filePath_objectFile = "TestObjFile.idata";
        String filePath_rankingFile = "TestRankFile.order";
        
        
        // Extract the object file data.
        Object[] retData = extractObjectFile(parentDir + filePath_objectFile);
        PL_Object[] objects = (PL_Object[]) retData[0];
        int numFeatures = (int) retData[1];
        
        // Extract the ranking file data.
        PL_Ranking[] rankings = extractRankingFile(parentDir + filePath_rankingFile);
        
        
        // Run with the extracted data.
        Object[] trainingWrapper = runRankSVM(objects,rankings,numFeatures);
        
        // Calculate accuracy over the training dataset itself.
        double accuracy_OverTrainingSet = calculateAccuracy((svm_model) trainingWrapper[0],
                                                            (svm_parameter) trainingWrapper[2],
                                                            (svm_problem_pl) trainingWrapper[1],
                                                            (svm_problem_pl) trainingWrapper[1]);
        
        System.out.println("Accuracy over training set: "+(accuracy_OverTrainingSet*100)+"%");
        
        System.out.println("PLT_SVM Done.");
    }
    
    
    public Object[] extractObjectFile(String para_objFilePath)
    {
        
        ArrayList<PL_Object> tmpObjList = new ArrayList<PL_Object>();
        
        try
        {
            BufferedReader bReader = new BufferedReader(new FileReader(para_objFilePath));

            
            int lineNumber = 0;
            int nxtPlObjID = 0;
            String nxtLine = null;
            while((nxtLine = bReader.readLine()) != null)
            {
                if(lineNumber == 0)
                {
                    // Discard column header row.
                }
                else
                {
                    nxtLine = nxtLine.trim();
                    nxtLine = nxtLine.substring(0, nxtLine.length());


                    String[] lineComponents = nxtLine.split("\t");
                    
                    // Extract Features.
                    double[] nwFeatures = new double[lineComponents.length-1];
                    for(int i=1; i<lineComponents.length; i++)
                    {
                        nwFeatures[i-1] = Double.parseDouble(lineComponents[i]);
                    }
                    PL_Object nwPLObj = new PL_Object(nxtPlObjID, nwFeatures);
                    tmpObjList.add(nwPLObj);
                    nxtPlObjID++;
                }
                
                lineNumber++;
            }
            
            bReader.close();
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        
        
        PL_Object[] plObjArr = new PL_Object[tmpObjList.size()];
        for(int i=0; i<tmpObjList.size(); i++)
        {
            plObjArr[i] = tmpObjList.get(i);
        }
        
        Object[] retData = new Object[2];
        retData[0] = plObjArr;
        retData[1] = plObjArr[0].getNumFeatures();
        return retData;
        
        //return tmpObjList.toArray(new PL_Object[tmpObjList.size()]);
    }
    
    public PL_Ranking[] extractRankingFile(String para_rankingFilePath)
    {
        
        ArrayList<PL_Ranking> tmpRankingList = new ArrayList<PL_Ranking>();
        
        try
        {
            BufferedReader bReader = new BufferedReader(new FileReader(para_rankingFilePath));

            
            int lineNumber = 0;
            int nxtPlRankID = 0;
            String nxtLine = null;
            while((nxtLine = bReader.readLine()) != null)
            {
                
                nxtLine = nxtLine.trim();
                nxtLine = nxtLine.substring(0, nxtLine.length());


                String[] lineComponents = nxtLine.split(" ");

                // Extract Features.
                int objID_Pref = Integer.parseInt(lineComponents[0]);
                int objID_NonPref = Integer.parseInt(lineComponents[1]);

                PL_Ranking nwPLRanking = new PL_Ranking(objID_Pref, objID_NonPref);
                tmpRankingList.add(nwPLRanking);
                nxtPlRankID++;
                
                
                lineNumber++;
            }
            
            bReader.close();
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        
              
        return tmpRankingList.toArray(new PL_Ranking[tmpRankingList.size()]);
    }
    
    public Object[] runRankSVM(PL_Object[] para_objects,
                               PL_Ranking[] para_rankings,
                               int para_numOfFeatures)
    {
        // Safety Check.
        if((para_objects == null)||(para_rankings == null)) return null;
        
        svm_parameter algParams = createDefault_RankSVMAlgParams();
        svm_problem_pl dSet = createDataset(para_objects, para_rankings, para_numOfFeatures);
        Object[] trainingWrapper = runRankSVM(algParams,dSet,para_numOfFeatures);
        return trainingWrapper;
    }
    
    public Object[] runRankSVM(svm_parameter para_algParams,
                               svm_problem_pl para_dataset,
                               int para_numOfFeatures)
    {
        
        // Safety Check.
        if(para_dataset == null) return null;

        
        
        // This extension of LIBSVM provides Rank SVM for PLT.
        svm_model trainedModel = null;
        if(para_algParams.svm_type == svm_parameter.RANK)
        {
            // If param.kernelType != svm_parameter.PRECOMPUTED.
            // And param.svmType != svm_parameter.EPSILON_SVM.
            // And param.svmType != svm_parameter.NU_SVM.

            // Construct model via training.
            trainedModel = svm.svm_train(para_dataset, para_algParams);
        }

        Object[] trainingWrapper = new Object[3];
        trainingWrapper[0] = trainedModel;
        trainingWrapper[1] = para_dataset;
        trainingWrapper[2] = para_algParams;
        return trainingWrapper;
    }
    
    
    public double calculateAccuracy(svm_model para_trainedModel,
                                    svm_parameter para_algParams,
                                    svm_problem_pl para_trainingDataset,
                                    svm_problem_pl para_testingDataset)
    {
        return svm.svm_pl_accuracyChecks(para_trainedModel, para_algParams, para_trainingDataset, para_testingDataset);
    }
    
    private svm_parameter createDefault_RankSVMAlgParams()
    {
        // Set SVM algorithm parameters. (Default Values)
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
        
        return param;
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
    
}
