/*                   GNU LESSER GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.


  This version of the GNU Lesser General Public License incorporates
the terms and conditions of version 3 of the GNU General Public
License, supplemented by the additional permissions listed below.

  0. Additional Definitions.

  As used herein, "this License" refers to version 3 of the GNU Lesser
General Public License, and the "GNU GPL" refers to version 3 of the GNU
General Public License.

  "The Library" refers to a covered work governed by this License,
other than an Application or a Combined Work as defined below.

  An "Application" is any work that makes use of an interface provided
by the Library, but which is not otherwise based on the Library.
Defining a subclass of a class defined by the Library is deemed a mode
of using an interface provided by the Library.

  A "Combined Work" is a work produced by combining or linking an
Application with the Library.  The particular version of the Library
with which the Combined Work was made is also called the "Linked
Version".

  The "Minimal Corresponding Source" for a Combined Work means the
Corresponding Source for the Combined Work, excluding any source code
for portions of the Combined Work that, considered in isolation, are
based on the Application, and not on the Linked Version.

  The "Corresponding Application Code" for a Combined Work means the
object code and/or source code for the Application, including any data
and utility programs needed for reproducing the Combined Work from the
Application, but excluding the System Libraries of the Combined Work.

  1. Exception to Section 3 of the GNU GPL.

  You may convey a covered work under sections 3 and 4 of this License
without being bound by section 3 of the GNU GPL.

  2. Conveying Modified Versions.

  If you modify a copy of the Library, and, in your modifications, a
facility refers to a function or data to be supplied by an Application
that uses the facility (other than as an argument passed when the
facility is invoked), then you may convey a copy of the modified
version:

   a) under this License, provided that you make a good faith effort to
   ensure that, in the event an Application does not supply the
   function or data, the facility still operates, and performs
   whatever part of its purpose remains meaningful, or

   b) under the GNU GPL, with none of the additional permissions of
   this License applicable to that copy.

  3. Object Code Incorporating Material from Library Header Files.

  The object code form of an Application may incorporate material from
a header file that is part of the Library.  You may convey such object
code under terms of your choice, provided that, if the incorporated
material is not limited to numerical parameters, data structure
layouts and accessors, or small macros, inline functions and templates
(ten or fewer lines in length), you do both of the following:

   a) Give prominent notice with each copy of the object code that the
   Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the object code with a copy of the GNU GPL and this license
   document.

  4. Combined Works.

  You may convey a Combined Work under terms of your choice that,
taken together, effectively do not restrict modification of the
portions of the Library contained in the Combined Work and reverse
engineering for debugging such modifications, if you also do each of
the following:

   a) Give prominent notice with each copy of the Combined Work that
   the Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the Combined Work with a copy of the GNU GPL and this license
   document.

   c) For a Combined Work that displays copyright notices during
   execution, include the copyright notice for the Library among
   these notices, as well as a reference directing the user to the
   copies of the GNU GPL and this license document.

   d) Do one of the following:

       0) Convey the Minimal Corresponding Source under the terms of this
       License, and the Corresponding Application Code in a form
       suitable for, and under terms that permit, the user to
       recombine or relink the Application with a modified version of
       the Linked Version to produce a modified Combined Work, in the
       manner specified by section 6 of the GNU GPL for conveying
       Corresponding Source.

       1) Use a suitable shared library mechanism for linking with the
       Library.  A suitable mechanism is one that (a) uses at run time
       a copy of the Library already present on the user's computer
       system, and (b) will operate properly with a modified version
       of the Library that is interface-compatible with the Linked
       Version.

   e) Provide Installation Information, but only if you would otherwise
   be required to provide such information under section 6 of the
   GNU GPL, and only to the extent that such information is
   necessary to install and execute a modified version of the
   Combined Work produced by recombining or relinking the
   Application with a modified version of the Linked Version. (If
   you use option 4d0, the Installation Information must accompany
   the Minimal Corresponding Source and Corresponding Application
   Code. If you use option 4d1, you must provide the Installation
   Information in the manner specified by section 6 of the GNU GPL
   for conveying Corresponding Source.)

  5. Combined Libraries.

  You may place library facilities that are a work based on the
Library side by side in a single library together with other library
facilities that are not Applications and are not covered by this
License, and convey such a combined library under terms of your
choice, if you do both of the following:

   a) Accompany the combined library with a copy of the same work based
   on the Library, uncombined with any other library facilities,
   conveyed under the terms of this License.

   b) Give prominent notice with the combined library that part of it
   is a work based on the Library, and explaining where to find the
   accompanying uncombined form of the same work.

  6. Revised Versions of the GNU Lesser General Public License.

  The Free Software Foundation may publish revised and/or new versions
of the GNU Lesser General Public License from time to time. Such new
versions will be similar in spirit to the present version, but may
differ in detail to address new problems or concerns.

  Each version is given a distinguishing version number. If the
Library as you received it specifies that a certain numbered version
of the GNU Lesser General Public License "or any later version"
applies to it, you have the option of following the terms and
conditions either of that published version or of any later version
published by the Free Software Foundation. If the Library as you
received it does not specify a version number of the GNU Lesser
General Public License, you may choose any version of the GNU Lesser
General Public License ever published by the Free Software Foundation.

  If the Library as you received it specifies that a proxy can decide
whether future versions of the GNU Lesser General Public License shall
apply, that proxy's public statement of acceptance of any version is
permanent authorization for you to choose that version for the
Library.*/

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
 * Original Pre PLT v1.0
 * @author Luca Querella <lucq@itu.dk>
 * 
 * Modified for PLT v1.0
 * @author Vincent E. Farrugia <vincent.e.farrugia@gmail.com>
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
