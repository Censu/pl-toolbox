package plt.gui.configurators;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import plt.gui.Main;
import plt.gui.component.AdvanceTextField;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.GA.ParentSelection;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOver;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.CrossOverType;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.GaussianMutation;
import plt.plalgorithm.neruoevolution.GA.genticaloperators.Invertion;
import plt.plalgorithm.neruoevolution.GA.parentselections.RouletteWheelSelection;
import plt.plalgorithm.neruoevolution.NE.ActivationFunction;
import plt.plalgorithm.neruoevolution.NE.HyperbolicTangent;
import plt.plalgorithm.neruoevolution.NE.Linear;
import plt.plalgorithm.neruoevolution.NE.Sigmond;

/**
 *
 * @author luca
 */
public class PLNeuroEvolutionConfigurator implements plt.plalgorithm.neruoevolution.PLNeuroEvolutionConfigurator {

    
    private ArrayList<TextField> topology;
    private ArrayList<ChoiceBox> choiceBoxTopology;
    private GridPane annGridPane;
    private HBox annBtnPane;
    private Button btnAddHiddenLayer;
    private Button btnRemoveHiddenLayer;
    
    private TextField poulationSize;
    private TextField iterations;
    private TextField mutation;
    private TextField selectionSize;
    private TextField elitSize;
    private TextField crossover;
    private ChoiceBox crossoverType;
    private TextField invertion;
    private ChoiceBox parentsSelection;

