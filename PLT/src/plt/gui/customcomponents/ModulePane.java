/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui.customcomponents;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Owner
 */
public class ModulePane extends BorderPane
{
    public Text txtTitle;
    public ChoiceBox choiceBox;
    public Node mainContent;
    
    HBox headerHBox;
    private VBox frameBox;
    Pane housingPane;
    
    String mainStyleClass;
    
    int prefWidth;
    
    public ModulePane(String para_Title,
                      ArrayList<String> para_ChoiceBoxOptions,
                      Node para_mainContent,
                      String para_styleClass,
                      int para_prefWidth)
    {
        txtTitle = new Text(para_Title);
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(para_ChoiceBoxOptions));
        mainContent = para_mainContent;
        BorderPane.setAlignment(mainContent, Pos.CENTER);
        
        headerHBox = new HBox(10);
        headerHBox.setAlignment(Pos.CENTER);
        headerHBox.getChildren().addAll(txtTitle,choiceBox);
        
        
        
        frameBox = new VBox(10);
        frameBox.setPadding(new Insets(5));
        frameBox.setAlignment(Pos.CENTER);
        frameBox.getChildren().addAll(headerHBox,mainContent);
        
        housingPane = new Pane();
        housingPane.getChildren().add(frameBox);
        
        
        this.setCenter(housingPane);
        mainStyleClass = para_styleClass;
        this.getStyleClass().add(para_styleClass);
        
        prefWidth = para_prefWidth;
    }
    
    public void setMainContent(Node para_mainContent)
    {
        mainContent = para_mainContent;
        BorderPane.setAlignment(mainContent, Pos.CENTER);
        
        HBox tmpWrapperPane = new HBox();
        tmpWrapperPane.setAlignment(Pos.CENTER);
        tmpWrapperPane.getChildren().add(mainContent);
        
        frameBox.getChildren().clear();
        frameBox.getChildren().addAll(headerHBox,tmpWrapperPane);
        
        
        housingPane.getChildren().clear();
        housingPane.getChildren().add(frameBox);
        
        
        frameBox.setPrefWidth(prefWidth);
        Pane castPane = (Pane) mainContent;
        castPane.setPrefWidth(prefWidth);
        
        this.setCenter(housingPane);
    }
    
    public void disableMPane()
    {
        this.getStyleClass().add("moduleDisabled");
        choiceBox.getSelectionModel().select(0);
        setMainContent(new Pane());
        
        this.setDisable(true);
    }
    
    public void enableMPane()
    {
        this.getStyleClass().clear();
        this.getStyleClass().add(mainStyleClass);
        
        this.setDisable(false);
    }
    
    public void setContentWidth(int para_width)
    {
        prefWidth = para_width;
        
        frameBox.setPrefWidth(para_width);
    }
   
    public void setChoiceBoxOptions(ArrayList<String> para_cmboxOptions)
    {
        choiceBox.setItems(FXCollections.observableArrayList(para_cmboxOptions));
        choiceBox.getSelectionModel().select(0);
    }
}
