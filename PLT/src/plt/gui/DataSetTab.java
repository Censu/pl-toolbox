package plt.gui;

import java.awt.GridBagConstraints;
import java.awt.MouseInfo;
import java.awt.Point;
import plt.gui.component.ModalPopup;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;
import plt.dataset.sushireader.UramakiFileParseStatus;


import plt.gui.customcomponents.ButtonedTitledPane;
import plt.gui.customcomponents.ContentNActionPane;
import plt.help.HelpDataStore;

/**
 *
 * @author luca
 */
public class DataSetTab extends Tab {

    private Stage stage;
    private Experiment experiment;
    //private HelpDataStore helpStore;

        
    String latestWorkingDir;
    String latestObjectFilePath;
    String latestRankingFilePath;
    
    
    ContentNActionPane iDataPane;
    ContentNActionPane orderDataPane;
    
    Pane customToolTipNode;
    Popup toolTip;
    
    
    Button loadIDataFileBtn;
    Button loadOrderDataFileBtn;
    
    
    public DataSetTab(Stage s, Experiment e) {
        super();
        this.experiment = e;
        this.stage = s;
        //this.helpStore = hStore;
        
        latestWorkingDir = null;
        latestObjectFilePath = null;
        latestRankingFilePath = null;
        
        setup();
    }

    private void setup() {
        BorderPane bp = new BorderPane();
        this.setContent(bp);

        Font tabTitleFont = Font.font("BirchStd", FontWeight.BOLD, 50);
        Label lblTabHeader = new Label("Dataset Setup");
        lblTabHeader.setFont(tabTitleFont);
        
        
        final Text text = new Text("Load both the object file and the ranking \nfile before you can start.");
        text.setFont(Font.font("Helvetica-neue", FontWeight.SEMI_BOLD, 15));
        final ImageView image = new ImageView(new Image(DataSetTab.class.getResourceAsStream("dataset_empty.png")));
        image.setFitWidth(250);
        image.setFitHeight(300);
        

        
        VBox componentPane = new VBox();
        componentPane.setSpacing(20);
        
        
        
        
        // Object File Pane (IData)
        Text iDataPane_mainContent = new Text("LOAD - Object Data");
        HashMap<String,Button> iDataPane_btns = new HashMap<String,Button>();
        
        iDataPane = new ContentNActionPane(iDataPane_mainContent,iDataPane_btns);
        iDataPane.setPrefSize(450, 150);
        iDataPane.setStyleClassForAllComponents("fileLoadStatus-nofile");
        iDataPane.setButtonAlignment(Pos.CENTER);
        iDataPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new LoadIDataFileHandler());
        
        // Ranking File Pane (Order)
        Text orderDataPane_mainContent = new Text("LOAD - Ranking Data");
        HashMap<String,Button> orderDataPane_btns = new HashMap<String,Button>();
        