    private static int parseOrFailWithZero(TextField t) {
        try {
            return Integer.parseInt(t.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private static double parseDoubleOrFailWithZero(TextField t) {
        try {
            return Double.parseDouble(t.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public PLNeuroEvolutionConfigurator() {
        
        // Section 1: ANN
        
        topology = new ArrayList<TextField>();
        choiceBoxTopology = new ArrayList<ChoiceBox>();
        
        

        
        // Section 2: Evolution
        
        poulationSize = new AdvanceTextField("[0-9]", "200");
        iterations = new AdvanceTextField("[0-9]", "50");
        selectionSize = new AdvanceTextField("[0-9]", "40");
        elitSize = new AdvanceTextField("[0-9]", "20");
        crossoverType = new ChoiceBox(FXCollections.observableArrayList("One point", "Two point", "Uniform"));
        crossover = new AdvanceTextField("[0-9.]", "0.8");
        crossoverType.getSelectionModel().selectLast();
        parentsSelection = new ChoiceBox(FXCollections.observableArrayList("Roulette wheel"));
        parentsSelection.getSelectionModel().selectFirst();
        invertion = new AdvanceTextField("[0-9.]", "0");
        mutation = new AdvanceTextField("[0-9.]", "0.1");
        
        poulationSize.setPrefWidth(30);
        iterations.setPrefWidth(30);
        selectionSize.setPrefWidth(30);
        elitSize.setPrefWidth(30);
        crossoverType.setPrefWidth(150);
        crossover.setPrefWidth(30);
        parentsSelection.setPrefWidth(150);
        invertion.setPrefWidth(30);
        mutation.setPrefWidth(30);
    }

    @Override
    public int[] getTopology(int inputSize)
    {
        int j = 0;
        for (int i = 0; i < topology.size(); i++) 
        {
            int n = parseOrFailWithZero(topology.get(i));
            if (n > 0) 
            {
                j++;
            }
        }
        int[] result = new int[j + 2];
        result[0] = inputSize;
        j = 1;
        for (int i = 0; i < topology.size(); i++)
        {
            int n = parseOrFailWithZero(topology.get(i));
            if (n > 0)
            {
                result[j++] = n;
            }
        }
        result[j] = 1;
        return result;
    }

    @Override
    public int iterations()
    {
        return parseOrFailWithZero(iterations);
    }

    @Override
    public GeneticAlgorithmConfigurator getGeneticAlgorithmConfigurator() {
        final PLNeuroEvolutionConfigurator self = this;
        return new GeneticAlgorithmConfigurator() {
            
            @Override
            public int getIterations() {
                return parseOrFailWithZero(self.iterations);
            }
            
            @Override
            public int getPopulationSize() {
                return parseOrFailWithZero(self.poulationSize);
            }

            @Override
            public int getNumberOfParents() {
                return parseOrFailWithZero(self.selectionSize);
            }

            @Override
            public int getElitSize() {
                return parseOrFailWithZero(self.elitSize);
            }

            @Override
            public ParentSelection getParentSelection() {
                int i = crossoverType.getSelectionModel().getSelectedIndex();
                ParentSelection result;

                switch (i) {
                    case 0:
                        result = new RouletteWheelSelection();
                        break;

                    default:
                        result = new RouletteWheelSelection();
                        break;
                }

                return result;
            }

            @Override
            public CrossOver getCrossOver() {
                int i = crossoverType.getSelectionModel().getSelectedIndex();
                CrossOver result;

                switch (i) {
                    case 0:
                        result = new CrossOver(parseDoubleOrFailWithZero(self.crossover), CrossOverType.ONEPOINT);
                        break;

                    case 1:
                        result = new CrossOver(parseDoubleOrFailWithZero(self.crossover), CrossOverType.TWOPOINT);
                        break;

                    case 2:
                        result = new CrossOver(parseDoubleOrFailWithZero(self.crossover), CrossOverType.UNIFORM);
                        break;


                    default:
                        result = new CrossOver(parseDoubleOrFailWithZero(self.crossover), CrossOverType.ONEPOINT);
                        break;
                }

                return result;
            }

            @Override
            public GaussianMutation getMutation() {
                return new GaussianMutation(parseDoubleOrFailWithZero(self.mutation));
            }

            @Override
            public Invertion getInvertion() {
                return new Invertion(parseDoubleOrFailWithZero(self.invertion));
            }
        };
    }

    public TitledPane[] ui() {

        annGridPane = new GridPane();
        annGridPane.setPadding(new Insets(20));
        annGridPane.setHgap(15);
        annGridPane.setVgap(12);

        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(20));
        grid2.setHgap(10);
        grid2.setVgap(12);
        
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        
        

        // Section 1: (ANN)
        Label lblAnnSectionHeader = new Label("Multilayer Perceptron");
        
        Label lblAnnLayerHeader = new Label("ANN Layer");
        Label lblNumOfNeuronsInLayerHeader = new Label("Neurons");
        Label lblActivationFuncHeader = new Label("Activation Function");
        Label lblHiddenLayerHandler = new Label("Hidden Layer");
        
        lblAnnSectionHeader.setFont(headerFont);
        lblAnnLayerHeader.setFont(headerFont);
        lblNumOfNeuronsInLayerHeader.setFont(headerFont);
        lblActivationFuncHeader.setFont(headerFont);
        lblHiddenLayerHandler.setFont(headerFont);
        
        
        
        btnAddHiddenLayer = new Button();
        btnAddHiddenLayer.setVisible(true);
        btnAddHiddenLayer.setFont(new Font(0));
        btnAddHiddenLayer.setPrefWidth(30);
        btnAddHiddenLayer.setPrefHeight(30);
        btnAddHiddenLayer.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("plus_small.png"))));
        btnAddHiddenLayer.setStyle("-fx-background-color: transparent");
        btnAddHiddenLayer.setOnAction(new AddANNLayerHandler());
        
        
        
        btnRemoveHiddenLayer = new Button();
        btnRemoveHiddenLayer.setVisible(true);
        btnRemoveHiddenLayer.setFont(new Font(0));
        btnRemoveHiddenLayer.setPrefWidth(30);
        btnRemoveHiddenLayer.setPrefHeight(30);
        btnRemoveHiddenLayer.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("minus_small.png"))));
        btnRemoveHiddenLayer.setStyle("-fx-background-color: transparent");
        btnRemoveHiddenLayer.setOnAction(new RemoveANNLayerHandler());
        
        
        
        annBtnPane = new HBox();
        BorderPane.setAlignment(annBtnPane, Pos.CENTER);
        annBtnPane.getChildren().add(btnAddHiddenLayer);
        
        
        BorderPane hiddenLayerControlPane = new BorderPane();
        hiddenLayerControlPane.setPadding(new Insets(0,20,0,20));
        
        BorderPane tmpBPane = new BorderPane();
        tmpBPane.setLeft(lblHiddenLayerHandler);
        tmpBPane.setRight(annBtnPane);
        tmpBPane.setStyle("-fx-border-radius: 1; -fx-border-color: black");
        
        hiddenLayerControlPane.setCenter(tmpBPane);
        
        
        annGridPane.add(lblAnnLayerHeader, 0, 0);
        annGridPane.add(lblNumOfNeuronsInLayerHeader, 1, 0);
        annGridPane.add(lblActivationFuncHeader, 2, 0);
        annGridPane.getColumnConstraints().add(new ColumnConstraints(110));
        
        
        int currGridRow = 1;
                
