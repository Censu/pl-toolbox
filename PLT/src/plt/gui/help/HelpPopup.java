/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui.help;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import plt.gui.component.ModalPopup;

/**
 *
 * @author owner
 */
public class HelpPopup extends ModalPopup 
{
    String helpText_html;
    
    public HelpPopup(String para_helpTextHTML)
    {
        helpText_html = para_helpTextHTML;
    }
    
    public void show(Parent parent, final EventHandler eventHandler)
    {

        WebView w = new WebView();
        w.setMaxSize(450 , 400);

        WebEngine e = w.getEngine();
        e.loadContent(helpText_html);
        super.show(w, parent,null,new Button("close"), 500,500,false);  
    }
}