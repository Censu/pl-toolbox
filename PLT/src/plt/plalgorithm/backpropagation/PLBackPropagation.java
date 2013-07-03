
package plt.plalgorithm.backpropagation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;
import plt.json.JsonObjIO;
import plt.model.Model;
import plt.plalgorithm.PLAlgorithm;
import plt.plalgorithm.neruoevolution.NE.SimpleNeuralNetwork;
import plt.plalgorithm.neruoevolution.PLNeuroEvolution;
import plt.report.ModelFileData;
import plt.utils.Preference;

/**
 *
 * @author luca
 */
public class PLBackPropagation extends PLAlgorithm {
    private PLBackPropagationConfigurator configurator;
    private NeuralNetwork network;
    
    public PLBackPropagation(TrainableDataSet dataSet, PLBackPropagationConfigurator configurator) {
        super(dataSet);
        
        this.configurator = configurator;
        
    }
    
    @Override
    protected Model run() {
        
        Logger.getLogger("plt.logger").log(Level.INFO, "run PLBackPropagation");

        TrainableDataSet dataSet = this.getDataset();
        boolean trained = false;
       
        for (int i=0; i< this.configurator.getMaxNumberOfIterations() && !trained; i++) {
            double error = 0;
            
            
            for (int j =0; j< dataSet.getNumberOfPreferences() ; j++) {

                Preference instance = this.getDataset().getPreference(j);
                                
                double[] featuresPreferred = this.getFeatureSelection().select( dataSet.getFeatures(instance.getPreferred()));
                double[] featuresOther= this.getFeatureSelection().select( dataSet.getFeatures(instance.getOther()));

                
                network.setInputs(featuresPreferred);
                double fPreferred = network.getOutputs()[0];
                network.setInputs(featuresOther);
                double fOther = network.getOutputs()[0];
                

                network.setInputs(featuresOther);
                error += network.backpropagate(false, fPreferred);
                network.setInputs(featuresPreferred);
                error += network.backpropagate(true, fOther);

            }
            
            error /= dataSet.getNumberOfPreferences()*2;
            trained =  error < this.configurator.getErrorThreeshold();
            
            network.applyDeltas();

        }
        
        
        return modelForNetwork(network, this.getDataset(), this.getFeatureSelection());

    
    }

    @Override
    protected Model beforeRun() {
        int inputSize = this.getFeatureSelection().getSize();
        this.network = new NeuralNetwork(
                this.configurator.getTopology(inputSize), 
                this.configurator.getActivationsFunctions(), 
                this.configurator.getLearningRate()
                );
    
        return modelForNetwork(network, this.getDataset(), this.getFeatureSelection());
    }
    
    
        static private Model modelForNetwork(final SimpleNeuralNetwork network, TrainableDataSet dataSet, final SelectedFeature selection) {
        Model model = new Model(dataSet, selection) {

            @Override
            protected double calculatePreference(double[] features) {

                network.setInputs(features);
                double a = network.getOutputs()[0];
                return a;
            
            }

            @Override
            public void save(File file) throws IOException{
                try {
                    Date date=new Date() ;  
                    //BufferedWriter out = new BufferedWriter( new FileWriter (new File(file, "BP"+date.getTime())));
                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
                    out.write("BP#"+ Arrays.toString(network.weights)+ "#" +Arrays.toString(network.topology));
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(PLNeuroEvolution.class.getName()).log(Level.SEVERE, null, ex);
                    
                    throw ex;
                }

            }
            
            /*Override
            public void save(File file, Experiment experiment) throws IOException{
                try {
                 
                    // Construct file data for chosen model.
                    ModelFileData objToStore = new ModelFileData("Backpropagation",
                                                                 network,
                                                                 this.getDataSet(),
                                                                 this.selectedFeature(),
                                                                 experiment);        
                    
                    // Store data to file as JSON.
                    JsonObjIO jsonRW = new JsonObjIO();
                    jsonRW.writeObjToFile(file.getAbsolutePath(), objToStore);
                }
                catch (Exception ex) {
                   Logger.getLogger(PLNeuroEvolution.class.getName()).log(Level.SEVERE,null,ex);
                   
                   throw ex;
                }
            }*/
            
        };
        
        return model;        
    }

    @Override
    public ArrayList<Object[]> getProperties() 
    {
        // Multilayer Perceptron Properties:
        
        String subSec1_header = "Multilayer Perceptron";
        ArrayList<String[]> subSec1_content = new ArrayList<>();
        
        String[] inpLayerContentPair = new String[3];
        inpLayerContentPair[0] = "Input Layer:";
        inpLayerContentPair[1] = ""+this.getFeatureSelection().getSize();
        inpLayerContentPair[2] = "N/A";
        subSec1_content.add(inpLayerContentPair);
        
        
        int inputSize = this.getFeatureSelection().getSize();
        int[] fullTopology = configurator.getTopology(inputSize);
        
        for(int i=1; i<fullTopology.length-1; i++)
        {
            if(fullTopology[i] > 0)
            {
                String[] nwContentPair = new String[3];
                nwContentPair[0] = "Hidden Layer "+i+":";
                nwContentPair[1] = ""+fullTopology[i];
                nwContentPair[2] = configurator.getActivationsFunctions()[i-1].toString();
                
                subSec1_content.add(nwContentPair);
            }
        }
        
        String[] outLayerContentPair = new String[3];
        outLayerContentPair[0] = "Output Layer:";
        outLayerContentPair[1] = ""+1;
        outLayerContentPair[2] = configurator.getActivationsFunctions()[fullTopology.length-2].toString();
        subSec1_content.add(outLayerContentPair);
        
        
        
        // Backprop Properties:
        String subSec2_header = "Backpropagation";
        ArrayList<String[]> subSec2_content = new ArrayList<>();
        
        
        String[] errorThresholdContentPair = new String[2];
        errorThresholdContentPair[0] = "Error Threshold:";
        errorThresholdContentPair[1] = ""+configurator.getErrorThreeshold();
        subSec2_content.add(errorThresholdContentPair);
        
        String[] learningRateContentPair = new String[2];
        learningRateContentPair[0] = "Learning Rate:";
        learningRateContentPair[1] = ""+configurator.getLearningRate();
        subSec2_content.add(learningRateContentPair);
        
        String[] maxIterationsContentPair = new String[2];
        maxIterationsContentPair[0] = "Epochs:";
        maxIterationsContentPair[1] = ""+configurator.getMaxNumberOfIterations();
        subSec2_content.add(maxIterationsContentPair);
        
        
        
        
        Object[] wrapper1 = new Object[2];
        wrapper1[0] = subSec1_header;
        wrapper1[1] = subSec1_content;
        
        Object[] wrapper2 = new Object[2];
        wrapper2[0] = subSec2_header;
        wrapper2[1] = subSec2_content;
        
        ArrayList<Object[]> retData = new ArrayList<>();
        retData.add(wrapper1);
        retData.add(wrapper2);
        
        return retData;
    }
}
