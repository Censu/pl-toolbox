/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import plt.dataset.preprocessing.Ignoring;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.featureselection.examples.NBest;
import plt.featureselection.examples.SFS;
import plt.gui.component.ModalPopup;
import plt.model.Model;
import plt.report.Report;

/**
 *
 * @author luca
 */
public class ResultViewer {
    Experiment experiment;
    Report report;
    
    public ResultViewer(Experiment experiment, Report report) {
        this.experiment = experiment;
        this.report = report;
    }
    
    public void show(final Stage stage) {
        
        final Parent parent = stage.getScene().getRoot();
         ModalPopup p = new ModalPopup();
         
         
         BorderPane mainBPane = new BorderPane();
         mainBPane.setPadding(new Insets(20));
         
         Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 20);
         Label lblReportHeader = new Label("Experiment Report");
         lblReportHeader.setFont(headerFont);      
         
         Font subsectionHeaderFont = Font.font("BirchStd", FontWeight.BOLD, 15);
         
         int maxPathStrWidth = 30;
         
         BorderPane bpDatasetUsed = new BorderPane();
         bpDatasetUsed.setStyle("-fx-border-radius: 1; -fx-border-color: black");
         Label lblDatasetUsedHeader = new Label("Dataset Used");
         lblDatasetUsedHeader.setFont(subsectionHeaderFont);
         BorderPane.setAlignment(lblDatasetUsedHeader, Pos.CENTER);
         bpDatasetUsed.setTop(lblDatasetUsedHeader);
         
         VBox innerPaneDatasetUsed = new VBox();
         innerPaneDatasetUsed.setPadding(new Insets(20,10,10,10));
         GridPane gPaneDatasetUsed = new GridPane();
         gPaneDatasetUsed.setHgap(20);
         gPaneDatasetUsed.setVgap(5);
         Label lblObjFileName = new Label("Object File Name:");
         Label lblObjFilePath = new Label("Object File Path:");
         Label lblRankFileName = new Label("Rank File Name:");
         Label lblRankFilePath = new Label("Rank File Path:");
         gPaneDatasetUsed.add(lblObjFileName, 0, 0);
         gPaneDatasetUsed.add(new Label(""+experiment.idataProperty().get().getName()), 1, 0);
         gPaneDatasetUsed.add(lblRankFileName, 0, 1);
         gPaneDatasetUsed.add(new Label(adjustPathStrSpacing(""+experiment.orderProperty().get().getName(),maxPathStrWidth)), 1, 1);
         gPaneDatasetUsed.add(lblObjFilePath, 0, 2);
         gPaneDatasetUsed.add(new Label(adjustPathStrSpacing(""+experiment.idataProperty().get().getAbsolutePath(),maxPathStrWidth)), 1, 2);
         gPaneDatasetUsed.add(lblRankFilePath, 0, 3);
         gPaneDatasetUsed.add(new Label(adjustPathStrSpacing(""+experiment.orderProperty().get().getAbsolutePath(),maxPathStrWidth)), 1, 3);
         innerPaneDatasetUsed.getChildren().add(gPaneDatasetUsed);
         
         
         bpDatasetUsed.setCenter(innerPaneDatasetUsed);
         
         
         BorderPane bpFeaturesIncluded = new BorderPane();
         bpFeaturesIncluded.setStyle("-fx-border-radius: 1; -fx-border-color: black");
         Label lblFeaturesIncludedHeader = new Label("User Included Features and Preprocessing");
         lblFeaturesIncludedHeader.setFont(subsectionHeaderFont);
         BorderPane.setAlignment(lblFeaturesIncludedHeader, Pos.CENTER);
         bpFeaturesIncluded.setTop(lblFeaturesIncludedHeader);
         
         VBox innerPaneFeaturesIncluded = new VBox();
         innerPaneFeaturesIncluded.setPadding(new Insets(20,10,10,10));
         GridPane gPaneFeaturesIncluded = new GridPane();
         gPaneFeaturesIncluded.setHgap(20);
         gPaneFeaturesIncluded.setVgap(5);
         
