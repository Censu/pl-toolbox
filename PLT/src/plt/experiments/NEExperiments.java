/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.experiments;

import javafx.application.Application;
import javafx.stage.Stage;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.functions.MathematicalFunction;
import plt.dataset.experiment.ExperimentDataset;
import plt.dataset.preprocessing.Numeric;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.functions.LinearFunction;
import plt.functions.QuadraticFuncion;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.GA.ParentSelection;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOver;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOverType;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.GaussianMutation;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.Invertion;
import plt.plalgorithm.neruoevolution.GA.parentselections.RouletteWheelSelection;
import plt.plalgorithm.neruoevolution.NE.ActivationFunction;
import plt.plalgorithm.neruoevolution.NE.Linear;
import plt.plalgorithm.neruoevolution.NE.Sigmond;
import plt.plalgorithm.neruoevolution.PLNeuroEvolution;
import plt.plalgorithm.neruoevolution.PLNeuroEvolutionConfigurator;
import plt.report.Report;
import plt.validator.Validator;
import plt.validator.examples.SplitValidation;

/**
 *
 * @author luca
 */
public class NEExperiments extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        long a, b;

        int functionValues[][] = new int[][]{new int[]{3, 2, -7, -1, 4},
            new int[]{1, 8, -6, -7, -1, 11, 3, 15, 7, -1},
            new int[]{14, 4, -8, -5, 1, -9, -1, -15, -8, 2, +8, 3, -4, -12, 5}};


        for (int i = 0; i < 3; i++) {

            a = System.currentTimeMillis() / 1000;
            testNE(100, 2, functionValues[i].length, new LinearFunction(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

            a = System.currentTimeMillis() / 1000;
            testNE(100, 10, functionValues[i].length, new LinearFunction(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

            a = System.currentTimeMillis() / 1000;
            testNE(500, 10, functionValues[i].length, new LinearFunction(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

            a = System.currentTimeMillis() / 1000;
            testNE(1000, 5, functionValues[i].length, new LinearFunction(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

            a = System.currentTimeMillis() / 1000;
            testNE(100, 2, functionValues[i].length, new QuadraticFuncion(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

            a = System.currentTimeMillis() / 1000;
            testNE(100, 10, functionValues[i].length, new QuadraticFuncion(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

            a = System.currentTimeMillis() / 1000;
            testNE(500, 10, functionValues[i].length, new QuadraticFuncion(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

            a = System.currentTimeMillis() / 1000;
            testNE(1000, 5, functionValues[i].length, new QuadraticFuncion(functionValues[i]), 5, 20);
            b = System.currentTimeMillis() / 1000;
            System.out.println("done:" + (b - a));

        }


    }

    public static void testNE(int numberOfReports, int numberOfObjects,
            int numberOfFeatures, MathematicalFunction function, final int hiddenLayer, int traials) {


        System.out.println("--- " + numberOfReports + " reports, " + numberOfObjects
                + " objects, " + numberOfFeatures + " feature -----");

        System.out.println("algorithm: NE");
        System.out.println("function: " + function);

        for (int i = 0; i < traials; i++) {

            ExperimentDataset dataSet = new ExperimentDataset(numberOfReports, numberOfObjects, numberOfFeatures, function);

            PreprocessingOperation[] po = new PreprocessingOperation[numberOfFeatures];
            for (int j = 0; j < numberOfFeatures; j++) {
                po[j] = new Numeric(dataSet, j);
            }

            TrainableDataSet trainableDataSet = new PreprocessedDataSet(dataSet, po);


            Validator v = new SplitValidation(20);
            PLNeuroEvolution bp = new PLNeuroEvolution(trainableDataSet, new PLNeuroEvolutionConfigurator() {
                @Override
                public GeneticAlgorithmConfigurator getGeneticAlgorithmConfigurator() {
                    return new GeneticAlgorithmConfigurator() {
                        
                        @Override
                        public int getIterations() {
                            return 50;
                        }
                        
                        @Override
                        public int getPopulationSize() {
                            return 200;
                        }

                        @Override
                        public int getNumberOfParents() {
                            return 40;
                        }

                        @Override
                        public int getElitSize() {
                            return 10;
                        }

                        @Override
                        public ParentSelection getParentSelection() {
                            return new RouletteWheelSelection();
                        }

                        @Override
                        public CrossOver getCrossOver() {
                            return new CrossOver(0.8, CrossOverType.UNIFORM);
                        }

                        @Override
                        public GaussianMutation getMutation() {
                            return new GaussianMutation(0.1);
                        }

                        @Override
                        public Invertion getInvertion() {
                            return new Invertion(0);
                        }
                    };
                }

                @Override
                public int iterations() {
                    //return 150;
                    return 200;
                }

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
            });

            Report r = bp.createModelWithValidation(v);
            System.out.println((i + 1) + " of " + traials + " accuracy:" + r.getAVGAccuracy());


        }

    }
}
