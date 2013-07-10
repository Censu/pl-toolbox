package plt.plalgorithm.neruoevolution.NE;

import java.util.logging.Level;
import java.util.logging.Logger;
import plt.plalgorithm.neruoevolution.GA.DNA;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithm;
import plt.plalgorithm.neruoevolution.GA.GeneticEncoder;

/**
 *
 * @author luca
 */

public class NeuroEvolutionAlgorithm {
    private NeuroEvolutionAlgorithmConfigurator configurator;
    protected GeneticAlgorithm ga;
    private NetworkEvalutaor evaluator;

     
    public NeuroEvolutionAlgorithm(NeuroEvolutionAlgorithmConfigurator configurator,NetworkEvalutaor networkEvaluator) {
        
        this.configurator = configurator;
        this.evaluator = networkEvaluator;
        
        final SimpleNeuralNetwork n = new SimpleNeuralNetwork(this.configurator.getTopology(), this.configurator.getActivationsFunctions());
        final NetworkEvalutaor e = this.evaluator;
        this.ga = new GeneticAlgorithm(this.configurator.getGeneticAlgorithmConfigurator(), new GeneticEncoder() {

            @Override
            public Object decode(DNA dna) {
                double[] weights = new double[dna.vector.length];
                for (int i=0; i<weights.length;i++) {
                    weights[i] = (dna.vector[i]*10)-5;
                }
                
                n.setWeights(weights);
                return n;
            }

            @Override
            public double evaluationFunction(DNA dna) {
                double[] weights = new double[dna.vector.length];
                for (int i=0; i<weights.length;i++) {
                    weights[i] = (dna.vector[i]*10)-5;
                }
                
                n.setWeights(weights);
                
                try {
                    return e.evaluate((SimpleNeuralNetwork)n.clone());
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException();
                }
            }

            @Override
            public int dnaSize() {
                return n.getNumberOfWeights();
            }
        });
    }
    
        
    public void runUntillReach(double threshold) {
        Logger.getLogger("plt.logger").log(Level.INFO, "run NeuroEvolution with threshold "+ threshold);
        ga.runUntillReach(threshold);
    }
    
    public void runFor(int times) throws InterruptedException {
        Logger.getLogger("plt.logger").log(Level.INFO, "run NeuroEvolution for "+ times+" iterations ");
        ga.runFor(times);
    }
    
    public SimpleNeuralNetwork getNeuralNetuork() {
        return (SimpleNeuralNetwork)ga.getResult();
    }

    
    
    
}
