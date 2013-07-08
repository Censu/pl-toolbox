
package plt.report;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;
import plt.plalgorithm.neruoevolution.NE.SimpleNeuralNetwork;
import plt.utils.TimeHelper;

public class NNModelFileData extends ModelFileData implements Serializable
{
    String algorithm;
    
    ExpMetaData experiment_metadata;
    ExpDatasetData experiment_dataset;
    
    
    // Feature Config Data.
    String[] selected_features;
    PreprocessingInfo[] preprocessingInfo;
    
    
    // NN Alg Specifics.
    NeuralNetworkInfo neuralNetworkInfo;
    
    
    // para_algName == Backpropagation or NeuroEvolution.
    public NNModelFileData(String para_fileName,
                           String para_algName,
                           SimpleNeuralNetwork para_network,
                           TrainableDataSet para_dataSet,
                           SelectedFeature para_selF,
                           Experiment para_exp,
                           double para_accResult_specificModel,
                           double para_accResult_averageOverFolds)
    {
        
        // Create object which will be stored to file.
        
        algorithm = para_algName;
        
        experiment_metadata = new ExpMetaData(para_fileName,
                                              "dd/MM/yyyy   HH:mm:ss",
                                              para_exp.expStartTimestampProperty().get(),
                                              para_exp.expCompleteTimestampProperty().get());
        
        experiment_dataset = new ExpDatasetData(para_exp.idataProperty().get().getAbsolutePath(),
                                                para_exp.orderProperty().get().getAbsolutePath(),
                                                para_accResult_specificModel,
                                                para_accResult_averageOverFolds);
        
        
        // Selected Features (ANN inputs)
        Object[] selFeatData = this.createSFInfo(para_exp, para_selF);
        int[] selFeatIDs = (int[]) selFeatData[0];
        String[] selFeatNames = (String[]) selFeatData[1];
        selected_features = selFeatNames;
        preprocessingInfo = this.createPreproInfo(para_exp, selFeatIDs, selFeatNames);
        
        
        // Neural Network Info.
        neuralNetworkInfo = new NeuralNetworkInfo(para_network,para_dataSet,selFeatNames);
    }
    
    
    
    private class NeuralNetworkInfo
    {
        NeuralNetLayer[] mlpLayers;
                
        
        public NeuralNetworkInfo(SimpleNeuralNetwork para_neuralNet,
                                 TrainableDataSet para_dataset,
                                 String[] para_selFeatureNames)
        {
            int[] networkTopology = para_neuralNet.topology;
            double[] networkWeights = para_neuralNet.weights;
            mlpLayers = new NeuralNetLayer[networkTopology.length];
            
            int nxtAvailableNeuronID = 0;
            
            int currWeightArrIndex = 0;
            
            int[] prevLayerNeuronIDs = null;
            int[] currLayerNeuronIDs = null;
            
            for(int i=0; i<networkTopology.length; i++)
            {
                NeuralNetLayer nwLayer;
                String layerName = "Layer";
                if(i == 0)
                {
                    layerName = "Input Layer";
                }
                else if(i == (networkTopology.length-1))
                {
                    layerName = "Output Layer";
                }
                else
                {
                    layerName = "Hidden Layer "+(i-1);
                }
                nwLayer = new NeuralNetLayer(layerName,networkTopology[i]);
                
                currLayerNeuronIDs = new int[networkTopology[i]];                
                
                for(int j=0; j<networkTopology[i]; j++)
                {
                    double[] neuronWeights = null;
                    String[] incConNeurons = null;
                    
                    if(i == 0)
                    {
                        // Input Layer:
                        neuronWeights = null;
                        incConNeurons = new String[1];
                        incConNeurons[0] = para_selFeatureNames[j];
                    }
                    else
                    {
                        // Hidden or Output Layer:
                        
                        int numIncConnections = networkTopology[i-1] + 1;

                        neuronWeights = null;
                        if(i > 0)
                        {
                            neuronWeights = new double[numIncConnections];

                            for(int wCounter=0; wCounter<neuronWeights.length; wCounter++)
                            {
                                neuronWeights[wCounter] = networkWeights[currWeightArrIndex];
                                currWeightArrIndex++;
                            }
                        }

                        incConNeurons = new String[numIncConnections];
                        for(int conCounter=0; conCounter<numIncConnections; conCounter++)
                        {
                            String valItem = "ERROR_VAL";

                            if(conCounter == (numIncConnections-1))
                            {
                                incConNeurons[conCounter] = "Bias";
                            }
                            else
                            {                             
                                incConNeurons[conCounter] = ""+prevLayerNeuronIDs[conCounter];   
                            }
                        }
                    }
                    
                    
                    
                    currLayerNeuronIDs[j] = nxtAvailableNeuronID;
                    
                            
                    NeuronInfo nwNeuronInf = new NeuronInfo(nxtAvailableNeuronID, neuronWeights, incConNeurons);
                    nxtAvailableNeuronID++;                   
                    
                    nwLayer.addNeuronEntry(j, nwNeuronInf);
                }
                
                prevLayerNeuronIDs = currLayerNeuronIDs;
                
                mlpLayers[i] = nwLayer;
            }
        }
               
        class NeuralNetLayer
        {
            String layerName;
            NeuronInfo[] layerNeurons;
            
            public NeuralNetLayer(String para_layerName, int para_numOfNeurons)
            {
                layerName = para_layerName;
                layerNeurons = new NeuronInfo[para_numOfNeurons];
            }
            
            public void addNeuronEntry(int para_neuronPosInLayer, NeuronInfo para_nwNeuronEntry)
            {
                layerNeurons[para_neuronPosInLayer] = para_nwNeuronEntry;
            }
        }
        
        class NeuronInfo
        {
            int neuronID;
            double[] weights;
            String[] incomingConnNeurons;
            
            public NeuronInfo(int para_NeuronID, double[] para_Weights, String[] para_IncConNeurons)
            {
                neuronID = para_NeuronID;
                weights = para_Weights;
                incomingConnNeurons = para_IncConNeurons;
            }
        }
    }   
}


