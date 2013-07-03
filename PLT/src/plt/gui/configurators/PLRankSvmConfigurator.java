package plt.gui.configurators;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import plt.gui.component.AdvanceTextField;
import plt.plalgorithm.svm.libsvm_plt.IPLRankSvmConfigurator;

/**
 *
 * @author Owner
 */

public class PLRankSvmConfigurator implements IPLRankSvmConfigurator
{
               
    private ChoiceBox cbKernelType;
    private TextField txtGamma;
    private TextField txtDegree;
    
    private Label lblGamma;
    private Label lblDegree;
    

    public PLRankSvmConfigurator()
    {
        ObservableList<String> availableKernelTypes = FXCollections.observableArrayList();
        availableKernelTypes.addAll(new String[] {"Linear","Poly","RBF"});
        cbKernelType = new ChoiceBox<>(availableKernelTypes);
        cbKernelType.valueProperty().addListener(new KernelChangeListener());
        
        txtGamma = new AdvanceTextField("[0-9.]","0");
        txtDegree = new AdvanceTextField("[0-9.]","3");
        
        
        float inputColWidth = 200;
        cbKernelType.setPrefWidth(inputColWidth);
        txtGamma.setPrefWidth(inputColWidth);
        txtDegree.setPrefWidth(inputColWidth);
    }
    
  
    public TitledPane[] ui()
    {        
        
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
                
        Label lblRankSvmSectionHeader = new Label("Rank SVM");
        Label lblKernelSelection = new Label("Kernel");
        lblGamma = new Label("Gamma:");
        lblDegree = new Label("Degree:");
        
        lblRankSvmSectionHeader.setFont(headerFont);
               
        
        
        GridPane innerGrid = new GridPane();
        innerGrid.setAlignment(Pos.CENTER);
        innerGrid.setPadding(new Insets(20));
        innerGrid.setHgap(10);
        innerGrid.setVgap(12);
        
        innerGrid.add(lblKernelSelection, 0, 0);
        innerGrid.add(cbKernelType, 1, 0);
        innerGrid.add(lblGamma,0,1);
        innerGrid.add(txtGamma,1,1);
        innerGrid.add(lblDegree,0,2);
        innerGrid.add(txtDegree,1,2);
        
        
        BorderPane svmPane = new BorderPane();
        BorderPane.setAlignment(lblRankSvmSectionHeader, Pos.CENTER);
        BorderPane.setAlignment(innerGrid, Pos.CENTER);
        svmPane.setTop(lblRankSvmSectionHeader);
        svmPane.setCenter(innerGrid);
        svmPane.setPrefWidth(960);
        
        svmPane.getStyleClass().add("modulePane1Child");
        
        
        cbKernelType.getSelectionModel().select(0);
        
        return new TitledPane[]{new TitledPane("Rank SVM", svmPane)};
    }
    
    @Override
    public String getKernelType()
    {
        return (String) cbKernelType.getSelectionModel().getSelectedItem();
    }
    
    @Override
    public double getGamma()
    {
        return parseDobuleOrFailWithZero(txtGamma);
    }
    
    @Override
    public double getDegree()
    {
        return parseDobuleOrFailWithZero(txtDegree);
    }
    
    public boolean gammaRequired()
    {
        return txtGamma.isVisible();
    }
    
    public boolean degreeRequired()
    {
        return txtDegree.isVisible();
    }
    
    private static int parseIntegerOrFailWithZero(TextField t)
    {
        try
        {
            return Integer.parseInt(t.getText());
        }
        catch (NumberFormatException e)
        {
            return 0;
        } 
    }
    
    private static double parseDobuleOrFailWithZero(TextField t)
    {
        try
        {
            return Double.parseDouble(t.getText());
        } 
        catch (NumberFormatException e)
        {
            return 0;
        } 
    }

    
    class KernelChangeListener implements ChangeListener
    {
        
        
        @Override
        public void changed(ObservableValue ov, Object t, Object t1)
        {

            
            int i =  cbKernelType.getSelectionModel().getSelectedIndex();
            switch (i)
            {
                // Linear Kernel.
                case 0:  lblGamma.setVisible(false);
                         txtGamma.setVisible(false);
                         lblDegree.setVisible(false);
                         txtDegree.setVisible(false);
                         break;

                // Poly Kernel.
                case 1:  lblGamma.setVisible(true);
                         txtGamma.setVisible(true);
                         lblDegree.setVisible(true);
                         txtDegree.setVisible(true);
                         break;

                // RBF Kernel.
                case 2:  lblGamma.setVisible(true);
                         txtGamma.setVisible(true);
                         lblDegree.setVisible(false);
                         txtDegree.setVisible(false);
                         break;                   
            }


        }
            
         
    }
    

}