package plt.validator.examples;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import plt.dataset.DataSet;
import plt.dataset.TrainableDataSet;
import plt.model.Model;
import plt.plalgorithm.PLAlgorithm;
import plt.report.Report;
import plt.utils.Preference;
import plt.validator.Validator;

/**
 *
 * @author luca
 */
public class KFoldCV extends Validator {
    public int k;
    
    public KFoldCV(int k) {
        this.k = k;
    }
    

    @Override
    public Report runWithValidation(PLAlgorithm algorithm) {
        Report report = new Report();

        
        TrainableDataSet originalDataSet = algorithm.getDataset();
        List<Set<Integer>> groups = KFoldCV.createGroups(originalDataSet, this.k);
                
        for (int i=0; i<this.k;i++) {
            Logger.getLogger("plt.logger").log(Level.INFO, "KFoldCV ["+(i+1)+"/"+k+"]");

            TrainableDataSet validationDataSet = originalDataSet.subSet(groups.get(i));
            
            Set<Integer> inputSet = new HashSet<>();
            for (int j=0; j<this.k;j++) {
                if (j!=i) {
                    inputSet.addAll(groups.get(j));
                }
            }
            
           TrainableDataSet candidateDataSet = originalDataSet.subSet(inputSet);
           
           algorithm.setDataSet(candidateDataSet);
           Model model = algorithm.createModel();
           
           double correctness = 0;
           for (int z=0; z<validationDataSet.getNumberOfPreferences(); z++) {
               Preference instance = validationDataSet.getPreference(z);
               if (model.preference(instance.getPreferred(), instance.getOther())) {
                   correctness++;
               }
           }
           correctness /= validationDataSet.getNumberOfPreferences();
           
           report.addExperimentResult(model, correctness);

        }

        return report;
        
    }
    
    protected static List<Set<Integer>> createGroups(TrainableDataSet t, int k) {
        List<Set<Integer>> atomicGroups = t.atomicGroups();
        Collections.sort(atomicGroups, new Comparator<Set<Integer>>() {

            @Override
            public int compare(Set<Integer> t, Set<Integer> t1) {
                return t.size() - t1.size();
            }
        });
        
        List<Set<Integer>> groups = new ArrayList<>();
        int[] groupsSize = new int[k];        

        for (int i=0;i<k; i++)
            groups.add(new HashSet<Integer>());

        
        for (Set<Integer> s : atomicGroups) {
            int candidate = 0;
            for (int i=0; i<k; i++)
                if (groupsSize[i] < groupsSize[candidate]) candidate = i;
            
            groupsSize[candidate] += s.size();
            groups.get(candidate).addAll(s);
        }
        
        
        return groups;
    }
    
    @Override
    public String toString() {
        return "KFoldCV: {k:"+this.k+"}";
    }

}