         int currRow = 0;
         PreprocessingOperation[] preProOps = experiment.preprocessingOperationsProperty().get();
         for(int i=0; i<preProOps.length; i++)
         {
             PreprocessingOperation tmpPreProOp = preProOps[i];
             if(! (tmpPreProOp instanceof Ignoring))
             {
                 Label lblFeatureName = new Label(experiment.dataSetProperty().get().getFeatureName(tmpPreProOp.getFeature()));
                 Label lblPreProOp = new Label(tmpPreProOp.getOperationName());
                 
                 gPaneFeaturesIncluded.add(lblFeatureName, 0, currRow);
                 gPaneFeaturesIncluded.add(lblPreProOp, 1, currRow);
                 currRow++;
             }
         }
         
         innerPaneFeaturesIncluded.getChildren().add(gPaneFeaturesIncluded);
         
         bpFeaturesIncluded.setCenter(innerPaneFeaturesIncluded);
         
         
         BorderPane bpFeatureSelection = new BorderPane();
         bpFeatureSelection.setStyle("-fx-border-radius: 1; -fx-border-color: black");
         Label lblFeatureSelectionHeader = new Label("Feature Selection");
         lblFeatureSelectionHeader.setFont(subsectionHeaderFont);
         BorderPane.setAlignment(lblFeatureSelectionHeader, Pos.CENTER);
         bpFeatureSelection.setTop(lblFeatureSelectionHeader);
         
         VBox innerPaneFeatureSelection = new VBox(10);
         innerPaneFeatureSelection.setPadding(new Insets(20,10,10,10));
         
         if(experiment.featureSelectionProperty().get() == null)
         {
             innerPaneFeatureSelection.getChildren().add(new Label("NONE"));
         }
         else
         {
            GridPane gPaneFeatureSelection = new GridPane();
            gPaneFeatureSelection.setHgap(20);
            gPaneFeatureSelection.setVgap(5);

            Label lblFeatureSelectionType = new Label("Feature Selection Type");
            gPaneFeatureSelection.add(lblFeatureSelectionType, 0, 0);

            String fselTName = "Unknown F Selection Type";
            if(experiment.featureSelectionProperty().get() instanceof NBest)
            {
                fselTName = "N-Best";
            }
            else if(experiment.featureSelectionProperty().get() instanceof SFS)
            {
                fselTName = "SFS";
            }

            gPaneFeatureSelection.add(new Label(fselTName), 1, 0);
            
            innerPaneFeatureSelection.getChildren().add(gPaneFeatureSelection);
            
            BorderPane fselPane = new BorderPane();
            Label lblFSelHeader = new Label("Selected Features");
            lblFSelHeader.setFont(subsectionHeaderFont);
            fselPane.setTop(lblFSelHeader);
            
            GridPane gPaneSelections = new GridPane();
            gPaneSelections.setHgap(20);
            gPaneSelections.setVgap(5);
            
            int[] selFeatures = experiment.featureSelectionProperty().get().getResult().getSelectedFeatures();
            for(int i=0; i<selFeatures.length; i++)
            {
                String tmpFName = experiment.dataSetProperty().get().getFeatureName(i);
                gPaneSelections.add(new Label(tmpFName), 0, i);
            }
            
            fselPane.setCenter(gPaneSelections);
            innerPaneFeatureSelection.getChildren().add(fselPane);
            

            ArrayList<Object[]> reqFSelAlgProperties = experiment.algorithmForFeatureSelectionProperty().get().getProperties();
            for(int k=0; k<reqFSelAlgProperties.size(); k++)
            {
                
                Object[] tmpWrapper = reqFSelAlgProperties.get(k);
                String title = (String) tmpWrapper[0];
                ArrayList<String[]> dataPairs = (ArrayList<String[]>) tmpWrapper[1];

                Label lblTitle = new Label(title);
                lblTitle.setFont(subsectionHeaderFont);
                
                GridPane tmpGPane = new GridPane();
                tmpGPane.setHgap(20);
                tmpGPane.setVgap(5);
                
                for(int counter=0; counter<dataPairs.size(); counter++)
                {
                    String[] tmpDataPair = dataPairs.get(counter);

                    for(int c2=0; c2<tmpDataPair.length; c2++)
                    {
                       tmpGPane.add(new Label(tmpDataPair[c2]), c2, counter);                        
                    }
                }

                BorderPane tmpBPane = new BorderPane();
                BorderPane.setAlignment(lblTitle, Pos.CENTER_LEFT);
                tmpBPane.setTop(lblTitle);
                tmpBPane.setCenter(tmpGPane);
                
                innerPaneFeatureSelection.getChildren().add(tmpBPane);
            }
            
            
            BorderPane validationBPane = new BorderPane();
            Label lblValidationHeader = new Label("Validation");
            lblValidationHeader.setFont(subsectionHeaderFont);
            
            GridPane validationGPane = new GridPane();
            validationGPane.setHgap(20);
            validationGPane.setVgap(5);
            
            
            if(experiment.useValidatorForFeatureSelectionProperty().get())
            {
                validationBPane.setTop(lblValidationHeader);
                        
                validationGPane.add(new Label("Type:"), 0, 0);
                validationGPane.add(new Label("K-Fold"), 1, 0);
                validationGPane.add(new Label("# Folds:"), 0, 1);
                validationGPane.add(new Label(experiment.kForFeatureSelectionProperty().get()), 1, 1);
                
                validationBPane.setCenter(validationGPane);
            }
            else
            {
                validationGPane.add(lblValidationHeader, 0, 0);
                validationGPane.add(new Label("NONE"), 1, 0);
                
                validationBPane.setCenter(validationGPane);
            }
            innerPaneFeatureSelection.getChildren().add(validationBPane);
         }
                