        orderDataPane = new ContentNActionPane(orderDataPane_mainContent,orderDataPane_btns);
        orderDataPane.setPrefSize(450,150);
        orderDataPane.setStyleClassForAllComponents("fileLoadStatus-nofile");
        orderDataPane.setButtonAlignment(Pos.CENTER);
        orderDataPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new LoadOrderDataFileHandler());
        
        componentPane.getChildren().add(iDataPane);
        componentPane.getChildren().add(orderDataPane);
              
        
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(30);
        grid.add(image,0,1);
        grid.add(componentPane,1,1);
        
        
        
        VBox tmpVBox = new VBox();
        tmpVBox.getChildren().addAll(lblTabHeader, grid);
        tmpVBox.setAlignment(Pos.CENTER);
        
        bp.setCenter(new Group(tmpVBox));
        
        
        
        final float gridHGap = (float) grid.getHgap();
        
        

        this.experiment.isReadyToUseDataSetProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    text.setText("Dataset ready to be used");
                } 
            }
        });
        
        this.experiment.isParsingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                //is parsing
                if (t1) {
                    
                    text.setText("Loading...");
                    text.visibleProperty().set(true);
                    
                    image.setImage(new Image(DataSetTab.class.getResourceAsStream("dataset_empty.png")));
                    
                } else {
                //pasing finished (assuming the worst)
                    text.setText("Dataset not valid:\nload both the object file and the ranking \nfile before you can start.");
                    text.visibleProperty().set(true);
                }
            }
        });
        
        this.experiment.hasPerformedAParseStageProperty().addListener(new ChangeListener<Boolean>() {
            
            UramakiFileParseStatus nwParseStatus;
            
            
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue)
            {
                if(newValue)
                {
                    experiment.hasPerformedAParseStageProperty().set(false);
                    
                    nwParseStatus = experiment.dataSetParseStatusProperty().getValue();
                
                    updateUI();                
                }
            } 
            
            public void updateUI()
            {
                Platform.runLater(new Runnable() {
                    @Override public void run()
                    {
                        Node[] btns = setupButtons();
                
                        if((nwParseStatus.error_iDataFile != -1)
                        &&(nwParseStatus.error_orderDataFile != -1)
                        &&(nwParseStatus.overallParseResult))
                        {
                            Parent parent = stage.getScene().getRoot();
                            MasterGUI mGUI = (MasterGUI) parent;
                            mGUI.enableTabs(new ArrayList<Integer>(Arrays.asList(1,2,3)));
                            
                            image.setImage(new Image(DataSetTab.class.getResourceAsStream("dataset_valid.png")));
                            
                            ModalPopup notification = new ModalPopup();
                            notification.show(new Label("Valid Dataset Loaded - You may now proceed to preprocess the data."), stage.getScene().getRoot(),null,new Button("OK"), 200,550,false);  
                        }
                        else if(  ((nwParseStatus.error_iDataFile == 0)&&(nwParseStatus.error_orderDataFile == -1))
                                ||((nwParseStatus.error_iDataFile == -1)&&(nwParseStatus.error_orderDataFile == 0))
                               )
                        {
                            Parent parent = stage.getScene().getRoot();
                            MasterGUI mGUI = (MasterGUI) parent;
                            mGUI.disableTabs(new ArrayList<Integer>(Arrays.asList(1,2,3)));
                            
                            image.setImage(new Image(DataSetTab.class.getResourceAsStream("dataset_empty.png")));
                        }
                        else
                        {
                            Parent parent = stage.getScene().getRoot();
                            MasterGUI mGUI = (MasterGUI) parent;
                            mGUI.disableTabs(new ArrayList<Integer>(Arrays.asList(1,2,3)));
                            
                            image.setImage(new Image(DataSetTab.class.getResourceAsStream("dataset_error.png")));
                        }
                        
                        if(nwParseStatus.error_iDataFile == 1)
                        {   
                            // Invalid IData File.
                            
                                                       
                            
                            GridPane errorGPane = new GridPane();
                            errorGPane.setHgap(10);
                            Text lblErrorTitle = new Text(experiment.idataProperty().get().getName());
                            Text lblReason = new Text(nwParseStatus.error_iDataFile_reason);
                            errorGPane.add(new Text("Invalid Object File:"), 0, 0);
                            errorGPane.add(new Text("Reason:"), 0, 1);
                            errorGPane.add(lblErrorTitle, 1, 0);
                            errorGPane.add(lblReason, 1, 1);
                            
                            errorGPane.setAlignment(Pos.CENTER);
                            
                            Button btnIDataHelp = new Button();
                            btnIDataHelp.setFont(new Font(0));
                            btnIDataHelp.setPrefWidth(30);
                            btnIDataHelp.setPrefHeight(30);
                            btnIDataHelp.setGraphic(new ImageView(new Image(DataSetTab.class.getResourceAsStream("help_small.png"))));

                            
                            iDataPane.setMainContent(errorGPane);
                            iDataPane.addNwButton("IData_Help",btnIDataHelp);
                            iDataPane.setPrefSize(450, 150);
                            iDataPane.setStyleClassForAllComponents("fileLoadStatus-invalidfile");
                            
                            
                            // line here
                        }
                        else if(nwParseStatus.error_iDataFile == 0)
                        {
                            // Valid IData File.
                            
                            
                            
                            GridPane detailsGPane = new GridPane();
                            detailsGPane.setHgap(10);
                            

                            Text lblFileName = new Text(experiment.idataProperty().get().getName());
                            Text lblFilePath = new Text(experiment.idataProperty().get().getPath());
                            Text lblNumOfObjectsInFile = new Text(""+experiment.dataSetProperty().get().getNumberOfObjects());
                            Text lblNumOfFeaturesInFile = new Text(""+experiment.dataSetProperty().get().getNumberOfFeatures());
                            
                            lblFilePath.setFont(new Font(10));
                            lblFilePath.wrappingWidthProperty().set(100);
                            
                            detailsGPane.add(new Text("Valid Object File:"), 0, 0);
                            detailsGPane.add(lblFileName, 1, 0);
                            detailsGPane.add(new Text("File Path:"), 0, 1);
                            detailsGPane.add(lblFilePath, 1, 1);
                            detailsGPane.add(new Text("# Objects:"), 0, 2);
                            detailsGPane.add(lblNumOfObjectsInFile, 1, 2);
                            detailsGPane.add(new Text("# Features:"), 0, 3);
                            detailsGPane.add(lblNumOfFeaturesInFile, 1, 3);
                            
                            detailsGPane.setAlignment(Pos.CENTER);
                            
                            iDataPane.setMainContent(detailsGPane);
                            iDataPane.removeButton("IData_Help");
                            iDataPane.setPrefSize(450, 150);
                            iDataPane.setStyleClassForAllComponents("fileLoadStatus-validfile");
                        }

                        if(nwParseStatus.error_orderDataFile == 1)
                        {
                            // Invalid Order File.
                            
                                                        
                            GridPane errorGPane = new GridPane();
                            errorGPane.setHgap(10);
                            Text lblErrorTitle = new Text(experiment.orderProperty().get().getName());
                            Text lblReason = new Text(nwParseStatus.error_orderDataFile_reason);
                            errorGPane.add(new Text("Invalid Ranking File:"), 0, 0);
                            errorGPane.add(new Text("Reason:"), 0, 1);
                            errorGPane.add(lblErrorTitle, 1, 0);
                            errorGPane.add(lblReason, 1, 1);
                            
                            errorGPane.setAlignment(Pos.CENTER);
                            
                            Button btnIDataHelp = new Button();
                            btnIDataHelp.setFont(new Font(0));
                            btnIDataHelp.setPrefWidth(30);
                            btnIDataHelp.setPrefHeight(30);
                            btnIDataHelp.setGraphic(new ImageView(new Image(DataSetTab.class.getResourceAsStream("help_small.png"))));

                            
                            orderDataPane.setMainContent(errorGPane);
                            orderDataPane.addNwButton("OrderData_Help",btnIDataHelp);
                            orderDataPane.setPrefSize(450, 150);
                            orderDataPane.setStyleClassForAllComponents("fileLoadStatus-invalidfile");
                            
                            
                            
                        }
                        else if(nwParseStatus.error_orderDataFile == 0)
                        {   
                            // Valid Order File.
                            
                            
                            
                            GridPane detailsGPane = new GridPane();
                            detailsGPane.setHgap(10);
                            

                            Text lblFileName = new Text(experiment.orderProperty().get().getName());
                            Text lblFilePath = new Text(experiment.orderProperty().get().getPath());
                            Text lblNumOfPreferencesInFile = new Text(""+experiment.dataSetProperty().get().getNumberOfPreferences());
                            
                            lblFilePath.setFont(new Font(10));
                            lblFilePath.wrappingWidthProperty().set(90);
                            
                            detailsGPane.add(new Text("Valid Ranking File:"), 0, 0);
                            detailsGPane.add(lblFileName, 1, 0);
                            detailsGPane.add(new Text("File Pathf:"), 0, 1);
                            detailsGPane.add(lblFilePath, 1, 1);
                            detailsGPane.add(new Text("# Preferences:"), 0, 2);
                            detailsGPane.add(lblNumOfPreferencesInFile, 1, 2);
                            
                            
                            detailsGPane.setAlignment(Pos.CENTER);
                            
                            orderDataPane.setMainContent(detailsGPane);
                            orderDataPane.removeButton("OrderData_Help");
                            orderDataPane.setPrefSize(450, 150);
                            orderDataPane.setStyleClassForAllComponents("fileLoadStatus-validfile");
                            
                            
                            
                        }
                        
                        
                        
                        /*if(nwParseStatus.error_iDataFile == 0)
                        {
                            loadOrderDataFileBtn.setDisable(false);
                        }
                        else if(nwParseStatus.error_iDataFile == 1)
                        {
                            loadOrderDataFileBtn.setDisable(true);
                        }*/
                        
                    }
                });
            }
        });
        
        
    }
    
    

    private Node[] setupButtons()
    {
        loadIDataFileBtn = new Button();
        loadIDataFileBtn.setFont(new Font(0));
        loadIDataFileBtn.setPrefWidth(30);
        loadIDataFileBtn.setPrefHeight(30);
        loadIDataFileBtn.setGraphic(new ImageView(new Image(DataSetTab.class.getResourceAsStream("import icon.png"))));
        
        
        loadOrderDataFileBtn = new Button();
        loadOrderDataFileBtn.setFont(new Font(0));
        loadOrderDataFileBtn.setPrefWidth(30);
        loadOrderDataFileBtn.setPrefHeight(30);
        loadOrderDataFileBtn.setGraphic(new ImageView(new Image(DataSetTab.class.getResourceAsStream("import icon.png"))));
        loadOrderDataFileBtn.setDisable(true);
        
        
        loadIDataFileBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {

                PopupWindow pw = new PopupControl();
                pw.show(stage);

                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(stage);

                if (file != null) {
                    experiment.isReadyToParseIdataProperty().set(false);
                    
                                        
                    experiment.idataProperty().set(file);
                    setupPopupIdata();
                }
            }
        });
        
        loadOrderDataFileBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

            
            @Override
            public void handle(MouseEvent t) {

                
                PopupWindow pw = new PopupControl();
                pw.show(stage);

                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(stage);

                if (file != null) {
                    experiment.isReadyToParseOrderProperty().set(false);
                    
                    

                    
                    experiment.orderProperty().set(file);
                    setupPopupOrder();
                }
            }
        });
        
        
        Node[] retBtns = new Node[2];
        retBtns[0] = loadIDataFileBtn;
        retBtns[1] = loadOrderDataFileBtn;
        return retBtns;
    }

    private void setupPopupIdata() {


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);
        


        Label separatorLabel = new Label("Separator:");
        
        final ToggleGroup tGroup = new ToggleGroup();
        RadioButton rBtn_commaSeparator = new RadioButton("COMMA");
        RadioButton rBtn_tabSeparator = new RadioButton("TAB");
        RadioButton rBtn_spaceSeparator = new RadioButton("SPACE");
        
        boolean customSelFlag = false;
        final Label lbl_customStrSeparator = new Label("CUSTOM STRING");
        final TextField txt_customStrSeparator = new TextField("");
        txt_customStrSeparator.setPrefWidth(100);
        
        
        
        final RadioButton rBtn_customStrSeparator = new RadioButton();
        rBtn_customStrSeparator.setGraphic(lbl_customStrSeparator);
        
        rBtn_customStrSeparator.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean newValue)
            {
                if(newValue)
                {
                    rBtn_customStrSeparator.setGraphic(txt_customStrSeparator);
                }
                else
                {
                    rBtn_customStrSeparator.setGraphic(lbl_customStrSeparator);
                }
            }
        });
        
        rBtn_commaSeparator.setUserData(0);
        rBtn_tabSeparator.setUserData(1);
        rBtn_spaceSeparator.setUserData(2);
        rBtn_customStrSeparator.setUserData(3);
        
        rBtn_commaSeparator.setToggleGroup(tGroup);
        rBtn_tabSeparator.setToggleGroup(tGroup);
        rBtn_spaceSeparator.setToggleGroup(tGroup);
        rBtn_customStrSeparator.setToggleGroup(tGroup);
        tGroup.selectToggle(rBtn_commaSeparator);
        
        
        GridPane sepHelpPane = new GridPane();
        sepHelpPane.setPadding(new Insets(20,20,0,20));
        sepHelpPane.setAlignment(Pos.CENTER);
        
        
        Label separatorHelpNotice = new Label("A valid Object File conforms to the following format:\n"
                                             +"<object-id><idata-separator><feature i for object-id>");
        
        separatorHelpNotice.setWrapText(true);
        
        separatorHelpNotice.getStylesheets().add(Main.class.getResource("MainCSS.css").toExternalForm());
        separatorHelpNotice.getStyleClass().add("helpNotice");
        
        sepHelpPane.add(separatorHelpNotice,0,0);
        
        grid.add(separatorLabel, 0, 0);
        grid.add(rBtn_commaSeparator, 1, 0);
        grid.add(rBtn_tabSeparator,1,1);
        grid.add(rBtn_spaceSeparator,1,2);
        grid.add(rBtn_customStrSeparator,1,3);
        
        
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(sepHelpPane);
        mainPane.setCenter(grid);
        

  
        final Experiment e = this.experiment;
        final Parent parent = this.stage.getScene().getRoot();
        final EventHandler eventHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {

                String idataSeparator;
                
                int toggleIndex = (int) tGroup.getSelectedToggle().getUserData();
                
                switch(toggleIndex)
                {
                    case 0: idataSeparator = ","; break;
                    case 1: idataSeparator = "\t"; break;
                    case 2: idataSeparator = " "; break;
                    case 3: idataSeparator = txt_customStrSeparator.getText(); break;
                    default: idataSeparator = "\t"; break;
                }
               
                e.idataSeparatorProperty().set(idataSeparator);
                e.isReadyToParseIdataProperty().set(true);
            }
        };
        
        ModalPopup modalPopup = new ModalPopup();
        modalPopup.show(mainPane, parent, eventHandler, null, false);
    }

    private void setupPopupOrder() {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);


        Label ignoreLineLabel = new Label("Ignore lines from top:");
        final TextField ignoreLine = new TextField("0");
        ignoreLine.setPrefWidth(50);
        Label ignoreColumnsLabel = new Label("Ignore columns from left:");
        final TextField ignoreColumns = new TextField("0");
        ignoreColumns.setPrefWidth(50);
        

        
        Label separatorLabel = new Label("Separator:");
        
        final ToggleGroup tGroup = new ToggleGroup();
        RadioButton rBtn_commaSeparator = new RadioButton("COMMA");
        RadioButton rBtn_tabSeparator = new RadioButton("TAB");
        RadioButton rBtn_spaceSeparator = new RadioButton("SPACE");
        
        boolean customSelFlag = false;
        final Label lbl_customStrSeparator = new Label("CUSTOM STRING");
        final TextField txt_customStrSeparator = new TextField("");
        txt_customStrSeparator.setPrefWidth(100);
        
        
        
        final RadioButton rBtn_customStrSeparator = new RadioButton();
        rBtn_customStrSeparator.setGraphic(lbl_customStrSeparator);
        
        rBtn_customStrSeparator.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean newValue)
            {
                if(newValue)
                {
                    rBtn_customStrSeparator.setGraphic(txt_customStrSeparator);
                }
                else
                {
                    rBtn_customStrSeparator.setGraphic(lbl_customStrSeparator);
                }
            }
        });
        
        rBtn_commaSeparator.setUserData(0);
        rBtn_tabSeparator.setUserData(1);
        rBtn_spaceSeparator.setUserData(2);
        rBtn_customStrSeparator.setUserData(3);
        
        rBtn_commaSeparator.setToggleGroup(tGroup);
        rBtn_tabSeparator.setToggleGroup(tGroup);
        rBtn_spaceSeparator.setToggleGroup(tGroup);
        rBtn_customStrSeparator.setToggleGroup(tGroup);
        tGroup.selectToggle(rBtn_commaSeparator);
        
        
        GridPane sepHelpPane = new GridPane();
        sepHelpPane.setPadding(new Insets(20,20,0,20));
        sepHelpPane.setAlignment(Pos.CENTER);
        
        
        Label separatorHelpNotice = new Label("A valid Ranking File conforms to the following format:\n"
                                             +"<object1-id><ranking-separator><object2-id>");
        
        separatorHelpNotice.setWrapText(true);
        
        separatorHelpNotice.getStylesheets().add(Main.class.getResource("MainCSS.css").toExternalForm());
        separatorHelpNotice.getStyleClass().add("helpNotice");
        
        sepHelpPane.add(separatorHelpNotice,0,0);
        
        
        grid.add(ignoreLineLabel, 0, 0);
        grid.add(ignoreLine, 1, 0);
        grid.add(ignoreColumnsLabel, 0, 1);
        grid.add(ignoreColumns, 1, 1);
        grid.add(separatorLabel, 0, 2);
        grid.add(rBtn_commaSeparator, 1, 2);
        grid.add(rBtn_tabSeparator, 1, 3);
        grid.add(rBtn_spaceSeparator, 1, 4);
        grid.add(rBtn_customStrSeparator, 1, 5);
        
        
        
        
        VBox mainPane = new VBox(10);
        mainPane.getChildren().addAll(sepHelpPane,grid);
        
        
        
        
        
        
        

        final Experiment e = this.experiment;
        final Parent parent = this.stage.getScene().getRoot();
        final EventHandler eventHandler = new EventHandler<MouseEvent>() {
           

            @Override
            public void handle(MouseEvent t) {

                String orderSeparator;
                
                int toggleIndex = (int) tGroup.getSelectedToggle().getUserData();
                
                switch(toggleIndex)
                {
                    case 0: orderSeparator = ","; break;
                    case 1: orderSeparator = "\t"; break;
                    case 2: orderSeparator = " "; break;
                    case 3: orderSeparator = txt_customStrSeparator.getText(); break;
                    default: orderSeparator = "\t"; break;
                }
                
                
                e.orderSeparatorProperty().set(orderSeparator);


                try {
                    e.orderSkipColumnsProperty().set(Integer.parseInt(ignoreColumns.getText()));
                } catch (NumberFormatException exception) {
                    e.orderSkipColumnsProperty().set(0);
                }

                try {
                    e.orderSkipLinesProperty().set(Integer.parseInt(ignoreLine.getText()));
                } catch (NumberFormatException exception) {
                    e.orderSkipLinesProperty().set(0);
                }
                e.isReadyToParseOrderProperty().set(true);
                parent.setEffect(null);
            }
        };
        
        ModalPopup modalPopup = new ModalPopup();
        modalPopup.show(mainPane, parent, eventHandler, null, false);
    }
    
    
    
    class LoadIDataFileHandler implements EventHandler<MouseEvent>
    {
        @Override
            public void handle(MouseEvent t) {

                PopupWindow pw = new PopupControl();
                pw.show(stage);

                FileChooser fc = new FileChooser();
                if(latestWorkingDir != null) { fc.setInitialDirectory(new File(latestWorkingDir)); }
                File file = fc.showOpenDialog(stage);

                if (file != null) {
                    experiment.isReadyToParseIdataProperty().set(false);
                    
                    
                    
                    latestObjectFilePath = file.getAbsolutePath();
                    if(latestWorkingDir == null) { latestWorkingDir = file.getParent(); }
                    
                    experiment.idataProperty().set(file);
                    setupPopupIdata();
                }
            }
    }
    
    class LoadOrderDataFileHandler implements EventHandler<MouseEvent>
    {
        @Override
            public void handle(MouseEvent t) {

                PopupWindow pw = new PopupControl();
                pw.show(stage);

                FileChooser fc = new FileChooser();
                if(latestWorkingDir != null) { fc.setInitialDirectory(new File(latestWorkingDir)); }
                File file = fc.showOpenDialog(stage);

                if (file != null) {
                    experiment.isReadyToParseOrderProperty().set(false);
                    
                    
                    
                    latestRankingFilePath = file.getAbsolutePath();
                    if(latestWorkingDir == null) { latestWorkingDir = file.getParent(); }
                    
                    experiment.orderProperty().set(file);
                    setupPopupOrder();
                }
            }
    }
    
    class UramakiMouseEventHandler implements EventHandler<MouseEvent>
    {

        @Override
        public void handle(MouseEvent mouseEvent)
        {
            EventType eType = mouseEvent.getEventType();
            String eTypeName = eType.getName();
            
            if(((eTypeName).equals("MOUSE_ENTERED"))
            ||((eTypeName).equals("MOUSE_EXITED")))
            {
                Node uiComponent = (Node) mouseEvent.getSource();
                
                Parent parent = stage.getScene().getRoot();
                MasterGUI mGUI = (MasterGUI) parent;
                
                
                
                if((eTypeName).equals("MOUSE_ENTERED"))
                {
                    String uiComponent_id = uiComponent.getId();
                    final String tooltipHelpText = "";//helpStore.getToolTip(uiComponent_id);

                    

                    
                    ToolTipCreator nwTTCjob = new ToolTipCreator(uiComponent,tooltipHelpText);
                    Thread nwTTThread = new Thread(nwTTCjob);
                    nwTTThread.start();
                    
                                        
                
                    
                    
                    System.out.println("Testing");
                    
                    
                }
                else if((eTypeName).equals("MOUSE_EXITED"))
                {
                    
                    
                    toolTip.hide();
                }
            }
            
            
            
        }
    }
    
    class ToolTipCreator implements Runnable
    {
        long tooltipDelay_ms = 1500;
        Node uiComponent;
        String ttStr;
        
        public ToolTipCreator(Node para_uiComponent, String para_TTTextStr)
        {
            uiComponent = para_uiComponent;
            ttStr = para_TTTextStr;
        }
        
        @Override
        public void run()
        {
            try 
            {
                Thread.sleep(tooltipDelay_ms);
            }
            catch (InterruptedException ex) 
            {
                Logger.getLogger(DataSetTab.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
            if(uiComponent.hoverProperty().getValue())
            {
                // Display Tool Tip.
                
                Platform.runLater(new Runnable() 
                {
                    @Override public void run()
                    {
                        customToolTipNode = new Pane();
                        double maxToolTipWidth = 260;

                        double tooltipTextWidth = ttStr.length() * 7;

                        double actualToolTipWidth;
                        if(tooltipTextWidth < maxToolTipWidth)
                        {
                            actualToolTipWidth = tooltipTextWidth;
                        }
                        else
                        {
                            actualToolTipWidth = maxToolTipWidth;
                        }

                        int numOfTTLines = (int) (tooltipTextWidth / maxToolTipWidth); 
                        if((tooltipTextWidth % maxToolTipWidth) != 0) {numOfTTLines++;}

                        double actualToolTipHeight = numOfTTLines * 20 + 10;

                        customToolTipNode.setPrefSize(actualToolTipWidth,actualToolTipHeight);
                        customToolTipNode.setId("ToolTipBox");

                        Text ttText = new Text(10,20,ttStr);
                        ttText.setWrappingWidth(actualToolTipWidth);
                        customToolTipNode.getChildren().add(ttText);

                        toolTip.getContent().clear();
                        toolTip.getContent().add(customToolTipNode);
                        


                        Point mousePt = MouseInfo.getPointerInfo().getLocation();
                        Window reqWindow = stage.getScene().getWindow();
                        int windowX = mousePt.x - (int) reqWindow.getX();
                        int windowY = mousePt.y - (int) reqWindow.getY();
                        toolTip.show(stage.getScene().getWindow(), mousePt.x+10, mousePt.y+10);
                    }
                });
               
                
                System.out.println("Displayed Tooltip");
            }
        }
        
    }
}
