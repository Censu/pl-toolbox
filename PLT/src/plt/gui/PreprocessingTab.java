package plt.gui;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import plt.dataset.preprocessing.PreprocessingOperation;

/**
 *
 * @author luca
 */
public class PreprocessingTab extends Tab {

    final private Stage stage;
    private Experiment experiment;
    
    TableView featureList_tView;
        
    
    
    ObservableList<FeatureListTableRowData> tableDataSet;

    public PreprocessingTab(Stage s, Experiment e) {
        super();
        this.experiment = e;
        this.stage = s;
        setup();
    }
    
    
    private void setup()
    {
        final Stage localStage = this.stage;
        
        final BorderPane bp = new BorderPane();
        
        
        Font tabTitleFont = Font.font("BirchStd", FontWeight.BOLD, 50);
        final Label lblTabHeader = new Label("Preprocessing");
        lblTabHeader.setFont(tabTitleFont);  
        
        
        final GridPane innerPane = new GridPane();
        innerPane.setHgap(100);
        
        
        final VBox loading = this.setupLoadingVbox();
        final PreprocessingTab self = this;
        
        final FeaturesPreview features = new FeaturesPreview();

        

        innerPane.setVisible(false);
        loading.setVisible(true);
        
        
        
        
        
        // Feature List Table (NOT Feature Preview)
        
        featureList_tView = new TableView();
        featureList_tView.setPrefSize(400, 400);
        featureList_tView.setEditable(false);
        
        final HashMap<Integer,CheckBox> chkBoxMap = new HashMap<Integer,CheckBox>();
        final HashMap<Integer,Button> preProBtnMap = new HashMap<Integer,Button>();
        
        
        TableColumn col1 = new TableColumn("Include?");
        col1.setPrefWidth(70);
        col1.setCellValueFactory(new PropertyValueFactory("rowID"));
        
        
        
        col1.setCellFactory(new Callback<TableColumn<FeatureListTableRowData,Integer>,TableCell<FeatureListTableRowData,Integer>>(){       

            @Override
            public TableCell<FeatureListTableRowData, Integer> call(TableColumn<FeatureListTableRowData, Integer> p)
            {
                TableCell<FeatureListTableRowData, Integer> cell = new TableCell<FeatureListTableRowData, Integer>(){

	            @Override
	            public void updateItem(Integer item, boolean empty) 
                    {
                        if(item!=null)
                        {
                           final int rowNum = item;
                            
                           CheckBox chkBox;
                           
                           if(chkBoxMap.containsKey(rowNum))
                           {
                               chkBox = chkBoxMap.get(rowNum);
                           }
                           else
                           {
                               chkBox = new CheckBox();
                           
                               chkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

                                     @Override
                                     public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean newValue) 
                                     {
                                         experiment.ignoredFeaturesProperty().get()[rowNum] = !newValue;

                                         FeatureListTableRowData fRowData = tableDataSet.get(rowNum);
                                         fRowData.setIncludeFlag(newValue);
                                         tableDataSet.set(rowNum, fRowData);



                                         
                                     }
                                });
                                chkBox.setSelected(true);
                                
                                chkBoxMap.put(rowNum, chkBox);
                           }
                           
                           
	                   setGraphic(chkBox);
                                                   
	                }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }


	});
        
