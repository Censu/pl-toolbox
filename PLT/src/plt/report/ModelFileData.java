
package plt.report;

import java.io.Serializable;
import java.util.ArrayList;
import plt.dataset.TrainableDataSet;
import plt.dataset.preprocessing.MinMax;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.dataset.preprocessing.ZScore;
import plt.dataset.sushireader.SushiFormatDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;
import plt.plalgorithm.neruoevolution.NE.SimpleNeuralNetwork;

public class ModelFileData implements Serializable
{
    // The selected features that were used as inputs into the neural network.
    String[] inputs;
    
    PreprocessingInfo[] preprocessingInfo;
    NeuralNetworkInfo neuralNetworkInfo;
    
    
    // para_algName == Backpropagation or NeuroEvolution.
    public ModelFileData(String para_algName,
                         SimpleNeuralNetwork para_network,
                         TrainableDataSet para_dataSet,
                         SelectedFeature para_selF,
                         Experiment para_exp)
    {
        
        // Create object which will be stored to file.
        
        
        // Selected Features (ANN inputs)
        int[] selF_ids = para_selF.getSelectedFeatures();
        inputs = new String[selF_ids.length];
        for(int i=0; i<selF_ids.length; i++)
        {
            inputs[i] = para_exp.dataSetProperty().get().getFeatureName(selF_ids[i]);
        }
        
        // Feature Preprocessing Data
        preprocessingInfo = new PreprocessingInfo[selF_ids.length];
        for(int i=0; i<selF_ids.length; i++)
        {
            PreprocessingOperation preProOp = para_exp.preprocessingOperationsProperty().get()[selF_ids[i]];
            
            PreprocessingInfo nwPreProInfo = new PreprocessingInfo();
            nwPreProInfo.featureName = inputs[i];
            nwPreProInfo.preprocessingTypeName = preProOp.getOperationName();
            
            if(preProOp instanceof MinMax)
            {
                MinMax castMinMax = (MinMax) preProOp;
                double userGivenMin = castMinMax.getMin();
                double userGivenMax = castMinMax.getMax();
                
                SushiFormatDataSet castDataSet = (SushiFormatDataSet) castMinMax.getDataSet();
                double featureMin = (double) castDataSet.getMinValForFeature(selF_ids[i]);
                double featureMax = (double) castDataSet.getMaxValForFeature(selF_ids[i]);
                
                nwPreProInfo.addNwEntry("User Min", userGivenMin);
                nwPreProInfo.addNwEntry("User Max", userGivenMax);
                nwPreProInfo.addNwEntry("Feature Min", featureMin);
                nwPreProInfo.addNwEntry("Feature Max", featureMax);               
            }
            else if(preProOp instanceof ZScore)
            {
                ZScore castZScore = (ZScore) preProOp;
                double average = castZScore.getAverage();
                double stdev = castZScore.getStdev();
                
                nwPreProInfo.addNwEntry("Average",average);
                nwPreProInfo.addNwEntry("Stdev", stdev);
            }
        }
        
        // Neural Network Info.
        NeuralNetworkInfo nnInf = new NeuralNetworkInfo(para_network,para_dataSet,para_selF);
    }
    
    private class PreprocessingInfo
    {
        String featureName;
        String preprocessingTypeName;
        
        ArrayList<DataWrapper> otherData;
        
        public PreprocessingInfo()
        {
            otherData = new ArrayList<DataWrapper>();
        }
        
        public void addNwEntry(String para_AttributeKey, Object para_StoredObj)
        {
            DataWrapper nwDWrap = new DataWrapper(para_AttributeKey,para_StoredObj);
            otherData.add(nwDWrap);
        }
        
        class DataWrapper
        {
            String key;
            Object data;
            
            public DataWrapper(String para_Key, Object para_StoredObj)
            {
                key = para_Key;
                data = para_StoredObj;
            }
        }
    }
    
    private class NeuralNetworkInfo
    {
        NeuralNetLayer[] mlpLayers;
                
        
        public NeuralNetworkInfo(SimpleNeuralNetwork para_neuralNet,
                                 TrainableDataSet para_dataset,
                                 SelectedFeature para_selFeatures)
        {
            int[] networkTopology = para_neuralNet.topology;
            double[] networkWeights = para_neuralNet.weights;
            mlpLayers = new NeuralNetLayer[networkTopology.length];
            
            int nxtAvailableNeuronID = 0;
            
            int currWeightArrIndex = 0;
            
            int[] prevLayerNeuronIDs;
            
            for(int i=0; i<networkTopology.length; i++)
            {
                NeuralNetLayer nwLayer = new NeuralNetLayer(networkTopology[i]);
                prevLayerNeuronIDs = new int[networkTopology[i]];                
                
                for(int j=0; j<networkTopology[i]; j++)
                {
                    int numIncConnections = networkTopology[i-1] + 1;
                    
                    double[] neuronWeights = null;
                    if(i > 0)
                    {
                        neuronWeights = new double[numIncConnections];
                        
                        for(int wCounter=0; wCounter<neuronWeights.length; wCounter++)
                        {
                            neuronWeights[wCounter] = networkWeights[currWeightArrIndex];
                            currWeightArrIndex++;
                        }
                    }
                        
                    String[] incConNeurons = new String[numIncConnections];
                    for(int conCounter=0; conCounter<numIncConnections; conCounter++)
                    {
                        String valItem = "ERROR_VAL";
                        
                        if(conCounter == (numIncConnections-1))
                        {
                            incConNeurons[conCounter] = "Bias";
                        }
                        else
                        {
                            if(i==0)
                            {
                                incConNeurons[conCounter] = ""+para_selFeatures.getSelectedFeatures()[conCounter];
                            }
                            else
                            {                                
                                incConNeurons[conCounter] = ""+prevLayerNeuronIDs[conCounter];
                            }
                        }
                    }
                    
                    
                    
                    prevLayerNeuronIDs[j] = nxtAvailableNeuronID;
                            
                    NeuronInfo nwNeuronInf = new NeuronInfo(nxtAvailableNeuronID, neuronWeights, incConNeurons);
                    nxtAvailableNeuronID++;                   
                    
                }
            }
        }
               
        class NeuralNetLayer
        {
            NeuronInfo[] layerNeurons;
            
            public NeuralNetLayer(int para_numOfNeurons)
            {
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


