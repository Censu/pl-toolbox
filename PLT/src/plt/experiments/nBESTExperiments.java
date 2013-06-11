/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.experiments;

import javafx.application.Application;
import javafx.stage.Stage;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.dataset.experiment.ExperimentDataset;
import plt.dataset.preprocessing.Numeric;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.featureselection.SelectedFeature;
import plt.featureselection.examples.NBest;
import plt.featureselection.examples.NBestConfigurator;
import plt.functions.LinearFunction;
import plt.functions.MathematicalFunction;
import plt.gui.configurators.PLBackPropagationConfigurator;
import plt.plalgorithm.backpropagation.PLBackPropagation;
import plt.plalgorithm.neruoevolution.NE.ActivationFunction;
import plt.plalgorithm.neruoevolution.NE.Linear;
import plt.plalgorithm.neruoevolution.NE.Sigmond;
import plt.report.Report;
import plt.validator.Validator;
import plt.validator.examples.NoValidation;
import plt.validator.examples.SplitValidation;

/**
 *
 * @author luca
 */
public class nBESTExperiments extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        long a, b;

        LinearFunction function = new LinearFunction(new int[]{1, 8, -6, -7, -1, 11});

        a = System.currentTimeMillis() / 1000;
        testNBest(100, 2, 10, function, 5, 20);
        b = System.currentTimeMillis() / 1000;
        System.out.println("done:" + (b - a));

        /*a = System.currentTimeMillis() / 1000;
        testNBest(100, 10, 10, function, 5, 5);
        b = System.currentTimeMillis() / 1000;
        System.out.println("done:" + (b - a));

        a = System.currentTimeMillis() / 1000;
        testNBest(500, 10, 10, function, 5, 5);
        b = System.currentTimeMillis() / 1000;
        System.out.println("done:" + (b - a));

        a = System.currentTimeMillis() / 1000;
        testNBest(1000, 5, 10, function, 5, 5);
        b = System.currentTimeMillis() / 1000;
        System.out.println("done:" + (b - a));*/


    }

    public static void testNBest(int numberOfReports, int numberOfObjects,
            int numberOfFeatures, MathematicalFunction function, final int hiddenLayer, int traials) {


        System.out.println("--- " + numberOfReports + " reports, " + numberOfObjects
                + " objects, " + numberOfFeatures + " feature -----");

        System.out.println("algorithm: BackPropagation");
        System.out.println("function: " + function);
        
        double correctness = 0;

        for (int i = 0; i < traials; i++) {

            ExperimentDataset dataSet = new ExperimentDataset(numberOfReports, numberOfObjects, numberOfFeatures, function);

            PreprocessingOperation[] po = new PreprocessingOperation[numberOfFeatures];
            for (int j = 0; j < numberOfFeatures; j++) {
                po[j] = new Numeric(dataSet, j);
            }

            TrainableDataSet trainableDataSet = new PreprocessedDataSet(dataSet, po);


            PLBackPropagationConfigurator bpC = new PLBackPropagationConfigurator() {
                @Override
                public int[] getTopology(int inputSize) {
                    if (hiddenLayer > 0) {
                        return new int[]{inputSize, hiddenLayer, 1};
                    } else {
                        return new int[]{inputSize, 1};
                    }
                }

                @Override
                public ActivationFunction[] getActivationsFunctions() {
                    if (hiddenLayer > 0) {
                        return new ActivationFunction[]{new Sigmond(), new Linear()};
                    } else {
                        return new ActivationFunction[]{new Linear()};
                    }


                }

                @Override
                public double getLearningRate() {
                    return 0.01;
                }

                @Override
                public double getErrorThreeshold() {
                    return 0.01;
                }

                @Override
                public int getMaxNumberOfIterations() {
                    return 15000;
                }
            };

            PLBackPropagationConfigurator AFSbpC = new PLBackPropagationConfigurator() {
                @Override
                public int[] getTopology(int inputSize) {
                    if (hiddenLayer > 0) {
                        return new int[]{inputSize, hiddenLayer, 1};
                    } else {
                        return new int[]{inputSize, 1};
                    }
                }

                @Override
                public ActivationFunction[] getActivationsFunctions() {
                    if (hiddenLayer > 0) {
                        return new ActivationFunction[]{new Sigmond(), new Linear()};
                    } else {
                        return new ActivationFunction[]{new Linear()};
                    }


                }

                @Override
                public double getLearningRate() {
                    return 0.01;
                }

                @Override
                public double getErrorThreeshold() {
                    return 0.01;
                }

                @Override
                public int getMaxNumberOfIterations() {
                   return 1000;
                }
            };


            Validator v = new SplitValidation(20);
            NBest nbest = new NBest(new NBestConfigurator() {
                @Override
                public int getN() {
                    return 6;
                }
            });
            
            SelectedFeature selection = new SelectedFeature();
            selection.setSelected(0, 5);

            nbest.run(new NoValidation(), new PLBackPropagation(trainableDataSet, AFSbpC));
            SelectedFeature selectedFeatures = nbest.getResult();
            
            if (selection.equals(selection))
                correctness += 1;
            
            System.out.println((i + 1) + " of " + traials + "complited");
        }
        
        System.out.println("result "+correctness/traials);
    }
}