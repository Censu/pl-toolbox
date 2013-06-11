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
public class Tab2Help extends ModalPopup {
        public void show(Parent parent, final EventHandler eventHandler) {
            
            WebView w = new WebView();
            w.setMaxSize(450 , 400);
           
            WebEngine e = w.getEngine();
            e.loadContent("<h1>Preference Learning Toolbox</h1>\n" +
"<h2>Preprocessing data</h2>\n" +
"Before training the models, this screen allows the user to preprocess every and each of the features in the loaded dataset.\n" +
"<ul>\n" +
"	<li>Check-boxes: features can be removed from the dataset by unchecking the tick boxes in the left-most part of each line.</li>\n" +
"	<li>Preprocessing buttons: this functionality includes a set of transformations that can be applied to a single feature (using the button in each row) or to all of them (using the button in the header). The following transformations are available: <ul>\n" +
"		<li>Binary transformation: given a nominal or ordinal feature, this transformation creates a binary encoding for each of the values of that feature. This encoding consists of a boolean feature for each distinct value; for every value, only the corresponding feature is true (1) while the others are false (0).</li>\n" +
"		<li>Min-max transformation: the values for the selected features are transposed to fit a given value range (by default 0 and 1).</li>\n" +
"		<li>Z-score transformation: the values of the selected feature are transformed so that the average value of the feature is zero and the standard deviation one.</li>\n" +
"	</ul></li>\n" +
"	<li>Data visualization: the right-most panel shows the values of the currently selected feature after the selected preprocessing method is applied.</li>\n" +
"</ul>");
            super.show(w, parent,null,new Button("close"), 500,500,false);  
        }
}
