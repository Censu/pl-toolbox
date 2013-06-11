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
public class Tab2HelpOld extends ModalPopup {
        public void show(Parent parent, final EventHandler eventHandler) {
            
            WebView w = new WebView();
            w.setMaxSize(450 , 400);
           
            WebEngine e = w.getEngine();
            e.loadContent("<div><p><span style=\"font-family: 'Helvetica Neue'; \"><br /></span></p><p><span style=\"font-family: 'Helvetica Neue'; \"><strong>[Preprocess data]</strong></span></p><p><span style=\"font-family: 'Helvetica Neue'; \"><u>*Options:</u></span></p><p><span style=\"font-family: 'Helvetica Neue'; \">select feature: checked or unchecked</span></p><p><span style=\"font-family: 'Helvetica Neue'; \">feature processing: No operation (Default), Nominal, Min max, binary, z transform</span></p><p><span style=\"font-family: 'Helvetica Neue'; \"><u><br /></u></span></p><p><span style=\"font-family: 'Helvetica Neue'; \"><u>*Description</u></span></p><p><span style=\"font-family: 'Helvetica Neue'; \">Use this option to manually select and pre-process features.</span></p><p><span style=\"font-family: 'Helvetica Neue'; \">The following ppreprocessing operations  are available for ordinal and nominal features:</span></p><p><span style=\"font-family: 'Helvetica Neue'; \">   - Nominal:  the possible feature values are transformed into integer consecutive positive integer values; </span><em style=\"font-family: 'Helvetica Neue'; \">{\"A\", \"B\", \"A\", \"C\"}</em><span style=\"font-family: 'Helvetica Neue'; \"> will be transform to </span><em style=\"font-family: 'Helvetica Neue'; \">{0,1,0,2} </em></p><p><span style=\"font-family: 'Helvetica Neue'; \">   - Binary representation: a new binary feature is created for every possible value of the transformed feature; </span><em style=\"font-family: 'Helvetica Neue'; \">{\"A\", \"B\", \"A\", \"C\"}</em><span style=\"font-family: 'Helvetica Neue'; \"> will be transformed in three different features </span><em style=\"font-family: 'Helvetica Neue'; \">{1,0,1,0} {0,1,0,0} </em><span style=\"font-family: 'Helvetica Neue'; \">and</span><em style=\"font-family: 'Helvetica Neue'; \"> {0,0,0,1}</em></p><p><span style=\"font-family: 'Helvetica Neue'; \"><br /></span></p><p><span style=\"font-family: 'Helvetica Neue'; \">The following preprocessing operation are available for numeric features: </span></p><p><span style=\"font-family: 'Helvetica Neue'; \">   - Default: no transformation is applied</span></p><p><span style=\"font-family: 'Helvetica Neue'; \">   - Min-Max: the following formula will be applied to all the values: (value-min)/(max-min)</span></p><p><span style=\"font-family: 'Helvetica Neue'; \">   - Binary representation: a new binary feature is created for every possible value of the transformed feature; </span><em style=\"font-family: 'Helvetica Neue'; \">{0,2,4,2}</em><span style=\"font-family: 'Helvetica Neue'; \"> will be transformed in three different features </span><em style=\"font-family: 'Helvetica Neue'; \">{1,0,0,0}, {0,1,0,1} </em><span style=\"font-family: 'Helvetica Neue'; \">and</span><em style=\"font-family: 'Helvetica Neue'; \"> {0,0,1,0}.</em></p><p><span style=\"font-family: 'Helvetica Neue'; \"><em>   - </em>Z-Score Normalization: Z-Score normalization will be applied to all the values</span></p><p><span style=\"font-family: 'Helvetica Neue'; \"><br /></span></p><p><span style=\"font-family: 'Helvetica Neue'; \"><strong>[Data preview]</strong></span></p><p><span style=\"font-family: 'Helvetica Neue'; \"><u>*Description</u></span></p><p><span style=\"font-family: 'Helvetica Neue'; \">On the right side of the tap a pagined-preview of the dataset is shown, as it will be used in the algorithm, so with all the pre-processing operations already applied.</span></p></div>");
            super.show(w, parent,null,new Button("close"), 500,500,false);  
        }
}
