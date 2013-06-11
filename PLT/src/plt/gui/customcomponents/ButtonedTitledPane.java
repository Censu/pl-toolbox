/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui.customcomponents;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import plt.gui.DataSetTab;

/**
 *
 * @author user
 */
public class ButtonedTitledPane extends TitledPane
{
    BorderPane customHeader;
    Node[] btnPanelItems;
    
    
    public ButtonedTitledPane(String para_title,
                              Node para_contentPane,
                              double para_parentAccordionWidth,
                              Node[] para_buttonPaneItems)
    {
        super("",para_contentPane);
        
        
        customHeader = new BorderPane();
        customHeader.setPrefWidth(para_parentAccordionWidth-30);
        
        
        // Create Header Title Label.
        Label lblHeaderTitle = new Label(para_title);
        lblHeaderTitle.setMaxWidth(Double.MAX_VALUE);
        
        
        // Create Header Button Pane.
        HBox buttonPane = new HBox();
        btnPanelItems = para_buttonPaneItems;
        for(int i=0; i<para_buttonPaneItems.length; i++)
        {
            Node tmpNode = para_buttonPaneItems[i];
            buttonPane.getChildren().add(tmpNode);
        }
        
        
        // Add components to header.
        BorderPane.setAlignment(lblHeaderTitle, Pos.CENTER);
        customHeader.setLeft(lblHeaderTitle);
        customHeader.setRight(buttonPane);
        
        
        this.setGraphic(customHeader);
    }
    
          
    public void setCustomTitle(String para_title)
    {
        Label lblTitle = (Label) customHeader.getLeft();
        lblTitle.setText(para_title);
        customHeader.setLeft(lblTitle);
    }
    
    public void setCustomStyleID(String para_styleID)
    {
        // Loop through the titled pane's direct children.
        ObservableList<Node> children = customHeader.getChildren();
        for(int i=0; i<children.size(); i++)
        {
            Node child = children.get(i);
            //child.setId(para_styleID);
        }
        
        // Loop through buttons.
        for(int i=0; i<btnPanelItems.length; i++)
        {
            Node btn = btnPanelItems[i];
            btn.setId(para_styleID);
        }
        
        this.lookup(".title").setId(para_styleID);
    }
    
    /*public void setHeaderStyle(String para_nwStyle)
    {
        Node graphic = this.getGraphic();
        graphic.setStyle(para_nwStyle);
        this.setGraphic(graphic);
    }*/
}
