package plt.validator;

import plt.plalgorithm.PLAlgorithm;
import plt.report.Report;

/**
 *
 * @author luca
 */
public abstract class Validator {

    public abstract Report runWithValidation(PLAlgorithm algorithm);
    
}
