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
 * @author luca
 */
public class Tab1Help extends ModalPopup {
        public void show(Parent parent, final EventHandler eventHandler) {
            
            WebView w = new WebView();
            w.setMaxSize(450 , 400);
           
            WebEngine e = w.getEngine();
            e.loadContent("<h1>Preference Learning Toolbox</h1>\n" +
                          "<h2>Loading data</h2>\n" +
                          "This toolbox can train models from a dataset encoded in two files: the object file and the rank file. Two valid datasets must be loaded before continuing to the next steps.\n" +
                          "<ul>\n" +
                          "<li>The <b>object file</b> contains the features that will serve as potential inputs to the models trained. Each line in the file must contain the feature values for one data sample (object) separated by a given character (comma by default). Each object must be identified by an integer ID specified as first feature. Optionally, the first line of the file may contain a textual label for each feature (first one is expected to be 'ID').</li>\n" +
                          "<li>The <b>rank file</b> contains the known relations among the data samples in the object file. Each of these relations is specified as an order among a subset of objects and it is encoded as a list of object IDs from the most preferred/highest ranked object to the least preferred/lowest ranked. The object IDs are separated by a given character (comma by default).</li>\n" +
                          "</ul>\n" +
                          "For an example dataset, the user is referred to the <a href=http://130.226.142.6/people/hpma/PLT/data.zip>synthetic datasets</a> available online.");
            super.show(w, parent,null,new Button("close"), 500,500,false);  
        }
}
