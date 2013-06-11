package plt.gui;

import java.util.logging.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import plt.gui.customcomponents.ExecutionModalPopup;
import plt.report.Report;

/**
 *
 * @author luca
 */
public class Execution  {

    private final Experiment experiment;
    private final Logger logger;
    
    Thread execThread;
    boolean terminatedFlag;
    Report latestReport;
    
    public Execution(Experiment exp) {
        super();
        this.experiment = exp;
        this.logger =  Logger.getLogger("plt.logger");
        
        execThread = null;
        terminatedFlag = false;
        latestReport = null;
    }

    public void show(final Stage stage) {

        final Parent parent = stage.getScene().getRoot();
        final Execution self = this;
        final Button button = new Button("Running..");
        button.disableProperty().set(true);
        
        ListView<String> list = new ListView<>();
        list.setMaxHeight(300);
        list.setMaxWidth(450);
        final ObservableList<String> items = FXCollections.observableArrayList();
        list.setItems(items);
        
        final HBox progressUpdateHBox = new HBox(10);
        ProgressBar pBar = new ProgressBar(0);
        pBar.progressProperty().bind(ExecutionProgress.totProgress);
        pBar.setPrefSize(460,20);
        ProgressIndicator pIndi = new ProgressIndicator(-1);
        pIndi.setPrefSize(20, 20);
        final Button btnAbort = new Button("Abort");
        btnAbort.setPrefSize(150, 20);
        //btnAbort.setGraphic(new ImageView(new Image(Execution.class.getResourceAsStream("halt_icon.gif"))));
        EventHandler<MouseEvent> abortHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                terminatedFlag = true;
                if(execThread != null)
                {
                    execThread.interrupt();
                }
            }
        };
        progressUpdateHBox.getChildren().addAll(pBar,pIndi,btnAbort);
        
        
        final ExecutionModalPopup emp = new ExecutionModalPopup();
        emp.show(parent, list, progressUpdateHBox, btnAbort, abortHandler, 400, 600);

        logger.addHandler(new Handler() {

            @Override
            public void publish(LogRecord lr) {
                final String log = lr.getMessage();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        items.add(log);
                    }
                });
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });
        
        Task<Report> task = new Task<Report>() {

            @Override
            protected Report call() throws Exception {
                               
                return self.experiment.start();
            }
        };

        
        
        task.valueProperty().addListener(new ChangeListener<Report>() {

            @Override
            public void changed(ObservableValue<? extends Report> ov, Report t, Report t1) {
                
                if(! terminatedFlag)
                {
                    latestReport = t1;
                    
                    progressUpdateHBox.getChildren().clear();
                    btnAbort.setText("Close Console");
                    
                    Button btnGenerateReport = new Button("Generate Report");
                    btnGenerateReport.setOnMouseClicked(new EventHandler<MouseEvent>() 
                    {
                        @Override
                        public void handle(MouseEvent t) 
                        {
                            button.disableProperty().set(false);
                            button.textProperty().set("Close console");
                            ResultViewer v = new ResultViewer(experiment,latestReport);
                            v.show(stage);
                        }
                    });
                    
                    Button btnCompleteNotice = new Button("Execution Success");
                    btnCompleteNotice.setStyle("-fx-background-color: linear-gradient(#35C700, #227D01);"+"-fx-background-radius: 8;"+"-fx-background-insets: 0;"+"-fx-text-fill: white;");
                    
                    
                    progressUpdateHBox.getChildren().addAll(btnCompleteNotice,btnGenerateReport,btnAbort);
                    
                    button.disableProperty().set(false);
                    button.textProperty().set("Close console");
                    ResultViewer v = new ResultViewer(experiment,latestReport);
                    v.show(stage);
                }

            }
        });
        
        execThread = new Thread(task);
        execThread.start();
    }
    
    
}