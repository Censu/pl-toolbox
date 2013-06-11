
package plt.gui.customcomponents;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Owner
 */
public class ContentNActionPane extends BorderPane
{
    Node mainContent;
    VBox btnPane;
    
    HashMap<String,Button> btnMap;
    
    public ContentNActionPane(Node para_mContent,
                              HashMap<String,Button> para_sideButtons)
    {
        // Assign main content.
        mainContent = para_mContent;
        BorderPane.setAlignment(mainContent, Pos.CENTER);
        
        // Assign the first set of buttons to the button pane.
        btnPane = new VBox(3);
        btnPane.setPrefWidth(30);
        //btnPane.setPrefHeight(40 * para_sideButtons.size());
        btnPane.setPrefHeight(100);
        
        btnMap = new HashMap<String,Button>();
        
        for(Map.Entry<String,Button> pair : para_sideButtons.entrySet())
        {
            btnMap.put(pair.getKey(), pair.getValue());
            
            btnPane.getChildren().add( pair.getValue() );
        }
        
        
        this.setCenter(mainContent);
        this.setRight(btnPane);
    }
    
    public void setMainContent(Node para_MContent)
    {
        mainContent = para_MContent;
        BorderPane.setAlignment(mainContent, Pos.CENTER);
        this.setCenter(mainContent);
    }
    
    public void addNwButton(String para_ButtonName, Button para_Button)
    {
        // Prevent button duplication.
        if(! btnMap.containsKey(para_ButtonName))
        {
            para_Button.setStyle("-fx-background-color: transparent");
            btnMap.put(para_ButtonName, para_Button);
            btnPane.getChildren().add( para_Button );
            
            //btnPane.setPrefHeight(40 * btnMap.size());
            btnPane.setPrefHeight(100);
        }
    }
    
    public void removeButton(String para_ButtonName)
    {
        if(btnMap.containsKey(para_ButtonName))
        {
            Button reqBtnToDelete = btnMap.get(para_ButtonName);
            btnPane.getChildren().remove(reqBtnToDelete);
            btnMap.remove(para_ButtonName);
            
            btnPane.setPrefHeight(40 * btnMap.size());
        }
    }
    
    
    // Eg: "my_css_classname" NOT ".my_css_classname"
    public void setStyleClassForAllComponents(String para_StyleClass)
    {
        this.getStyleClass().clear();
        this.getStyleClass().add(para_StyleClass);
        
       // mainContent.getStyleClass().clear();
       // mainContent.getStyleClass().add(para_StyleClass);
        
        //btnPane.getStyleClass().clear();
        //btnPane.getStyleClass().add(para_StyleClass);
        btnPane.setStyle("-fx-background-color: transparent");
        
        for(int i=0; i<btnPane.getChildren().size(); i++)
        {
            Node tmpChild = btnPane.getChildren().get(i);
            tmpChild.setStyle("-fx-background-color: transparent"); //("-fx-background-color: linear-gradient(#A8A8A8, #787878);");
            
            //tmpChild.getStyleClass().clear();
            //tmpChild.getStyleClass().add(para_StyleClass);
        }
    }
    
    public void setButtonAlignment(Pos para_btnAlignment)
    {
        btnPane.alignmentProperty().set(para_btnAlignment);
    }
}
