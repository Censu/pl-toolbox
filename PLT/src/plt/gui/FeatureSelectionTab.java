/*                   GNU LESSER GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.


  This version of the GNU Lesser General Public License incorporates
the terms and conditions of version 3 of the GNU General Public
License, supplemented by the additional permissions listed below.

  0. Additional Definitions.

  As used herein, "this License" refers to version 3 of the GNU Lesser
General Public License, and the "GNU GPL" refers to version 3 of the GNU
General Public License.

  "The Library" refers to a covered work governed by this License,
other than an Application or a Combined Work as defined below.

  An "Application" is any work that makes use of an interface provided
by the Library, but which is not otherwise based on the Library.
Defining a subclass of a class defined by the Library is deemed a mode
of using an interface provided by the Library.

  A "Combined Work" is a work produced by combining or linking an
Application with the Library.  The particular version of the Library
with which the Combined Work was made is also called the "Linked
Version".

  The "Minimal Corresponding Source" for a Combined Work means the
Corresponding Source for the Combined Work, excluding any source code
for portions of the Combined Work that, considered in isolation, are
based on the Application, and not on the Linked Version.

  The "Corresponding Application Code" for a Combined Work means the
object code and/or source code for the Application, including any data
and utility programs needed for reproducing the Combined Work from the
Application, but excluding the System Libraries of the Combined Work.

  1. Exception to Section 3 of the GNU GPL.

  You may convey a covered work under sections 3 and 4 of this License
without being bound by section 3 of the GNU GPL.

  2. Conveying Modified Versions.

  If you modify a copy of the Library, and, in your modifications, a
facility refers to a function or data to be supplied by an Application
that uses the facility (other than as an argument passed when the
facility is invoked), then you may convey a copy of the modified
version:

   a) under this License, provided that you make a good faith effort to
   ensure that, in the event an Application does not supply the
   function or data, the facility still operates, and performs
   whatever part of its purpose remains meaningful, or

   b) under the GNU GPL, with none of the additional permissions of
   this License applicable to that copy.

  3. Object Code Incorporating Material from Library Header Files.

  The object code form of an Application may incorporate material from
a header file that is part of the Library.  You may convey such object
code under terms of your choice, provided that, if the incorporated
material is not limited to numerical parameters, data structure
layouts and accessors, or small macros, inline functions and templates
(ten or fewer lines in length), you do both of the following:

   a) Give prominent notice with each copy of the object code that the
   Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the object code with a copy of the GNU GPL and this license
   document.

  4. Combined Works.

  You may convey a Combined Work under terms of your choice that,
taken together, effectively do not restrict modification of the
portions of the Library contained in the Combined Work and reverse
engineering for debugging such modifications, if you also do each of
the following:

   a) Give prominent notice with each copy of the Combined Work that
   the Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the Combined Work with a copy of the GNU GPL and this license
   document.

   c) For a Combined Work that displays copyright notices during
   execution, include the copyright notice for the Library among
   these notices, as well as a reference directing the user to the
   copies of the GNU GPL and this license document.

   d) Do one of the following:

       0) Convey the Minimal Corresponding Source under the terms of this
       License, and the Corresponding Application Code in a form
       suitable for, and under terms that permit, the user to
       recombine or relink the Application with a modified version of
       the Linked Version to produce a modified Combined Work, in the
       manner specified by section 6 of the GNU GPL for conveying
       Corresponding Source.

       1) Use a suitable shared library mechanism for linking with the
       Library.  A suitable mechanism is one that (a) uses at run time
       a copy of the Library already present on the user's computer
       system, and (b) will operate properly with a modified version
       of the Library that is interface-compatible with the Linked
       Version.

   e) Provide Installation Information, but only if you would otherwise
   be required to provide such information under section 6 of the
   GNU GPL, and only to the extent that such information is
   necessary to install and execute a modified version of the
   Combined Work produced by recombining or relinking the
   Application with a modified version of the Linked Version. (If
   you use option 4d0, the Installation Information must accompany
   the Minimal Corresponding Source and Corresponding Application
   Code. If you use option 4d1, you must provide the Installation
   Information in the manner specified by section 6 of the GNU GPL
   for conveying Corresponding Source.)

  5. Combined Libraries.

  You may place library facilities that are a work based on the
Library side by side in a single library together with other library
facilities that are not Applications and are not covered by this
License, and convey such a combined library under terms of your
choice, if you do both of the following:

   a) Accompany the combined library with a copy of the same work based
   on the Library, uncombined with any other library facilities,
   conveyed under the terms of this License.

   b) Give prominent notice with the combined library that part of it
   is a work based on the Library, and explaining where to find the
   accompanying uncombined form of the same work.

  6. Revised Versions of the GNU Lesser General Public License.

  The Free Software Foundation may publish revised and/or new versions
of the GNU Lesser General Public License from time to time. Such new
versions will be similar in spirit to the present version, but may
differ in detail to address new problems or concerns.

  Each version is given a distinguishing version number. If the
Library as you received it specifies that a certain numbered version
of the GNU Lesser General Public License "or any later version"
applies to it, you have the option of following the terms and
conditions either of that published version or of any later version
published by the Free Software Foundation. If the Library as you
received it does not specify a version number of the GNU Lesser
General Public License, you may choose any version of the GNU Lesser
General Public License ever published by the Free Software Foundation.

  If the Library as you received it specifies that a proxy can decide
whether future versions of the GNU Lesser General Public License shall
apply, that proxy's public statement of acceptance of any version is
permanent authorization for you to choose that version for the
Library.*/

