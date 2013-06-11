package plt.gui.configurators;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

public class PLBackPropagationConfigurator implements plt.plalgorithm.backpropagation.PLBackPropagationConfigurator {
    
    private ArrayList<TextField> topology;
    private ArrayList<ChoiceBox> choiceBoxTopology;
    private GridPane annGridPane;
    private HBox annBtnPane;
    private Button btnAddHiddenLayer;
    private Button btnRemoveHiddenLayer;
    
    private TextField learningRate;
    private TextField errorThreeshold;
    private TextField maxNumberOfIterations;    

    private static int parseIntegerOrFailWithZero(TextField t) {
        try {
            return Integer.parseInt(t.getText());
        } catch (NumberFormatException e) {
            return 0;
        } 
    }
    
    private static double parseDobuleOrFailWithZero(TextField t) {
        try {
            return Double.parseDouble(t.getText());
        } catch (NumberFormatException e) {
            return 0;
        } 
    }


    public PLBackPropagationConfigurator() {

        
        // Section 1: ANN
        
        topology = new ArrayList<TextField>();
        choiceBoxTopology = new ArrayList<ChoiceBox>();
        
        
        
        
        // Section 2: Backpropagation
        
        learningRate = new AdvanceTextField("[0-9.]","0.1");
        errorThreeshold = new AdvanceTextField("[0-9.]","0.1");
        maxNumberOfIterations = new AdvanceTextField("[0-9]","10");
        
        learningRate.setPrefWidth(30);
        errorThreeshold.setPrefWidth(30);
        maxNumberOfIterations.setPrefWidth(30);
    }
    
    
    
    @Override
    public int[] getTopology(int inputSize)
    {
        int j = 0;
        for (int i = 0; i < topology.size(); i++) 
        {
            int n = parseIntegerOrFailWithZero(topology.get(i));
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
            int n = parseIntegerOrFailWithZero(topology.get(i));
            if (n > 0)
            {
                result[j++] = n;
            }
        }
        result[j] = 1;
        return result;
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
        btnAddHiddenLayer.setOnAction(new PLBackPropagationConfigurator.AddANNLayerHandler());
        
        
        
        btnRemoveHiddenLayer = new Button();
        btnRemoveHiddenLayer.setVisible(true);
        btnRemoveHiddenLayer.setFont(new Font(0));
        btnRemoveHiddenLayer.setPrefWidth(30);
        btnRemoveHiddenLayer.setPrefHeight(30);
        btnRemoveHiddenLayer.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("minus_small.png"))));
        btnRemoveHiddenLayer.setStyle("-fx-background-color: transparent");
        btnRemoveHiddenLayer.setOnAction(new PLBackPropagationConfigurator.RemoveANNLayerHandler());
        
        
        
        annBtnPane = new HBox();
        BorderPane.setAlignment(annBtnPane, Pos.CENTER);
        annBtnPane.getChildren().add(btnAddHiddenLayer);
        //annBtnPane.getChildren().add(btnRemoveHiddenLayer);
        
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
        TextField tfOutputLayer = new AdvanceTextField("[0-9]", "1");//topology.get(numOfLayers-1);
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
        
        
        // Section 2: (Backpropagation)
        
        Label backpropHeaderLabel = new Label("Backpropagation");
        backpropHeaderLabel.setFont(headerFont);
        
        Label learningRateLabel = new Label("Learning rate:");
        Label errorThreesholdLabel = new Label("Error threshold:");
        Label maxNumberOfIterationsLabel = new Label("Epochs:");    
        
        grid2.add(learningRateLabel, 0, 0);
        grid2.add(learningRate, 1, 0);
        grid2.add(errorThreesholdLabel,0,1);
        grid2.add(errorThreeshold,1,1);
        grid2.add(maxNumberOfIterationsLabel,0,2);
        grid2.add(maxNumberOfIterations,1,2);
        
        
        BorderPane backpropPane = new BorderPane();
        BorderPane.setAlignment(backpropHeaderLabel, Pos.CENTER);
        BorderPane.setAlignment(grid2, Pos.CENTER);
        backpropPane.setTop(backpropHeaderLabel);
        backpropPane.setCenter(grid2);
        

        annPane.getStyleClass().add("modulePane1Child");
        backpropPane.getStyleClass().add("modulePane1Child");
        
        
               
        annPane.setPrefSize(490, 470);
        backpropPane.setPrefSize(470, 470);
        
        return new TitledPane[]{new TitledPane("Artificial neural network", annPane), new TitledPane("Genetic Algorithm", backpropPane)};
    }
   
    
    
    
    
    @Override
    public ActivationFunction[] getActivationsFunctions()
    {
        int j = 0;
        for (int i = 0; i < topology.size(); i++)
        {
            int n = parseIntegerOrFailWithZero(topology.get(i));
            if (n > 0) 
            {
                j++;
            }
        }
        ActivationFunction[] result = new ActivationFunction[j + 1];
        j = 0;
        for (int i = 0; i < topology.size(); i++)
        {
            int n = parseIntegerOrFailWithZero(topology.get(i));
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
    

    @Override
    public double getLearningRate() {
        return parseDobuleOrFailWithZero(learningRate);    
    }

    @Override
    public double getErrorThreeshold() {
        return parseDobuleOrFailWithZero(errorThreeshold);    
    }

    @Override
    public int getMaxNumberOfIterations() {
        return parseIntegerOrFailWithZero(maxNumberOfIterations);    
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
        for(int i=0; i<(numOfLayers); i++)
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