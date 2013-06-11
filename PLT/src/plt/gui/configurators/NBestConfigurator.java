package plt.gui.configurators;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import plt.gui.component.AdvanceTextField;

/**
 *
 * @author luca
 */
public class NBestConfigurator implements plt.featureselection.examples.NBestConfigurator {
    private TextField n;
    
    private static int parseOrFailWithZero(TextField t) {
        try {
            return Integer.parseInt(t.getText());
        } catch (NumberFormatException e) {
            return 0;
        } 
    }
    
    public NBestConfigurator() {
        n = new AdvanceTextField("[0-9.]","1");
        n.setPrefWidth(30);
    }        

    @Override
    public int getN() {
         return parseOrFailWithZero(this.n);
    }
    
        
    public TitledPane[] ui() {
        
       
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(20));
        grid1.setHgap(10);
        grid1.setVgap(12);
        
        
        Label nLabel = new Label("N:");
        

        grid1.add(nLabel, 0, 0);
        grid1.add(n, 1, 0);
        
        
        
        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 15);
        
        

        
        Label lblNBestHeader = new Label("N-Best Feature Selection");
        lblNBestHeader.setFont(headerFont);
        
        
        
        
        HBox cntentBx = new HBox(20);
        cntentBx.getChildren().addAll(nLabel,n);
        
        
        
        BorderPane nBestSelPane = new BorderPane();
        nBestSelPane.setLeft(lblNBestHeader);
        nBestSelPane.setRight(cntentBx);
        
        

        nBestSelPane.getStyleClass().add("modulePane3Child");
        
        
        return new TitledPane[] {new TitledPane("nBest", nBestSelPane)};
        

    }
    
}