        int numOfLayers = topology.size();
        for(int i=0; i<(numOfLayers); i++)
        {
            Label lblLayerName = null;
            
            lblLayerName = new Label("Hidden Layer "+i);
                       
            annGridPane.add(lblLayerName, 0, currGridRow);
            annGridPane.add(topology.get(i), 1, currGridRow);
            annGridPane.add(choiceBoxTopology.get(i), 2, currGridRow);
            
            currGridRow++;
        }
        
        
        
        
        GridPane outputLayerGPane = new GridPane();
        outputLayerGPane.setPadding(new Insets(20));
        outputLayerGPane.setHgap(15);
        outputLayerGPane.setVgap(12);
        outputLayerGPane.add(new Label("Output Layer   "), 0, 0);
        outputLayerGPane.getColumnConstraints().add(new ColumnConstraints(110));
        TextField tfOutputLayer = new AdvanceTextField("[0-9]", "1");
        tfOutputLayer.setDisable(true);
        tfOutputLayer.setPrefWidth(60);
        ChoiceBox outputLayerActivationFuncCBox = new ChoiceBox(FXCollections.observableArrayList("Sigmoid", "Hyperbolic Tangent", "Linear"));
        outputLayerActivationFuncCBox.getSelectionModel().selectFirst();
        choiceBoxTopology.add(outputLayerActivationFuncCBox);
        outputLayerGPane.add(tfOutputLayer, 1, 0);
        outputLayerGPane.add(outputLayerActivationFuncCBox, 2, 0);
        
        VBox tmpVBox = new VBox();
        tmpVBox.getChildren().addAll(annGridPane,hiddenLayerControlPane,outputLayerGPane);
        
        
        BorderPane annPane = new BorderPane();
        BorderPane.setAlignment(lblAnnSectionHeader, Pos.CENTER);
        annPane.setTop(lblAnnSectionHeader);
        annPane.setCenter(tmpVBox);
        
        
        
        // Section 2: (GA)
                                                  
        Label gaPropertiesHeaderLabel = new Label("Genetic Algorithm Parameters");
        gaPropertiesHeaderLabel.setFont(headerFont);
        
        Label lblGaPopulationParams = new Label("GA Population");
        Label lblGaOperatorsParams = new Label("GA Operators");
        Label lblGaSelectionParams = new Label("GA Selection");
        Label lblGaStrategyParams = new Label("GA Strategies");
        Label lblGaTerminationParams = new Label("GA Termination");
        
        lblGaPopulationParams.setFont(headerFont);
        lblGaOperatorsParams.setFont(headerFont);
        lblGaSelectionParams.setFont(headerFont);
        lblGaStrategyParams.setFont(headerFont);
        lblGaTerminationParams.setFont(headerFont);
        
        Label poulationSizeLabel = new Label("Population size:");
        Label iterationsLabel = new Label("Generations:");
        Label selectionSizeLabel = new Label("Parents:");
        Label elitSizeLabel = new Label("Elitism size:");
        Label crossoverLabel = new Label("Crossover probability:");
        Label crossoverTypeLabel = new Label("Crossover type:");
        Label mutationLabel = new Label("Mutation probability:");
        Label invertionLabel = new Label("Invertion probability:");
        Label parentsSelectionLabel = new Label("Selection scheme:");
        
        
        double inputColWidth = 200;
        poulationSize.setPrefWidth(inputColWidth);
        iterations.setPrefWidth(inputColWidth);
        mutation.setPrefWidth(inputColWidth);
        selectionSize.setPrefWidth(inputColWidth);
        elitSize.setPrefWidth(inputColWidth);
        crossover.setPrefWidth(inputColWidth);
        crossoverType.setPrefWidth(inputColWidth);
        invertion.setPrefWidth(inputColWidth);
        parentsSelection.setPrefWidth(inputColWidth);
        
        
        VBox gaPopulationBox = new VBox(0);
        GridPane gaPopulationGPane = new GridPane();
        gaPopulationGPane.setPadding(new Insets(5,20,5,20));
        gaPopulationGPane.setHgap(10);
        gaPopulationGPane.setVgap(12);
        gaPopulationGPane.getColumnConstraints().add(new ColumnConstraints(160));
        gaPopulationGPane.add(poulationSizeLabel, 0, 0);
        gaPopulationGPane.add(poulationSize, 1, 0);
        gaPopulationBox.getChildren().addAll(lblGaPopulationParams,gaPopulationGPane);
        
