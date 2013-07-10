package plt.gui;

import java.util.Calendar;
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
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import plt.gui.customcomponents.ExecutionModalPopup;
import plt.report.Report;
import plt.utils.TimeHelper;

/**
 *
 * @author luca
 */
public class Execution  {

    private final Experiment experiment;
    private final Logger logger;
    
    Thread execThread;
    Thread timerThread;
    boolean terminatedFlag;
    Report latestReport;
    
    Text txtCurrDuration;
    Calendar cStart;
    
    boolean haltTimerThread;
    
    public Execution(Experiment exp) {
        super();
        this.experiment = exp;
        this.logger =  Logger.getLogger("plt.logger");
        
        execThread = null;
        terminatedFlag = false;
        latestReport = null;
        
        haltTimerThread = false;
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
        
        final VBox progressUpdateBotBox = new VBox(2);
        final BorderPane progressUpdateFirstHBox = new BorderPane();
        Text txtCurrTask = new Text("test");                // WARNING: Using Label instead of Text hangs the program.
        txtCurrTask.textProperty().bind(ExecutionProgress.currTaskTextIndicator);
        txtCurrDuration = new Text("");
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
                    ExecutionProgress.requestThreadInterrupt(1); // Algorithm thread.
                    ExecutionProgress.requestThreadInterrupt(2); // Timer thread.
                }
            }
        };
        progressUpdateFirstHBox.setLeft(txtCurrTask);
        progressUpdateFirstHBox.setRight(txtCurrDuration);
        progressUpdateHBox.getChildren().addAll(pBar,pIndi,btnAbort);
        progressUpdateBotBox.getChildren().addAll(progressUpdateFirstHBox,progressUpdateHBox);
        
        
        final ExecutionModalPopup emp = new ExecutionModalPopup();
        emp.show(parent, list, progressUpdateBotBox, btnAbort, abortHandler, 400, 600);

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
        
        Task<Report> task = new Task<Report>() 
        {
            int threadID = 1;
            
            @Override
            protected Report call() throws Exception {
           
                ExecutionProgress.registerThread(threadID);
                return self.experiment.start();
            }
        };

        
        
        task.valueProperty().addListener(new ChangeListener<Report>() {

            @Override
            public void changed(ObservableValue<? extends Report> ov, Report t, Report t1) {
                
                if(! terminatedFlag)
                {
                    latestReport = t1;
                    
                    haltTimerThread = true;
                    
                    
                    progressUpdateBotBox.getChildren().clear();
                    progressUpdateFirstHBox.getChildren().clear();
                    
                    
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
                    progressUpdateBotBox.getChildren().add(progressUpdateHBox);
                    
                    button.disableProperty().set(false);
                    button.textProperty().set("Close console");
                    ResultViewer v = new ResultViewer(experiment,latestReport);
                    v.show(stage);
                }

            }
        });
        
        timerThread = new Thread(new TimerTask());
        timerThread.start();
        
        execThread = new Thread(task);
        execThread.start();
    }
    
    class TimerTask implements Runnable
    {
        int threadID = 2;
        
        public TimerTask()
        {
            cStart = Calendar.getInstance();
        }

        @Override
        public void run() 
        {
            ExecutionProgress.registerThread(threadID);
            while((! haltTimerThread)&&(! ExecutionProgress.shutdownProgram)&&(! ExecutionProgress.hasInterruptRequest(threadID)))
            {
                Calendar currTStamp = Calendar.getInstance();
                txtCurrDuration.setText( TimeHelper.calculateDuration(cStart, currTStamp) );
            }
            
            if((ExecutionProgress.needToShutdown())||(ExecutionProgress.hasInterruptRequest(threadID)))
            {
                ExecutionProgress.signalDeactivation(threadID);
            }
        }
    }
    
    
}