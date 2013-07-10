package plt.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author luca
 */
public class Main extends Application
{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        
        MasterGUI mGUI = new MasterGUI(stage);
        
        Scene mainScene2 = new Scene(mGUI, 1000, 900);
        mainScene2.getStylesheets().add(Main.class.getResource("MainCSS.css").toExternalForm());
        stage.setScene(mainScene2);
        stage.setTitle("Preference Learning Toolbox (PLT)");
        stage.getIcons().add(new Image(DataSetTab.class.getResourceAsStream("plt_logo.png")));
        stage.setResizable(true);
        stage.show();
        
        
        stage.setOnCloseRequest(new CloseHandler());
    }
    
    class CloseHandler implements EventHandler<WindowEvent>
    {
        @Override
        public void handle(WindowEvent t)
        {
            ExecutionProgress.shutdownProgram = true;
            while(! ExecutionProgress.safeToShutdown())
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }    
        }   
    }
}