package plt.gui;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
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
import plt.featureselection.examples.NBest;
import plt.featureselection.examples.SFS;
import plt.gui.configurators.NBestConfigurator;
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
public class FeatureSelectionTab extends Tab {

    final private Stage stage;
    private Experiment experiment;
    private TitledPane[] featureSelectionConfigurations;
    private TitledPane[] algorithimConfigurations;

    VBox tmpVBox;
    VBox moduleHBox;

    public FeatureSelectionTab(Stage s, Experiment e) {
        super();
        this.experiment = e;
        this.stage = s;
        this.featureSelectionConfigurations = new TitledPane[0];
        this.algorithimConfigurations  = new TitledPane[0];
        setup();
    }

    private void setup()
    {
        final BorderPane bp = new BorderPane();
        
        final FeatureSelectionTab self = this;
        
        
        Font tabTitleFont = Font.font("BirchStd", FontWeight.BOLD, 50);
        Label lblTabHeader = new Label("Feature Selection");
        lblTabHeader.setFont(tabTitleFont);
        
        
        
        final Pane nestedBp = new Pane();
        nestedBp.setPrefHeight(400);
        nestedBp.setPrefWidth(650);
        
        final ModulePane featureSelection = new ModulePane("Feature Selection", new ArrayList<String>(Arrays.asList("None", "nBest", "SFS")),new Pane(),"modulePane3",850);
        final ModulePane algorithmMPane = new ModulePane("Algorithm", new ArrayList<String>(Arrays.asList("None","Evolving NN","Back propagation")), new Pane(), "modulePane1",850);
        final ModulePane validatorMPane = new ModulePane("Cross Validation", new ArrayList<String>(Arrays.asList("None", "K-Fold")),new Pane(),"modulePane2",850);
        
                
        algorithmMPane.disableMPane();
        validatorMPane.disableMPane();
        
        Pane tmpParentPane1 = new Pane();
        tmpParentPane1.getChildren().add(featureSelection);
        Pane tmpParentPane2 = new Pane();
        tmpParentPane2.getChildren().add(algorithmMPane);
        Pane tmpParentPane3 = new Pane();
        tmpParentPane3.getChildren().add(validatorMPane);
        
        moduleHBox = new VBox(30);
        moduleHBox.getChildren().add(tmpParentPane1);
        moduleHBox.getChildren().add(tmpParentPane2);
        moduleHBox.getChildren().add(tmpParentPane3);
        
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
        
        tmpVBox = new VBox(10);
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
        
        
        this.experiment.algorithmForFeatureSelectionProperty().set(null);
        
        this.experiment.useValidatorProperty().bind(validatorMPane.choiceBox.getSelectionModel().selectedIndexProperty().isEqualTo(1));
        
        
        featureSelection.choiceBox.valueProperty().addListener(new ChangeListener() 
        {
            HBox featureSelMPane_contentBox;
            
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                
                self.featureSelectionConfigurations = new TitledPane[0];

                                        
                int i =  featureSelection.choiceBox.getSelectionModel().getSelectedIndex();
                switch (i) {
                    case 0:  
                        self.experiment.featureSelectionProperty().set(null);
                        featureSelection.setMainContent(new Pane());
                    break;
                    
                    case 1:  
                        NBestConfigurator conf = new NBestConfigurator();
                        self.experiment.featureSelectionProperty().set(new NBest(conf));
                        self.featureSelectionConfigurations = conf.ui();
                        
                        
                        
                        featureSelection.setMainContent(featureSelectionConfigurations[0].getContent());
                        
                        self.experiment.algorithmForFeatureSelectionProperty().set(null);
                        
                    break;
                        
                    case 2:  
                        self.experiment.featureSelectionProperty().set(new SFS());
                        featureSelection.setMainContent(new Pane());
                        
                        self.experiment.algorithmForFeatureSelectionProperty().set(null);
                    break;

                    default: 
                        self.experiment.featureSelectionProperty().set(null);
                        featureSelection.setMainContent(new Pane());
                    break;
                }
                
                
                if(i == 0)
                {
                    algorithmMPane.disableMPane();
                    validatorMPane.disableMPane();
                    
                    algorithmMPane.setChoiceBoxOptions(new ArrayList<String>(Arrays.asList("None","Evolving NN","Back propagation")));
                }
                else
                {
                    algorithmMPane.enableMPane();
                    validatorMPane.enableMPane();
                }
                
            }
            
            
        });
        
        
        algorithmMPane.choiceBox.valueProperty().addListener(new ChangeListener() {
            
            HBox algorithmMPane_contentBox;
            
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                
                if(featureSelection.choiceBox.getSelectionModel().getSelectedIndex() == 0)
                {
                    
                }
                else
                {
                    if((featureSelection.choiceBox.getSelectionModel().getSelectedIndex() != 0)
                    &&(algorithmMPane.choiceBox.getItems().size() != 2))
                    {
                        algorithmMPane.setChoiceBoxOptions(new ArrayList<String>(Arrays.asList("Evolving NN","Back propagation")));
                    }
                    
                    
                    int i =  algorithmMPane.choiceBox.getSelectionModel().getSelectedIndex();

                    switch (i) {
                        case 0:  
                            PLNeuroEvolutionConfigurator conf = new PLNeuroEvolutionConfigurator();
                            self.algorithimConfigurations  = conf.ui();



                            algorithmMPane_contentBox = new HBox(5);
                            for(int counter=0; counter<self.algorithimConfigurations.length; counter++)
                            {
                                Node tmpContentNode = algorithimConfigurations[counter].getContent();
                                algorithmMPane_contentBox.getChildren().add(tmpContentNode);
                            }


                            algorithmMPane.setMainContent(algorithmMPane_contentBox);
                            //algorithmMPane.autosize();

                            PLAlgorithm algo = new PLNeuroEvolution(null, conf);
                            self.experiment.algorithmForFeatureSelectionProperty().set(algo);                        
                        break;     

                        case 1:  
                            PLBackPropagationConfigurator conf2 = new PLBackPropagationConfigurator() {};
                            self.algorithimConfigurations = conf2.ui();


                            algorithmMPane_contentBox = new HBox(5);
                            for(int counter=0; counter<self.algorithimConfigurations.length; counter++)
                            {
                                Node tmpContentNode = algorithimConfigurations[counter].getContent();
                                algorithmMPane_contentBox.getChildren().add(tmpContentNode);
                            }


                            algorithmMPane.setMainContent(algorithmMPane_contentBox);


                            PLBackPropagation algo2 = new PLBackPropagation(null, conf2);
                            self.experiment.algorithmForFeatureSelectionProperty().set(algo2);                        
                        break;


                    }
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
        
        
        featureSelection.choiceBox.getSelectionModel().select(0);
        algorithmMPane.choiceBox.getSelectionModel().select(0);
        validatorMPane.choiceBox.getSelectionModel().select(0);
    }
    
    
}