        TableColumn col2 = new TableColumn("Feature");
        col2.setPrefWidth(180);
        col2.setCellValueFactory(new PropertyValueFactory("rowID"));
        col2.setCellFactory(new Callback<TableColumn<FeatureListTableRowData,Integer>,TableCell<FeatureListTableRowData,Integer>>(){       

            @Override
            public TableCell<FeatureListTableRowData, Integer> call(TableColumn<FeatureListTableRowData, Integer> p)
            {
                TableCell<FeatureListTableRowData, Integer> cell = new TableCell<FeatureListTableRowData, Integer>(){

	            @Override
	            public void updateItem(Integer item, boolean empty) 
                    {
                        if(item!=null)
                        {
                           final int rowNum = item;
                            
                           FeatureListTableRowData fRowData = tableDataSet.get(rowNum);
                           Label columnData = new Label(fRowData.getFeatureName());
                           setGraphic(columnData);                                            
	                }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }


	});
        
        
        TableColumn col3 = new TableColumn("Preprocessing Type");
        col3.setPrefWidth(148);
        col3.setCellValueFactory(new PropertyValueFactory("rowID"));
        col3.setCellFactory(new Callback<TableColumn<FeatureListTableRowData,Integer>,TableCell<FeatureListTableRowData,Integer>>(){       

            @Override
            public TableCell<FeatureListTableRowData, Integer> call(TableColumn<FeatureListTableRowData, Integer> p)
            {
                TableCell<FeatureListTableRowData, Integer> cell = new TableCell<FeatureListTableRowData, Integer>(){

	            @Override
	            public void updateItem(Integer item, boolean empty) 
                    {
                        if(item!=null)
                        {
                           final int rowNum = item;
                           
                           FeatureListTableRowData fRowData = tableDataSet.get(rowNum);
                           final Button btnPreProSelection;
                           if(preProBtnMap.containsKey(rowNum))
                           {
                               btnPreProSelection = preProBtnMap.get(rowNum);
                           }
                           else
                           {       
                               btnPreProSelection = new Button(fRowData.getCurrPreProOpName());
                           }
                           
                           btnPreProSelection.setPrefWidth(150);
                           btnPreProSelection.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent t) {
                                    ProcessingOperatorSelector p = new ProcessingOperatorSelector(rowNum, btnPreProSelection, experiment);
                                    p.show(localStage.getScene().getRoot(), new EventHandler() {

                                        @Override
                                        public void handle(Event t) {
                                            
                                            PreprocessingOperation po = experiment.preprocessingOperationsProperty().get()[rowNum];
                                            
                                            btnPreProSelection.setText(po.getOperationName());
                                            
                                            featureList_tView.getSelectionModel().select(rowNum);
                                            features.refreshTable(experiment, rowNum);
                                        }
                                    });
                                }
                            });
                           
                           
                           
                           
                           preProBtnMap.put(rowNum, btnPreProSelection);
                           
                           setGraphic(btnPreProSelection);                                       
	                }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }


	});
        
        
        
        
        featureList_tView.getColumns().addAll(col1,col2,col3);       
        
                
        
        
        
        
        VBox fListTViewPane = new VBox(10);
        fListTViewPane.setPadding(new Insets(10));
        fListTViewPane.setAlignment(Pos.CENTER);
        fListTViewPane.getStyleClass().add("modulePane1");
        
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        final Label lblTViewHeader = new Label("Available Features");
        lblTViewHeader.setFont(headerFont);
       
        Pane wrapperPane = new Pane();
        wrapperPane.getChildren().add(featureList_tView);
        
        
        Label padLbl = new Label(" \n ");
        
        GridPane effectAllGPane = new GridPane();
        effectAllGPane.setPadding(new Insets(10,2,10,0));
        effectAllGPane.setStyle("-fx-border-radius: 1; -fx-border-color: black");
        
        CheckBox chkIncludeAll = new CheckBox();
        chkIncludeAll.setSelected(true);
        GridPane.setHalignment(chkIncludeAll, HPos.CENTER);
        GridPane.setValignment(chkIncludeAll, VPos.CENTER);
        chkIncludeAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean newValue) 
            {
                int numOfFeatures = experiment.dataSetProperty().get().getNumberOfFeatures();
                for(int i=0; i<numOfFeatures; i++)
                {
                    // Let the individual check boxes handle logic changes.                    
                    chkBoxMap.get(i).setSelected(newValue);
                }
            }
       });
        
        
        Label lblAllFeatures = new Label("INCLUDE ALL");
        lblAllFeatures.setFont(headerFont);
        GridPane.setHalignment(lblAllFeatures, HPos.CENTER);
        GridPane.setValignment(lblAllFeatures, VPos.CENTER);
                
        final Button btnPreProTypeAll = new Button("Preprocess ALL");
        btnPreProTypeAll.setPrefWidth(180);
        GridPane.setHalignment(btnPreProTypeAll, HPos.LEFT);
        GridPane.setValignment(btnPreProTypeAll, VPos.CENTER);
        btnPreProTypeAll.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent t) 
            {
                        
                ProcessingOperatorSelectorALL p = new ProcessingOperatorSelectorALL(btnPreProTypeAll, experiment);
                p.show(localStage.getScene().getRoot(), new EventHandler()
                {

                    @Override
                    public void handle(Event t)
                    {
                        int numOfFeatures = experiment.dataSetProperty().get().getNumberOfFeatures();
                        
                        for(int i=0; i<numOfFeatures; i++)
                        {
                            PreprocessingOperation po = experiment.preprocessingOperationsProperty().get()[i];

                            if(preProBtnMap.containsKey(i))
                            {
                                Button tmpBtn = preProBtnMap.get(i);
                                tmpBtn.setText(po.getOperationName());
                                preProBtnMap.put(i, tmpBtn);
                            }
                        }
                        
                        featureList_tView.getSelectionModel().select(0);
                        features.refreshTable(experiment, 0);
                    }
                });
                
                
            }
        });       
        
        effectAllGPane.add(chkIncludeAll, 0, 0);
        effectAllGPane.add(lblAllFeatures, 1, 0);
        effectAllGPane.add(btnPreProTypeAll, 2, 0);
        effectAllGPane.getColumnConstraints().add(new ColumnConstraints(70));
        effectAllGPane.getColumnConstraints().add(new ColumnConstraints(180));
        effectAllGPane.getColumnConstraints().add(new ColumnConstraints(148));
        
        HBox padBox = new HBox();
        padBox.setAlignment(Pos.CENTER);
        padBox.setPadding(new Insets(5));
        
        
              
        fListTViewPane.getChildren().addAll(lblTViewHeader, effectAllGPane, wrapperPane, padBox);
        
        
        
        VBox fPreviewPane = new VBox(10);
        fPreviewPane.setPadding(new Insets(5));
        fPreviewPane.setAlignment(Pos.CENTER);
        fPreviewPane.getStyleClass().add("modulePane1");
        
        Label lblFeaturePreview = new Label("Feature Preview");
        lblFeaturePreview.setFont(headerFont);
        
        Pane wrapperPane2 = new Pane();
        wrapperPane2.getChildren().add(features.getContent());
        //wrapperPane2.getStyleClass().add("modulePane1Child");
        
        fPreviewPane.getChildren().addAll(lblFeaturePreview,wrapperPane2);
        
        
        Pane tmpPane1 = new Pane();
        tmpPane1.getChildren().add(fListTViewPane);
        Pane tmpPane2 = new Pane();
        tmpPane2.getChildren().add(fPreviewPane);
        
        innerPane.add(tmpPane1, 0, 0);
        innerPane.add(tmpPane2, 1, 0);
        innerPane.setAlignment(Pos.CENTER);
        
        
        final Pane wPane = new Pane();
        wPane.getChildren().add(innerPane);
        
        
        final VBox tmpVBox = new VBox(10);
        tmpVBox.setAlignment(Pos.CENTER);
        
        
        final ScrollPane sPane = new ScrollPane();  
        sPane.setStyle("-fx-background-color: transparent;"); // Hide the scrollpane gray border.
        sPane.setPrefSize(880,600);
        
        
        stage.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1){
                
                sPane.setPrefHeight(t1.doubleValue() * 0.7);
            }
            
        });
        
        if(loading.isVisible())
        {
            //tmpVBox.getChildren().addAll(lblTabHeader,loading);
            sPane.setContent(loading);
        }
        else
        {
            //tmpVBox.getChildren().addAll(lblTabHeader,new Group(wPane));
            sPane.setContent(innerPane);
        }
        
       
        tmpVBox.getChildren().addAll(lblTabHeader,sPane);
        
        
        bp.setCenter(tmpVBox);        
        this.setContent(bp);
        
        
        
        
        
        
        this.experiment.isParsingProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean newValue) {
                if (newValue) {
                    
                    tableDataSet = FXCollections.observableArrayList();
                    featureList_tView.setItems(tableDataSet);
                    
                    innerPane.setVisible(false);
                    loading.setVisible(true);
                    
                    tmpVBox.getChildren().clear();
                    tmpVBox.getChildren().addAll(lblTabHeader,loading);
                    
                    
                    
                }
            }
        });

        this.experiment.isReadyToUseDataSetProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean newValue) {
                if (newValue) {
                    
                    tableDataSet = createTableDataSet();
                    featureList_tView.setItems(tableDataSet);
                    
                    loading.setVisible(false);
                    innerPane.setVisible(true);
                    
                    tmpVBox.getChildren().clear();
                    tmpVBox.getChildren().addAll(lblTabHeader,new Group(wPane));
                    
                    
                    if(features.table.getItems().size() != 0)
                    {
                        featureList_tView.getSelectionModel().select(0);
                    }
                }
            }
        });
        
        featureList_tView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FeatureListTableRowData>(){

            @Override
            public void changed(ObservableValue<? extends FeatureListTableRowData> ov, FeatureListTableRowData t, FeatureListTableRowData t1) {
                features.refreshTable(experiment, t1.rowID.get());
            }
            
        });
        
        
        
        
        
    }
    
    public ObservableList<FeatureListTableRowData> createTableDataSet()
    {
        ObservableList<FeatureListTableRowData> dataSet =  FXCollections.observableArrayList();
        
        int numOfFeatures = experiment.dataSetProperty().get().getNumberOfFeatures();
        
        for(int i=0; i<numOfFeatures; i++)
        {
            String featureName = experiment.dataSetProperty().get().getFeatureName(i);
            
            PreprocessingOperation po = experiment.preprocessingOperationsProperty().get()[i];
            
            dataSet.add(new FeatureListTableRowData(i, true, featureName, "Default"));
        }
        
        return dataSet;
    }
    
   
    
    public static class FeatureListTableRowData
    {
        private final SimpleIntegerProperty rowID;
        private final SimpleBooleanProperty includeFlag;
        private final SimpleStringProperty featureName;
        private final SimpleStringProperty currPreProOpName;
        
        public FeatureListTableRowData(int para_rowID,
                                       boolean para_includeFlag,
                                       String para_featureName,
                                       String para_currPreProOpName)
        {
            this.rowID = new SimpleIntegerProperty(para_rowID);
            this.includeFlag = new SimpleBooleanProperty(para_includeFlag);
            this.featureName = new SimpleStringProperty(para_featureName);
            this.currPreProOpName = new SimpleStringProperty(para_currPreProOpName);
        }
        
        public int getRowID()
        {
            return rowID.get();
        }
        
        public boolean getIncludeFlag()
        {
            return includeFlag.get();
        }
        
        public String getFeatureName()
        {
            return featureName.get();
        }
        
        public String getCurrPreProOpName()
        {
            return currPreProOpName.get();
        }
        
        public void setRowID(int para_rowID)
        {
            rowID.set(para_rowID);
        }
        
        public void setIncludeFlag(boolean para_applyPreProFlag)
        {
            includeFlag.set(para_applyPreProFlag);
        }
        
        public void setFeatureName(String para_featureName)
        {
            featureName.set(para_featureName);
        }
        
        public void setCurrPreProOpName(String para_currPreProOpName)
        {
            currPreProOpName.set(para_currPreProOpName);
        }
    }
    
    

    private VBox setupLoadingVbox() {
        VBox vbox = new VBox();
        final Text text = new Text("No data set loaded");
        text.setFont(Font.font("Helvetica-neue", FontWeight.SEMI_BOLD, 20));
        ImageView image = new ImageView(new Image(DataSetTab.class.getResourceAsStream("dataset_empty.png")));
        ColorAdjust ca = new ColorAdjust();
        ca.setSaturation(-1);
        image.setEffect(ca);
        vbox.getChildren().addAll(image, text);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
    
}

