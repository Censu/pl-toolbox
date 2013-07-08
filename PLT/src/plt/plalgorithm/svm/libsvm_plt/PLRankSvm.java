/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.svm.libsvm_plt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;
import plt.gui.configurators.PLRankSvmConfigurator;
import plt.json.JsonObjIO;
import plt.model.Model;
import plt.plalgorithm.PLAlgorithm;
import plt.report.SvmModelFileData;

/**
 *
 * @author Owner
 */
public class PLRankSvm extends PLAlgorithm
{
    private PLRankSvmConfigurator svmConfig;
    private RankSvmManager svmMang;
    
    public PLRankSvm(TrainableDataSet para_tDataset, PLRankSvmConfigurator para_svmConfig)
    {
        super(para_tDataset);
        svmConfig = para_svmConfig;
    }
    
    
    @Override
    protected Model run()
    {
        Logger.getLogger("plt.logger").log(Level.INFO, "run PLRankSvm");

        svmMang.runRankSVM();
        
        return createModelForRankSVM(svmMang,this.getDataset(),this.getFeatureSelection());
    }

    @Override
    protected Model beforeRun()
    {
        HashMap<String,Object> userConfig = new HashMap<>();
        userConfig.put("kernel", svmConfig.getKernelType());
        userConfig.put("gamma", svmConfig.getGamma());
        userConfig.put("degree", svmConfig.getDegree());     
        
        svmMang = new RankSvmManager();
        svmMang.performSetup(this.getDataset(),this.getFeatureSelection(),userConfig);
        
        
        return createModelForRankSVM(svmMang, this.getDataset(), this.getFeatureSelection());
    }

    @Override
    public ArrayList<Object[]> getProperties() 
    {
        
        // Multilayer Perceptron Properties:
        
        String subSec1_header = "Rank SVM";
        ArrayList<String[]> subSec1_content = new ArrayList<>();
        
        String[] cPair1 = new String[2];
        cPair1[0] = "Kernel:";
        cPair1[1] = ""+svmConfig.getKernelType();
        subSec1_content.add(cPair1);
        
        if(svmConfig.gammaRequired())
        {
            String[] cPair2 = new String[2];
            cPair2[0] = "Gamma:";
            cPair2[1] = ""+svmConfig.getGamma();
            subSec1_content.add(cPair2);
        }
        
        if(svmConfig.degreeRequired())
        {
            String[] cPair3 = new String[2];
            cPair3[0] = "Degree:";
            cPair3[1] = ""+svmConfig.getDegree();
            subSec1_content.add(cPair3);
        }
                
        
        
        Object[] wrapper1 = new Object[2];
        wrapper1[0] = subSec1_header;
        wrapper1[1] = subSec1_content;
        
        
        ArrayList<Object[]> retData = new ArrayList<>();
        retData.add(wrapper1);
        
        return retData;
    }
    
    public PLRankSvmConfigurator getConfigurator()
    {
        return svmConfig;
    }
    
    private Model createModelForRankSVM(RankSvmManager para_rSVMMang, final TrainableDataSet para_dataSet, final SelectedFeature para_selection)
    {        
        Model model = new Model(para_dataSet, para_selection) {

            @Override
            protected double calculatePreference(double[] features)
            {
                return svmMang.calculateUtility(features);
            }

            /*@Override
            public void save(File file) throws IOException
            {    
                try
                {
                    Date date=new Date() ;  
                    //BufferedWriter out = new BufferedWriter( new FileWriter (new File(file, "SVM"+date.getTime())));
                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
                    out.write("SVM");
                    out.close();
                } catch (IOException ex)
                {
                    Logger.getLogger(PLRankSvm.class.getName()).log(Level.SEVERE, null, ex);   
                    throw ex;
                }
            }*/
            
            @Override
            public void save(File file, Experiment experiment, double accResult_specificModel, double accResult_averageOverFolds) throws IOException
            {
                
                try 
                {
                 
                    SVMDataStore svmDStore = svmMang.getDataForSVsAndAlphas(para_dataSet);
                    
                    // Construct file data for chosen model.
                    SvmModelFileData objToStore = new SvmModelFileData(file.getName(),
                                                                       svmDStore,
                                                                       this.getDataSet(),
                                                                       this.selectedFeature(),
                                                                       experiment,
                                                                       accResult_specificModel,
                                                                       accResult_averageOverFolds);
                    
                    // Store data to file as JSON.
                    JsonObjIO jsonRW = new JsonObjIO();
                    jsonRW.writeObjToFile(file.getAbsolutePath(), objToStore);
                }
                catch (Exception ex)
                {
                   Logger.getLogger(PLRankSvm.class.getName()).log(Level.SEVERE,null,ex);
                   throw ex;
                }
            }
            
        };
        
        return model;
    }
    
}