        VBox gaOperatorsBox = new VBox(0);
        GridPane gaOperatorsGPane = new GridPane();
        gaOperatorsGPane.setPadding(new Insets(5,20,5,20));
        gaOperatorsGPane.setHgap(10);
        gaOperatorsGPane.setVgap(12);
        gaOperatorsGPane.getColumnConstraints().add(new ColumnConstraints(160));
        gaOperatorsGPane.add(crossoverLabel, 0, 0);
        gaOperatorsGPane.add(crossover, 1, 0);
        gaOperatorsGPane.add(crossoverTypeLabel, 0, 1);
        gaOperatorsGPane.add(crossoverType, 1, 1);
        gaOperatorsGPane.add(mutationLabel, 0, 2);
        gaOperatorsGPane.add(mutation, 1, 2);
        gaOperatorsBox.getChildren().addAll(lblGaOperatorsParams,gaOperatorsGPane);
        
        VBox gaSelectionParamsBox = new VBox(0);
        GridPane gaSelectionParamsGPane = new GridPane();
        gaSelectionParamsGPane.setPadding(new Insets(5,20,5,20));
        gaSelectionParamsGPane.setHgap(10);
        gaSelectionParamsGPane.setVgap(12);
        gaSelectionParamsGPane.getColumnConstraints().add(new ColumnConstraints(160));
        gaSelectionParamsGPane.add(selectionSizeLabel, 0, 0);
        gaSelectionParamsGPane.add(selectionSize, 1, 0);
        gaSelectionParamsGPane.add(parentsSelectionLabel, 0, 1);
        gaSelectionParamsGPane.add(parentsSelection, 1, 1);
        gaSelectionParamsBox.getChildren().addAll(lblGaSelectionParams,gaSelectionParamsGPane);
        
        VBox gaStrategyParamsBox = new VBox(0);
        GridPane gaStrategyParamsGPane = new GridPane();
        gaStrategyParamsGPane.setPadding(new Insets(5,20,5,20));
        gaStrategyParamsGPane.setHgap(10);
        gaStrategyParamsGPane.setVgap(12);
        gaStrategyParamsGPane.getColumnConstraints().add(new ColumnConstraints(160));
        gaStrategyParamsGPane.add(elitSizeLabel, 0, 0);
        gaStrategyParamsGPane.add(elitSize, 1, 0);
        gaStrategyParamsBox.getChildren().addAll(lblGaStrategyParams,gaStrategyParamsGPane);
        
        VBox gaTerminationParamsBox = new VBox(0);
        GridPane gaTerminationParamsGPane = new GridPane();
        gaTerminationParamsGPane.setPadding(new Insets(5,20,5,20));
        gaTerminationParamsGPane.setHgap(10);
        gaTerminationParamsGPane.setVgap(12);
        gaTerminationParamsGPane.getColumnConstraints().add(new ColumnConstraints(160));
        gaTerminationParamsGPane.add(iterationsLabel, 0, 0);
        gaTerminationParamsGPane.add(iterations, 1, 0);
        gaTerminationParamsBox.getChildren().addAll(lblGaTerminationParams,gaTerminationParamsGPane);
        
        
        VBox gaContentBox = new VBox(10);
        gaContentBox.setPadding(new Insets(20));
        gaContentBox.getChildren().addAll(gaPopulationBox,
                                          gaOperatorsBox,
                                          gaSelectionParamsBox,
                                          gaStrategyParamsBox,
                                          gaTerminationParamsBox);
        
        
        
        
        
        
        BorderPane gaPropPane = new BorderPane();
        BorderPane.setAlignment(gaPropertiesHeaderLabel, Pos.CENTER);
        gaPropPane.setTop(gaPropertiesHeaderLabel);
        gaPropPane.setCenter(gaContentBox);
        
        

        annPane.getStyleClass().add("modulePane1Child");
        gaPropPane.getStyleClass().add("modulePane1Child");
        
        
        annPane.setPrefSize(490, 470);
        gaPropPane.setPrefSize(470, 470);
        
