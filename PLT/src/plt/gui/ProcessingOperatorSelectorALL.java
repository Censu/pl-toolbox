package plt.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import plt.dataset.preprocessing.*;
import plt.gui.component.ModalPopup;


public class ProcessingOperatorSelectorALL extends ModalPopup {

    private final BorderPane bp;
    private final Experiment experiment;
    private final Button button;
    
    public ProcessingOperatorSelectorALL(Button b, Experiment exp) {
        super();
        this.experiment = exp;
        this.button = b;
        this.bp = new BorderPane();
    }

    public void show(Parent parent, final EventHandler eventHandler) {
        
        final TextField minTextField = new TextField("0");
        minTextField.setPrefWidth(50);
        final TextField maxTextField = new TextField("1");
        maxTextField.setPrefWidth(50);


        bp.setPadding(new Insets(25));
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        
        // Non-Numeric Data Settings.
        BorderPane nonNumericDataPane = new BorderPane();
        nonNumericDataPane.setPadding(new Insets(10));
        nonNumericDataPane.setStyle("-fx-border-radius: 1; -fx-border-color: black");
        
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        Label lblNonNumericHeader = new Label("Non-Numeric Features");
        lblNonNumericHeader.setFont(headerFont);
        BorderPane.setAlignment(lblNonNumericHeader, Pos.CENTER);
        nonNumericDataPane.setTop(lblNonNumericHeader);
        
        ToggleGroup nonNumericDataToggle = new ToggleGroup();
        final RadioButton nonNPane_rb1 = new RadioButton("Nominal");
        final RadioButton nonNPane_rb2 = new RadioButton("Binary representation");
        nonNPane_rb1.setToggleGroup(nonNumericDataToggle);
        nonNPane_rb2.setToggleGroup(nonNumericDataToggle);
        VBox nonNumericDPane_innerVBox = new VBox(10);
        nonNumericDPane_innerVBox.getChildren().addAll(nonNPane_rb1, nonNPane_rb2);

        nonNumericDataToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle oldValue, Toggle newValue)
            {
                PreprocessingOperation[] pos = experiment.preprocessingOperationsProperty().get();

                int numOfFeatures = experiment.dataSetProperty().get().getNumberOfFeatures();
                
                if (nonNPane_rb1.isSelected()) 
                {    
                    for(int i=0; i<numOfFeatures; i++)
                    {
                        if(! experiment.dataSetProperty().get().isNumeric(i) )
                        {
                            pos[i] = new Nominal(experiment.dataSetProperty().get(), i);
                        }
                    }
                } 
                else 
                {
                    for(int i=0; i<numOfFeatures; i++)
                    {
                        if(! experiment.dataSetProperty().get().isNumeric(i) )
                        {
                            pos[i] = new Binary(experiment.dataSetProperty().get(), i, null);
                        }
                    }
                }
            }
        });

        nonNPane_rb1.setSelected(true);
        
        nonNumericDataPane.setLeft(nonNumericDPane_innerVBox);
        
        
        
        // Numeric Data Settings.
        final BorderPane numericDataPane = new BorderPane();
        numericDataPane.setPadding(new Insets(10));
        numericDataPane.setStyle("-fx-border-radius: 1; -fx-border-color: black");
        
        Label lblNumericHeader = new Label("Numeric Features");
        lblNumericHeader.setFont(headerFont);
        BorderPane.setAlignment(lblNumericHeader, Pos.CENTER);
        numericDataPane.setTop(lblNumericHeader);
        
        ToggleGroup numericDataToggle = new ToggleGroup();
        final RadioButton nPane_rb1 = new RadioButton("Default Value");
        final RadioButton nPane_rb2 = new RadioButton("Min Max Normalization");
        final RadioButton nPane_rb3 = new RadioButton("Binary Representation");
        final RadioButton nPane_rb4 = new RadioButton("Z-Score Normalization");
        nPane_rb1.setToggleGroup(numericDataToggle);
        nPane_rb2.setToggleGroup(numericDataToggle);
        nPane_rb3.setToggleGroup(numericDataToggle);
        nPane_rb4.setToggleGroup(numericDataToggle);
        VBox nDataPane_innerVBox = new VBox(10);
        nDataPane_innerVBox.getChildren().addAll(nPane_rb1, nPane_rb2, nPane_rb3, nPane_rb4);
        
        numericDataToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1)
            {

                PreprocessingOperation[] pos = experiment.preprocessingOperationsProperty().get();

                int numOfFeatures = experiment.dataSetProperty().get().getNumberOfFeatures();
                
                numericDataPane.setRight(null);
                
                
                if (nPane_rb1.isSelected())
                {
                    for(int i=0; i<numOfFeatures; i++)
                    {
                        if( experiment.dataSetProperty().get().isNumeric(i) )
                        {
                            pos[i] = new Numeric(experiment.dataSetProperty().get(), i);
                        }
                    }
                } 
                else if (nPane_rb2.isSelected()) 
                {
                    for(int i=0; i<numOfFeatures; i++)
                    {
                        if( experiment.dataSetProperty().get().isNumeric(i) )
                        {
                            pos[i] = new MinMax(experiment.dataSetProperty().get(), i, 0, 1);
                        }
                    }

                    GridPane grid = new GridPane();
                    grid.setPadding(new Insets(20));

                    Label minLabel = new Label("Min:");

                    Label maxLabel = new Label("Max:");

                    grid.add(minLabel, 0, 0);
                    grid.add(minTextField, 1, 0);
                    grid.add(maxLabel, 0, 2);
                    grid.add(maxTextField, 1, 2);
                    numericDataPane.setRight(grid);

                } 
                else if (nPane_rb3.isSelected())
                {
                    for(int i=0; i<numOfFeatures; i++)
                    {
                        if( experiment.dataSetProperty().get().isNumeric(i) )
                        {
                            pos[i] = new NumericBinary(experiment.dataSetProperty().get(), i, null);
                        }
                    }
                } 
                else
                {
                    for(int i=0; i<numOfFeatures; i++)
                    {
                        if( experiment.dataSetProperty().get().isNumeric(i) )
                        {
                            pos[i] = new ZScore(experiment.dataSetProperty().get(), i);
                        }
                    }
                }

            }
        });

        nPane_rb1.setSelected(true);
        
        numericDataPane.setLeft(nDataPane_innerVBox);
        

        
        vbox.getChildren().add(nonNumericDataPane);
        vbox.getChildren().add(numericDataPane);
        bp.setCenter(vbox);
        

        super.show(bp, parent, new EventHandler() {

            @Override
            public void handle(Event t) {
                PreprocessingOperation[] pos = experiment.preprocessingOperationsProperty().get();

                int numOfFeatures = experiment.dataSetProperty().get().getNumberOfFeatures();
                
                for(int i=0; i<numOfFeatures; i++)
                {
                    if (pos[i] instanceof MinMax) {
                        double min = 0, max = 1;
                        try {
                            min = Double.parseDouble(minTextField.textProperty().get());
                            max = Double.parseDouble(maxTextField.textProperty().get());
                        } catch (Exception e) {
                            min = 0;
                            max = 1;
                        }

                        if (min >= max) {
                            min = 0;
                            max = 1;
                        }
                        pos[i] = new MinMax(experiment.dataSetProperty().get(), i, min, max);
                    }

                    //button.setText(pos[item].toString());
                }
                
                eventHandler.handle(t);
            }
        }, null,400,400,false);
    }
}