         bpFeatureSelection.setCenter(innerPaneFeatureSelection);
         
         
         
         BorderPane bpPrefLearningMethods = new BorderPane();
         bpPrefLearningMethods.setStyle("-fx-border-radius: 1; -fx-border-color: black");
         Label lblPrefLearningMethodsHeader = new Label("Preference Learning Methods");
         lblPrefLearningMethodsHeader.setFont(subsectionHeaderFont);
         BorderPane.setAlignment(lblPrefLearningMethodsHeader, Pos.CENTER);
         bpPrefLearningMethods.setTop(lblPrefLearningMethodsHeader);
         
         VBox innerPanePrefLearningMethods = new VBox(10);
         innerPanePrefLearningMethods.setPadding(new Insets(20,10,10,10));
         
         if(experiment.algorithmProperty().get() == null)
         {
             innerPanePrefLearningMethods.getChildren().add(new Label("NONE"));
         }
         else
         {
             GridPane gPanePrefLearningMethods = new GridPane();
             gPanePrefLearningMethods.setHgap(20);
             gPanePrefLearningMethods.setVgap(5);

             
 
              
             ArrayList<Object[]> reqPrefMethodAlgProperties = experiment.algorithmProperty().get().getProperties();
             for(int k=0; k<reqPrefMethodAlgProperties.size(); k++)
             {
                 
 
                 Object[] tmpWrapper = reqPrefMethodAlgProperties.get(k);
                 String title = (String) tmpWrapper[0];
                 ArrayList<String[]> dataPairs = (ArrayList<String[]>) tmpWrapper[1];
 
                 Label lblTitle = new Label(title);
                 lblTitle.setFont(subsectionHeaderFont);
                 
                 GridPane tmpGPane = new GridPane();
                 tmpGPane.setHgap(20);
                 tmpGPane.setVgap(5);
                 
                 for(int counter=0; counter<dataPairs.size(); counter++)
                 {
                     String[] tmpDataPair = dataPairs.get(counter);
 
                     for(int c2=0; c2<tmpDataPair.length; c2++)
                     {
                        tmpGPane.add(new Label(tmpDataPair[c2]), c2, counter);                        
                     }
                 }
 
                 
                 BorderPane tmpBPane = new BorderPane();
                 BorderPane.setAlignment(lblTitle, Pos.CENTER_LEFT);
                 tmpBPane.setTop(lblTitle);
                 tmpBPane.setCenter(tmpGPane);
                
                 innerPanePrefLearningMethods.getChildren().add(tmpBPane);   
             }
             
             BorderPane validationBPane = new BorderPane();
             Label lblValidationHeader = new Label("Validation");
             lblValidationHeader.setFont(subsectionHeaderFont);
            
             GridPane validationGPane = new GridPane();
             validationGPane.setHgap(20);
             validationGPane.setVgap(5);
             
             
             if(experiment.useValidatorProperty().get())
             {
                 validationBPane.setTop(lblValidationHeader);
                         
                 validationGPane.add(new Label("Type:"), 0, 0);
                 validationGPane.add(new Label("K-Fold"), 1, 0);
                 validationGPane.add(new Label("# Folds:"), 0, 1);
                 validationGPane.add(new Label(experiment.kProperty().get()), 1, 1);
                 
                 validationBPane.setCenter(validationGPane);
             }
             else
             {
                 validationGPane.add(lblValidationHeader, 0, 0);
                 validationGPane.add(new Label("NONE"), 1, 0);
                 
                 validationBPane.setCenter(validationGPane);
             }
             innerPanePrefLearningMethods.getChildren().add(validationBPane);
             
         }
          
