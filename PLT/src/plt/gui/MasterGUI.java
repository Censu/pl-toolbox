/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import plt.featureselection.examples.NBest;
import plt.gui.component.ModalPopup;
import plt.gui.configurators.PLRankSvmConfigurator;
import plt.plalgorithm.neruoevolution.PLNeuroEvolutionConfigurator;
import plt.gui.help.Tab1Help;
import plt.gui.help.Tab2Help;
import plt.gui.help.Tab3Help;
import plt.gui.help.Tab4Help;
import plt.plalgorithm.neruoevolution.GA.GeneticAlgorithmConfigurator;
import plt.plalgorithm.neruoevolution.PLNeuroEvolution;
import plt.plalgorithm.svm.libsvm_plt.PLRankSvm;

/**
 *
 * @author Owner
 */
public class MasterGUI extends BorderPane
{
    Stage parentStage;
    Experiment experiment;
    
    TabPane tabPane;
    Text helpText;
    
    final String DEFAULT_TOOLTIP_HELPTEXT = "HELP:      Mouse over the program's components for helpful tips.";
    
    public MasterGUI(Stage stage)
    {
        parentStage = stage;
     
        
        //JsonHelpExtractor ujextractor = new JsonHelpExtractor();
        //HashMap<String,String> dataSetTab_tooltipHelp = ujextractor.extractToolTipFile("TestJson.json");
        //HashMap<String, ExtensiveHelp> dataSetTab_extensiveHelp = ujextractor.extractExtensiveHelpFile("DataSetExtensiveHelp.json");
        //final HelpDataStore dataSetTab_HelpStore = new HelpDataStore("DataSetTab",dataSetTab_tooltipHelp,dataSetTab_extensiveHelp);
        
        //ujextractor.writeExtensiveHelpFile("C:\\Users\\user\\Desktop\\ExtensiveSectionTest.json");
        
        experiment = new Experiment();

        tabPane = new TabPane();
        final Tab tab1 = new DataSetTab(parentStage, experiment);
        tab1.setText("Dataset");

        final Tab tab2 = new PreprocessingTab(parentStage, experiment);
        tab2.setText("Preprocessing");

        final Tab tab3 = new FeatureSelectionTab(parentStage, experiment);
        tab3.setText("Feature Selection");

        final Tab tab4 = new AlgorithmTab(parentStage, experiment);
        tab4.setText("Preference Learning Methods");

        
        
        tabPane.tabClosingPolicyProperty().set(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(tab1, tab2, tab3 ,tab4);
        
        
        BorderPane bottomPane = new BorderPane();
        
        
        final Button btnBack = new Button();
        btnBack.setPrefSize(200, 20);
        btnBack.setVisible(false);
        Label lblBackBtn = new Label("BACK");
        ImageView imgViewBkBtn = new ImageView(new Image(DataSetTab.class.getResourceAsStream("bkButton.png")));
        BorderPane backBtnInnerBPane = new BorderPane();
        backBtnInnerBPane.setLeft(imgViewBkBtn);
        backBtnInnerBPane.setCenter(lblBackBtn);
        btnBack.setGraphic(backBtnInnerBPane);
        
        
        
        btnBack.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                
                if(tabPane.getSelectionModel().getSelectedIndex() > 0)
                {
                    tabPane.selectionModelProperty().get().select(tabPane.selectionModelProperty().get().getSelectedIndex() - 1);
                }
            }
            
        });
        
        
        
        final Button btnNext = new Button();
        btnNext.setPrefSize(200, 20);
        Label lblNextBtn = new Label("NEXT");
        ImageView imgViewNextBtn = new ImageView(new Image(DataSetTab.class.getResourceAsStream("nxtButton.png")));
        BorderPane nextBtnInnerBPane = new BorderPane();
        nextBtnInnerBPane.setCenter(lblNextBtn);
        nextBtnInnerBPane.setRight(imgViewNextBtn);
        btnNext.setGraphic(nextBtnInnerBPane);
        
        btnNext.disableProperty().bind(this.experiment.isReadyToUseDataSetProperty().not());
        
        tabPane.selectionModelProperty();
        
        btnNext.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                
                
                if(! tab4.selectedProperty().get())
                {
                    tabPane.selectionModelProperty().get().select(tabPane.selectionModelProperty().get().getSelectedIndex() + 1);
                }
                else if(tab4.selectedProperty().get())
                {
                    // Perform safety checks.
                    boolean allClear = true;


                    int numOfIgnoredFeatures = 0;
                    boolean[] tmpIgArr = experiment.ignoredFeaturesProperty().get();
                    for(int i=0; i<tmpIgArr.length; i++)
                    {
                        if(tmpIgArr[i]) { numOfIgnoredFeatures++; }
                    }


                    if(numOfIgnoredFeatures == experiment.dataSetProperty().get().getNumberOfFeatures())
                    {
                        // You must include at least one feature.

                        ModalPopup notification = new ModalPopup();
                        notification.show(new Label("ERROR: You must include at least one feature from the dataset."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  

                        allClear = false;
                    }
                    else if(experiment.featureSelectionProperty().get() != null)
                    {
                        if(experiment.featureSelectionProperty().get() instanceof NBest)
                        {
                            NBest castNBest = (NBest) experiment.featureSelectionProperty().get();

                            int numOfUserIncludedFeatures = (experiment.dataSetProperty().get().getNumberOfFeatures() - numOfIgnoredFeatures);

                            if(castNBest.getN() > numOfUserIncludedFeatures)
                            {
                                // N val should always be less than or equal to the number of user included features.

                                ModalPopup notification = new ModalPopup();
                                notification.show(new Label("ERROR: N-Best N value cannot be greater than the number of included features. \n Current N Value = "+castNBest.getN()+" \n Current Num of Included Features = "+numOfUserIncludedFeatures), parentStage.getScene().getRoot(),null,new Button("OK"), 200,600,false);  

                                allClear = false;
                            }
                        }


                        if(experiment.algorithmForFeatureSelectionProperty().get() == null)
                        {
                            // You cannot select a feature selection type without stating an algorithm.

                            ModalPopup notification = new ModalPopup();
                            notification.show(new Label("ERROR: You must state an algorithm to work with "+experiment.featureSelectionProperty().get().getFSelName()+"."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  

                            allClear = false;
                        }

                    }

                    if(experiment.algorithmForFeatureSelectionProperty().get() != null)
                    {
                        if(experiment.algorithmForFeatureSelectionProperty().get() instanceof PLNeuroEvolution)
                        {
                            PLNeuroEvolution castPLNE = (PLNeuroEvolution) experiment.algorithmForFeatureSelectionProperty().get();
                            PLNeuroEvolutionConfigurator neConfig = castPLNE.getConfigurator();
                            GeneticAlgorithmConfigurator gaConfig = neConfig.getGeneticAlgorithmConfigurator();
                            
                            if(gaConfig.getNumberOfParents() > gaConfig.getPopulationSize())
                            {
                                ModalPopup notification = new ModalPopup();
                                notification.show(new Label("GA ERROR: The number of parents is greater than the GA population size."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  

                                allClear = false;
                            }

                            if(gaConfig.getElitSize() > gaConfig.getPopulationSize())
                            {
                                ModalPopup notification = new ModalPopup();
                                notification.show(new Label("GA ERROR: The elitism size is greater than the GA population size."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  

                                allClear = false;
                            }
                        }
                        
                        if(experiment.algorithmProperty().get() instanceof PLRankSvm)
                        {
                            PLRankSvm castPLRS = (PLRankSvm) experiment.algorithmProperty().get();
                            PLRankSvmConfigurator svmConfig = castPLRS.getConfigurator();

                            if((svmConfig.gammaRequired())
                            &&(svmConfig.getGamma() == 0))
                            {
                                ModalPopup notification = new ModalPopup();
                                notification.show(new Label("SVM ERROR: Gamma cannot be set to 0."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  
                                
                                allClear = false;
                            }        
                        }
                    }
                    
                    if(experiment.algorithmProperty().get() instanceof PLNeuroEvolution)
                    {
                        PLNeuroEvolution castPLNE = (PLNeuroEvolution) experiment.algorithmProperty().get();
                        PLNeuroEvolutionConfigurator neConfig = castPLNE.getConfigurator();
                        GeneticAlgorithmConfigurator gaConfig = neConfig.getGeneticAlgorithmConfigurator();
                        
                        int numParents = gaConfig.getNumberOfParents();
                        int popSize = gaConfig.getPopulationSize();
                        if(numParents > popSize)
                        {
                            ModalPopup notification = new ModalPopup();
                            notification.show(new Label("GA ERROR: The number of parents is greater than the GA population size."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  

                            allClear = false;
                        }
                        
                        if(gaConfig.getElitSize() > gaConfig.getPopulationSize())
                        {
                            ModalPopup notification = new ModalPopup();
                            notification.show(new Label("GA ERROR: The elitism size is greater than the GA population size."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  

                            allClear = false;
                        } 
                    }
                    
                    if(experiment.algorithmProperty().get() instanceof PLRankSvm)
                    {
                        PLRankSvm castPLRS = (PLRankSvm) experiment.algorithmProperty().get();
                        PLRankSvmConfigurator svmConfig = castPLRS.getConfigurator();
                        
                        if((svmConfig.gammaRequired())
                        &&(svmConfig.getGamma() == 0))
                        {
                            ModalPopup notification = new ModalPopup();
                            notification.show(new Label("SVM ERROR: Gamma cannot be set to 0."), parentStage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  
                            
                            allClear = false;
                        }        
                    }
                    

                    if(allClear)
                    {
                        Execution e = new Execution(experiment);
                        e.show(parentStage);
                    }
                }
            }
        });
        
        tab4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1)
                {
                    
                    Label lblNextBtn = new Label("RUN EXPERIMENT");
                    ImageView imgViewNextBtn = new ImageView(new Image(DataSetTab.class.getResourceAsStream("runExperimentButton.png")));
                    BorderPane nextBtnInnerBPane = new BorderPane();
                    nextBtnInnerBPane.setCenter(lblNextBtn);
                    nextBtnInnerBPane.setRight(imgViewNextBtn);
                    btnNext.setGraphic(nextBtnInnerBPane);
                }
                else
                {
                    Label lblNextBtn = new Label("NEXT");
                    ImageView imgViewNextBtn = new ImageView(new Image(DataSetTab.class.getResourceAsStream("nxtButton.png")));
                    BorderPane nextBtnInnerBPane = new BorderPane();
                    nextBtnInnerBPane.setCenter(lblNextBtn);
                    nextBtnInnerBPane.setRight(imgViewNextBtn);
                    btnNext.setGraphic(nextBtnInnerBPane);
                }
            }
        });
        
        tab1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                
                if(t1)
                {
                    btnBack.setVisible(false);
                    btnNext.setVisible(true);
                }
                else
                {
                    btnBack.setVisible(true);
                }
            }
        });
        
        
        
        
        Button helpButton = new Button();
        helpButton.setVisible(true);
        helpButton.setGraphic(new ImageView(new Image(DataSetTab.class.getResourceAsStream("helpButton.png"))));
        helpButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if(tab1.selectedProperty().get())
                {
                    Tab1Help h = new Tab1Help();
                    h.show(parentStage.getScene().getRoot(), null);
                }
                else if(tab2.selectedProperty().get())
                {
                    Tab2Help h = new Tab2Help();
                    h.show(parentStage.getScene().getRoot(), null);
                }
                else if(tab3.selectedProperty().get())
                {
                    Tab3Help h = new Tab3Help();
                    h.show(parentStage.getScene().getRoot(), null);
                }
                else if(tab4.selectedProperty().get())
                {
                    Tab4Help h = new Tab4Help();
                    h.show(parentStage.getScene().getRoot(), null);
                }
            }
            
        });
        
        /*helpButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (tab1.selectedProperty().get()) {
                    ArrayList<String> itemsToInclude = new ArrayList<String>();
                    itemsToInclude.add("Loading a dataset");
                    itemsToInclude.add("Button: Import Object File");
                    itemsToInclude.add("Button: Import Ranking File");
                    
                    String reqHTML = dataSetTab_HelpStore.constructHtml(itemsToInclude);
                    HelpPopup hPopup = new HelpPopup(reqHTML);
                    hPopup.show(parentStage.getScene().getRoot(), null);
                    
                    //Tab1Help h = new Tab1Help();
                    //h.show(parentStage.getScene().getRoot(), null);
                }
                
                if (tab2.selectedProperty().get()) {
                    Tab2Help h = new Tab2Help();
                    h.show(parentStage.getScene().getRoot(), null);
                }
                
            }
        });*/
        
        //helpButton.visibleProperty().bind(tab1.selectedProperty().or(tab2.selectedProperty()));
        
        
        
        
        HBox navBtnBox = new HBox(10);
        navBtnBox.getChildren().addAll(btnBack,btnNext);
        
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.setStyle("-fx-background-color: #336699;");
        bottomPane.setLeft(helpButton);
        bottomPane.setRight(navBtnBox);
        
        this.setCenter(tabPane);
        this.setBottom(bottomPane);
        
        disableTabs(new ArrayList<Integer>(Arrays.asList(1,2,3)));
        
        
        
        
    }
    
    public void enableTabs(ArrayList<Integer> para_tabsToEnable)
    {
        for(int i=0; i<para_tabsToEnable.size(); i++)
        {
            tabPane.getTabs().get(para_tabsToEnable.get(i)).setDisable(false);
        }
    }
    
    public void disableTabs(ArrayList<Integer> para_tabsToEnable)
    {
        for(int i=0; i<para_tabsToEnable.size(); i++)
        {
            tabPane.getTabs().get(para_tabsToEnable.get(i)).setDisable(true);
        }
    }
    
    
}
