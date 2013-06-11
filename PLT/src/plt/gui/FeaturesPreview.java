/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import plt.dataset.preprocessing.NumericBinary;
import plt.dataset.preprocessing.PreprocessingOperation;

/**
 *
 * @author luca
 */
public class FeaturesPreview  {
    VBox content;
    TableView<RowModel> table; 
    
    public FeaturesPreview() {
        content = new VBox(10);
        
        BorderPane tmpPane = new BorderPane();
        tmpPane.setPrefHeight(50);
        tmpPane.setPadding(new Insets(5,2,5,0));
        tmpPane.setStyle("-fx-border-radius: 1; -fx-border-color: black");
                
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        Label lblBlank = new Label(" NO SELECTION ");
        lblBlank.setFont(headerFont);
        BorderPane.setAlignment(lblBlank, Pos.CENTER);
        tmpPane.setCenter(lblBlank);
        
        
        table = new TableView<>();
        table.setPrefSize(400, 400);
        
        HBox blankFooter = new HBox();
        blankFooter.setSpacing(15);
        blankFooter.setAlignment(Pos.CENTER);
        blankFooter.setPadding(new Insets(10));
        
        content.getChildren().addAll(tmpPane,table,blankFooter);
        
        table.setPlaceholder(new Label("Select a feature to see the preview"));
    }
    
    public Node getContent() {
        return content;
        
    }

    void refreshTable(final Experiment experiment, final int value) {
        this.refreshTable(experiment, value, 0);
    }
    
    void refreshTable(final Experiment experiment, final int value, final int page) {
        final int limit = 10;
        Label label = new Label();
        final int numberOfObjects = experiment.dataSetProperty().getValue().getNumberOfObjects();
        String name = experiment.dataSetProperty().getValue().getFeatureName(value);
        
        
        int idOfLastObservableRow = (page*limit) + (limit-1);
        if((numberOfObjects-1) < idOfLastObservableRow) { idOfLastObservableRow = (numberOfObjects-1); }
        
        label.setText("Preview of feature: "+capitaliseFirstLetter(name)+"\n"+
                      "Displaying Objects ["+(page*limit)+".."+idOfLastObservableRow+"]");
        
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-padding: 2;");
        
        
        table.setPrefSize(400, 400);
        table.setPlaceholder(new Label("loading..."));
        table.getColumns().clear();
        
        HBox pagination = new HBox();
        pagination.setSpacing(15);
        pagination.setAlignment(Pos.CENTER);
        pagination.setPadding(new Insets(10));
        
        content.getChildren().set(2, pagination);
        
        
       int firstPrevPage = (page-1)*limit;
       if (firstPrevPage >= 0) {
            Button next = new Button("["+firstPrevPage+".."+(firstPrevPage+limit-1)+"]");
            pagination.getChildren().add(next);
            next.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    refreshTable(experiment, value, page-1);
                }
           
            });
        }
        
        int firstNextPage = (page+1)*limit;
        int lastEntryOnNxtPage = (firstNextPage+limit-1);
        if(lastEntryOnNxtPage > (numberOfObjects-1)) { lastEntryOnNxtPage = (numberOfObjects-1); }
        
        if (firstNextPage < numberOfObjects) {
            Button next = new Button("["+firstNextPage+".."+lastEntryOnNxtPage+"]");
            pagination.getChildren().add(next);
            next.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    refreshTable(experiment, value, page+1);
                }
           
            });
        }
        
        Pane tmpPane = new Pane();
        tmpPane.setPadding(new Insets(5,2,0,0));
        tmpPane.setStyle("-fx-border-radius: 1; -fx-border-color: black");
        tmpPane.getChildren().add(label);
        
        content.getChildren().set(0, tmpPane);
        Task<ObservableList<RowModel>> task = new Task<ObservableList<RowModel>>() {

            @Override
            protected ObservableList<RowModel> call() throws Exception {
                PreprocessingOperation preprocessingOperation = experiment.preprocessingOperationsProperty().get()[value];
                ObservableList<RowModel> data = FXCollections.observableArrayList();

                int rows = experiment.dataSetProperty().get().getNumberOfObjects();

                for (int i=page*limit; i< (page+1)*limit; i++) {
                    if (i<rows)
                        data.add(new RowModel(preprocessingOperation, i));
                }

                return data;
            }
        };

        task.valueProperty().addListener(new ChangeListener<ObservableList<RowModel>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<RowModel>> ov, ObservableList<RowModel> t, ObservableList<RowModel> t1) {
                table.setItems(t1);
                PreprocessingOperation preprocessingOperation = experiment.preprocessingOperationsProperty().get()[value];
                
                
                int widthPerTextChar = 11;
                
                String firstColumnName = "Object ID";
                TableColumn rowColumn = new TableColumn(firstColumnName);
                rowColumn.setPrefWidth(widthPerTextChar * firstColumnName.length());
                table.getColumns().add(rowColumn);
               
                rowColumn.setCellValueFactory(new Callback<CellDataFeatures<RowModel, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<RowModel, String> p) {
                        return new ReadOnlyObjectWrapper(p.getValue().getRow());
                    }
                });

                
                // Adjust column widths in order to remove table view empty space.
                int idColumnWidth = widthPerTextChar * firstColumnName.length();
                int tablePWidth = (int) table.getPrefWidth();
                int remainingSpaceForCols = tablePWidth - idColumnWidth - 2;
                
                float prefColWidth = remainingSpaceForCols / (preprocessingOperation.numberOfOutput() * 1.0f);
                float minColWidth = 90;
                
                if(prefColWidth < minColWidth)
                {
                    prefColWidth = minColWidth;
                }
                // End of column width adjustment.
                
                
                for (int i=0; i< preprocessingOperation.numberOfOutput(); i++) {

                        final int ii = i;
                        
                    
                        String columnName = "";
                        
                        
                        PreprocessingOperation[] currPreProcessingOps = experiment.preprocessingOperationsProperty().get();
                        PreprocessingOperation reqPreProcessingOp = currPreProcessingOps[value];
                        
                        if(reqPreProcessingOp instanceof NumericBinary)
                        {
                            columnName = ("BinSet "+ii);
                        }
                        else
                        { 
                            String featureName = experiment.dataSetProperty().getValue().getFeatureName(preprocessingOperation.getFeature());
                            featureName = capitaliseFirstLetter(featureName);
                            columnName = featureName;
                        }
                        
                        
                        
                        
                        TableColumn column = new TableColumn(columnName);
                        //if(i <)
                        //column.setPrefWidth(widthPerTextChar * columnName.length());
                        column.setPrefWidth(prefColWidth);
                        
                        column.setCellValueFactory(new Callback<CellDataFeatures<RowModel, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<RowModel, String> p) {
                                return new ReadOnlyObjectWrapper(p.getValue().getValue(ii));
                            }
                        });

                        table.getColumns().add(column);
                }
                
                
            }
        });
        new Thread(task).start();
    }
    
    String capitaliseFirstLetter(String sourceStr) {
        
        String resStr = sourceStr;
        
        char firstChar = sourceStr.charAt(0);
        if((Character.isLetter(firstChar))
        &&(Character.isLowerCase(firstChar))){
           firstChar = Character.toUpperCase(firstChar);
           resStr = firstChar + sourceStr.substring(1);
        }
        
        return resStr;
    }
    
    class RowModel {

        private PreprocessingOperation p;
        private int row;

        RowModel(PreprocessingOperation p, int row) {
            this.p = p;
            this.row = row;
        }

        double getValue(int n) {
            return p.value(row, n);
        }

        int getRow() {
            return this.row;
        }
    }
    
}
