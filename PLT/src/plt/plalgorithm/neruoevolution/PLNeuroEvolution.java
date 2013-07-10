/*                   GNU LESSER GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.


  This version of the GNU Lesser General Public License incorporates
the terms and conditions of version 3 of the GNU General Public
License, supplemented by the additional permissions listed below.

  0. Additional Definitions.

  As used herein, "this License" refers to version 3 of the GNU Lesser
General Public License, and the "GNU GPL" refers to version 3 of the GNU
General Public License.

  "The Library" refers to a covered work governed by this License,
other than an Application or a Combined Work as defined below.

  An "Application" is any work that makes use of an interface provided
by the Library, but which is not otherwise based on the Library.
Defining a subclass of a class defined by the Library is deemed a mode
of using an interface provided by the Library.

  A "Combined Work" is a work produced by combining or linking an
Application with the Library.  The particular version of the Library
with which the Combined Work was made is also called the "Linked
Version".

  The "Minimal Corresponding Source" for a Combined Work means the
Corresponding Source for the Combined Work, excluding any source code
for portions of the Combined Work that, considered in isolation, are
based on the Application, and not on the Linked Version.

  The "Corresponding Application Code" for a Combined Work means the
object code and/or source code for the Application, including any data
and utility programs needed for reproducing the Combined Work from the
Application, but excluding the System Libraries of the Combined Work.

  1. Exception to Section 3 of the GNU GPL.

  You may convey a covered work under sections 3 and 4 of this License
without being bound by section 3 of the GNU GPL.

  2. Conveying Modified Versions.

  If you modify a copy of the Library, and, in your modifications, a
facility refers to a function or data to be supplied by an Application
that uses the facility (other than as an argument passed when the
facility is invoked), then you may convey a copy of the modified
version:

   a) under this License, provided that you make a good faith effort to
   ensure that, in the event an Application does not supply the
   function or data, the facility still operates, and performs
   whatever part of its purpose remains meaningful, or

   b) under the GNU GPL, with none of the additional permissions of
   this License applicable to that copy.

  3. Object Code Incorporating Material from Library Header Files.

  The object code form of an Application may incorporate material from
a header file that is part of the Library.  You may convey such object
code under terms of your choice, provided that, if the incorporated
material is not limited to numerical parameters, data structure
layouts and accessors, or small macros, inline functions and templates
(ten or fewer lines in length), you do both of the following:

   a) Give prominent notice with each copy of the object code that the
   Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the object code with a copy of the GNU GPL and this license
   document.

  4. Combined Works.

  You may convey a Combined Work under terms of your choice that,
taken together, effectively do not restrict modification of the
portions of the Library contained in the Combined Work and reverse
engineering for debugging such modifications, if you also do each of
the following:

   a) Give prominent notice with each copy of the Combined Work that
   the Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the Combined Work with a copy of the GNU GPL and this license
   document.

   c) For a Combined Work that displays copyright notices during
   execution, include the copyright notice for the Library among
   these notices, as well as a reference directing the user to the
   copies of the GNU GPL and this license document.

   d) Do one of the following:

       0) Convey the Minimal Corresponding Source under the terms of this
       License, and the Corresponding Application Code in a form
       suitable for, and under terms that permit, the user to
       recombine or relink the Application with a modified version of
       the Linked Version to produce a modified Combined Work, in the
       manner specified by section 6 of the GNU GPL for conveying
       Corresponding Source.

       1) Use a suitable shared library mechanism for linking with the
       Library.  A suitable mechanism is one that (a) uses at run time
       a copy of the Library already present on the user's computer
       system, and (b) will operate properly with a modified version
       of the Library that is interface-compatible with the Linked
       Version.

   e) Provide Installation Information, but only if you would otherwise
   be required to provide such information under section 6 of the
   GNU GPL, and only to the extent that such information is
   necessary to install and execute a modified version of the
   Combined Work produced by recombining or relinking the
   Application with a modified version of the Linked Version. (If
   you use option 4d0, the Installation Information must accompany
   the Minimal Corresponding Source and Corresponding Application
   Code. If you use option 4d1, you must provide the Installation
   Information in the manner specified by section 6 of the GNU GPL
   for conveying Corresponding Source.)

  5. Combined Libraries.

  You may place library facilities that are a work based on the
Library side by side in a single library together with other library
facilities that are not Applications and are not covered by this
License, and convey such a combined library under terms of your
choice, if you do both of the following:

   a) Accompany the combined library with a copy of the same work based
   on the Library, uncombined with any other library facilities,
   conveyed under the terms of this License.

   b) Give prominent notice with the combined library that part of it
   is a work based on the Library, and explaining where to find the
   accompanying uncombined form of the same work.

  6. Revised Versions of the GNU Lesser General Public License.

  The Free Software Foundation may publish revised and/or new versions
of the GNU Lesser General Public License from time to time. Such new
versions will be similar in spirit to the present version, but may
differ in detail to address new problems or concerns.

  Each version is given a distinguishing version number. If the
Library as you received it specifies that a certain numbered version
of the GNU Lesser General Public License "or any later version"
applies to it, you have the option of following the terms and
conditions either of that published version or of any later version
published by the Free Software Foundation. If the Library as you
received it does not specify a version number of the GNU Lesser
General Public License, you may choose any version of the GNU Lesser
General Public License ever published by the Free Software Foundation.

  If the Library as you received it specifies that a proxy can decide
whether future versions of the GNU Lesser General Public License shall
apply, that proxy's public statement of acceptance of any version is
permanent authorization for you to choose that version for the
Library.*/

package plt.plalgorithm.neruoevolution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import plt.dataset.TrainableDataSet;
import plt.featureselection.SelectedFeature;
import plt.gui.Experiment;
import plt.json.JsonObjIO;
import plt.model.Model;
import plt.plalgorithm.PLAlgorithm;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOverType;
import plt.plalgorithm.neruoevolution.NE.*;
import plt.report.NNModelFileData;
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
    public Model run() throws InterruptedException {
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

            /*@Override
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
            }*/
            
            
            
            @Override
            public void save(File file, Experiment experiment, double accResult_specificModel, double accResult_averageOverFolds) throws IOException{
                try {
                 
                    // Construct file data for chosen model.
                    NNModelFileData objToStore = new NNModelFileData(file.getName(),
                                                                     "NeuroEvolution",
                                                                     network,
                                                                     this.getDataSet(),
                                                                     this.selectedFeature(),
                                                                     experiment,
                                                                     accResult_specificModel,
                                                                     accResult_averageOverFolds);        
                    
                    // Store data to file as JSON.
                    JsonObjIO jsonRW = new JsonObjIO();
                    jsonRW.writeObjToFile(file.getAbsolutePath(), objToStore);
                }
                catch (Exception ex) {
                   Logger.getLogger(PLNeuroEvolution.class.getName()).log(Level.SEVERE,null,ex);
                   
                   throw ex;
                }
            }
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