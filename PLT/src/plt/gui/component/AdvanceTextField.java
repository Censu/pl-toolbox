/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui.component;

import javafx.scene.control.TextField;


/**
 *
 * @author luca
 */
public class AdvanceTextField extends TextField {
    String regularExpression;

    public AdvanceTextField(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public AdvanceTextField(String regularExpression, String string) {
        super(string);
        this.regularExpression = regularExpression;
    } 
    
    @Override 
    public void replaceText(int start, int end, String text) {
        // If the replaced text would end up being invalid, then simply
        // ignore this call!
        if (text.matches(this.regularExpression) || text.isEmpty()) {
            super.replaceText(start, end, text);
        }
    }

    @Override 
    public void replaceSelection(String text) {
        if (text.matches(this.regularExpression) || text.isEmpty()) {
            super.replaceSelection(text);
        }
    }   
}
