/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.plalgorithm.neruoevolution.GA;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luca
 */
public class GeneticAlgorithm {
    private GeneticAlgorithmConfigurator configurator;
    private GeneticEncoder encoder;
    protected Population population;
    private Object result;
    
    public GeneticAlgorithm (GeneticAlgorithmConfigurator configurator, GeneticEncoder encoder) {
        this.configurator = configurator;
        this.encoder = encoder;
        this.population = new Population(configurator.getPopulationSize(), this.encoder);
        this.result = this.population.get(0).getPhenotype();
    }
    
    public Object getResult() {
        return result;
    }
    
    public void runUntillReach(double threshold) {
        
      Logger.getLogger("plt.logger").log(Level.INFO, "run GeneticAlgorithm with threshold"+threshold);

      int i=0;
        while(population.getMaxFitness() < threshold) {
            i++;
            
            Logger.getLogger("plt.logger").log(Level.INFO, "generation ["+i+"]");
            Logger.getLogger("plt.logger").log(Level.INFO, "max fitness:"+population.getMaxFitness());

            
            population.produceNextGeneration(configurator.getParentSelection(), 
                    configurator.getNumberOfParents(), 
                    configurator.getElitSize(), 
                    configurator.getCrossOver(), 
                    configurator.getInvertion(),
                    configurator.getMutation());
        }
              
        result = population.getBestPhenotype();
                    
    }
    
    public void runFor(int times) {
        Logger.getLogger("plt.logger").log(Level.INFO, "run GeneticAlgorithm for "+times+" iterations");

        for (int i=0; i<times; i++) {

            population.produceNextGeneration(configurator.getParentSelection(), 
                    configurator.getNumberOfParents(), 
                    configurator.getElitSize(), 
                    configurator.getCrossOver(), 
                    configurator.getInvertion(),
                    configurator.getMutation());
            
            //Logger.getLogger("plt.logger").log(Level.INFO, "generation ["+(i+1)+"/"+times+"]");
            //Logger.getLogger("plt.logger").log(Level.INFO, "max fitness:"+population.getMaxFitness());

        }
         
        System.out.println("pop:"+population.getMaxFitness());

        result = population.getBestPhenotype();
    }

}
