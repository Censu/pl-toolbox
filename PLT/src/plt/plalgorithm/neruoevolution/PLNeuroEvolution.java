package plt.plalgorithm.neruoevolution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.model.Model;
import plt.plalgorithm.PLAlgorithm;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOverType;
import plt.plalgorithm.neruoevolution.NE.*;
import plt.utils.Preference;


/**
 *
 * @author luca
 */
public class PLNeuroEvolution extends PLAlgorithm {
    private PLNeuroEvolutionConfigurator configurator;
    private NeuroEvolutionAlgorithm ne;
    private NeuroEvolutionAlgorithmConfigurator nec;
    private NetworkEvalutaor nee;
    

    public PLNeuroEvolution(TrainableDataSet n, PLNeuroEvolutionConfigurator configurator) {
        super(n);
        final PLNeuroEvolution self = this;

        this.configurator = configurator;
        this.nec = new NeuroEvolutionAlgorithmConfigurator(this.configurator.getGeneticAlgorithmConfigurator()) {

            @Override
            public int[] getTopology() {
                return self.configurator.getTopology(self.getFeatureSelection().getSize());
            }

            @Override
            public ActivationFunction[] getActivationsFunctions() {
                return self.configurator.getActivationsFunctions();
            }
        };
        
        this.nee = new NetworkEvalutaor() {
            @Override
            public double evaluate(SimpleNeuralNetwork network) {
                
                double fitness = 0;

                TrainableDataSet dataSet = self.getDataset();
                
                Hashtable<Integer,Double> h = new Hashtable<>();
                for (int i=0; i<dataSet.getNumberOfObjects(); i++) {
                    double[] featuresOther = self.getFeatureSelection().select( dataSet.getFeatures(i));
                    network.setInputs(featuresOther);
                    h.put(i,network.getOutputs()[0]);
                }
                    
                for (int i =0; i< dataSet.getNumberOfPreferences() ; i++) {
                    Preference instance = self.getDataset().getPreference(i);
                    double fPreferred = h.get(instance.getPreferred());
                    double fOther = h.get(instance.getOther());
                    
                    int epsilon = fOther > fPreferred ? 5 : 30;
                    double delta= plt.utils.Math.sigmoid(1, epsilon*(fPreferred-fOther)); 
                    
                    
                    fitness += delta;
                }
                                
                
                return fitness;
            }
        };

    }

    @Override
    public Model run() {
       Logger.getLogger("plt.logger").log(Level.INFO, "run PLNeuroEvolution");
       this.ne.runFor(this.configurator.iterations());
        final SimpleNeuralNetwork resultNetwork = ne.getNeuralNetuork(); 
        return modelForNetwork(resultNetwork, this.getDataset(), this.getFeatureSelection());

    }

    @Override
    protected Model beforeRun() {
        this.ne = new NeuroEvolutionAlgorithm(this.nec, nee);
        return modelForNetwork(ne.getNeuralNetuork(), this.getDataset(), this.getFeatureSelection());
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
                    //BufferedWriter out = new BufferedWriter( new FileWriter (new File(file, "NE"+date.getTime())));
                    BufferedWriter out = new BufferedWriter(new FileWriter(file));
                    out.write("NE#"+ Arrays.toString(network.weights)+ "#" +Arrays.toString(network.topology));
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(PLNeuroEvolution.class.getName()).log(Level.SEVERE, null, ex);
                    
                    throw ex;
                }
            }
            
            
            
            /*@Override
            public void save(File file, Experiment experiment) throws IOException{
                try {
                 
                    // Construct file data for chosen model.
                    ModelFileData objToStore = new ModelFileData("NeuroEvolution",
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

    public PLNeuroEvolutionConfigurator getConfigurator()
    {
        return configurator;
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
        
        
        
        // GA Properties:
        
        String subSec2_header = "GA Properties";
        ArrayList<String[]> subSec2_content = new ArrayList<>();
        
        GeneticAlgorithmConfigurator gaConfig = configurator.getGeneticAlgorithmConfigurator();
        
        String[] popContentPair = new String[2];
        popContentPair[0] = "Population:";
        popContentPair[1] = ""+gaConfig.getPopulationSize();
        subSec2_content.add(popContentPair);
        
        String[] crossoverRateContentPair = new String[2];
        crossoverRateContentPair[0] = "Crossover Probability:";
        crossoverRateContentPair[1] = ""+gaConfig.getCrossOver().getProbability();
        subSec2_content.add(crossoverRateContentPair);
        
        String[] crossoverTypeContentPair = new String[2];
        crossoverTypeContentPair[0] = "Crossover Type:";
        
        String typStr = "";
        if(gaConfig.getCrossOver().getCrossOverType() == CrossOverType.ONEPOINT) { typStr = "OnePoint"; }
        else if(gaConfig.getCrossOver().getCrossOverType() == CrossOverType.TWOPOINT) { typStr = "TwoPoint"; }
        else if(gaConfig.getCrossOver().getCrossOverType() == CrossOverType.UNIFORM) { typStr = "Uniform"; }
        crossoverTypeContentPair[1] = typStr;
        subSec2_content.add(crossoverTypeContentPair);
        
        
        String[] mutationRateContentPair = new String[2];
        mutationRateContentPair[0] = "Mutation Probability:";
        mutationRateContentPair[1] = ""+gaConfig.getMutation().getProbability();
        subSec2_content.add(mutationRateContentPair);
        
        String[] numOfParentsContentPair = new String[2];
        numOfParentsContentPair[0] = "Num of Parents:";
        numOfParentsContentPair[1] = ""+gaConfig.getNumberOfParents();
        subSec2_content.add(numOfParentsContentPair);
        
        String[] parentSelectionContentPair = new String[2];
        parentSelectionContentPair[0] = "Parent Selection:";
        parentSelectionContentPair[1] = ""+gaConfig.getParentSelection().getSelectionName();
        subSec2_content.add(parentSelectionContentPair);
        
        String[] elitismSizeContentPair = new String[2];
        elitismSizeContentPair[0] = "Elitism Size:";
        elitismSizeContentPair[1] = ""+gaConfig.getElitSize();
        subSec2_content.add(elitismSizeContentPair);
        
        String[] iterationsContentPair = new String[2];
        iterationsContentPair[0] = "Generations:";
        iterationsContentPair[1] = ""+gaConfig.getIterations();
        subSec2_content.add(iterationsContentPair);
        
        
        
    
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