         bpPrefLearningMethods.setCenter(innerPanePrefLearningMethods);
         
         
         
         
         
         BorderPane bpGenModels = new BorderPane();
         bpGenModels.setStyle("-fx-border-radius: 1; -fx-border-color: black");
         Label lblGenModelsHeader = new Label("Generated Models");
         lblGenModelsHeader.setFont(subsectionHeaderFont);
         BorderPane.setAlignment(lblGenModelsHeader, Pos.CENTER);
         bpGenModels.setTop(lblGenModelsHeader);
         
         
         
         Label lblModelAccuracy = new Label("Accuracy Values for Models");
         lblModelAccuracy.setFont(subsectionHeaderFont);
         
         
         ObservableList<ModelTableDataRow> data = FXCollections.observableArrayList();
         
         double averageAccuracy = 0;
         if(! experiment.useValidatorProperty().get())
         {
             data.add(new ModelTableDataRow(0, "Training Accuracy", this.report.resultAccurancy(0)));
             averageAccuracy = this.report.resultAccurancy(0);
         }
         else
         {
            double tmpSum = 0;
            for (int i=0; i<this.report.numberOfResults();i++)
            {
               data.add(new ModelTableDataRow(i, "Fold "+(i+1), this.report.resultAccurancy(i) * 100));
               tmpSum += this.report.resultAccurancy(i);
            }
            
            averageAccuracy = (tmpSum/this.report.numberOfResults());
         }
         averageAccuracy = ((Math.round(averageAccuracy * 100) * 1000) / 1000);
         
         
         TableColumn col1 = new TableColumn("Model Name");
         col1.setPrefWidth(100);
         col1.setCellValueFactory(new PropertyValueFactory("modelName"));
         TableColumn col2 = new TableColumn("Accuracy (%)");
         col2.setPrefWidth(350); // Since ScrollPane width is 450.
         col2.setCellValueFactory(new PropertyValueFactory("modelAccuracy"));
         
         final TableView modelTView = new TableView(data);
         modelTView.setPrefHeight((this.report.numberOfResults() + 1) * 30);
         modelTView.getColumns().addAll(col1,col2);
         
         GridPane gPaneAvrgAccuracy = new GridPane();
         gPaneAvrgAccuracy.setHgap(20);
         gPaneAvrgAccuracy.setVgap(5);
         Label lblAverageAccuracy = new Label("Average Accuracy: ");
         lblAverageAccuracy.setFont(subsectionHeaderFont);
         gPaneAvrgAccuracy.add(lblAverageAccuracy, 0, 0);
         gPaneAvrgAccuracy.add(new Label(""+averageAccuracy+"%"), 1, 0);
         
         VBox innerPaneGenModels = new VBox();
         innerPaneGenModels.setPadding(new Insets(20,10,10,10));
         innerPaneGenModels.getChildren().addAll(lblModelAccuracy, modelTView, gPaneAvrgAccuracy);
         
