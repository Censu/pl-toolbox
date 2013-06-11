package plt.gui.component;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author luca
 */
public class ModalPopup {

    public ModalPopup() {
    }

    public void show(Node content,
            final Parent parent,
            final EventHandler eventHandler,
            Button button,
            boolean isDecoratedWindow) {

        show(content, parent, eventHandler, button, 300, 450, isDecoratedWindow);

    }

    public void show(Node content,
            final Parent parent,
            final EventHandler eventHandler,
            Button button, int height, int width, boolean isDecoratedWindow) {

        this.show(content, parent, eventHandler, button, null, height, width, isDecoratedWindow);

    }

    public void show(Node content,
            final Parent parent,
            final EventHandler eventHandler,
            Button button, Button button2, int height, int width, boolean isDecoratedWindow) {

        final Stage s;
        if(isDecoratedWindow)
        {
            s = new Stage(StageStyle.DECORATED);
            s.setResizable(true);
        }
        else
        {
            s = new Stage(StageStyle.TRANSPARENT);
        }
        s.initModality(Modality.APPLICATION_MODAL);
        
        BorderPane border = new BorderPane();

        if (button == null) {
            button = new Button("Submit");
        }

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);
        button.setStyle("-fx-font-size: 15pt;");

        hbButtons.getChildren().add(button);
        
        if (button2 != null) 
            hbButtons.getChildren().add(button2);
        
        hbButtons.setStyle("-fx-background-color: #336699; "
                + "-fx-background-insets: 12; "
                + "-fx-background-radius: 6;"
                + "-fx-padding: 20;"
                + "-fx-spacing: 10;"
                + "-fx-alignment:center;");

        border.setStyle("-fx-effect: dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4); "
                + "-fx-background-color: white; "
                + "-fx-background-insets: 12; "
                + "-fx-background-radius: 6;");

        border.setCenter(content);
        border.setBottom(hbButtons);

        s.setScene(new Scene(border, width, height));
        s.getScene().setFill(Color.TRANSPARENT);
        s.getScene().getRoot().setOpacity(0.9);
        s.show();
        GaussianBlur effect = new GaussianBlur();
        effect.setRadius(7);
        
        if(parent != null)
        {
            parent.setEffect(effect);
        }
        
        
        Button btnDefaultClose = button;
        if(button2 != null) { btnDefaultClose = button2; }
        else if(button != null) { btnDefaultClose = button; }
        
        btnDefaultClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                s.close();
                if(parent != null)
                {
                    parent.setEffect(null);
                }
                if (eventHandler != null) {
                    eventHandler.handle(t);
                }
            }
        });

    }
}
