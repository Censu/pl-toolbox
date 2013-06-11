/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui.customcomponents;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author user
 */
public class ExecutionModalPopup {

    public ExecutionModalPopup() {
    }

    
    public void show(final Parent parent,
                     Node mainContent,
                     Node botContent,
                     Button btnClose,
                     final EventHandler btnCloseHandler,
                     int height, int width) {

        final Stage s = new Stage(StageStyle.TRANSPARENT);
        s.initModality(Modality.APPLICATION_MODAL);

        BorderPane border = new BorderPane();

        Font headerFont = Font.font("BirchStd", FontWeight.BOLD, 20);
        Label lblHeader = new Label("Executing...");
        lblHeader.setFont(headerFont);      

        
        
        
        
        lblHeader.setStyle( "-fx-background-insets: 12; "
                                 + "-fx-padding: 20;"
                                 + "-fx-spacing: 10;"
                                 + "-fx-alignment:center;");
        
        botContent.setStyle("-fx-background-color: #336699; "
                                 + "-fx-background-insets: 12; "
                                 + "-fx-background-radius: 6;"
                                 + "-fx-padding: 20;"
                                 + "-fx-spacing: 10;"
                                 + "-fx-alignment:center;");

        border.setStyle("-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4); "
                + "-fx-background-color: white; "
                + "-fx-background-insets: 12; "
                + "-fx-background-radius: 6;");

        border.setTop(lblHeader);
        border.setCenter(mainContent);
        border.setBottom(botContent);

        s.setScene(new Scene(border, width, height));
        s.getScene().setFill(Color.TRANSPARENT);
        s.getScene().getRoot().setOpacity(0.9);
        s.show();
        GaussianBlur effect = new GaussianBlur();
        effect.setRadius(7);
        parent.setEffect(effect);
        btnClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                s.close();
                parent.setEffect(null);
                if (btnCloseHandler != null) {
                    btnCloseHandler.handle(t);
                }
            }
        });
    }
}