/*
 * final Pane mainPane = new Pane();
        
        GridPane featureListGPane = new GridPane();
        featureListGPane.setPadding(new Insets(0,20,20,20));
        featureListGPane.setHgap(15);
        featureListGPane.setVgap(12);
        
        Label lblFeatureList_SectionHeader = new Label("Available Features");
        Label lblApplyPreprocessing_ColumnHeader = new Label("Apply Preprocessing?");
        Label lblFeatureName_ColumnHeader = new Label("Feature");
        Label lblPreprocessingType_ColumnHeader = new Label("Preprocessing Type");
        
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        lblFeatureList_SectionHeader.setFont(headerFont);
        lblApplyPreprocessing_ColumnHeader.setFont(headerFont);
        lblFeatureName_ColumnHeader.setFont(headerFont);
        lblPreprocessingType_ColumnHeader.setFont(headerFont);
        
        featureListGPane.add(lblApplyPreprocessing_ColumnHeader, 0, 0);
        featureListGPane.add(lblFeatureName_ColumnHeader, 1, 0);
        featureListGPane.add(lblPreprocessingType_ColumnHeader, 2, 0);
                
        BorderPane featureList_mainPane = new BorderPane();
        BorderPane.setAlignment(lblFeatureList_SectionHeader, Pos.CENTER);
        BorderPane.setAlignment(featureListGPane, Pos.CENTER);
        featureList_mainPane.setTop(lblFeatureList_SectionHeader);
        featureList_mainPane.setCenter(featureListGPane);
 * 
 */