         bpGenModels.setCenter(innerPaneGenModels);
         
         
         
         
         final Button save = new Button("Save model");
         save.disableProperty().setValue(this.report.numberOfResults() != 1);
         

         modelTView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                save.disableProperty().setValue(false);               
            }   
         });
         
         save.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
               int index = modelTView.getSelectionModel().selectedIndexProperty().get();
               if (index < 0) 
                   index = 0;
            
               Model m = report.getModel(index);
               
               
               
               try
               {
                    FileChooser fChooser = new FileChooser();
                    File file = fChooser.showSaveDialog(stage);
                    //m.save(file);
                    m.save(file,experiment);
                    
                    ModalPopup notification = new ModalPopup();
                    notification.show(new Label("SUCCESS: The selected model has been saved."), stage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  
               }
               catch(IOException ex)
               {
                   ModalPopup notification = new ModalPopup();
                   notification.show(new Label("FAILED: The selected model COULD NOT be saved!"), stage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  
               }
               
            }
         
         
         });
         
         
         VBox mContentVBox = new VBox(20);
         mContentVBox.setAlignment(Pos.CENTER);
         mContentVBox.setPadding(new Insets(10,10,10,20));
         mContentVBox.getChildren().add(bpDatasetUsed);
         mContentVBox.getChildren().add(bpFeaturesIncluded);
         mContentVBox.getChildren().add(bpFeatureSelection);
         mContentVBox.getChildren().add(bpPrefLearningMethods);
         mContentVBox.getChildren().add(bpGenModels);
         
         BorderPane cPane = new BorderPane();
         BorderPane.setAlignment(mContentVBox, Pos.CENTER);
         cPane.setCenter(mContentVBox);
         
         ScrollPane sPane = new ScrollPane();
         sPane.setPrefSize(450, 600);
         sPane.setContent(cPane);
         
         mainBPane.setTop(lblReportHeader);
         mainBPane.setCenter(sPane);
         
         //p.show(mainBPane, parent, null, save, new Button("Close"),500,600);
         p.show(mainBPane, parent, null, save, new Button("Close"),500,600,true);
    }
    
    public String adjustPathStrSpacing(String para_srcStr, int para_maxWidthInChars)
    {
        String retStr = "";
        
        if(! para_srcStr.contains(File.pathSeparator)) { return para_srcStr; }
        
        String[] splitStrs = para_srcStr.split(File.pathSeparator);
        int currRowWidth = 0;
        
        for(int i=0; i<splitStrs.length; i++)
        {
            String nxtSubStr = splitStrs[i];
            if( (currRowWidth + (nxtSubStr.length() + 1)) < para_maxWidthInChars )
            {
                // No need to add a newline.
                currRowWidth += (nxtSubStr.length() + 1);
            }
            else
            {
                // Add a newline.
                retStr += System.getProperty("line.separator");
                currRowWidth = 0;
            }
            retStr += (nxtSubStr + File.pathSeparator);
        }
        
        return retStr;
    }
    
    
    
    public static class ModelTableDataRow
    {
        
        private final SimpleIntegerProperty rowID;
        private final SimpleStringProperty modelName;
        private final SimpleDoubleProperty modelAccuracy;
        
        public ModelTableDataRow(int para_rowID,
                                 String para_modelName,
                                 double para_modelAccuracy)
        {
            this.rowID = new SimpleIntegerProperty(para_rowID);
            this.modelName = new SimpleStringProperty(para_modelName);
            this.modelAccuracy = new SimpleDoubleProperty(para_modelAccuracy);
        }
        
        public int getRowID()
        {
            return rowID.get();
        }
        
        public String getModelName()
        {
            return modelName.get();
        }
        
        public double getModelAccuracy()
        {
            return modelAccuracy.get();
        }
        
        
        
        public void setRowID(int para_rowID)
        {
            rowID.set(para_rowID);
        }
        
        public void setModelName(String para_modelName)
        {
            modelName.set(para_modelName);
        }
        
        public void setModelAccuracy(double para_modelAccuracy)
        {
            modelAccuracy.set(para_modelAccuracy);
        }
        
    }
}