        return new TitledPane[]{new TitledPane("Artificial neural network", annPane), new TitledPane("Genetic Algorithm", gaPropPane)};
    }
    
    

    @Override
    public ActivationFunction[] getActivationsFunctions()
    {
        int j = 0;
        for (int i = 0; i < topology.size(); i++)
        {
            int n = parseOrFailWithZero(topology.get(i));
            if (n > 0) 
            {
                j++;
            }
        }
        ActivationFunction[] result = new ActivationFunction[j + 1];
        j = 0;
        for (int i = 0; i < topology.size(); i++)
        {
            int n = parseOrFailWithZero(topology.get(i));
            if (n > 0)
            {

                switch (this.choiceBoxTopology.get(i).getSelectionModel().getSelectedIndex())
                {
                    case 0:
                        result[j++] = new Sigmond();
                        break;
                    case 1:
                        result[j++] = new HyperbolicTangent();
                        break;
                    case 2:
                        result[j++] = new Linear();
                        break;
                    default:
                        result[j++] = new Sigmond();
                        break;
                }
            }
        }

        
        switch (this.choiceBoxTopology.get(choiceBoxTopology.size()-1).getSelectionModel().getSelectedIndex())
        {
            case 0:
                result[j] = new Sigmond();
                break;
            case 1:
                result[j] = new HyperbolicTangent();
                break;
            case 2:
                result[j] = new Linear();
                break;
        }
        
        return result;
             
    }
    
    public void refreshANNLayerPane()
    {
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        
        
        
        Label lblAnnLayerHeader = new Label("ANN Layer");
        Label lblNumOfNeuronsInLayerHeader = new Label("Neurons");
        Label lblActivationFuncHeader = new Label("Activation Function");
        
        
        lblAnnLayerHeader.setFont(headerFont);
        lblNumOfNeuronsInLayerHeader.setFont(headerFont);
        lblActivationFuncHeader.setFont(headerFont);
        
        annGridPane.getChildren().clear();
        
        annGridPane.add(lblAnnLayerHeader, 0, 0);
        annGridPane.add(lblNumOfNeuronsInLayerHeader, 1, 0);
        annGridPane.add(lblActivationFuncHeader, 2, 0);
        
        
        
        int currGridRow = 1;
                
        int numOfLayers = topology.size();
        for(int i=0; i<numOfLayers; i++)
        {
            Label lblLayerName = null;
            
            lblLayerName = new Label("Hidden Layer "+i);
                        
            annGridPane.add(lblLayerName, 0, currGridRow);
            annGridPane.add(topology.get(i), 1, currGridRow);
            annGridPane.add(choiceBoxTopology.get(i), 2, currGridRow);
            
            currGridRow++;
        }
    }
    
    public void updateRemLayerBtnVisibility()
    {
        annBtnPane.getChildren().clear();
        
        if(topology.size() > 0)
        {
            annBtnPane.getChildren().addAll(btnAddHiddenLayer,btnRemoveHiddenLayer);
        }
        else
        {
            annBtnPane.getChildren().addAll(btnAddHiddenLayer);
        }
    }
    
    class AddANNLayerHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent t) 
        {
            
            
            AdvanceTextField nwNumNeuronTxtField = new AdvanceTextField("[0-9]", "0");
            nwNumNeuronTxtField.setPrefWidth(30);
            
            ArrayList<TextField> nwTopologyList = new ArrayList<TextField>();
            for(int i=0; i<topology.size(); i++)
            {
                nwTopologyList.add(topology.get(i));
            }
            nwTopologyList.add(nwNumNeuronTxtField);
            
            
            topology = nwTopologyList;
            
            
            
            
            
            ChoiceBox nwActivationFuncCBox = new ChoiceBox(FXCollections.observableArrayList("Sigmoid", "Hyperbolic Tangent", "Linear"));
            nwActivationFuncCBox.getSelectionModel().selectFirst();
            
            ArrayList<ChoiceBox> nwChoiceBoxTop = new ArrayList<ChoiceBox>();
            for(int i=0; i<choiceBoxTopology.size()-1; i++)
            {
                nwChoiceBoxTop.add(choiceBoxTopology.get(i));
            }
            nwChoiceBoxTop.add(nwActivationFuncCBox);
            nwChoiceBoxTop.add(choiceBoxTopology.get(choiceBoxTopology.size()-1));
            
            
            choiceBoxTopology = nwChoiceBoxTop;
            
            
            updateRemLayerBtnVisibility();
            refreshANNLayerPane();
        }
    }
    
    class RemoveANNLayerHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent t) 
        {
            // Remove a hidden layer.
            topology.remove(topology.size()-1);
            choiceBoxTopology.remove(choiceBoxTopology.size()-1);
            
            updateRemLayerBtnVisibility();
            refreshANNLayerPane();
        }
    }
        
}