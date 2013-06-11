package plt.gui;


import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import plt.gui.component.AdvanceTextField;
import plt.gui.configurators.PLBackPropagationConfigurator;
import plt.gui.configurators.PLNeuroEvolutionConfigurator;
import plt.gui.customcomponents.ModulePane;
import plt.plalgorithm.PLAlgorithm;
import plt.plalgorithm.backpropagation.PLBackPropagation;
import plt.plalgorithm.neruoevolution.PLNeuroEvolution;


/**
 *
 * @author luca
 */
public class AlgorithmTab extends Tab {

    final private Stage stage;
    private Experiment experiment;
    private TitledPane[] algorithmPanes;

    public AlgorithmTab(Stage s, Experiment e) {
        super();
        this.experiment = e;
        this.stage = s;
        this.algorithmPanes = new TitledPane[0];
        setup();
    }

    
    private void setup()
    {
        final BorderPane bp = new BorderPane();
        
        final AlgorithmTab self = this;
        
        
        Font tabTitleFont = Font.font("BirchStd", FontWeight.BOLD, 50);
        Label lblTabHeader = new Label("Preference Learning Methods");
        lblTabHeader.setFont(tabTitleFont);        
        
        final Pane nestedBp = new Pane();
        nestedBp.setPrefHeight(400);
        nestedBp.setPrefWidth(650);
        
        final ModulePane algorithmMPane = new ModulePane("Algorithm", new ArrayList<String>(Arrays.asList("Evolving NN","Back propagation")), new Pane(), "modulePane1",850);
        final ModulePane validatorMPane = new ModulePane("Cross Validation", new ArrayList<String>(Arrays.asList("None", "K-Fold")),new Pane(),"modulePane2",850);
        
        
        
        Pane tmpParentPane1 = new Pane();
        tmpParentPane1.getChildren().add(algorithmMPane);
        Pane tmpParentPane2 = new Pane();
        tmpParentPane2.getChildren().add(validatorMPane);
        
        VBox moduleHBox = new VBox(30);
        moduleHBox.getChildren().add(tmpParentPane1);
        moduleHBox.getChildren().add(tmpParentPane2);
        
        nestedBp.getChildren().add(moduleHBox); 
        
        
        final ScrollPane sPane = new ScrollPane();  
        sPane.setStyle("-fx-background-color: transparent;"); // Hide the scrollpane gray border.
        sPane.setPrefSize(880,600);
        sPane.setContent(moduleHBox);    
        
        stage.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1){
                
                sPane.setPrefHeight(t1.doubleValue() * 0.7);
            }
            
        });
        
        VBox tmpVBox = new VBox(10);
        //tmpVBox.setPrefSize(855,700);
        tmpVBox.getChildren().addAll(lblTabHeader,sPane);
        
        
        bp.setCenter(new Group(tmpVBox));
        this.setContent(bp);
        
        
        
        // Cross-Validation UI.
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        Label lblCrossValidationHeader = new Label("K-Fold Cross Validation");
        lblCrossValidationHeader.setFont(headerFont);
        BorderPane.setAlignment(lblCrossValidationHeader, Pos.CENTER);
        
        Label kLabel = new Label("K:");
        TextField k  = new AdvanceTextField("[0-9]", "3");
        this.experiment.kProperty().bind(k.textProperty());

        k.setPrefWidth(30);
        kLabel.visibleProperty().bind(validatorMPane.choiceBox.getSelectionModel().selectedIndexProperty().isEqualTo(1));
        k.visibleProperty().bind(kLabel.visibleProperty());
        
        GridPane validatorContentGPane = new GridPane();
        validatorContentGPane.setPadding(new Insets(20));
        validatorContentGPane.setHgap(15);
        validatorContentGPane.setVgap(12);
        
        
        validatorContentGPane.add(kLabel, 0, 0);
        validatorContentGPane.add(k, 1, 0);
        
        
        
        
        
        HBox cntentBx = new HBox(20);
        cntentBx.getChildren().addAll(kLabel,k);
        
        
        final BorderPane kBPane = new BorderPane();
        kBPane.getStyleClass().add("modulePane2Child");
        kBPane.setLeft(lblCrossValidationHeader);
        kBPane.setRight(cntentBx);
        
        
        
        
        this.experiment.useValidatorProperty().bind(validatorMPane.choiceBox.getSelectionModel().selectedIndexProperty().isEqualTo(1));
        
        
        algorithmMPane.choiceBox.valueProperty().addListener(new ChangeListener() {
            
            HBox algorithmMPane_contentBox;
            
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                int i =  algorithmMPane.choiceBox.getSelectionModel().getSelectedIndex();
                
                switch (i) {
                    case 0:  
                        PLNeuroEvolutionConfigurator conf = new PLNeuroEvolutionConfigurator();
                        self.algorithmPanes = conf.ui();
                        
                        
                        
                        algorithmMPane_contentBox = new HBox(5);
                        for(int counter=0; counter<self.algorithmPanes.length; counter++)
                        {
                            Node tmpContentNode = algorithmPanes[counter].getContent();
                            algorithmMPane_contentBox.getChildren().add(tmpContentNode);
                        }
                        
                        
                        algorithmMPane.setMainContent(algorithmMPane_contentBox);
                        
                        
                        PLAlgorithm algo = new PLNeuroEvolution(null, conf);
                        self.experiment.algorithmProperty().set(algo);                        
                    break;     
                        
                    case 1:  
                        PLBackPropagationConfigurator conf2 = new PLBackPropagationConfigurator() {};
                        self.algorithmPanes = conf2.ui();
                        
                        
                        algorithmMPane_contentBox = new HBox(5);
                        for(int counter=0; counter<self.algorithmPanes.length; counter++)
                        {
                            Node tmpContentNode = algorithmPanes[counter].getContent();
                            algorithmMPane_contentBox.getChildren().add(tmpContentNode);
                        }
                        
                        
                        algorithmMPane.setMainContent(algorithmMPane_contentBox);
                        
                        
                        PLBackPropagation algo2 = new PLBackPropagation(null, conf2);
                        self.experiment.algorithmProperty().set(algo2);                        
                    break;
                        
                    
                }
            }
        });
        
        
        validatorMPane.choiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                int i =  validatorMPane.choiceBox.getSelectionModel().getSelectedIndex();
                
                switch (i) {
                    case 0:  
                        validatorMPane.setMainContent(new Pane());
                    break;     
                        
                    case 1:  
                        validatorMPane.setMainContent(kBPane);
                    break;
                        
                    
                }
            }
        });
        
        
        algorithmMPane.choiceBox.getSelectionModel().select(0);
        validatorMPane.choiceBox.getSelectionModel().select(1);
    }

}