package plt.gui;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import plt.featureselection.examples.NBest;
import plt.featureselection.examples.SFS;
import plt.gui.configurators.NBestConfigurator;
import plt.gui.component.AdvanceTextField;
import plt.gui.configurators.PLBackPropagationConfigurator;
import plt.gui.configurators.PLNeuroEvolutionConfigurator;
import plt.gui.configurators.PLRankSvmConfigurator;
import plt.gui.customcomponents.ModulePane;
import plt.plalgorithm.PLAlgorithm;
import plt.plalgorithm.backpropagation.PLBackPropagation;
import plt.plalgorithm.neruoevolution.PLNeuroEvolution;
import plt.plalgorithm.svm.libsvm_plt.PLRankSvm;

/**
 * Original Pre PLT v1.0
 * @author Luca Querella <lucq@itu.dk>
 * 
 * Modified for PLT v1.0
 * @author Vincent E. Farrugia <vincent.e.farrugia@gmail.com>
 */
public class FeatureSelectionTab extends Tab {

    final private Stage stage;
    private Experiment experiment;
    private TitledPane[] featureSelectionConfigurations;
    private TitledPane[] algorithimConfigurations;

    VBox tmpVBox;
    VBox moduleHBox;

    public FeatureSelectionTab(Stage s, Experiment e) {
        super();
        this.experiment = e;
        this.stage = s;
        this.featureSelectionConfigurations = new TitledPane[0];
        this.algorithimConfigurations  = new TitledPane[0];
        setup();
    }

