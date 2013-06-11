package plt.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import plt.dataset.preprocessing.*;
import plt.gui.component.ModalPopup;

/**
 *
 * @author luca
 */
public class ProcessingOperatorSelector extends ModalPopup {

    private final BorderPane bp;
    private final Experiment experiment;
    private final int item;
    private final Button button;
    
    public ProcessingOperatorSelector(int i, Button b, Experiment exp) {
        super();
        this.experiment = exp;
        this.item = i;
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
        ToggleGroup toggle = new ToggleGroup();
        vbox.setSpacing(10);


        if (!experiment.dataSetProperty().get().isNumeric(item)) {
            final RadioButton rb1 = new RadioButton("Nominal");
            rb1.setToggleGroup(toggle);

            final RadioButton rb2 = new RadioButton("Binary representation");
            rb2.setToggleGroup(toggle);
            vbox.getChildren().addAll(rb1, rb2);

            toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle oldValue, Toggle newValue) {
                    PreprocessingOperation[] pos = experiment.preprocessingOperationsProperty().get();

                    if (rb1.isSelected()) {
                        pos[item] = new Nominal(experiment.dataSetProperty().get(), item);
                    } else {
                        pos[item] = new Binary(experiment.dataSetProperty().get(), item, null);
                    }


                }
            });

            PreprocessingOperation po = experiment.preprocessingOperationsProperty().get()[item];
            rb1.setSelected(po instanceof Nominal);
            rb2.setSelected(po instanceof Binary);



        } else {
            final RadioButton rb1 = new RadioButton("Default Value");
            rb1.setToggleGroup(toggle);

            final RadioButton rb2 = new RadioButton("Min Max Normalization");
            rb2.setToggleGroup(toggle);

            final RadioButton rb3 = new RadioButton("Binary representation");
            rb3.setToggleGroup(toggle);
            
            RadioButton rb4 = new RadioButton("Z-Score Normalization");
            rb4.setToggleGroup(toggle);


            vbox.getChildren().addAll(rb1, rb2, rb3, rb4);

            toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                    PreprocessingOperation[] pos = experiment.preprocessingOperationsProperty().get();

                    bp.setRight(null);

                    if (rb1.isSelected()) {
                        pos[item] = new Numeric(experiment.dataSetProperty().get(), item);
                    } else if (rb2.isSelected()) {
                        pos[item] = new MinMax(experiment.dataSetProperty().get(), item, 0, 1);

                        GridPane grid = new GridPane();
                        grid.setPadding(new Insets(20));

                        Label minLabel = new Label("Min:");

                        Label maxLabel = new Label("Max:");

                        grid.add(minLabel, 0, 0);
                        grid.add(minTextField, 1, 0);
                        grid.add(maxLabel, 0, 2);
                        grid.add(maxTextField, 1, 2);
                        bp.setRight(grid);

                    } else if (rb3.isSelected()) {
                        pos[item] = new NumericBinary(experiment.dataSetProperty().get(), item, null);
                    } else {
                        pos[item] = new ZScore(experiment.dataSetProperty().get(), item);
                    }

                }
            });

            PreprocessingOperation po = experiment.preprocessingOperationsProperty().get()[item];
            rb1.setSelected(po instanceof Numeric);
            rb3.setSelected(po instanceof Binary);
            rb2.setSelected(po instanceof MinMax);
            rb4.setSelected(po instanceof ZScore);

            if (po instanceof MinMax) {
                MinMax minMax = (MinMax) po;
                minTextField.textProperty().set(minMax.getMin() + "");
                maxTextField.textProperty().set(minMax.getMax() + "");
            }

        }

        bp.setLeft(vbox);
        


        super.show(bp, parent, new EventHandler() {

            @Override
            public void handle(Event t) {
                PreprocessingOperation[] pos = experiment.preprocessingOperationsProperty().get();

                if (pos[item] instanceof MinMax) {
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
                    pos[item] = new MinMax(experiment.dataSetProperty().get(), item, min, max);
                }

                //button.setText(pos[item].toString());
                
                eventHandler.handle(t);
            }
        }, null, false);
    }
}