    private void setup()
    {
        final BorderPane bp = new BorderPane();
        
        final FeatureSelectionTab self = this;
        
        
        Font tabTitleFont = Font.font("BirchStd", FontWeight.BOLD, 50);
        Label lblTabHeader = new Label("Feature Selection");
        lblTabHeader.setFont(tabTitleFont);
        
        
        
        final Pane nestedBp = new Pane();
        nestedBp.setPrefHeight(400);
        nestedBp.setPrefWidth(650);
        
        final ModulePane featureSelection = new ModulePane("Feature Selection", new ArrayList<String>(Arrays.asList("None", "nBest", "SFS")),new Pane(),"modulePane3",850);
        final ModulePane algorithmMPane = new ModulePane("Algorithm", new ArrayList<String>(Arrays.asList("None","Evolving NN","Back propagation","Ranking SVM")), new Pane(), "modulePane1",850);
        final ModulePane validatorMPane = new ModulePane("Cross Validation", new ArrayList<String>(Arrays.asList("None", "K-Fold")),new Pane(),"modulePane2",850);
        
                
        algorithmMPane.disableMPane();
        validatorMPane.disableMPane();
        
        Pane tmpParentPane1 = new Pane();
        tmpParentPane1.getChildren().add(featureSelection);
        Pane tmpParentPane2 = new Pane();
        tmpParentPane2.getChildren().add(algorithmMPane);
        Pane tmpParentPane3 = new Pane();
        tmpParentPane3.getChildren().add(validatorMPane);
        
        moduleHBox = new VBox(30);
        moduleHBox.getChildren().add(tmpParentPane1);
        moduleHBox.getChildren().add(tmpParentPane2);
        moduleHBox.getChildren().add(tmpParentPane3);
        
        nestedBp.getChildren().add(moduleHBox); 
        
        
        
        
        
        
        final ScrollPane sPane = new ScrollPane();  
        sPane.setStyle("-fx-background-color: transparent;"); // Hide the scrollpane gray border.
        sPane.setPrefSize(880,600);
        sPane.setContent(moduleHBox);    
        
        stage.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1){
                
                sPane.setPrefHeight(t1.doubleValue() * 0.7);
            }
            
        });
        
        tmpVBox = new VBox(10);
        //tmpVBox.setPrefSize(855,700);
        tmpVBox.getChildren().addAll(lblTabHeader,sPane);
        
        
        bp.setCenter(new Group(tmpVBox));
        this.setContent(bp);
        
        
        
        // Cross-Validation UI.
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        Label lblCrossValidationHeader = new Label("K-Fold Cross Validation");
        lblCrossValidationHeader.setFont(headerFont);
        BorderPane.setAlignment(lblCrossValidationHeader, Pos.CENTER);
        
        Label kLabel = new Label("K:");
        TextField k  = new AdvanceTextField("[0-9]", "3");
        this.experiment.kProperty().bind(k.textProperty());

        k.setPrefWidth(30);
        kLabel.visibleProperty().bind(validatorMPane.choiceBox.getSelectionModel().selectedIndexProperty().isEqualTo(1));
        k.visibleProperty().bind(kLabel.visibleProperty());
        
        GridPane validatorContentGPane = new GridPane();
        validatorContentGPane.setPadding(new Insets(20));
        validatorContentGPane.setHgap(15);
        validatorContentGPane.setVgap(12);
        
        
        validatorContentGPane.add(kLabel, 0, 0);
        validatorContentGPane.add(k, 1, 0);
        
        
        
        
        
        HBox cntentBx = new HBox(20);
        cntentBx.getChildren().addAll(kLabel,k);
        
        
        final BorderPane kBPane = new BorderPane();
        kBPane.getStyleClass().add("modulePane2Child");
        kBPane.setLeft(lblCrossValidationHeader);
        kBPane.setRight(cntentBx);
        
        
        this.experiment.algorithmForFeatureSelectionProperty().set(null);
        
        this.experiment.useValidatorProperty().bind(validatorMPane.choiceBox.getSelectionModel().selectedIndexProperty().isEqualTo(1));
        
        
        featureSelection.choiceBox.valueProperty().addListener(new ChangeListener() 
        {
            HBox featureSelMPane_contentBox;
            
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                
                self.featureSelectionConfigurations = new TitledPane[0];

                                        
                int i =  featureSelection.choiceBox.getSelectionModel().getSelectedIndex();
                switch (i) {
                    case 0:  
                        self.experiment.featureSelectionProperty().set(null);
                        featureSelection.setMainContent(new Pane());
                    break;
                    
                    case 1:  
                        NBestConfigurator conf = new NBestConfigurator();
                        self.experiment.featureSelectionProperty().set(new NBest(conf));
                        self.featureSelectionConfigurations = conf.ui();
                        
                        
                        
                        featureSelection.setMainContent(featureSelectionConfigurations[0].getContent());
                        
                        self.experiment.algorithmForFeatureSelectionProperty().set(null);
                        
                    break;
                        
                    case 2:  
                        self.experiment.featureSelectionProperty().set(new SFS());
                        featureSelection.setMainContent(new Pane());
                        
                        self.experiment.algorithmForFeatureSelectionProperty().set(null);
                    break;

                    default: 
                        self.experiment.featureSelectionProperty().set(null);
                        featureSelection.setMainContent(new Pane());
                    break;
                }
                
                
                if(i == 0)
                {
                    algorithmMPane.disableMPane();
                    validatorMPane.disableMPane();
                    
                    algorithmMPane.setChoiceBoxOptions(new ArrayList<String>(Arrays.asList("None","Evolving NN","Back propagation","Ranking SVM")));
                }
                else
                {
                    algorithmMPane.enableMPane();
                    validatorMPane.enableMPane();
                }
                
            }
            
            
        });
        
        
        algorithmMPane.choiceBox.valueProperty().addListener(new ChangeListener() {
            
            HBox algorithmMPane_contentBox;
            
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                
                if(featureSelection.choiceBox.getSelectionModel().getSelectedIndex() == 0)
                {
                    
                }
                else
                {
                    if((featureSelection.choiceBox.getSelectionModel().getSelectedIndex() != 0)
                    &&(algorithmMPane.choiceBox.getItems().size() != 3))
                    {
                        algorithmMPane.setChoiceBoxOptions(new ArrayList<String>(Arrays.asList("Evolving NN","Back propagation","Ranking SVM")));
                    }
                    
                    
                    int i =  algorithmMPane.choiceBox.getSelectionModel().getSelectedIndex();
                    
                    if(i == -1)
                    {
                        String txtChoice = (String) t;
                        if(txtChoice.equals("Evolving NN")) { i = 0; }
                        else if(txtChoice.equals("Back propagation")) { i = 1; }
                        else if(txtChoice.equals("Ranking SVM")) { i = 2; }
                    }
                    
                    switch (i) {
                        case 0:  
                            PLNeuroEvolutionConfigurator conf = new PLNeuroEvolutionConfigurator();
                            self.algorithimConfigurations  = conf.ui();



                            algorithmMPane_contentBox = new HBox(5);
                            for(int counter=0; counter<self.algorithimConfigurations.length; counter++)
                            {
                                Node tmpContentNode = algorithimConfigurations[counter].getContent();
                                algorithmMPane_contentBox.getChildren().add(tmpContentNode);
                            }


                            algorithmMPane.setMainContent(algorithmMPane_contentBox);
                            //algorithmMPane.autosize();

                            PLAlgorithm algo = new PLNeuroEvolution(null, conf);
                            self.experiment.algorithmForFeatureSelectionProperty().set(algo);                        
                        break;     

                        case 1:  
                            PLBackPropagationConfigurator conf2 = new PLBackPropagationConfigurator() {};
                            self.algorithimConfigurations = conf2.ui();


                            algorithmMPane_contentBox = new HBox(5);
                            for(int counter=0; counter<self.algorithimConfigurations.length; counter++)
                            {
                                Node tmpContentNode = algorithimConfigurations[counter].getContent();
                                algorithmMPane_contentBox.getChildren().add(tmpContentNode);
                            }


                            algorithmMPane.setMainContent(algorithmMPane_contentBox);


                            PLBackPropagation algo2 = new PLBackPropagation(null, conf2);
                            self.experiment.algorithmForFeatureSelectionProperty().set(algo2);                        
                        break;
                            
                        case 2:
                            PLRankSvmConfigurator conf3 = new PLRankSvmConfigurator();
                            self.algorithimConfigurations = conf3.ui();
                            
                            algorithmMPane_contentBox = new HBox(5);
                            for(int counter=0; counter<self.algorithimConfigurations.length; counter++)
                            {
                                Node tmpContentNode = algorithimConfigurations[counter].getContent();
                                algorithmMPane_contentBox.getChildren().add(tmpContentNode);
                            } 

                            algorithmMPane.setMainContent(algorithmMPane_contentBox);
                            
                            PLRankSvm algo3 = new PLRankSvm(null,conf3);
                            self.experiment.algorithmForFeatureSelectionProperty().set(algo3);
                        break;
                    }
                }
                
                
            }
        });
        
        
        validatorMPane.choiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                int i =  validatorMPane.choiceBox.getSelectionModel().getSelectedIndex();
                
                switch (i) {
                    case 0:  
                        validatorMPane.setMainContent(new Pane());
                    break;     
                        
                    case 1:  
                        validatorMPane.setMainContent(kBPane);
                    break;
                        
                    
                }
                
                
            }
        });
        
        
        featureSelection.choiceBox.getSelectionModel().select(0);
        algorithmMPane.choiceBox.getSelectionModel().select(0);
        validatorMPane.choiceBox.getSelectionModel().select(0);
    }
    
    
}